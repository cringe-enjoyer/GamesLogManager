package ru.example.gameslogmanager.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.gameslogmanager.models.Developer;
import ru.example.gameslogmanager.repositories.DeveloperRepository;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class DeveloperService {

    private DeveloperRepository developerRepository;

    @Autowired
    public DeveloperService(DeveloperRepository developerRepository) {
        this.developerRepository = developerRepository;
    }

    public Optional<Developer> getDeveloperById(int id) {
        return developerRepository.findById(id);
    }

    public Optional<Developer> getDeveloperByName(String name) {
        return developerRepository.findByName(name);
    }

    @Transactional
    public void saveDeveloper(Developer developer) {
        developerRepository.save(developer);
    }
}
