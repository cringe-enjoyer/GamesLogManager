package ru.example.gameslogmanager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.example.gameslogmanager.dto.GenreDTO;
import ru.example.gameslogmanager.mapper.GenreMapper;
import ru.example.gameslogmanager.services.GenreService;

import java.util.List;

@RestController
@RequestMapping("/genre")
public class GenresController {
    private final GenreService genreService;
    private final GenreMapper genreMapper;

    @Autowired
    public GenresController(GenreService genreService, GenreMapper genreMapper) {
        this.genreService = genreService;
        this.genreMapper = genreMapper;
    }

    @GetMapping
    public List<GenreDTO> getGenres() {
        return genreService.getAllGenres().stream().map(genreMapper::convertToDTO).toList();
    }
}
