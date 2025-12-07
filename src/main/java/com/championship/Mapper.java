package com.championship;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Mapper {
    private DataLoader dataLoader;
    private List<Player> players;
    
    public void setDataLoader(DataLoader dataLoader) {
        this.dataLoader = dataLoader;
        if (dataLoader != null) {
            players = dataLoader.loadPlayers();
        }
    }
    
    public Map<String, Double> prepareGoalsPerPositionData() {
        return players.stream()
            .collect(Collectors.groupingBy(
                Player::getPosition,
                Collectors.averagingDouble(Player::getGoals)
            ));
    }
    
    public Map<String, Long> preparePlayersPerTeamData() {
        return players.stream()
            .collect(Collectors.groupingBy(
                Player::getTeam,
                Collectors.counting()
            ));
    }
    
    public Map<String, Double> prepareCardsPerPositionData() {
        return players.stream()
            .collect(Collectors.groupingBy(
                Player::getPosition,
                Collectors.averagingDouble(p -> p.getYellowCards() + p.getRedCards() * 2)
            ));
    }
}
