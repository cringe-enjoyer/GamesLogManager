package ru.example.gameslogmanager.exceptions;

public class FriendsListNotFoundException extends RuntimeException {
    public FriendsListNotFoundException(String message) {
        super(message);
    }
}
