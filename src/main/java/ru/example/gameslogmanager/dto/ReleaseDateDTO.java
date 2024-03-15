package ru.example.gameslogmanager.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReleaseDateDTO {
    @JsonProperty("coming_soon")
    private boolean comingSoon;

    @JsonProperty("date")
    private String date;

    public void setComingSoon(boolean comingSoon){
        this.comingSoon = comingSoon;
    }

    public boolean isComingSoon(){
        return comingSoon;
    }

    public void setDate(String date){
        this.date = date;
    }

    public String getDate(){
        return date;
    }
}
