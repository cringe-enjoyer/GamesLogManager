package ru.example.gameslogmanager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.example.gameslogmanager.dto.FriendsListDTO;
import ru.example.gameslogmanager.mapper.FriendsListMapper;
import ru.example.gameslogmanager.models.FriendsList;
import ru.example.gameslogmanager.models.User;
import ru.example.gameslogmanager.services.FriendsListService;
import ru.example.gameslogmanager.services.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/friends")
public class FriendsListController {

    private final FriendsListService friendsListService;
    private final UserService userService;
    private final FriendsListMapper friendsListMapper;

    @Autowired
    public FriendsListController(FriendsListService friendsListService, UserService userService, FriendsListMapper friendsListMapper) {
        this.friendsListService = friendsListService;
        this.userService = userService;
        this.friendsListMapper = friendsListMapper;
    }

    @GetMapping("/{id}")
    public List<FriendsListDTO> getFriendsList(@PathVariable("id") int userId) {
        Optional<User> userById = userService.getUserById(userId);
        if (userById.isEmpty()) {
            return null;
        }

        return friendsListService.getFriendsLists(userById.get()).stream()
                .map(friendsListMapper::convertToDto)
                .toList();
    }

    @DeleteMapping("/{id}")
    public HttpEntity<HttpStatus> deleteFriend(@PathVariable("id") long id) {
        Optional<FriendsList> friendsListsById = friendsListService.getFriendsListsById(id);
        if (friendsListsById.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        friendsListService.deleteFriendConnectionById(id);

        return new HttpEntity<>(HttpStatus.OK);
    }
}
