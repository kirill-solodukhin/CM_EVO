package org.example.supportive;

import org.example.entities.FriendsRequest;
import org.example.entities.Message;
import org.example.entities.User;
import org.example.models.News;
import org.example.models.UserContext;
import org.example.models.UserInformation;
import org.example.repositories.MessagesRepository;
import org.example.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Component
public class SocialDataSource
{
    @Autowired
    UsersRepository usersRepository;

    @Autowired
    MessagesRepository messagesRepository;

    public UserContext getUserContext(String userName, String lastName, String patronymic)
    {
        User user = usersRepository.findByFullName(userName, lastName, patronymic);

        UserContext userContext = new UserContext();

        userContext.setUser(user);
        userContext.setFriends(getFriends(user));
        userContext.setOnlineFriends(getOnlineFriends(userContext));
        userContext.setSubscribers(getSubscribes(userContext));
        userContext.setFriendshipOffers(getFriendshipOffers(userContext));
        userContext.setNews(getNews(userContext));

        return userContext;
    }

    private List<News> getNews(UserContext userContext)
    {
       List<Message> allMessage = messagesRepository.getAllMessage(userContext.getFriends().stream().map(UserInformation::getUserId).toList());

        return allMessage.stream()
                .filter(el -> el.getSendDate()
                        .isAfter(
                                userContext.getUser().getOnline() ? LocalDateTime.now() : userContext.getUser().getLastVisit()
                        )
                )
                .map(el ->
                    new News(
                            el.getAuthor(),
                            el.getLikes(),
                            el.getText()
                    ))
                .toList();
    }

    private List<UserInformation> getFriendshipOffers(UserContext userContext)
    {
        List<FriendsRequest> offers = usersRepository.getFriendshipOffers(userContext.getUser().getID());

        return offers.stream().map(el -> new UserInformation(
                el.getUserFrom().getFirstName(),
                el.getUserFrom().getLastName(),
                el.getUserFrom().getPatronymic(),
                el.getUserFrom().getOnline(),
                el.getUserFrom().getID())).toList();
    }

    private List<UserInformation> getSubscribes(UserContext userContext)
    {
        List<User> candidate = usersRepository.getSubscribes(userContext.getUser().getID());

        return candidate.stream()
                .filter(el -> !userContext.getFriends()
                        .stream().map(UserInformation::getUserId).toList().contains(el.getID()))
                .map(el -> new UserInformation(
                        el.getFirstName(),
                        el.getLastName(),
                        el.getPatronymic(),
                        el.getOnline(),
                        el.getID()))
                .toList();
    }

    private List<UserInformation> getOnlineFriends(UserContext userContext)
    {
        return userContext.getFriends().stream().filter(UserInformation::isOnline).toList();
    }

    private List<UserInformation> getFriends(User user)
    {
        List<UserInformation> userInformationList = new ArrayList<>();

        List<FriendsRequest> iSent_req = usersRepository.getReceivedNotRejectedRequestsById(user.getID());
        List<FriendsRequest> iAccept_req = usersRepository.getAcceptedNotRejectedRequestsById(user.getID());

        List<User> crossFriend = iSent_req.stream()
                .map(FriendsRequest::getUserTo)
                .filter(el -> iAccept_req
                        .stream()
                        .map(FriendsRequest::getUserFrom)
                        .map(User::getID)
                        .toList()
                        .contains(el.getID())
                ).toList();

        var a = iSent_req.stream().filter(el -> el.getStatus() == 2).map(FriendsRequest::getUserTo).toList();
        var b = iAccept_req.stream().filter(el -> el.getStatus() == 2).map(FriendsRequest::getUserFrom).toList();

        List<User> allFriends = Stream.concat(
                Stream.concat(
                        iSent_req.stream().filter(el -> el.getStatus() == 2).map(FriendsRequest::getUserTo),
                        iAccept_req.stream().filter(el -> el.getStatus() == 2).map(FriendsRequest::getUserFrom)
                ),
                crossFriend.stream()
        ).distinct().toList();

        return allFriends.stream().map(el -> new UserInformation(
                el.getFirstName(),
                el.getLastName(),
                el.getPatronymic(),
                el.getOnline(),
                el.getID()
        )).toList();
    }
}
