package ru.example.gameslogmanager.exceptions;

public class GamesListNotFoundException extends RuntimeException {
    public GamesListNotFoundException(String message) {
        super(message);
    }
}
