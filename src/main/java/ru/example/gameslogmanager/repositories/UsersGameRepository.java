package ru.example.gameslogmanager.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.example.gameslogmanager.models.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UsersGameRepository extends JpaRepository<UsersGame, Integer> {
    Optional<UsersGame> findByGameAndList(Game game, GamesList list);

    long countAllByList(GamesList list);

    Page<UsersGame> findAllByList(Pageable pageable, GamesList list);

    //List<UsersGame> findFirst3ByOrderByUpdateDateDesc(GamesList list);
    List<UsersGame> findFirstByListOrderByUpdateDateDesc(GamesList list);

    List<UsersGame> findByPlatformAndList_User(Platform platform, User user);

    List<UsersGame> findByListAndDateAddedAfter(GamesList list, LocalDate dateAdded);

    List<UsersGame> findByGame_GenresInAndList_User(Set<Genre> game_genres, User list_user);
    //List<UsersGame> findByList_UserAndGame_Genres(User user, Genre genres);
}