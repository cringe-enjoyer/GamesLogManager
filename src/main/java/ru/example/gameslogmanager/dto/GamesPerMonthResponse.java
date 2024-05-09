package ru.example.gameslogmanager.dto;

import java.util.Objects;

public class GamesPerMonthResponse {
    private Integer month;
    private Long count;

    public GamesPerMonthResponse() {
    }

    public GamesPerMonthResponse(Integer month, Long count) {
        this.month = month;
        this.count = count;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GamesPerMonthResponse that = (GamesPerMonthResponse) o;
        return Objects.equals(month, that.month) && Objects.equals(count, that.count);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(month);
        result = 31 * result + Objects.hashCode(count);
        return result;
    }
}
