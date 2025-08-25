package org.example.repository;

import org.example.model.Song;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SongRepository extends CrudRepository<Song, Integer>
{
    @Query(value = """
                        SELECT * FROM song" +
                        "where album_id = :a_id
            """, nativeQuery = true)
    Iterable<Song> findAllByAlbumId(@Param("a_id") int a_id);
}