package ru.example.gameslogmanager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.example.gameslogmanager.dto.FriendsListDTO;
import ru.example.gameslogmanager.dto.FriendsNewsResponse;
import ru.example.gameslogmanager.exceptions.FriendsListNotFoundException;
import ru.example.gameslogmanager.exceptions.UserNotFoundException;
import ru.example.gameslogmanager.mapper.FriendMapper;
import ru.example.gameslogmanager.mapper.FriendsListMapper;
import ru.example.gameslogmanager.mapper.UsersGameMapper;
import ru.example.gameslogmanager.models.FriendsList;
import ru.example.gameslogmanager.models.User;
import ru.example.gameslogmanager.services.FriendsListService;
import ru.example.gameslogmanager.services.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/friends")
public class FriendsListController {

    private final FriendsListService friendsListService;
    private final UserService userService;
    private final FriendsListMapper friendsListMapper;
    private final FriendMapper friendMapper;
    private final UsersGameMapper usersGameMapper;

    @Autowired
    public FriendsListController(FriendsListService friendsListService, UserService userService,
                                 FriendsListMapper friendsListMapper, FriendMapper friendMapper, UsersGameMapper usersGameMapper) {
        this.friendsListService = friendsListService;
        this.userService = userService;
        this.friendsListMapper = friendsListMapper;
        this.friendMapper = friendMapper;
        this.usersGameMapper = usersGameMapper;
    }

    @GetMapping("/{id}")
    public List<FriendsListDTO> getFriendsList(@PathVariable("id") int userId) {
        Optional<User> userById = userService.getUserById(userId);
        if (userById.isEmpty()) {
            String message = "User not found";
            throw new UserNotFoundException(message);
        }

        return friendsListService.getFriendsLists(userById.get()).stream()
                .map(friendsListMapper::convertToDto)
                .toList();
    }

    @DeleteMapping("/{id}")
    public HttpEntity<HttpStatus> deleteFriend(@PathVariable("id") long id) {
        Optional<FriendsList> friendsListsById = friendsListService.getFriendsListsById(id);
        if (friendsListsById.isEmpty()) {
            String message = "Friends list not found";
            throw new FriendsListNotFoundException(message);
        }

        friendsListService.deleteFriendConnectionById(id);

        return new HttpEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}/news")
    public List<FriendsNewsResponse> getFriendsNews(@PathVariable("id") int userId) {
        Optional<User> userById = userService.getUserById(userId);
        if (userById.isEmpty()) {
            String message = "User not found";
            throw new UserNotFoundException(message);
        }
        List<FriendsNewsResponse> response = new ArrayList<>();
        friendsListService.getFriendsNews(userById.get()).forEach((user, usersGames) ->
                response.add(new FriendsNewsResponse(friendMapper.convertToDTO(user),
                        usersGameMapper.convertToDTO(usersGames)))
                );

        return response;
    }
}
