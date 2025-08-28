package org.example.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "friends_requests")
public class FriendsRequest
{
    @Id
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id_accepted")
    private User userTo;

    @ManyToOne
    @JoinColumn(name = "user_id_send")
    private User userFrom;

    @Column(name = "send_date")
    private LocalDateTime sendDate;

    private int status;
}
