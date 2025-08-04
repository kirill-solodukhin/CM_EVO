package org.example;

import org.example.Models.News;
import org.example.Models.User;
import org.example.Models.UserInformation;

import java.util.List;

public class UserContext
{
    private User user;
    private List<UserInformation> friends;
    private List<UserInformation> onlineFriends;
    private List<UserInformation> friendshipOffers;
    private List<UserInformation> subscribers;
    private List<News> news;

    public void setUser(User user) {
        this.user = user;
    }

    public void setFriends(List<UserInformation> friends) {
        this.friends = friends;
    }

    public void setOnlineFriends(List<UserInformation> onlineFriends) {
        this.onlineFriends = onlineFriends;
    }

    public void setFriendshipOffers(List<UserInformation> friendshipOffers) {
        this.friendshipOffers = friendshipOffers;
    }

    public void setSubscribers(List<UserInformation> subscribers) {
        this.subscribers = subscribers;
    }

    public void setNews(List<News> news) {
        this.news = news;
    }

    public User getUser() {
        return user;
    }

    public List<UserInformation> getFriends() {
        return friends;
    }

    public List<UserInformation> getOnlineFriends() {
        return onlineFriends;
    }

    public List<UserInformation> getFriendshipOffers() {
        return friendshipOffers;
    }

    public List<UserInformation> getSubscribers() {
        return subscribers;
    }

    public List<News> getNews() {
        return news;
    }
}
