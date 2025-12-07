package com.championship;

public class Player {
    private String name;
    private String teamAndCity;
    private String position;
    private String agency;
    private double transferValue;
    private int matchesPlayed;
    private int goals;
    private int assists;
    private int yellowCards;
    private int redCards;

    public Player(String name, String teamAndCity, String position, String agency,
                  double transferValue, int matchesPlayed, int goals, int assists,
                  int yellowCards, int redCards) {
        this.name = name;
        this.teamAndCity = teamAndCity;
        this.position = position;
        this.agency = agency;
        this.transferValue = transferValue;
        this.matchesPlayed = matchesPlayed;
        this.goals = goals;
        this.assists = assists;
        this.yellowCards = yellowCards;
        this.redCards = redCards;
    }

    public String getName() { return name; }
    public String getTeamAndCity() { return teamAndCity; }
    public String getPosition() { return position; }
    public String getAgency() { return agency; }
    public double getTransferValue() { return transferValue; }
    public int getMatchesPlayed() { return matchesPlayed; }
    public int getGoals() { return goals; }
    public int getAssists() { return assists; }
    public int getYellowCards() { return yellowCards; }
    public int getRedCards() { return redCards; }

    public String getTeam() {
        if (teamAndCity == null || teamAndCity.isEmpty()) {
            return "";
        }
        String[] parts = teamAndCity.split(" ");
        return parts.length > 0 ? parts[0] : "";
    }

    public boolean isGerman() {
        if (teamAndCity == null) return false;
        return teamAndCity.toLowerCase().contains("германия") || 
               teamAndCity.toLowerCase().contains("germany");
    }

    @Override
    public String toString() {
        return "Player{name='" + name + "'}";
    }
}
