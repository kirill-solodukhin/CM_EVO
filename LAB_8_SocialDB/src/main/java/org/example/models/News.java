package org.example.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.entities.Like;
import org.example.entities.User;

import java.util.List;

@Data
@AllArgsConstructor
public class News
{
    private User author;
    private List<Like> likes;
    private String text;
}
