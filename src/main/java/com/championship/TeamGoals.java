package com.championship;

public class TeamGoals {
    private String team;
    private int totalGoals;

    public TeamGoals(String team, int totalGoals) {
        this.team = team;
        this.totalGoals = totalGoals;
    }

    public String getTeam() { return team; }
    public int getTotalGoals() { return totalGoals; }

    @Override
    public String toString() {
        return team + " - " + totalGoals + " голов";
    }
}
