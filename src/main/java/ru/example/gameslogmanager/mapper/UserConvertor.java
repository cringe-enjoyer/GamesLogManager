package ru.example.gameslogmanager.mapper;

import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;

@Component
public class UserConvertor extends AbstractConverter<String, String> {

    @Override
    protected String convert(String s) {
        return s;
    }
}
