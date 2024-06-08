package ru.example.gameslogmanager.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.example.gameslogmanager.dto.PublisherDTO;
import ru.example.gameslogmanager.exceptions.PublisherNotFoundException;
import ru.example.gameslogmanager.mapper.PublisherMapper;
import ru.example.gameslogmanager.models.Publisher;
import ru.example.gameslogmanager.services.PublisherService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/publisher")
public class PublisherController {
    private final PublisherService publisherService;
    private final PublisherMapper publisherMapper;

    public PublisherController(PublisherService publisherService, PublisherMapper publisherMapper) {
        this.publisherService = publisherService;
        this.publisherMapper = publisherMapper;
    }

    @GetMapping
    public List<PublisherDTO> getPublisher() {
        List<Publisher> publishers = publisherService.getAllPublishers();
        return publishers.stream().map(publisherMapper::convertToDTO).toList();
    }

    @GetMapping("/{id}")
    public PublisherDTO getPublisherById(@PathVariable("id") int id) {
        Optional<Publisher> publisher = publisherService.getPublisherById(id);
        if (publisher.isEmpty()) {
            String message = "Publisher not found";
            throw new PublisherNotFoundException(message);
        }

        return publisherMapper.convertToDTO(publisher.get());
    }
}
