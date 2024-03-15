package ru.example.gameslogmanager.dto;

public class GenreDTO {
    private String id;
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "GenreDTO{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}
