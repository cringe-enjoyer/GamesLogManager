package ru.example.gameslogmanager.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.example.gameslogmanager.dto.GenreDTO;
import ru.example.gameslogmanager.models.Genre;

@Component
public class GenreMapper {

    private final ModelMapper modelMapper;

    @Autowired
    public GenreMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Genre convertToEntity(GenreDTO genre) {
        return modelMapper.map(genre, Genre.class);
    }

    public GenreDTO convertToDTO(Genre genre) {
        return modelMapper.map(genre, GenreDTO.class);
    }
}
