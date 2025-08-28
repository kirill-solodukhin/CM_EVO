package org.example.models;

import lombok.Data;
import org.example.entities.User;

import java.util.List;

@Data
public class UserContext
{
    private User user;
    private List<UserInformation> friends;
    private List<UserInformation> onlineFriends;
    private List<UserInformation> friendshipOffers;
    private List<UserInformation> subscribers;
    private List<News> news;
}
