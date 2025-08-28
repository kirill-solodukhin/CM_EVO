package org.example.generator;

import org.example.entities.FriendsRequest;
import org.example.entities.Like;
import org.example.entities.Message;
import org.example.entities.User;
import org.example.repositories.FriendsRequestsRepository;
import org.example.repositories.LikesRepository;
import org.example.repositories.MessagesRepository;
import org.example.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FillDB
{
    @Autowired UsersRepository usersRepository;
    @Autowired LikesRepository likesRepository;
    @Autowired MessagesRepository messagesRepository;
    @Autowired FriendsRequestsRepository friendsRequestsRepository;

    private static List<User> users;
    private static List<Message> messages;
    private static List<Like> likes;
    private static List<FriendsRequest> friendsRequests;

    public void fill(int userCount, int messageCount, int friendsCount)
    {
        Generator generator = new Generator(userCount, messageCount, friendsCount);

        users = generator.getUsers();
        likes = generator.getLikes();
        messages = generator.getMessages();
        friendsRequests = generator.getFriendsRequests();

        usersRepository.deleteAll();
        usersRepository.saveAll(users);

        messagesRepository.deleteAll();
        messagesRepository.saveAll(messages);

        likesRepository.deleteAll();
        likesRepository.saveAll(likes);

        friendsRequestsRepository.deleteAll();
        friendsRequestsRepository.saveAll(friendsRequests);
    }
}
