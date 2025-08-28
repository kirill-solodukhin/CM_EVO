package org.example.generator;

import com.github.javafaker.Faker;
import org.example.entities.User;

import java.time.*;
import java.util.ArrayList;
import java.util.List;

public class GenerateUser implements GenerateEntity<User>
{
    private List<User> users;
    private final Faker faker;

    private final int usersCount;

    public GenerateUser(Faker faker, int usersCount)
    {
        this.faker = faker;
        this.usersCount = usersCount;
    }

    @Override
    public void generate()
    {
        users = new ArrayList<>(usersCount);

        for (int i = 0; i < usersCount; i++)
        {
            users.add(generateUser(i));
        }
    }

    @Override
    public List<User> get()
    {

        return users;
    }

    private User generateUser(int id)
    {
       User user = new User();

       user.setID(id);

       user.setFirstName(faker.name().firstName());
       user.setLastName(faker.name().lastName());
       user.setPatronymic(faker.name().firstName() + "\\'s");

       user.setOnline(faker.bool().bool());

       user.setLastVisit(user.getOnline() ?
               null :
               LocalDateTime.of(
                       LocalDate.ofInstant(
                               faker.date().birthday(0, 1)
                                       .toInstant(),
                               ZoneId.systemDefault()
                       ),
                       LocalTime.of(
                               faker.number().numberBetween(0, 23),
                               faker.number().numberBetween(0, 59),
                               faker.number().numberBetween(0, 59)
                       )
               )
       );

       user.setDateOfBirth(LocalDate.ofInstant(
               faker.date().birthday(18, 50)
                       .toInstant(),
               ZoneId.systemDefault()));

       user.setGender(faker.number().numberBetween(0, 1));

        return user;
    }
}