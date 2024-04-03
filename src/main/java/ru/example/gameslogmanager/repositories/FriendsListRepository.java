package ru.example.gameslogmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.example.gameslogmanager.models.FriendsList;
import ru.example.gameslogmanager.models.User;

import java.util.Optional;

@Repository
public interface FriendsListRepository extends JpaRepository<FriendsList, Long> {

    Optional<FriendsList> findByUserAndFriend(User user, User friend);
}