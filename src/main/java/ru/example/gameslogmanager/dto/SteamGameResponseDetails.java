package ru.example.gameslogmanager.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SteamGameResponseDetails implements Serializable {
    private boolean success;
    @JsonProperty("data")
    private SteamGameInfoDTO data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public SteamGameInfoDTO getData() {
        return data;
    }

    public void setData(SteamGameInfoDTO data) {
        this.data = data;
    }


}
