package org.example.supportive;

import org.example.models.UserContext;

public class Viewer
{
    private final UserContext userContext;

    public Viewer(UserContext userContext)
    {
        this.userContext = userContext;
    }

    public void view()
    {
        System.out.println('\n');
        System.out.println('\n');
        System.out.println('\n');
        System.out.println("_________________________________________");
        System.out.println('\n');

        System.out.println("Hello,  " + userContext.getUser().getLastName() + " " + userContext.getUser().getFirstName());
        System.out.println("\nOur friends:");
        userContext.getFriends().forEach(el -> System.out.println(
                el.getLastName() + " " + el.getFirstName()  + " " + el.getPatronymic() +
                        (el.isOnline() ? " - online" : " - offline")
        ));

        System.out.println("\nOur subscribers:");
        userContext.getSubscribers().forEach(el -> System.out.println(
                el.getLastName() + " " + el.getFirstName()  + " " + el.getPatronymic() +
                        (el.isOnline() ? " - online" : " - offline")
        ));

        System.out.println("\nOur friendship offers:");
        userContext.getFriendshipOffers().forEach(el -> System.out.println(
                el.getLastName() + " " + el.getFirstName()  + " " + el.getPatronymic() +
                        (el.isOnline() ? " - online" : " - offline")
        ));

        System.out.println("\nOur news:");
        userContext.getNews().forEach(el -> System.out.println(
                el.getAuthor().getLastName() + " " + el.getAuthor().getFirstName()  + " " + el.getAuthor().getPatronymic() +
                        " - writing: " + el.getText() + "it's like: " + el.getLikes().size() + " people"
        ));

        System.out.println('\n');
        System.out.println("_________________________________________");
        System.out.println('\n');
        System.out.println('\n');
        System.out.println('\n');
    }
}
