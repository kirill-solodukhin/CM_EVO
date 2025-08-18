package org.example.Models;

import java.time.OffsetDateTime;

public record User(OffsetDateTime dateOfBirth, Integer gender,OffsetDateTime lastVisit,
                   String name, Boolean online, Integer userId) implements JSONModel
{
    @Override
    public String entityToString()
    {
        return "    {\n" +
                "       \"dateOfBirth\": \"" + dateOfBirth + "\",\n" +
                "       \"gender\": " + gender + ",\n" +
                "       \"lastVisit\": \"" + lastVisit + "\",\n" +
                "       \"name\": \"" + name + "\",\n" +
                "       \"online\": " + online + ",\n" +
                "       \"userId\": " + userId + "\n" +
                "    }";
    }
}

