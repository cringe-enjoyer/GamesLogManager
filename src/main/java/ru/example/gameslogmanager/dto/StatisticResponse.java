package ru.example.gameslogmanager.dto;

import java.util.List;

public class StatisticResponse {
    private int gamesFinished;

    private List<GamesListDTO> lists;

    private List<UsersGoalDTO> goal;

    public StatisticResponse() {
    }

    public StatisticResponse(int gamesFinished, List<GamesListDTO> lists, List<UsersGoalDTO> goal) {
        this.gamesFinished = gamesFinished;
        this.lists = lists;
        this.goal = goal;
    }

    public int getGamesFinished() {
        return gamesFinished;
    }

    public void setGamesFinished(int gamesFinished) {
        this.gamesFinished = gamesFinished;
    }

    public List<GamesListDTO> getLists() {
        return lists;
    }

    public void setLists(List<GamesListDTO> lists) {
        this.lists = lists;
    }

    public List<UsersGoalDTO> getGoal() {
        return goal;
    }

    public void setGoal(List<UsersGoalDTO> goal) {
        this.goal = goal;
    }
}
