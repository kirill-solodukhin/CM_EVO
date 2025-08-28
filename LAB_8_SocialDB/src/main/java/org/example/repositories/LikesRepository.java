package org.example.repositories;

import org.example.entities.Like;
import org.springframework.data.repository.CrudRepository;

public interface LikesRepository extends CrudRepository<Like, Integer>
{
}
