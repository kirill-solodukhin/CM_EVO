package org.example.repositories;

import org.example.entities.Message;
import org.example.models.UserInformation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessagesRepository extends CrudRepository<Message, Integer>
{
    @Query("""
            FROM Message
            WHERE author.ID IN (:friends)
            """)
    List<Message> getAllMessage(@Param("friends") List<Integer> friendsID);
}