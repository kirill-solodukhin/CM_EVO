package org.example.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
public class Album
{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    @OneToMany
    @JoinColumn(name = "album_id")
    private List<Song> songs = new ArrayList<>();

    private String title;

    @JoinColumn(name = "date")
    private LocalDate date;
}
