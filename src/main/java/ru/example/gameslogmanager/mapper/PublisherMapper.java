package ru.example.gameslogmanager.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.example.gameslogmanager.dto.PublisherDTO;
import ru.example.gameslogmanager.models.Publisher;

@Component
public class PublisherMapper {

    private final ModelMapper modelMapper;

    @Autowired
    public PublisherMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Publisher convertToEntity(PublisherDTO publisherDTO) {
        return modelMapper.map(publisherDTO, Publisher.class);
    }

    public PublisherDTO convertToDTO(Publisher publisher) {
        return modelMapper.map(publisher, PublisherDTO.class);
    }
}
