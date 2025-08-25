package org.example.console;

import org.example.model.Album;
import org.example.model.Line;
import org.example.model.Song;
import org.example.repository.AlbumRepository;
import org.example.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class Menu
{
    @Autowired
    private CustomConsole console;

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private SongRepository songRepository;

    private List<Album> albums;
    private List<Line> lines =  new ArrayList<>();

    private int cursorPosition = 0;
    private int expandingAlbum = -1;

    public void loadingAlbums()
    {
        albums = (List<Album>) albumRepository.findAll();
        lines = new ArrayList<>();
        lines.addAll(albums);
    }

    public void positionUp()
    {
        if(cursorPosition++ < lines.size() - 1)
        {
            menu();
            return;
        }

        cursorPosition--;
    }

    public void positionDown()
    {
        if(cursorPosition-- > 0)
        {
            menu();
            return;
        }

        cursorPosition++;
    }

    public void menu()
    {
        console.clear();

        for (int i = 0; i < lines.size(); i++)
        {
            if(lines.get(i) instanceof Album)
            {
                console.writeln(
                        i == cursorPosition ?
                                " > " + lines.get(i).getTitle() :
                                lines.get(i).getTitle());

                continue;
            }

            console.writeln(
                    i == cursorPosition ?
                            "       > " + lines.get(i).getTitle() :
                            "       " +  lines.get(i).getTitle());

        }
    }

    public void openExpendedList()
    {
        Line l = lines.get(cursorPosition);

        if(l instanceof Song)
        {
            return;
        }

        cursorPosition = getAlbumPosition((Album) l);
        expandingAlbum = cursorPosition;
        getLines(cursorPosition);

        menu();
    }

    public void closeExpendedList()
    {
        Line l = lines.get(cursorPosition);

        if(l instanceof Song)
        {
            return;
        }

        lines = new ArrayList<>();
        lines.addAll(albums);

        menu();

        cursorPosition = getAlbumPosition((Album) l);
        expandingAlbum = -1;
    }

    public void deleteEntity()
    {
        Line l = lines.get(cursorPosition);

        if(l instanceof Album)
        {
            albumRepository.delete((Album) l);

            loadingAlbums();
            menu();

            return;
        }

        songRepository.delete((Song) l);

        loadingAlbums();
        menu();
    }

    public void addSong()
    {
        Song song = new Song();

        console.clear();

        song.setTitle(console.read("Input title of song > "));

        try
        {
            LocalTime time = LocalTime.ofSecondOfDay(
                    Integer.parseInt(console.read("Input duration song (seconds) > ")));

            song.setDuration(time);

            song.setAlbumId(expandingAlbum >= 0 ?
                    albums.get(expandingAlbum).getId() :
                    albums.get(cursorPosition).getId());

            songRepository.save(song);
            openExpendedList();
        }
        catch (Exception ex)
        {
            criticalMessage(ex);
        }
    }

    public void addAlbum()
    {
        Album album = new Album();

        console.clear();

        try
        {
            album.setTitle(console.read("Input title of album > "));

            int year = Integer.parseInt(console.read("Input year of publication > "));
            int month = Integer.parseInt(console.read("Input month of publication > "));
            int day = Integer.parseInt(console.read("Input day of publication > "));

            album.setDate(LocalDate.of(year, month, day));

            albumRepository.save(album);
        }
        catch (NumberFormatException ex)
        {
            criticalMessage(ex);
        }

        loadingAlbums();
        menu();
    }

    private void getLines(int alPosition)
    {
        lines = new ArrayList<>();

        for (int i = 0; i <= alPosition; i++)
        {
            lines.add(albums.get(i));
        }

        lines.addAll((List<Song>)songRepository.findAllByAlbumId(albums.get(alPosition).getId()));

        for (int i = alPosition + 1; i < albums.size(); i++)
        {
            lines.add(albums.get(i));
        }
    }

    private int getAlbumPosition(Album album)
    {
        for (int i = 0; i < albums.size(); i++)
        {
            if(albums.get(i) == album)
            {
                return i;
            }
        }

        return 0;
    }

    public boolean expendedListIsOpen()
    {
        //
        return expandingAlbum >= 0;
    }

    private void criticalMessage(Exception ex)
    {
        byte counter = 5;

        while (counter > 0)
        {
           try
           {
               console.clear();

               console.writeln("");
               console.writeln("Error: " + ex.getClass() +  " { " + ex.getMessage() + " } ");
               console.writeln("");
               console.writeln("The window will close automatically after: " + counter + " seconds");

               counter--;

               Thread.sleep(998);
           }
           catch (InterruptedException _)
           {
           }
        }

        menu();
    }
}
