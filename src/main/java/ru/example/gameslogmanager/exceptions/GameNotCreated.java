package ru.example.gameslogmanager.exceptions;

public class GameNotCreated extends RuntimeException {
    public GameNotCreated(String message) {
        super(message);
    }
}
