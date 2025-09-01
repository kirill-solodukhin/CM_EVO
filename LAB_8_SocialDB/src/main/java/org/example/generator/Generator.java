package org.example.generator;

import com.github.javafaker.Faker;
import lombok.Getter;
import org.example.entities.FriendsRequest;
import org.example.entities.Like;
import org.example.entities.Message;
import org.example.entities.User;
import org.example.exceptions.UsersCountException;

import java.util.List;

@Getter
public class Generator
{
    private final Faker faker;

    private final GenerateUser generateUser;
    private final GenerateLikes generateLikes;
    private final GenerateMessage generateMessages;
    private final GenerateFriendsRequests generateFriendsRequests;

    private List<User> users;
    private List<Message> messages;
    private List<Like> likes;
    private List<FriendsRequest> friendsRequests;

    public Generator(int usersCount, int messagesCount, int friendsRequestsCount)
    {
        checkCorrectedInput(usersCount, messagesCount, friendsRequestsCount);

        faker = new Faker();

        generateUser = new GenerateUser(faker, usersCount);
        generateUser.generate();
        users = generateUser.get();

        generateMessages = new GenerateMessage(faker, messagesCount, users);
        generateMessages.generate();
        messages = generateMessages.get();

        generateLikes = new GenerateLikes(faker, users, messages);
        generateLikes.generate();
        likes = generateLikes.get();

        generateFriendsRequests = new GenerateFriendsRequests(faker, friendsRequestsCount, users);
        generateFriendsRequests.generate();
        friendsRequests = generateFriendsRequests.get();
    }

    private void checkCorrectedInput(int usersCount, int messagesCount, int friendsRequestsCount)
    {
        if(usersCount < 2)
        {
            throw new UsersCountException("Users should more 1");
        }
    }
}
