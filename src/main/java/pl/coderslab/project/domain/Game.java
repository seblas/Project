package pl.coderslab.project.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "games")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(name = "games_users")
    private Set<User> users;

    @ManyToOne
    private Field field;

    // private LocalDate date;

    private double cost;

    private int minAge;

    private int maxAge;

    private int minLevel;

    private int maxLevel;

    private String description;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private LocalDateTime recruitmentEndTime;

    public Game() {
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public LocalDateTime getRecruitmentEndTime() {
        return recruitmentEndTime;
    }

    public void setRecruitmentEndTime(LocalDateTime recruitmentEndTime) {
        this.recruitmentEndTime = recruitmentEndTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    //public LocalDate getDate() {
     //   return date;
    //}

    //public void setDate(LocalDate date) {
     //   this.date = date;
    //}

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getMinAge() {
        return minAge;
    }

    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    public int getMinLevel() {
        return minLevel;
    }

    public void setMinLevel(int minLevel) {
        this.minLevel = minLevel;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", users=" + users +
                ", field=" + field +
                ", cost=" + cost +
                ", minAge=" + minAge +
                ", maxAge=" + maxAge +
                ", minLevel=" + minLevel +
                ", maxLevel=" + maxLevel +
                ", description='" + description + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", recruitmentEndTime=" + recruitmentEndTime +
                '}';
    }
}
