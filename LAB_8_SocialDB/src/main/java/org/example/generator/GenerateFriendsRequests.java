package org.example.generator;

import com.github.javafaker.Faker;
import org.example.entities.FriendsRequest;
import org.example.entities.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GenerateFriendsRequests implements GenerateEntity<FriendsRequest>
{
    private final Faker faker;
    private final List<User> users;
    private final List<FriendsRequest> friendsRequests;

    private final int requestsCount;

    public GenerateFriendsRequests(Faker faker, int requestsCount, List<User> users)
    {
        this.users = users;
        this.faker = faker;
        this.requestsCount = requestsCount;
        this.friendsRequests = new ArrayList<>(requestsCount);
    }

    @Override
    public void generate()
    {
        for (int i = 0; i < requestsCount; i++)
        {
            friendsRequests.add(generateRequest(i));
        }
    }

    @Override
    public List<FriendsRequest> get()
    {
        return friendsRequests;
    }

    private FriendsRequest generateRequest(int id)
    {
        FriendsRequest friendsRequest = new FriendsRequest();

        friendsRequest.setId(id);
        friendsRequest.setStatus(faker.number().numberBetween(0, 4));

        int fromID = 0;
        int toId = 0;

        while (fromID == toId)
        {
            fromID = faker.number().numberBetween(0, users.size());
            toId = faker.number().numberBetween(0, users.size());
        }

        friendsRequest.setUserTo(users.get(toId));

        User userFrom = users.get(fromID);
        friendsRequest.setUserFrom(userFrom);

        friendsRequest.setSendDate(
                generateSandDate(userFrom.getOnline() ?
                        LocalDateTime.now() :
                        userFrom.getLastVisit()
                )
        );

        return friendsRequest;
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
