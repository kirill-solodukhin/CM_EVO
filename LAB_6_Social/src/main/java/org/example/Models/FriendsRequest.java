package org.example.Models;

import java.time.OffsetDateTime;

public record FriendsRequest (int fromUserId, OffsetDateTime sendDate, int status, int toUserId) implements JSONModel
{

    @Override
    public String entityToString()
    {
        return "    {\n" +
                "       \"fromUserId\": " + fromUserId + ",\n" +
                "       \"sendDate\": \"" + sendDate + "\",\n" +
                "       \"status\": " + status + ",\n" +
                "       \"toUserId\": " + toUserId + "\n" +
                "    }\n";
    }
}