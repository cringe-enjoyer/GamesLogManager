package ru.example.gameslogmanager.models;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "users_goal")
public class UsersGoal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "goal_id")
    private Integer id;

    @Column(name = "goal_count")
    private Integer goalCount;

    @Column(name = "done")
    private Integer done;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "year")
    private Integer year;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UsersGoal usersGoal = (UsersGoal) o;
        return Objects.equals(id, usersGoal.id) && Objects.equals(goalCount, usersGoal.goalCount) && Objects.equals(done, usersGoal.done) && Objects.equals(user, usersGoal.user) && Objects.equals(year, usersGoal.year);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(goalCount);
        result = 31 * result + Objects.hashCode(done);
        result = 31 * result + Objects.hashCode(user);
        result = 31 * result + Objects.hashCode(year);
        return result;
    }
}