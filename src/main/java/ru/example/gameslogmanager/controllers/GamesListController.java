package ru.example.gameslogmanager.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.example.gameslogmanager.dto.GamesListDTO;
import ru.example.gameslogmanager.dto.GamesListsResponseDTO;
import ru.example.gameslogmanager.dto.UserDTO;
import ru.example.gameslogmanager.models.GamesList;
import ru.example.gameslogmanager.models.User;
import ru.example.gameslogmanager.services.GamesListService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/lists")
public class GamesListController {

    private final GamesListService gamesListService;
    private final ModelMapper modelMapper;

    @Autowired
    public GamesListController(GamesListService gamesListService, ModelMapper modelMapper) {
        this.gamesListService = gamesListService;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public GamesListsResponseDTO getAll(@RequestBody UserDTO userDTO) {
        List<GamesList> allUserLists = gamesListService.findAllByUser(convertToUser(userDTO));

        return new GamesListsResponseDTO(allUserLists.stream()
                .map((element) -> modelMapper.map(element, GamesListDTO.class))
                .toList());
    }

    @GetMapping("/{id}")
    public GamesListDTO getList(@PathVariable int id) {
        Optional<GamesList> list = gamesListService.findById(id);
        if (list.isPresent())
            return convertGamesListDTO(list.get());
        return null;
    }

    @GetMapping("/{id}/count")
    public Long getGamesCountInList(@PathVariable("id") int id) {
        return gamesListService.getGamesCountInListById(id);
    }

    @PostMapping("/add")
    public HttpEntity<HttpStatus> addList(@RequestBody GamesListDTO gamesListDTO) {
        gamesListService.save(convertToGamesList(gamesListDTO));
        return new HttpEntity<>(HttpStatus.CREATED);
    }

    @PostMapping()
    public HttpEntity<HttpStatus> removeList(@RequestBody GamesListDTO gamesListDTO) {
        Optional<GamesList> list = gamesListService.findByUserAndName(convertToUser(gamesListDTO.getUser()),
                gamesListDTO.getName());

        if (!list.isPresent())
            return null;

        gamesListService.remove(list.get().getId());

        return new HttpEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{id}")
    public HttpEntity<HttpStatus> removeList(@PathVariable("id") int listId) {
        gamesListService.remove(listId);
        return new HttpEntity<>(HttpStatus.OK);
    }

    private User convertToUser(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    private GamesListDTO convertGamesListDTO(GamesList gamesList) {
        return modelMapper.map(gamesList, GamesListDTO.class);
    }

    private GamesList convertToGamesList(GamesListDTO gamesListDTO) {
        return modelMapper.map(gamesListDTO, GamesList.class);
    }
}
