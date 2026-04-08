package com.example.arnonfinalhta;

public class Team {
    public int position;
    public String name;
    public int points;
    public int goalDiff;

    public Team(int position, String name, int points, int goalDiff) {
        this.position = position;
        this.name = name;
        this.points = points;
        this.goalDiff = goalDiff;
    }
}