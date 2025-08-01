package org.example.Mappers;

import com.fasterxml.jackson.datatype.jsr310.ser.OffsetDateTimeSerializer;
import org.example.Exceptions.ParsingException;
import org.example.Models.Message;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageMapper
{
    private final Pattern patternFields = Pattern.compile(
            "(?<authorId>\"authorId\"\\s*:\\s*\\d+\\s*,)|" +
            "(?<likes>\"likes\"\\s*:\\s\\[(\\s*\\d+\\s*,?)*],)|" +
            "(?<messageId>\"messageId\"\\s*:\\s*\\d+\\s*,)|" +
            "(?<sendDate>\"sendDate\"\\s*:\\s*\"\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\")|" +
            "(?<text>\"text\"\\s*:\\s*\"[^}]*\")");

    private final List<String> strEntities = new ArrayList<>();

    public MessageMapper(String text)
    {
        Pattern entityPattern = Pattern.compile("(?<entity>\\{[^}]+})");
        Matcher matcher = entityPattern.matcher(text);

        while (matcher.find())
        {
            strEntities.add(matcher.group());
        }
    }

    public List<Message> getListEntity()
    {
        if(strEntities.isEmpty())
        {
            throw new ParsingException("Entities is not found");
        }

        List<Message> messages = new ArrayList<>();

        for (String el : strEntities)
        {
            messages.add(getEntity(el));
        }

        return messages;
    }

    private Message getEntity(String element)
    {
        Matcher matcher = patternFields.matcher(element);

        int authorId = -1;
        int messageId = -1;
        List<Integer> likes = new ArrayList<>();
        LocalDateTime sendDate = LocalDateTime.MIN;

        String findingLine = "";
        while (matcher.find())
        {
            if((findingLine = matcher.group("authorId")) != null)
            {
                authorId = Integer.parseInt(findingLine.split(":")[1]
                        .replace(",", "")
                        .trim());

                continue;
            }

            if((findingLine = matcher.group("likes")) != null)
            {
               findingLine = findingLine.split(":")[1].trim();
               findingLine = findingLine.substring(1, findingLine.length() - 1);
               String[] values = findingLine.replace("]", "").split(",");

               for(String val : values)
               {
                   likes.add(Integer.parseInt(val.trim()));
               }

               continue;
            }

            if((findingLine = matcher.group("messageId")) != null)
            {
                messageId = Integer.parseInt(findingLine.split(":")[1]
                        .replace(",", "")
                        .trim());

                continue;
            }

            if((findingLine = matcher.group("sendDate")) != null)
            {
                findingLine =  findingLine.substring(11).replace("\"", "").trim();
                sendDate = LocalDateTime.parse(findingLine, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));

                continue;
            }

            if((findingLine = matcher.group("text")) != null)
            {
                findingLine = findingLine.replace("\"text\":", "").trim();
            }
        }

        throwIfBadFind(authorId, likes, messageId, sendDate, findingLine);

        return new Message(authorId, likes, messageId, sendDate, findingLine);
    }

    private void throwIfBadFind(int authorId, List<Integer> likes,
                                int messageId, LocalDateTime sendDate, String findingLine)
    {
        if(authorId < 0)
        {
            throw new ParsingException("authorId is not found");
        }

        if(likes.isEmpty())
        {
            throw new ParsingException("likes is Empty");
        }

        if(messageId < 0)
        {
            throw new ParsingException("messageId is not found");
        }

        if(sendDate == LocalDateTime.MIN)
        {
            throw new ParsingException("sendDate is not found");
        }

        if(findingLine.isEmpty())
        {
            throw new ParsingException("text is not found");
        }
    }
}
