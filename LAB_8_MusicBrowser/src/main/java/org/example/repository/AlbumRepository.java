package org.example.repository;

import org.example.model.Album;
import org.springframework.data.repository.CrudRepository;

public interface AlbumRepository extends CrudRepository<Album, Integer>
{

}
