package ru.example.gameslogmanager.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.gameslogmanager.models.Genre;
import ru.example.gameslogmanager.repositories.GenreRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class GenreService {

    private final GenreRepository genreRepository;

    @Autowired
    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public Optional<Genre> getGenreByName(String name) {
        return genreRepository.findByName(name);
    }

    @Transactional
    public void save(Genre genre) {
        genreRepository.save(genre);
    }

    public Set<Genre> getAllGenresByNames(List<String> genres) {
        return genreRepository.findDistinctByNameIn(genres);
    }
}
