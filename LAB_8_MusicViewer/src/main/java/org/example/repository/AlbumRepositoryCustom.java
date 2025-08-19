package org.example.repository;

import org.example.model.Album;
import org.example.model.Song;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class AlbumRepositoryCustom implements AutoCloseable
{
    Connection connection;
    Statement statement;

    public AlbumRepositoryCustom() throws SQLException
    {
        connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/LAB_7_MusicViewer",
                "postgres",
                "1");

        statement = connection.createStatement();
    }

    public List<Album> getAllAlbums()
    {
        ResultSet resultSet;
        List<Album> albums = new ArrayList<>();
        List<Song> songs = new ArrayList<>();

        try
        {
            resultSet = statement.executeQuery(
                        "SELECT " +
                                "album.id, album.date, album.title, " +
                                "song.id AS song_id, song.album_id as s_album_id, song.duration, song.title as s_title " +
                                "FROM album " +
                            "INNER JOIN song " +
                            "ON song.album_id = album.id"
                    );

            int lastID = 0;
            while (resultSet.next())
            {
                int currentId = resultSet.getInt("id");

                if(currentId != lastID)
                {
                    lastID = currentId;

                    albums.add(new Album(
                            resultSet.getInt("id"),
                            new ArrayList<>(){},
                            resultSet.getString("title"),
                            resultSet.getObject("date", LocalDate.class)
                    ));
                }

                albums.getLast().getSongs().add(new Song(
                        resultSet.getInt("song_id"),
                        resultSet.getString("s_title"),
                        resultSet.getObject("duration", LocalTime.class),
                        resultSet.getInt("s_album_id")
                ));

            }

            return albums;
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
            return albums;
        }
    }

    private Set<Song> getAllSongs()
    {
        ResultSet resultSet;
        Set<Song> songs = new HashSet<>();

        try
        {
            resultSet = statement.executeQuery("SELECT * FROM song");

            while (resultSet.next())
            {
                songs.add(new Song(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getObject("duration", LocalTime.class),
                        resultSet.getInt("album_id")
                ));
            }

            return songs;
        }
        catch (SQLException e)
        {
            return songs;
        }
    }


    @Override
    public void close()
    {
        try
        {
            if(connection != null)
            {
                connection.close();
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }
}
