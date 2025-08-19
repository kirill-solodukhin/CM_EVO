package org.example;

import org.example.model.Album;
import org.example.model.Song;
import org.example.repository.AlbumRepositoryCustom;
import org.example.repository.AlbumRepositoryHibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.List;

@Component
public class Shower
{
    @Autowired
    private AlbumRepositoryHibernate repository;
    private final AlbumRepositoryCustom repositoryCustom;

    public Shower()
    {
        try
        {
            repositoryCustom = new AlbumRepositoryCustom();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void ShowAll()
    {
        List<Album> albums =  repositoryCustom.getAllAlbums();
        String albumPattern = " \n ID: {0}, album name: \"{1}\" songs: ";
        String songPattern = " song name : \"{0}\", duration: {1}";

        for(Album a : albums)
        {
            System.out.println(MessageFormat.format(albumPattern, a.getID(), a.getTitle()));

            for (Song s : a.getSongs())
            {
                System.out.println(MessageFormat.format(songPattern, s.getTitle(), s.getDuration()));
            }
        }
    }
}
