package ru.example.gameslogmanager.dto;

import java.io.Serializable;

/**
 * DTO for {@link ru.example.gameslogmanager.models.UsersGoal}
 */
public class UsersGoalDTO implements Serializable {
    private Integer goalCount;
    private Integer done;
    private UserDTO user;
    private Integer year;

    public UsersGoalDTO() {
    }

    public UsersGoalDTO(Integer goalCount, Integer done, UserDTO user,
                        Integer year) {
        this.goalCount = goalCount;
        this.done = done;
        this.user = user;
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

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
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
                "user = " + user + ")";
    }
}