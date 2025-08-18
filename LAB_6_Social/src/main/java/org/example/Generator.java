package org.example;


import com.github.javafaker.Faker;
import org.example.Models.FriendsRequest;
import org.example.Models.Message;
import org.example.Models.User;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Generator
{
    private final Faker faker = new Faker();
    private final Random random = new Random();

    private final List<User> users = new ArrayList<>();
    private final List<Message> messages = new ArrayList<>();
    private final List<FriendsRequest> friendsRequests = new ArrayList<>();

    public Generator(int usersCount, int messageCount, int friendsRequestCount)
    {
        generateUsers(usersCount);
        generateMessages(messageCount);
        generateFriendsRequest(friendsRequestCount);
    }

    public List<User> getUsers()
    {
        return users;
    }

    public List<Message> getMessage()
    {
        return messages;
    }

    public List<FriendsRequest> getFriendsRequest()
    {
        return friendsRequests;
    }

    private void generateFriendsRequest(int count)
    {
        for(int i = 0; i < count; i++)
        {
            friendsRequests.add(new FriendsRequest(
                    random.nextInt(users.size() + 1),
                    OffsetDateTime.ofInstant(faker.date().birthday(0, 5).toInstant(), ZoneId.systemDefault()),
                    random.nextInt(5),
                    random.nextInt(users.size() + 1)
            ));
        }
    }

    private void generateUsers(int count)
    {
        for (int i = 0; i < count; i++)
        {
            users.add(new User(
                    OffsetDateTime.ofInstant(faker.date().birthday(10, 80).toInstant(), ZoneId.systemDefault()),
                    random.nextInt(2),
                    random.nextBoolean() ? OffsetDateTime.now() :
                            OffsetDateTime.ofInstant(faker.date().birthday(0, 1).toInstant(),ZoneId.systemDefault()),
                    faker.name().firstName(),
                    random.nextBoolean(),
                    i
            ));
        }
    }

    private void generateMessages(int count)
    {
        for(int i = 0; i <  count; i++)
        {
            messages.add(new Message(
                    random.nextInt(users.size()),
                    getLikes(),
                    i,
                    LocalDateTime.ofInstant(faker.date().birthday(0, 1).toInstant(), ZoneId.systemDefault()),
                    faker.superhero().name()
            ));
        }

    }

    private List<Integer> getLikes()
    {
        int max = random.nextInt(users.size());
        List<Integer> likes = new ArrayList<>();

        for(int i = 0; i < max; i++)
        {
            likes.add(random.nextInt(users.size()));
        }

        return likes.stream().distinct().toList();
    }
}
