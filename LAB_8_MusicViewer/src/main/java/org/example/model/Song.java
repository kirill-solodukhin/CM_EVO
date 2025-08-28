package org.example.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalTime;

@Entity
@Data
@AllArgsConstructor
public class Song
{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int ID;

    @Column(name = "title")
    private String title;

    @Column(name = "duration")
    private LocalTime duration;

    @Column(name = "album_id")
    private int albumId;
}