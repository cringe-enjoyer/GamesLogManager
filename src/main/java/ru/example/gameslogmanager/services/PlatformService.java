package ru.example.gameslogmanager.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.gameslogmanager.models.Platform;
import ru.example.gameslogmanager.repositories.PlatformRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PlatformService {

    private final PlatformRepository platformRepository;

    @Autowired
    public PlatformService(PlatformRepository platformRepository) {
        this.platformRepository = platformRepository;
    }

    public List<Platform> getAllPlatforms() {
        return platformRepository.findAll();
    }

    public Optional<Platform> getByName(String name) {
        return platformRepository.findByName(name);
    }
}
