package ru.example.gameslogmanager.models;

public enum FriendStatus {
    /**
     * Добавлен
     */
    ADDED,
    /**
     * Отклонён (после этого спустя день нужно удалить запись из бд)
     */
    REJECTED,
    /**
     * Отправлена
     */
    SENT
}
