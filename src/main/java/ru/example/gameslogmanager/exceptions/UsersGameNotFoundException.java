package ru.example.gameslogmanager.exceptions;

public class UsersGameNotFoundException extends RuntimeException {
    public UsersGameNotFoundException(String message) {
        super(message);
    }
}
