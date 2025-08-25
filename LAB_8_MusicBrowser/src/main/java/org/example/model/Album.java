package org.example.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Album implements Line
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDate date;
    private String title;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "album_id")
    private List<Song> songs = new ArrayList<>();

    @Override
    public String getTitle()
    {
        return title;
    }
}
