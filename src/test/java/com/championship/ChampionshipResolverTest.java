package com.championship;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ChampionshipResolverTest {
    
    @Mock
    private DataLoader dataLoader;
    
    private ChampionshipResolver resolver;
    private List<Player> testPlayers;
    
    @BeforeEach
    void setUp() {
        resolver = new ChampionshipResolver();
        resolver.setDataLoader(dataLoader);
        
        // Создаем тестовых игроков
        testPlayers = Arrays.asList(
            // Игроки без агентства
            new Player("Иван Иванов", "Спартак Москва", "защитник", "", 5000000, 20, 5, 3, 2, 0),
            new Player("Петр Петров", "Динамо Москва", "нападающий", "нет", 3000000, 18, 8, 4, 1, 0),
            
            // Немецкие игроки
            new Player("Томас Мюллер", "Бавария Германия", "нападающий", "Agency1", 15000000, 30, 20, 15, 3, 0),
            new Player("Мануэль Нойер", "Бавария Германия", "вратарь", "Agency2", 12000000, 28, 0, 0, 1, 0),
            
            // Защитники
            new Player("Серхио Рамос", "Реал Мадрид", "защитник", "Agency3", 10000000, 25, 10, 2, 10, 2),
            new Player("Виридиус ван Дейк", "Ливерпуль", "защитник", "Agency4", 8000000, 22, 3, 1, 4, 0),
            
            // Для теста команд
            new Player("Лионель Месси", "Барселона Испания", "нападающий", "Agency5", 90000000, 35, 30, 10, 0, 0),
            new Player("Луис Суарес", "Барселона Испания", "нападающий", "Agency5", 50000000, 32, 25, 8, 3, 0)
        );
        
        when(dataLoader.loadPlayers()).thenReturn(testPlayers);
        resolver.loadData();
    }
    
    @Test
    void testCountPlayersWithoutAgency() {
        // Act
        long result = resolver.countPlayersWithoutAgency();
        
        // Assert
        assertEquals(2, result, "Должно быть 2 игрока без агентства");
        verify(dataLoader, times(1)).loadPlayers();
    }
    
    @Test
    void testMaxGoalsByDefender() {
        // Act
        int result = resolver.maxGoalsByDefender();
        
        // Assert
        assertEquals(10, result, "Максимум голов у защитника должен быть 10");
    }
    
    @Test
    void testRussianPositionOfMostExpensiveGermanPlayer() {
        // Act
        String result = resolver.russianPositionOfMostExpensiveGermanPlayer();
        
        // Assert
        assertEquals("Нападающий", result, "Самый дорогой немецкий игрок - нападающий");
    }
    
    @Test
    void testGroupPlayersByPosition() {
        // Act
        Map<String, List<String>> result = resolver.groupPlayersByPosition();
        
        // Assert
        assertNotNull(result);
        assertTrue(result.containsKey("защитник"));
        assertTrue(result.containsKey("нападающий"));
        assertTrue(result.containsKey("вратарь"));
        
        assertEquals(2, result.get("защитник").size());
        assertEquals(3, result.get("нападающий").size()); // Петров, Мюллер, Месси, Суарес
    }
    
    @Test
    void testGetUniqueTeams() {
        // Act
        Set<String> result = resolver.getUniqueTeams();
        
        // Assert
        assertNotNull(result);
        assertEquals(5, result.size());
        assertTrue(result.contains("Спартак"));
        assertTrue(result.contains("Бавария"));
        assertTrue(result.contains("Барселона"));
    }
    
    @Test
    void testGetTop5TeamsByGoals() {
        // Act
        List<TeamGoals> result = resolver.getTop5TeamsByGoals();
        
        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        
        // Барселона должна быть на первом месте (30+25=55 голов)
        assertEquals("Барселона", result.get(0).getTeam());
        assertEquals(55, result.get(0).getTotalGoals());
    }
    
    @Test
    void testGetAgencyWithMinTotalValue() {
        // Act
        String result = resolver.getAgencyWithMinTotalValue();
        
        // Assert
        // Agency1: 15M, Agency2: 12M, Agency3: 10M, Agency4: 8M, Agency5: 90M+50M=140M
        assertEquals("Agency4", result);
    }
    
    @Test
    void testGetTeamWithHighestAvgRedCards() {
        // Act
        String result = resolver.getTeamWithHighestAvgRedCards();
        
        // Assert
        // У Реал Мадрид 2 красные карточки у одного игрока = среднее 2.0
        assertEquals("Реал", result);
    }
    
    @Test
    void testCountPlayersWithoutAgency_EmptyList() {
        // Arrange
        when(dataLoader.loadPlayers()).thenReturn(Collections.emptyList());
        resolver.loadData();
        
        // Act
        long result = resolver.countPlayersWithoutAgency();
        
        // Assert
        assertEquals(0, result);
    }
    
    @Test
    void testRussianPositionOfMostExpensiveGermanPlayer_NoGerman() {
        // Arrange
        List<Player> noGermans = Arrays.asList(
            new Player("Игрок1", "Спартак Россия", "защитник", "Agency", 1000, 10, 5, 0, 0, 0)
        );
        when(dataLoader.loadPlayers()).thenReturn(noGermans);
        resolver.loadData();
        
        // Act
        String result = resolver.russianPositionOfMostExpensiveGermanPlayer();
        
        // Assert
        assertEquals("Игрок не найден", result);
    }
}
