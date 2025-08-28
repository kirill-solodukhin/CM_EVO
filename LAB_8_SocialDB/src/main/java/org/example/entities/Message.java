package org.example.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "messages")
public class Message
{
    @Id
    private Integer Id;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @Column(name = "likes")
    @OneToMany(mappedBy = "message",cascade = CascadeType.ALL)
    private List<Like> likes = new ArrayList<>();

    @Column(name = "send_date")
    private LocalDateTime sendDate;

    private String text;
}
