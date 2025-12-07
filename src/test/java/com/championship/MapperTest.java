package com.championship;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MapperTest {
    
    @Mock
    private DataLoader dataLoader;
    
    private Mapper mapper;
    
    @BeforeEach
    void setUp() {
        mapper = new Mapper();
        
        List<Player> testPlayers = Arrays.asList(
            new Player("Игрок1", "Команда1", "защитник", "Agency1", 5000000, 20, 5, 3, 2, 0),
            new Player("Игрок2", "Команда1", "нападающий", "Agency2", 10000000, 25, 15, 10, 1, 0),
            new Player("Игрок3", "Команда2", "защитник", "Agency1", 3000000, 18, 3, 8, 3, 1),
            new Player("Игрок4", "Команда2", "нападающий", "Agency3", 7000000, 22, 12, 5, 2, 0)
        );
        
        when(dataLoader.loadPlayers()).thenReturn(testPlayers);
        mapper.setDataLoader(dataLoader);
    }
    
    @Test
    void testPrepareGoalsPerPositionData() {
        // Act
        Map<String, Double> result = mapper.prepareGoalsPerPositionData();
        
        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        
        // Среднее у защитников: (5 + 3) / 2 = 4.0
        assertEquals(4.0, result.get("защитник"), 0.001);
        
        // Среднее у нападающих: (15 + 12) / 2 = 13.5
        assertEquals(13.5, result.get("нападающий"), 0.001);
        
        verify(dataLoader, times(1)).loadPlayers();
    }
    
    @Test
    void testPreparePlayersPerTeamData() {
        // Act
        Map<String, Long> result = mapper.preparePlayersPerTeamData();
        
        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        
        assertEquals(2, result.get("Команда1"));
        assertEquals(2, result.get("Команда2"));
    }
    
    @Test
    void testPrepareCardsPerPositionData() {
        // Act
        Map<String, Double> result = mapper.prepareCardsPerPositionData();
        
        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        
        // Защитники: (2 желтых + 0 красных*2) и (3 желтых + 1 красная*2) = 2 и 5, среднее 3.5
        assertEquals(3.5, result.get("защитник"), 0.001);
        
        // Нападающие: (1 желтая) и (2 желтых) = 1 и 2, среднее 1.5
        assertEquals(1.5, result.get("нападающий"), 0.001);
    }
    
    @Test
    void testSetDataLoader_Null() {
        // Act & Assert
        assertDoesNotThrow(() -> mapper.setDataLoader(null));
    }
}
