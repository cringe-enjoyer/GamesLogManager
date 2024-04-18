package ru.example.gameslogmanager.utils;

/**
 * Перечисление содержащие названия стандартных списков в БД
 */
public enum DefaultLists {
    FINISHED("пройдено"),
    BACKLOG("бэклог"),
    INPROGRES("прохожу"),
    DROP("брошено");

    /**
     * Название на русском в БД
     */
    final String ruValue;

    DefaultLists(String ruValue) {
        this.ruValue = ruValue;
    }

    public String getRuValue() {
        return ruValue;
    }
}
