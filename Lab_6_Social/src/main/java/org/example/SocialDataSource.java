package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.Exceptions.FileNotFoundException;
import org.example.Exceptions.ParsingException;
import org.example.Exceptions.UserNotFindException;
import org.example.Mappers.MessageMapper;
import org.example.Models.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class SocialDataSource
{
    private List<User> users;
    private List<FriendsRequest> friendsRequests;
    private List<Message> messages;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public SocialDataSource(String pathUsers, String pathFriends, String pathMessages) throws IOException
    {
        objectMapper.registerModule(new JavaTimeModule());

        getUsers(pathUsers);
        getFriendsRequests(pathFriends);
        getMessages(pathMessages);
    }

    public UserContext getUserContext(String userName)
    {
        UserContext userContext = new UserContext();

        User user = getUser(userName);

        userContext.setUser(user);
        userContext.setFriends(getFriends(user));
        userContext.setOnlineFriends(getOnlineFriends(userContext));
        userContext.setSubscribers(getSubscribers(userContext));
        userContext.setFriendshipOffers(getFriendshipOffers(userContext));
        userContext.setNews(getNews(userContext));

        return userContext;
    }

    private List<News> getNews(UserContext userContext)
    {
        return  messages.stream()
                .filter(                                                                                 // Только сообщения друзей
                        ms -> userContext.getFriends().stream()
                                .map(UserInformation::userId).toList().contains(ms.authorId()))
                .filter(ms -> ms.sendDate().isAfter(userContext.getUser().lastVisit().toLocalDateTime())) // После последнего входа в сеть
                .map(ms ->
                        new News(
                                ms.authorId(),
                                userContext.getFriends().stream().filter(el -> el.userId() == ms.authorId()).findFirst().get().name(),
                                ms.likes(),
                                ms.text()
                ))
                .toList();
    }

    private List<UserInformation> getFriendshipOffers(UserContext userContext)
    {
        List<Integer> ids = friendsRequests.stream()
                .filter(el -> el.toUserId() == userContext.getUser().userId()) // Заявка направлена нам
                .filter(el -> el.status() <= 0)                                // Нужный статус
                .filter(el -> el.sendDate().isAfter(userContext.getUser().lastVisit())) // Нужное время
                .map(FriendsRequest::fromUserId)
                .toList();

        return users.stream().filter(el -> ids.contains(el.userId()))
                .map(el -> new UserInformation(el.name(), el.online(), el.userId())).toList();
    }

    private List<UserInformation> getSubscribers(UserContext userContext)
    {
        List<Integer> ids = friendsRequests.stream()
                .filter(el -> el.toUserId() == userContext.getUser().userId()) // Заявка пришла нам
                .filter(el -> el.status() <= 1)                                 // Нужный статус
                .filter(el -> !userContext.getFriends().stream().map(UserInformation::userId).toList().contains(el))
                .map(FriendsRequest::fromUserId).toList();

        return users.stream().filter(el -> ids.contains(el.userId()))
                .map(el -> new UserInformation(el.name(), el.online(), el.userId())).toList();
    }

    private List<UserInformation> getOnlineFriends(UserContext userContext)
    {
        return userContext.getFriends().stream().filter(UserInformation::online).toList();
    }

    private List<UserInformation> getFriends(User user)
    {
        //  Тот кто отправил запрос на дружбу выбранному пользователю
        List<FriendsRequest> acceptedRequests = friendsRequests.stream()
                .filter(el -> el.status() != 3)
                .filter(el -> el.toUserId() == user.userId()).toList();

        // В чей адрес был отправлен запрос от выбранного пользователя
        List<FriendsRequest> sentRequests = friendsRequests.stream()
                .filter(el -> el.status() != 3)
                .filter(el -> el.fromUserId() == user.userId()).toList();

        // Симметричный запрос
        List<Integer> crossRequestId = new ArrayList<>();

        for(FriendsRequest ac : acceptedRequests)
        {
            for(FriendsRequest sr : sentRequests)
            {
                if(ac.fromUserId() == sr.toUserId())
                {
                    crossRequestId.add(ac.fromUserId());
                }
            }
        }

        List<Integer> ids = Stream.concat(
                Stream.concat(
                        acceptedRequests.stream().filter(el -> el.status() == 2).map(FriendsRequest::fromUserId),
                        sentRequests.stream().filter(el -> el.status() == 2).map(FriendsRequest::toUserId)),
                crossRequestId.stream()).distinct().toList();


        return users.stream().filter(el -> ids.contains(el.userId()))
                .map(el -> new UserInformation(el.name(), el.online(), el.userId())).toList();
    }

    private User getUser(String name)
    {
        for(User user : users)
        {
            if(!name.equals(user.name()))
            {
                continue;
            }

            return user;
        }

        throw new UserNotFindException("User with name: { " + name + " } is not found");
    }

    private void getMessages(String path) throws IOException, ParsingException
    {
        String messagesText = readFile(path);
        MessageMapper mapper = new MessageMapper(messagesText);

        try
        {
            messages = mapper.getListEntity();
        }
        catch (RuntimeException ex)
        {
            throw new ParsingException("Error parsing in  getMessages methode : { " + ex.getMessage() + " }");
        }
    }

    private void getFriendsRequests(String path) throws IOException, JsonProcessingException
    {
        String friendsRequestText = readFile(path);

        try
        {
            friendsRequests = objectMapper.readValue(
                    friendsRequestText, new TypeReference<List<FriendsRequest>>(){});
        }
        catch (JsonProcessingException exception)
        {
            throw new ParsingException("Error parsing in  getFriendsRequests methode");
        }
    }

    private void getUsers(String path) throws IOException
    {
        String usersText = readFile(path);
        users = objectMapper
                .readValue( usersText, new TypeReference<List<User>>(){});
    }

    private String readFile(String path) throws IOException, FileNotFoundException
    {
        InputStream inputStream =
                SocialDataSource.class
                        .getClassLoader()
                        .getResourceAsStream(path);

        if(inputStream == null)
        {
            inputStream = getStream(path);
            // throw new FileNotFoundException("File with path: " + path + " could not find");
        }

        StringBuilder stringBuilder = new StringBuilder();
        String line;

        try (InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferReader = new BufferedReader(inputStreamReader))
        {
            while((line = bufferReader.readLine()) != null)
            {
                stringBuilder.append(line);
            }
        }

        return stringBuilder.toString();
    }

    private InputStream getStream(String uri)
    {
        Path path = Paths.get(uri);

        try
        {
            return Files.newInputStream(path);
        }
        catch (IOException e)
        {
            throw new FileNotFoundException("File with path: " + path + " could not find");
        }
    }
}
