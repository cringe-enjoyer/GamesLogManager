package ru.example.gameslogmanager.mapper;

import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;
import ru.example.gameslogmanager.models.Publisher;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PublishersConverter extends AbstractConverter<Set<Publisher>, Set<Integer>> {

    @Override
    protected Set<Integer> convert(Set<Publisher> publishers) {
        return publishers.stream().map(Publisher::getId).collect(Collectors.toSet());
    }
}
