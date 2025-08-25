package org.example.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Song implements Line
{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "album_id")
    int albumId;
    LocalTime duration;
    String title;

    @Override
    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
