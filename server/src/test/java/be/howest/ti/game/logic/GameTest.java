package be.howest.ti.game.logic;
import be.howest.ti.game.logic.gameTools.Development;
import be.howest.ti.game.logic.gameTools.GameState;
import be.howest.ti.game.logic.gameTools.Token;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.EnumMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    private Game game;
    private Player player1;


    @BeforeEach
    void setUp() {
        player1 = new Player("Alice");
        game = new Game("TestGame", 1, 2, player1,true);

    }

    @Test
    void testConstructorInitializesFieldsCorrectly() {
        assertEquals("TestGame", game.getGameName());
        assertEquals(1, game.getGameId());
        assertFalse(game.isStarted());
        assertEquals(2, game.getNumberOfPlayers());
        assertEquals(player1, game.getActivePlayer());
        assertEquals(1, game.getPlayers().size());
        assertEquals(player1, game.getPlayers().getFirst());
        assertNotNull(game.getMarket());
        assertNull(game.getWinner());
    }

    @Test
    void testAddPlayerDoesNotAddWhenGameIsFullOrAlreadyStarted() {
        game.joinGame("Bob");
        assertTrue(game.isStarted());
        Player player3 = new Player("Charlie");

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
        assertEquals(GameState.TURN_ACTION, game.getGameState());
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
        Map<Token, Integer> tokens = new EnumMap<>(Token.class);
        tokens.put(Token.DIAMOND, 2);

        boolean result = game.handleTokenPurchase(tokens);

        assertTrue(result);
        assertEquals(2, game.getMarket().getUnclaimedTokens().get(Token.DIAMOND));
        assertEquals(4, game.getMarket().getUnclaimedTokens().getOrDefault(Token.EMERALD, 0));
        assertEquals(4, game.getMarket().getUnclaimedTokens().getOrDefault(Token.ONYX, 0));
        assertEquals(4, game.getMarket().getUnclaimedTokens().getOrDefault(Token.RUBY, 0));
        assertEquals(4, game.getMarket().getUnclaimedTokens().getOrDefault(Token.SAPPHIRE, 0));
        assertEquals(5, game.getMarket().getUnclaimedTokens().getOrDefault(Token.GOLD, 0));

        assertEquals(2, player1.getTokens().get(Token.DIAMOND));
        assertNull( player1.getTokens().get(Token.EMERALD));
        assertNull( player1.getTokens().get(Token.ONYX));
        assertNull( player1.getTokens().get(Token.RUBY));
        assertNull(player1.getTokens().get(Token.SAPPHIRE));

    }
    @Test
    void testPlayerTakesTokensFromMarket_TwoOfSameTokenType_FailsIfNotEnoughTokensInMarket() {
        game.joinGame("Bob");
        Map<Token, Integer> tokens = new EnumMap<>(Token.class);
        tokens.put(Token.DIAMOND, 2);

        game.getMarket().removeTokensFromMarket(tokens);
        boolean result = game.handleTokenPurchase(tokens);

        assertFalse(result);
        assertEquals(2, game.getMarket().getUnclaimedTokens().get(Token.DIAMOND));
        assertEquals(4, game.getMarket().getUnclaimedTokens().getOrDefault(Token.EMERALD, 0));
        assertEquals(4, game.getMarket().getUnclaimedTokens().getOrDefault(Token.ONYX, 0));
        assertEquals(4, game.getMarket().getUnclaimedTokens().getOrDefault(Token.RUBY, 0));
        assertEquals(4, game.getMarket().getUnclaimedTokens().getOrDefault(Token.SAPPHIRE, 0));
        assertEquals(5, game.getMarket().getUnclaimedTokens().getOrDefault(Token.GOLD, 0));
        assertNull(player1.getTokens().get(Token.DIAMOND));
    }

    @Test
     void testPlayerTakesTokensFromMarket_OneOfEachType() {
        game.joinGame("Bob");
        Map<Token, Integer> requestedTokens = new EnumMap<>(Token.class);
        requestedTokens.put(Token.ONYX, 1);
        requestedTokens.put(Token.EMERALD, 1);
        requestedTokens.put(Token.DIAMOND, 1);

        assertTrue(game.getMarket().areTokensAvailableInMarket(requestedTokens));
        game.handleTokenPurchase(requestedTokens);

        assertEquals(1, player1.getTokens().get(Token.ONYX));
        assertEquals(1, player1.getTokens().get(Token.EMERALD));
        assertEquals(1, player1.getTokens().get(Token.DIAMOND));

        assertEquals(3, game.getMarket().getUnclaimedTokens().get(Token.ONYX));
        assertEquals(3, game.getMarket().getUnclaimedTokens().get(Token.EMERALD));
        assertEquals(3, game.getMarket().getUnclaimedTokens().get(Token.DIAMOND));
        assertEquals("Bob", game.getActivePlayer().getName());
    }
    @Test
     void testPlayerTakesTokensFromMarket_OneOfEachType_failsIfNotEnoughTokensInMarket() {
        game.joinGame("Bob");

        Map<Token, Integer> toRemove = new EnumMap<>(Token.class);
        toRemove.put(Token.ONYX, 4);
        toRemove.put(Token.EMERALD, 1);
        toRemove.put(Token.DIAMOND, 1);

        Map<Token, Integer> requestedTokens = new EnumMap<>(Token.class);
        requestedTokens.put(Token.ONYX, 1);
        requestedTokens.put(Token.EMERALD, 1);
        requestedTokens.put(Token.DIAMOND, 1);

        game.getMarket().removeTokensFromMarket(toRemove);
        assertFalse(game.getMarket().areTokensAvailableInMarket(requestedTokens));
        game.handleTokenPurchase(requestedTokens);

        assertNull( player1.getTokens().get(Token.ONYX));
        assertNull( player1.getTokens().get(Token.EMERALD));
        assertNull( player1.getTokens().get(Token.DIAMOND));

        assertEquals(0, game.getMarket().getUnclaimedTokens().get(Token.ONYX));
        assertEquals(3, game.getMarket().getUnclaimedTokens().get(Token.EMERALD));
        assertEquals(3, game.getMarket().getUnclaimedTokens().get(Token.EMERALD));
        assertEquals("Alice", game.getActivePlayer().getName());
    }
    @Test
    void testHandleTokenReturn_Success() {
        game.joinGame("Bob");

        Map<Token, Integer> tokensToAdd = new EnumMap<>(Token.class);
        tokensToAdd.put(Token.EMERALD, 3);
        player1.addTokens(tokensToAdd);

        Map<Token, Integer> tokensToReturn = new EnumMap<>(Token.class);
        tokensToReturn.put(Token.EMERALD, 2);

        boolean result = game.handleTokenReturn(tokensToReturn);

        assertTrue(result);
        assertEquals(1, player1.getTokens().getOrDefault(Token.EMERALD, 0));
        assertEquals(6, game.getMarket().getUnclaimedTokens().get(Token.EMERALD));
    }
    @Test
    void testHandleTokenReturn_Fails_PlayerDoesNotHaveTokens() {
        game.joinGame("Bob");

        Map<Token, Integer> tokensToReturn = new EnumMap<>(Token.class);
        tokensToReturn.put(Token.RUBY, 2);

        boolean result = game.handleTokenReturn(tokensToReturn);

        assertFalse(result);
        assertNull(player1.getTokens().get(Token.RUBY));
        assertEquals(4, game.getMarket().getUnclaimedTokens().getOrDefault(Token.RUBY, 0));
    }

    @Test
    void testHandleTokenReturn_SwitchesTurn() {
        game.joinGame("Bob");

        Map<Token, Integer> tokensToAdd = new EnumMap<>(Token.class);
        tokensToAdd.put(Token.ONYX, 2);
        player1.addTokens(tokensToAdd);

        Map<Token, Integer> tokensToReturn = new EnumMap<>(Token.class);
        tokensToReturn.put(Token.ONYX, 2);

        assertEquals("Alice", game.getActivePlayer().getName());
        game.handleTokenReturn(tokensToReturn);
        assertEquals("Bob", game.getActivePlayer().getName());
    }
    private Development createTestDevelopmentCard() {
      return game.getMarket().getCardsAvailableInMarket().getFirst().getFirst();

    }

    @Test
    void purchaseWithExactTokensSucceeds() {
        System.out.println(game.getMarket().getUnclaimedTokens());
        System.out.println(game.getMarket().getAllCards().getFirst().size());
        System.out.println(game.getMarket().getAllCards().get(1).size());
        System.out.println(game.getMarket().getAllCards().get(2).size());
        game.joinGame("Bob");
        game.startGame();
        Development dev = createTestDevelopmentCard();
        Map<Token, Integer> tokensToAdd = dev.getCost();
        player1.addTokens(tokensToAdd);

        Map<Token, Integer> tokensProvided = new EnumMap<>(Token.class);
        tokensProvided.put(Token.DIAMOND, 1);
        tokensProvided.put(Token.EMERALD, 1);

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

        Map<Token, Integer> tokensProvided = new EnumMap<>(Token.class);
        tokensProvided.put(Token.EMERALD, 1);
        tokensProvided.put(Token.GOLD, 1);
        boolean result = game.handleDevelopmentCardPurchase(dev, false, tokensProvided);
        assertTrue(result);
        assertTrue(player1.getPurchasedDevelopments().contains(dev));
        assertEquals("Bob", game.getActivePlayer().getName());
        assertEquals(dev.getPrestigePoints(), player1.getPrestigePoints());
    }

    @Test
    void purchaseFailsIfNotEnoughTokens() {
        Development dev = createTestDevelopmentCard();
        player1.addTokens(new EnumMap<>(Token.class));

        Map<Token, Integer> tokensProvided = new EnumMap<>(Token.class);
        tokensProvided.put(Token.DIAMOND, 1);
        tokensProvided.put(Token.EMERALD, 1);

        boolean result = game.handleDevelopmentCardPurchase(dev, false, tokensProvided);
        assertFalse(result);
        assertFalse(player1.getPurchasedDevelopments().contains(dev));
        assertEquals(player1, game.getActivePlayer());
    }

    @Test
    void testEndGame() {
        game.joinGame("Bob");

        game.endGame();

        assertFalse(game.getActive(), "Game should be inactive after ending.");
        assertTrue(game.isStarted(), "Game should be marked as started.");


    }

}






