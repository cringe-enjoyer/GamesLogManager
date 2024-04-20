package ru.example.gameslogmanager.mapper;

import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;
import ru.example.gameslogmanager.models.Developer;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class DevelopersConverter extends AbstractConverter<Set<Developer>, Set<Integer>> {

    @Override
    protected Set<Integer> convert(Set<Developer> developers) {
        return developers.stream().map(Developer::getId).collect(Collectors.toSet());
    }
}
