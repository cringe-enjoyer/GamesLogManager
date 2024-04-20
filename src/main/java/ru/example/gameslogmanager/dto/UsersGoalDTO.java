package ru.example.gameslogmanager.dto;

import java.io.Serializable;

/**
 * DTO for {@link ru.example.gameslogmanager.models.UsersGoal}
 */
public class UsersGoalDTO implements Serializable {
    private Integer goalCount;
    private Integer done;
    private Integer userId;
    private Integer year;

    public UsersGoalDTO() {
    }

    public UsersGoalDTO(Integer goalCount, Integer done, Integer userId, Integer year) {
        this.goalCount = goalCount;
        this.done = done;
        this.userId = userId;
        this.year = year;
    }

    public Integer getGoalCount() {
        return goalCount;
    }

    public void setGoalCount(Integer goalCount) {
        this.goalCount = goalCount;
    }

    public Integer getDone() {
        return done;
    }

    public void setDone(Integer done) {
        this.done = done;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "goalCount = " + goalCount + ", " +
                "done = " + done + ", " +
                "userId = " + userId + ")";
    }
}