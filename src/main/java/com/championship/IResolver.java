package com.championship;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IResolver {
    long countPlayersWithoutAgency();
    int maxGoalsByDefender();
    String russianPositionOfMostExpensiveGermanPlayer();
    Map<String, List<String>> groupPlayersByPosition();
    Set<String> getUniqueTeams();
    List<TeamGoals> getTop5TeamsByGoals();
    String getAgencyWithMinTotalValue();
    String getTeamWithHighestAvgRedCards();
}
