package ru.example.gameslogmanager.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.example.gameslogmanager.models.*;
import ru.example.gameslogmanager.dto.GamesPerMonthResponse;

import java.time.LocalDate;
import java.util.Collection;
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

    List<UsersGame> findByListAndDateFinishedAfter(GamesList list, LocalDate dateAdded);

    List<UsersGame> findByGame_GenresInAndList_User(Set<Genre> game_genres, User list_user);

    List<UsersGame> findAllByList_UserAndDateFinishedBetweenOrderByDateFinishedDesc(User user,
                                                                                    LocalDate dateFinishedStart,
                                                                                    LocalDate dateFinishedEnd);

    @Query("select new ru.example.gameslogmanager.dto.GamesPerMonthResponse(" +
            "extract(month from date_trunc('month', u.dateFinished)), count(u)) from UsersGame u " +
            "where u.list.user = ?1 and u.dateFinished between ?2 and ?3 " +
            "GROUP BY date_trunc('month', u.dateFinished)")
    List<GamesPerMonthResponse> findByList_UserAndDateFinishedBetween(User user, LocalDate dateFinishedStart,
                                                                      LocalDate dateFinishedEnd);

    List<UsersGame> findFirst5ByList_UserOrderByDateFinishedDesc(User user);

    Optional<UsersGame> findFirstByList_UserOrderByDateFinishedDesc(User user);

    long countByPlatformAndList_User(Platform platform, User user);

    long countByGame_GenresInAndList_User(Collection<Genre> genres, User user);

    Optional<UsersGame> findByGameAndList_User(Game game, User user);
}