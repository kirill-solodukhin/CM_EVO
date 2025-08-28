package org.example.Models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public record Message (Integer authorId, List<Integer> likes, Integer messageId,
        LocalDateTime sendDate, String text) implements JSONModel
{
    @Override
    public String entityToString()
    {
        return "    {\n" +
                "       \"authorId\": " + authorId + ",\n" +
                "       \"likes\": " + getLikesString() + ",\n" +
                "       \"messageId\": " + messageId + ",\n" +
                "       \"sendDate\": \"" + sendDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'hh:mm:ss")) + "\",\n" + // "2019-02-27T00:00:00"
                "       \"text\": \"" + text + "\"\n" +
                "    }";
    }

    private String getLikesString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("[");

        for(int i = 0; i < likes.size(); i++)
        {
            sb.append(likes.get(i));

            if(i == likes.size() - 1)
            {
                break;
            }

            sb.append(",");
        }

        sb.append("]");

        return sb.toString();
    }
}
