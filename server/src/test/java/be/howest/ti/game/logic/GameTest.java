package be.howest.ti.game.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    private Game game;
    private Player player1;
    private Player player2;
    private List<Player> players;

    @BeforeEach
    void setUp() {
        player1 = new Player("Alice", 1);
        player2 = new Player("Bob", 1);
        players = new ArrayList<>();
        players.add(player1);
        players.add(player2);

        game = new Game("TestGame", 1, players);
    }

    @Test
    void testConstructorInitializesFieldsCorrectly() {
        assertEquals("TestGame", game.getGameName());
        assertEquals(1, game.getGameId());
        assertFalse(game.isStarted());
        assertEquals(2, game.getNumberOfPlayers());
        assertEquals(player1, game.getActivePlayer());
        assertEquals(players, game.getPlayers());
        assertNotNull(game.getMarket());
        assertNull(game.getWinner());
    }

    @Test
    void testAddPlayerDoesNotAddWhenGameIsFull() {
        Player newPlayer = new Player("Charlie", 1);
        game.addPlayer(newPlayer);
        // The players list should not be modified because it's already full
        assertEquals(2, game.getPlayers().size());
        assertFalse(game.getPlayers().contains(newPlayer));
    }

    @Test
    void testSwitchTurnCyclesBetweenPlayers() {
        assertEquals(player1, game.getActivePlayer());
        game.switchTurn();
        assertEquals(player2, game.getActivePlayer());
        game.switchTurn();
        assertEquals(player1, game.getActivePlayer()); // back to player1
    }

    @Test
    void testToStringContainsImportantInfo() {
        String result = game.toString();
        assertTrue(result.contains("TestGame"));
        assertTrue(result.contains("gameId=1"));
        assertTrue(result.contains("Alice") || result.contains("Bob"));
    }

    @Test
    void testGettersReturnExpectedDefaults() {
        assertNull(game.getGameState());
        assertNull(game.getUnclaimedTokens());
        assertNull(game.getUnclaimedNobles());
    }
}
