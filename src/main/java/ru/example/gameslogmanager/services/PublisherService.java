package ru.example.gameslogmanager.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.gameslogmanager.models.Publisher;
import ru.example.gameslogmanager.repositories.PublisherRepository;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PublisherService {
    private PublisherRepository publisherRepository;

    @Autowired
    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    public Optional<Publisher> findPublisherById(int id) {
        return publisherRepository.findById(id);
    }
}