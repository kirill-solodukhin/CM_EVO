package org.example.generator;

import com.github.javafaker.Faker;
import org.example.entities.Message;
import org.example.entities.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GenerateMessage implements GenerateEntity<Message>
{
    private final Faker faker;
    private final int messagesCount;
    private final List<User> users;
    private final List<Message> messages;

    public GenerateMessage(Faker faker, int messagesCount, List<User> users)
    {
        this.messagesCount = messagesCount;
        messages = new ArrayList<>();
        this.faker = faker;
        this.users = users;
    }

    @Override
    public void generate()
    {
        for (int i = 0; i < messagesCount; i++)
        {
            messages.add(generateMessage(i));
        }
    }

    @Override
    public List<Message> get()
    {

        return messages;
    }

    private String generateText()
    {
        return faker.superhero().name() + " " +
                faker.superhero().power() + " " +
                faker.space().distanceMeasurement();
    }

    private Message generateMessage(int id)
    {
        Message message = new Message();

        message.setId(id);

        int authorId = faker.number().numberBetween(0, users.size());
        message.setAuthor(users.get(authorId));

        message.setSendDate(
                generateSandDate(
                users.get(authorId).getOnline() ?
                        LocalDateTime.now() :
                        users.get(authorId).getLastVisit()
                )
        );

        message.setText(generateText());

        return message;
    }

    private LocalDateTime generateSandDate(LocalDateTime before)
    {
        LocalDate localDate = null;
        LocalTime localTime = null;

        localDate = LocalDate.ofInstant(
                faker.date().between(
                        Date.from(before.minusYears(1).atZone(ZoneId.systemDefault()).toInstant()),
                        Date.from(before.atZone(ZoneId.systemDefault()).toInstant())
                ).toInstant(),
                ZoneId.systemDefault()
        );

        localTime = LocalDate.now().equals(localDate) ?
                LocalTime.of(
                        faker.number().numberBetween(0, before.getHour()),
                        faker.number().numberBetween(0, before.getMinute())
                ):
                LocalTime.of(
                        faker.number().numberBetween(0, 23),
                        faker.number().numberBetween(0, 59)
                );

        return LocalDateTime.of(localDate, localTime);
    }
}
