package org.example.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User
{
    @Id
    private Integer ID;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "patronymic")
    private String patronymic;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "gender")
    private Integer gender;

    @Column(name = "last_visit")
    private LocalDateTime lastVisit;

    @Column(name = "online")
    private Boolean online;

    @OneToMany(mappedBy = "author",cascade = CascadeType.ALL)
    private List<Message> messages = new ArrayList<>();

    @OneToMany(mappedBy = "userFrom", cascade = CascadeType.ALL)
    private List<FriendsRequest> sendRequests = new ArrayList<>();

    @OneToMany(mappedBy = "userTo",cascade = CascadeType.ALL)
    private List<FriendsRequest> acceptedRequests = new ArrayList<>();

    @OneToMany(mappedBy = "author")
    private List<Like> likes = new ArrayList<>();
}
