package ru.example.gameslogmanager.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.example.gameslogmanager.dto.PlatformDTO;
import ru.example.gameslogmanager.models.Platform;

import java.util.Objects;

@Component
public class PlatformMapper {

    private final ModelMapper modelMapper;

    @Autowired
    public PlatformMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    /**
     * Обновляет поля в существующей сущности на основе DTO
     *
     * @param platformDTO DTO с новыми данными
     * @param platform существующая сущность платформы
     * @return сущность платформы с обновленными данными
     */
    public Platform convertToEntity(PlatformDTO platformDTO, Platform platform) {
        if (Objects.isNull(platformDTO))
            return null;

        modelMapper.map(platformDTO, platform);
        return platform;
    }

    public Platform convertToEntity(PlatformDTO platformDTO) {
        return Objects.isNull(platformDTO) ? null : modelMapper.map(platformDTO, Platform.class);
    }

    public PlatformDTO convertToDTO(Platform platform) {
        return Objects.isNull(platform) ? null : modelMapper.map(platform, PlatformDTO.class);
    }
}
