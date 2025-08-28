package org.example.repositories;

import org.example.entities.FriendsRequest;
import org.example.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UsersRepository extends CrudRepository<User, Integer>
{
    @Query("""
            FROM User
            WHERE firstName = :first_name
            AND   lastName = :last_name
            AND   patronymic = :pat_name
            """)
    User findByFullName(@Param("first_name") String userName,
                        @Param("last_name") String lastName,
                        @Param("pat_name") String patronymic);

    @Query("""
            FROM FriendsRequest
            WHERE userFrom.ID = :user_id
            AND status != 3
            """)
    List<FriendsRequest> getReceivedNotRejectedRequestsById(
            @Param("user_id") int id
    );

    @Query("""
            FROM FriendsRequest
            WHERE userTo.ID = :user_id
            AND status != 3
            """)
    List<FriendsRequest> getAcceptedNotRejectedRequestsById(
            @Param("user_id") int id
    );

    @Query("""
            SELECT userFrom FROM FriendsRequest
            WHERE userTo.ID = :user_id
            AND status = 1
            """)
    List<User> getSubscribes(@Param("user_id") int id);

    @Query("""
            FROM FriendsRequest
            WHERE userTo.ID = :user_id
            AND status = 0
            """)
    List<FriendsRequest> getFriendshipOffers(@Param("user_id") int id);
}