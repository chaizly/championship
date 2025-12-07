package com.championship;

import java.util.*;
import java.util.stream.Collectors;

public class ChampionshipResolver implements IResolver {
    private List<Player> players;
    private DataLoader dataLoader;
    
    public void setDataLoader(DataLoader dataLoader) {
        this.dataLoader = dataLoader;
    }
    
    public void loadData() {
        if (dataLoader != null) {
            players = dataLoader.loadPlayers();
        } else {
            players = new ArrayList<>();
        }
    }
    
    @Override
    public long countPlayersWithoutAgency() {
        return players.stream()
            .filter(p -> p.getAgency() == null || 
                        p.getAgency().isEmpty() || 
                        p.getAgency().equalsIgnoreCase("нет"))
            .count();
    }
    
    @Override
    public int maxGoalsByDefender() {
        return players.stream()
            .filter(p -> p.getPosition().equalsIgnoreCase("защитник") ||
                        p.getPosition().equalsIgnoreCase("defender"))
            .mapToInt(Player::getGoals)
            .max()
            .orElse(0);
    }
    
    @Override
    public String russianPositionOfMostExpensiveGermanPlayer() {
        return players.stream()
            .filter(Player::isGerman)
            .max(Comparator.comparingDouble(Player::getTransferValue))
            .map(player -> translatePositionToRussian(player.getPosition()))
            .orElse("Игрок не найден");
    }
    
    private String translatePositionToRussian(String position) {
        if (position == null) return "Неизвестно";
        switch(position.toLowerCase()) {
            case "goalkeeper":
            case "вратарь":
                return "Вратарь";
            case "defender":
            case "защитник":
                return "Защитник";
            case "midfielder":
            case "полузащитник":
                return "Полузащитник";
            case "forward":
            case "нападающий":
                return "Нападающий";
            default:
                return position;
        }
    }
    
    @Override
    public Map<String, List<String>> groupPlayersByPosition() {
        return players.stream()
            .collect(Collectors.groupingBy(
                Player::getPosition,
                Collectors.mapping(Player::getName, Collectors.toList())
            ));
    }
    
    @Override
    public Set<String> getUniqueTeams() {
        return players.stream()
            .map(Player::getTeam)
            .filter(team -> team != null && !team.isEmpty())
            .collect(Collectors.toSet());
    }
    
    @Override
    public List<TeamGoals> getTop5TeamsByGoals() {
        Map<String, Integer> teamGoals = players.stream()
            .collect(Collectors.groupingBy(
                Player::getTeam,
                Collectors.summingInt(Player::getGoals)
            ));
        
        return teamGoals.entrySet().stream()
            .filter(entry -> !entry.getKey().isEmpty())
            .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
            .limit(5)
            .map(entry -> new TeamGoals(entry.getKey(), entry.getValue()))
            .collect(Collectors.toList());
    }
    
    @Override
    public String getAgencyWithMinTotalValue() {
        Map<String, Double> agencyValues = players.stream()
            .filter(p -> p.getAgency() != null && !p.getAgency().isEmpty() && 
                        !p.getAgency().equalsIgnoreCase("нет"))
            .collect(Collectors.groupingBy(
                Player::getAgency,
                Collectors.summingDouble(Player::getTransferValue)
            ));
        
        if (agencyValues.isEmpty()) {
            return "Агентства не найдены";
        }
        
        return agencyValues.entrySet().stream()
            .min(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse("Агентство не найдено");
    }
    
    @Override
    public String getTeamWithHighestAvgRedCards() {
        Map<String, List<Player>> playersByTeam = players.stream()
            .filter(p -> !p.getTeam().isEmpty())
            .collect(Collectors.groupingBy(Player::getTeam));
        
        if (playersByTeam.isEmpty()) {
            return "Команды не найдены";
        }
        
        return playersByTeam.entrySet().stream()
            .map(entry -> {
                String team = entry.getKey();
                double avgRedCards = entry.getValue().stream()
                    .mapToInt(Player::getRedCards)
                    .average()
                    .orElse(0.0);
                return new AbstractMap.SimpleEntry<>(team, avgRedCards);
            })
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse("Команда не найдена");
    }
}
