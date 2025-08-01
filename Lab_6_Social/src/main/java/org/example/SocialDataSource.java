package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.Exceptions.FileNotFoundException;
import org.example.Exceptions.ParsingException;
import org.example.Mappers.MessageMapper;
import org.example.Models.FriendsRequest;
import org.example.Models.Message;
import org.example.Models.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class SocialDataSource
{
    private List<User> users;
    private List<FriendsRequest> friendsRequests;
    private List<Message> messages;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public SocialDataSource(String pathUsers, String pathFriends, String pathMessages)
    {
        objectMapper.registerModule(new JavaTimeModule());

        getUsers(pathUsers);
        getFriendsRequests(pathFriends);
        getMessages(pathMessages);
    }

    public UserContext getUserContext(String userName)
    {
        return null;
    }

    private void getMessages(String path)
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

    private void getFriendsRequests(String path)
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

    private void getUsers(String path)
    {
        String usersText = readFile(path);

        try
        {
            users = objectMapper.readValue(
                    usersText, new TypeReference<List<User>>(){});
        }
        catch (JsonProcessingException exception)
        {
            throw new ParsingException("Error parsing in getUsers methode");
        }
    }

    private String readFile(String path)
    {
        InputStream inputStream =
                SocialDataSource.class
                        .getClassLoader()
                        .getResourceAsStream(path);

        if(inputStream == null)
        {
            throw new FileNotFoundException("File with path: " + path + " could not find");
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
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }

        return stringBuilder.toString();
    }
}
