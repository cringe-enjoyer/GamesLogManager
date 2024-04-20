package ru.example.gameslogmanager.models;

/**
 * Статус заявки на добавление в список друзей
 */
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
