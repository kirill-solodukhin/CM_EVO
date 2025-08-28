package org.example.generator;

import com.github.javafaker.Faker;
import org.example.entities.Like;
import org.example.entities.Message;
import org.example.entities.User;

import java.util.ArrayList;
import java.util.List;

public class GenerateLikes implements GenerateEntity<Like>
{
    private final Faker faker;
    private final List<Like> likes;
    private final List<User> users;
    private final List<Message> messages;

    public GenerateLikes(Faker faker, List<User> users, List<Message> messages)
    {
        this.faker = faker;
        this.users = users;
        this.messages = messages;

        likes = new ArrayList<>();
    }

    @Override
    public List<Like> get()
    {

        return likes;
    }

    @Override
    public void generate()
    {
        for (int i = 0; i < faker.number().numberBetween(messages.size() / 2, messages.size() * 2); i++)
        {
            likes.add(generateLike(i));
        }
    }

    private Like generateLike(int id)
    {
        Like like = new Like();

        like.setId(id);
        like.setMessage(messages.get(faker.number().numberBetween(0, messages.size())));
        like.setAuthor(users.get(faker.number().numberBetween(0, users.size())));

        return like;
    }
}
