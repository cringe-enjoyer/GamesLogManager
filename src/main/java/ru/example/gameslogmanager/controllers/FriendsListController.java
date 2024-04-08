package ru.example.gameslogmanager.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.example.gameslogmanager.dto.FriendsListDTO;
import ru.example.gameslogmanager.models.FriendsList;
import ru.example.gameslogmanager.models.User;
import ru.example.gameslogmanager.services.FriendsListService;
import ru.example.gameslogmanager.services.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/friends/{id}")
public class FriendsListController {

    private final FriendsListService friendsListService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public FriendsListController(FriendsListService friendsListService, UserService userService,
                                 ModelMapper modelMapper) {
        this.friendsListService = friendsListService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<FriendsListDTO> getFriendsList(@PathVariable("id") int userId) {
        Optional<User> userById = userService.getUserById(userId);
        if (!userById.isPresent()) {
            return null;
        }

        return userById.get().getFriendsLists().stream()
                .map(this::convertToFriendsListDTO)
                .collect(Collectors.toList());
    }

    @DeleteMapping
    public HttpEntity<HttpStatus> deleteFriend(@PathVariable("id") int userId,
                                               @RequestBody FriendsListDTO friendsListDTO) {
        Optional<User> userById = userService.getUserById(userId);
        if (userById.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        //TODO: протестировать
        friendsListService.deleteFriendConnectionByUserAndFriend(userById.get(),
                convertToFriendsList(friendsListDTO).getFriend());

        return new HttpEntity<>(HttpStatus.OK);
    }

    private FriendsList convertToFriendsList(FriendsListDTO friendsListDTO) {
        return modelMapper.map(friendsListDTO, FriendsList.class);
    }

    private FriendsListDTO convertToFriendsListDTO(FriendsList element) {
        return modelMapper.map(element, FriendsListDTO.class);
    }
}
