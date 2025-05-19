package be.howest.ti.game.logic;

import be.howest.ti.game.logic.gametools.GameState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    private Game game;
    private Player player1;

    @BeforeEach
    void setUp() {
        player1 = new Player("Alice");
        game = new Game("TestGame", 1, 2, player1);
    }

    @Test
    void testConstructorInitializesFieldsCorrectly() {
        assertEquals("TestGame", game.getGameName());
        assertEquals(1, game.getGameId());
        assertFalse(game.isStarted());
        assertEquals(2, game.getNumberOfPlayers());
        assertEquals(player1, game.getActivePlayer());
        assertEquals(1, game.getPlayers().size());
        assertEquals(player1, game.getPlayers().get(0));
        assertNotNull(game.getMarket());
        assertNull(game.getWinner());
    }

    @Test
    void testAddPlayerDoesNotAddWhenGameIsFullOrAlreadyStarted() {
        Player player2 = new Player("Bob");
        game.joinGame("Bob");
        assertTrue(game.isStarted());
        Player player3 = new Player("Charlie");

        game.addPlayer(player3);

        assertEquals(2, game.getPlayers().size());
        assertFalse(game.getPlayers().contains(player3));
    }

    @Test
    void testSwitchTurnCyclesBetweenPlayers() {
        game.joinGame("Bob");
        assertEquals(player1, game.getActivePlayer());
        game.switchTurn();
        assertEquals("Bob", game.getActivePlayer().getName());
        game.switchTurn();
        assertEquals("Alice", game.getActivePlayer().getName());
    }

    @Test
    void testStartGameSetsStartedToTrue() {
        assertFalse(game.isStarted());
        game.startGame();
        assertTrue(game.isStarted());
    }

    @Test
    void testGettersReturnExpectedDefaults() {
        assertNull(game.getGameState());
        assertNotNull(game.getUnclaimedTokens());
        assertNotNull(game.getUnclaimedNobles());
        assertFalse(game.isReturnExcessTokensRequired());
        assertFalse(game.isPickNobleRequired());
        assertTrue(game.getActive());
    }

    @Test
    void testJoinGameAddsPlayerAndStartsGame() {
        assertEquals(1, game.getPlayers().size());
        game.joinGame("Charlie");
        assertEquals(2, game.getPlayers().size());
        assertTrue(game.isStarted());
        assertEquals(GameState.TurnAction, game.getGameState());
        assertEquals("Charlie", game.getPlayers().get(1).getName());

    }
    @Test
    void testJoinFullGame(){
        assertEquals(1, game.getPlayers().size());
        game.joinGame("Ben");
        assertEquals(2, game.getPlayers().size());
        assertThrows(IllegalStateException.class, () -> game.joinGame("Charlie"));
    }
}
