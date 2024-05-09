package ru.example.gameslogmanager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.example.gameslogmanager.dto.DeveloperDTO;
import ru.example.gameslogmanager.exceptions.DeveloperNotFoundException;
import ru.example.gameslogmanager.mapper.DeveloperMapper;
import ru.example.gameslogmanager.models.Developer;
import ru.example.gameslogmanager.services.DeveloperService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/developer")
public class DeveloperController {
    private final DeveloperService developerService;
    private final DeveloperMapper developerMapper;

    @Autowired
    public DeveloperController(DeveloperService developerService, DeveloperMapper developerMapper) {
        this.developerService = developerService;
        this.developerMapper = developerMapper;
    }

    @GetMapping
    public List<DeveloperDTO> getDevelopers() {
        return developerService.getDevelopers().stream().map(developerMapper::convertToDTO).toList();
    }

    @GetMapping("/{id}")
    public DeveloperDTO getDeveloperById(@PathVariable int id) {
        Optional<Developer> developer = developerService.getDeveloperById(id);
        if (developer.isEmpty()) {
            String message = "Developer not found";
            throw new DeveloperNotFoundException(message);
        }

        return developerMapper.convertToDTO(developer.get());
    }
}
