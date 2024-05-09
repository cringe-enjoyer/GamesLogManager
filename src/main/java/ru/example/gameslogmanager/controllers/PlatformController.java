package ru.example.gameslogmanager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.example.gameslogmanager.dto.PlatformDTO;
import ru.example.gameslogmanager.mapper.PlatformMapper;
import ru.example.gameslogmanager.services.PlatformService;

import java.util.List;

@RestController
@RequestMapping("platform")
public class PlatformController {
    private final PlatformService platformService;
    private final PlatformMapper platformMapper;

    @Autowired
    public PlatformController(PlatformService platformService, PlatformMapper platformMapper) {
        this.platformService = platformService;
        this.platformMapper = platformMapper;
    }

    @GetMapping
    public List<PlatformDTO> getPlatforms() {
        return platformService.getAllPlatforms().stream().map(platformMapper::convertToDTO).toList();
    }
}
