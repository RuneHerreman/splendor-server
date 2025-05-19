package be.howest.ti.game.logic;

import be.howest.ti.game.logic.gameTools.Development;
import be.howest.ti.game.logic.gameTools.GameState;
import be.howest.ti.game.logic.gameTools.Token;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

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
        game.joinGame("Bob");
        assertTrue(game.isStarted());
        Player player3 = new Player("Charlie");

       // game.addPlayer(player3);

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
    @Test
    void testPlayerTakesTokensFromMarket_TwoOfSameTokenType() {
        game.joinGame("Bob");
        Map<Token, Integer> tokens = new HashMap<>();
        tokens.put(Token.Diamond, 2);

        boolean result = game.handleTokenPurchase(tokens);

        assertTrue(result);
        assertEquals(2, game.getMarket().getUnclaimedTokens().get(Token.Diamond));
        assertEquals(4, game.getMarket().getUnclaimedTokens().getOrDefault(Token.Emerald, 0));
        assertEquals(4, game.getMarket().getUnclaimedTokens().getOrDefault(Token.Onyx, 0));
        assertEquals(4, game.getMarket().getUnclaimedTokens().getOrDefault(Token.Ruby, 0));
        assertEquals(4, game.getMarket().getUnclaimedTokens().getOrDefault(Token.Sapphire, 0));
        assertEquals(5, game.getMarket().getUnclaimedTokens().getOrDefault(Token.Gold, 0));

        assertEquals(2, player1.getTokens().get(Token.Diamond));
        assertNull( player1.getTokens().get(Token.Emerald));
        assertNull( player1.getTokens().get(Token.Onyx));
        assertNull( player1.getTokens().get(Token.Ruby));
        assertNull(player1.getTokens().get(Token.Sapphire));

    }
    @Test
    void testPlayerTakesTokensFromMarket_TwoOfSameTokenType_FailsIfNotEnoughTokensInMarket() {
        game.joinGame("Bob");
        Map<Token, Integer> tokens = new HashMap<>();
        tokens.put(Token.Diamond, 2);

        game.getMarket().removeTokensFromMarket(tokens);
        boolean result = game.handleTokenPurchase(tokens);

        assertFalse(result);
        assertEquals(2, game.getMarket().getUnclaimedTokens().get(Token.Diamond));
        assertEquals(4, game.getMarket().getUnclaimedTokens().getOrDefault(Token.Emerald, 0));
        assertEquals(4, game.getMarket().getUnclaimedTokens().getOrDefault(Token.Onyx, 0));
        assertEquals(4, game.getMarket().getUnclaimedTokens().getOrDefault(Token.Ruby, 0));
        assertEquals(4, game.getMarket().getUnclaimedTokens().getOrDefault(Token.Sapphire, 0));
        assertEquals(5, game.getMarket().getUnclaimedTokens().getOrDefault(Token.Gold, 0));
        assertNull(player1.getTokens().get(Token.Diamond));
    }

    @Test
    public void testPlayerTakesTokensFromMarket_OneOfEachType() {
        game.joinGame("Bob");
        Map<Token, Integer> requestedTokens = new HashMap<>();
        requestedTokens.put(Token.Onyx, 1);
        requestedTokens.put(Token.Emerald, 1);
        requestedTokens.put(Token.Diamond, 1);

        assertTrue(game.getMarket().areTokensAvailableInMarket(requestedTokens));
        game.handleTokenPurchase(requestedTokens);

        assertEquals(1, player1.getTokens().get(Token.Onyx));
        assertEquals(1, player1.getTokens().get(Token.Emerald));
        assertEquals(1, player1.getTokens().get(Token.Diamond));

        assertEquals(3, game.getMarket().getUnclaimedTokens().get(Token.Onyx));
        assertEquals(3, game.getMarket().getUnclaimedTokens().get(Token.Emerald));
        assertEquals(3, game.getMarket().getUnclaimedTokens().get(Token.Diamond));
        assertEquals("Bob", game.getActivePlayer().getName());
    }
    @Test
    public void testPlayerTakesTokensFromMarket_OneOfEachType_failsIfNotEnoughTokensInMarket() {
        game.joinGame("Bob");

        Map<Token, Integer> toRemove = new HashMap<>();
        toRemove.put(Token.Onyx, 4);
        toRemove.put(Token.Emerald, 1);
        toRemove.put(Token.Diamond, 1);

        Map<Token, Integer> requestedTokens = new HashMap<>();
        requestedTokens.put(Token.Onyx, 1);
        requestedTokens.put(Token.Emerald, 1);
        requestedTokens.put(Token.Diamond, 1);

        game.getMarket().removeTokensFromMarket(toRemove);
        assertFalse(game.getMarket().areTokensAvailableInMarket(requestedTokens));
        game.handleTokenPurchase(requestedTokens);

        assertNull( player1.getTokens().get(Token.Onyx));
        assertNull( player1.getTokens().get(Token.Emerald));
        assertNull( player1.getTokens().get(Token.Diamond));

        assertEquals(0, game.getMarket().getUnclaimedTokens().get(Token.Onyx));
        assertEquals(3, game.getMarket().getUnclaimedTokens().get(Token.Emerald));
        assertEquals(3, game.getMarket().getUnclaimedTokens().get(Token.Diamond));
        assertEquals("Alice", game.getActivePlayer().getName());
    }
    @Test
    void testHandleTokenReturn_Success() {
        game.joinGame("Bob");

        Map<Token, Integer> tokensToAdd = new HashMap<>();
        tokensToAdd.put(Token.Emerald, 3);
        player1.addTokens(tokensToAdd);

        Map<Token, Integer> tokensToReturn = new HashMap<>();
        tokensToReturn.put(Token.Emerald, 2);

        boolean result = game.handleTokenReturn(tokensToReturn);

        assertTrue(result);
        assertEquals(1, player1.getTokens().getOrDefault(Token.Emerald, 0));
        assertEquals(6, game.getMarket().getUnclaimedTokens().get(Token.Emerald));
    }
    @Test
    void testHandleTokenReturn_Fails_PlayerDoesNotHaveTokens() {
        game.joinGame("Bob");

        Map<Token, Integer> tokensToReturn = new HashMap<>();
        tokensToReturn.put(Token.Ruby, 2);

        boolean result = game.handleTokenReturn(tokensToReturn);

        assertFalse(result);
        assertNull(player1.getTokens().get(Token.Ruby));
        assertEquals(4, game.getMarket().getUnclaimedTokens().getOrDefault(Token.Ruby, 0));
    }

    @Test
    void testHandleTokenReturn_SwitchesTurn() {
        game.joinGame("Bob");

        Map<Token, Integer> tokensToAdd = new HashMap<>();
        tokensToAdd.put(Token.Onyx, 2);
        player1.addTokens(tokensToAdd);

        Map<Token, Integer> tokensToReturn = new HashMap<>();
        tokensToReturn.put(Token.Onyx, 2);

        assertEquals("Alice", game.getActivePlayer().getName());
        game.handleTokenReturn(tokensToReturn);
        assertEquals("Bob", game.getActivePlayer().getName());
    }
    private Development createTestDevelopmentCard() {
      return game.getMarket().getCardsAvailableInMarket().get(0).get(0);

    }

    @Test
    void purchaseWithExactTokensSucceeds() {
        Development dev = createTestDevelopmentCard();
        game.joinGame("Bob");
        Map<Token, Integer> tokensToAdd = dev.getCost();
        player1.addTokens(tokensToAdd);

        Map<Token, Integer> tokensProvided = new HashMap<>();
        tokensProvided.put(Token.Diamond, 1);
        tokensProvided.put(Token.Emerald, 1);

        boolean result = game.handleDevelopmentCardPurchase(dev, false, tokensProvided);
        assertTrue(result);
        assertTrue(player1.getPurchasedDevelopments().contains(dev));
        assertEquals("Bob", game.getActivePlayer().getName());
        assertEquals(dev.getPrestigePoints(), player1.getPrestigePoints());
    }

    @Test
    void purchaseWithGoldTokensSucceeds() {
        Development dev = createTestDevelopmentCard();
        game.joinGame("Bob");
        Map<Token, Integer> tokensToAdd = dev.getCost();

        player1.addTokens(tokensToAdd);

        Map<Token, Integer> tokensProvided = new HashMap<>();
        tokensProvided.put(Token.Emerald, 1);
        tokensProvided.put(Token.Gold, 1);
        boolean result = game.handleDevelopmentCardPurchase(dev, false, tokensProvided);
        assertTrue(result);
        assertTrue(player1.getPurchasedDevelopments().contains(dev));
        assertEquals("Bob", game.getActivePlayer().getName());
        assertEquals(dev.getPrestigePoints(), player1.getPrestigePoints());
    }


    @Test
    void purchaseFailsIfNotEnoughTokens() {
        Development dev = createTestDevelopmentCard();
        player1.addTokens(new HashMap<>());

        Map<Token, Integer> tokensProvided = new HashMap<>();
        tokensProvided.put(Token.Diamond, 1);
        tokensProvided.put(Token.Emerald, 1);

        boolean result = game.handleDevelopmentCardPurchase(dev, false, tokensProvided);
        assertFalse(result);
        assertFalse(player1.getPurchasedDevelopments().contains(dev));
        assertEquals(player1, game.getActivePlayer());
    }

}






