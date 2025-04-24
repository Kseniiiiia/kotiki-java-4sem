package com.example.atm.repository;

import com.example.atm.model.Friendship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

    @Query("SELECT f FROM Friendship f WHERE f.pet.id = :petId OR f.friend.id = :petId")
    List<Friendship> findAllFriendshipsById(@Param("petId") Long petId);

    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END " +
            "FROM Friendship f WHERE " +
            "(f.pet.id = :petId AND f.friend.id = :friendId) OR " +
            "(f.pet.id = :friendId AND f.friend.id = :petId)")
    boolean existsFriendship(@Param("petId") Long petId, @Param("friendId") Long friendId);
}