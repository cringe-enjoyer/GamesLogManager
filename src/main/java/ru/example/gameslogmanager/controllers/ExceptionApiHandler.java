package ru.example.gameslogmanager.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.example.gameslogmanager.dto.ErrorResponse;
import ru.example.gameslogmanager.exceptions.*;

@RestControllerAdvice
public class ExceptionApiHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(UserNotFoundException e) {
        ErrorResponse response = new ErrorResponse(e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FriendsListNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(FriendsListNotFoundException e) {
        ErrorResponse response = new ErrorResponse(e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(GamesListNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(GamesListNotFoundException e) {
        ErrorResponse response = new ErrorResponse(e.getMessage());
        
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(GameNotCreated.class)
    public ResponseEntity<ErrorResponse> handleException(GameNotCreated e) {
        ErrorResponse response = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DeveloperNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(DeveloperNotFoundException e) {
        ErrorResponse response = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UsersGameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(UsersGameNotFoundException e) {
        ErrorResponse response = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PublisherNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(PublisherNotFoundException e) {
        ErrorResponse response = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
