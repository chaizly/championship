package com.championship;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class AdditionalTestsForCoverage {
    
    @Test
    void testPlayerConstructorAndGetters() {
        // Arrange & Act
        Player player = new Player(
            "Тест Игрок",
            "Команда Город",
            "защитник",
            "Агентство",
            1000000.0,
            20,
            5,
            3,
            2,
            1
        );
        
        // Assert
        assertEquals("Тест Игрок", player.getName());
        assertEquals("Команда Город", player.getTeamAndCity());
        assertEquals("защитник", player.getPosition());
        assertEquals("Агентство", player.getAgency());
        assertEquals(1000000.0, player.getTransferValue(), 0.001);
        assertEquals(20, player.getMatchesPlayed());
        assertEquals(5, player.getGoals());
        assertEquals(3, player.getAssists());
        assertEquals(2, player.getYellowCards());
        assertEquals(1, player.getRedCards());
    }
    
    @Test
    void testPlayerGetTeam() {
        // Arrange
        Player player1 = new Player("Игрок1", "Спартак Москва", "позиция", "агент", 1000, 10, 5, 0, 0, 0);
        Player player2 = new Player("Игрок2", "", "позиция", "агент", 1000, 10, 5, 0, 0, 0);
        Player player3 = new Player("Игрок3", null, "позиция", "агент", 1000, 10, 5, 0, 0, 0);
        
        // Act & Assert
        assertEquals("Спартак", player1.getTeam());
        assertEquals("", player2.getTeam());
        assertEquals("", player3.getTeam());
    }
    
    @Test
    void testPlayerIsGerman() {
        // Arrange
        Player german1 = new Player("Игрок", "Бавария Германия", "позиция", "агент", 1000, 10, 5, 0, 0, 0);
        Player german2 = new Player("Игрок", "Боруссия Germany", "позиция", "агент", 1000, 10, 5, 0, 0, 0);
        Player notGerman = new Player("Игрок", "Спартак Россия", "позиция", "агент", 1000, 10, 5, 0, 0, 0);
        
        // Act & Assert
        assertTrue(german1.isGerman());
        assertTrue(german2.isGerman());
        assertFalse(notGerman.isGerman());
    }
    
    @Test
    void testTeamGoals() {
        // Arrange & Act
        TeamGoals teamGoals = new TeamGoals("Команда", 25);
        
        // Assert
        assertEquals("Команда", teamGoals.getTeam());
        assertEquals(25, teamGoals.getTotalGoals());
        assertTrue(teamGoals.toString().contains("Команда"));
    }
    
    @Test
    void testChampionshipResolverEdgeCases() {
        // Arrange
        ChampionshipResolver resolver = new ChampionshipResolver();
        
        // Act & Assert - не должно быть исключений при пустых данных
        assertDoesNotThrow(() -> resolver.countPlayersWithoutAgency());
        assertDoesNotThrow(() -> resolver.getUniqueTeams());
    }
    
    @Test
    void testMapperWithEmptyData() {
        // Arrange
        Mapper mapper = new Mapper();
        
        // Act & Assert - не должно быть исключений
        assertDoesNotThrow(() -> mapper.prepareGoalsPerPositionData());
    }
}
