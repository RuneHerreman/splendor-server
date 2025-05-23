package be.howest.ti.game.logic.service;

import be.howest.ti.game.logic.Game;
import be.howest.ti.game.logic.gameTools.Market;
import be.howest.ti.game.logic.gameTools.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SplendorServiceImplTest {
    private SplendorServiceImpl service;
    private String gameName;
    private Player john;
    private int numberOfPlayers;

    @BeforeEach
    void setUp() {
        service = new SplendorServiceImpl();
        gameName = "Test Game";
        john = new Player("John Doe", ".");
        numberOfPlayers = 4;
    }

    @Test
    void createGame() {
        Game game = service.createGame(numberOfPlayers, john, true);

        assertEquals(1, service.getAllGames().size());
        assertEquals(4, game.getNumberOfPlayers());
        assertNotNull(game.getPlayers());
    }

    @Test
    void createGameWithName() {
        Game game = service.createGame(gameName, numberOfPlayers, john, true);

        assertEquals(1, service.getAllGames().size());
        assertEquals("Test Game", game.getGameName());
        assertEquals(4, game.getNumberOfPlayers());
        assertNotNull(game.getPlayers());
    }

    @Test
    void deleteGame() {
        Game game = service.createGame(gameName, numberOfPlayers, john, true);

        service.deleteGame(game.getGameId());

        assertEquals(0, service.getAllGames().size());
    }

    @Test
    void deleteAllGames() {
        service.createGame(gameName, numberOfPlayers, john, true);
        service.createGame(gameName, numberOfPlayers, john, true);
        service.createGame(gameName, numberOfPlayers, john, true);
        service.createGame(gameName, numberOfPlayers, john, true);
        service.createGame(gameName, numberOfPlayers, john, true);

        service.deleteAllGames();

        assertEquals(0, service.getAllGames().size());
    }

    @Test
    void getGameByID() {
        Game game = service.createGame(gameName, numberOfPlayers, john, true);

        Game retrievedGame = service.getGameByID(game.getGameId());

        assertEquals(game, retrievedGame);
    }

    @Test
    void getAllGames() {
        service.createGame(gameName, numberOfPlayers, john, true);
        service.createGame(gameName, numberOfPlayers, john, true);
        service.createGame(gameName, numberOfPlayers, john, true);
        service.createGame(gameName, numberOfPlayers, john, true);
        service.createGame(gameName, numberOfPlayers, john, true);

        assertEquals(5, service.getAllGames().size());
    }

    @Test
    void testGetAllGames() {
        Game startedGame = service.createGame(gameName, numberOfPlayers, john, true);
        startedGame.startGame();
        service.createGame(gameName, numberOfPlayers, john, true);
        service.createGame(gameName, numberOfPlayers, john, true);
        service.createGame(gameName, numberOfPlayers, john, true);
        service.createGame(gameName, numberOfPlayers, john, true);

        assertEquals(1, service.getAllGames(true).size());
    }

    @Test
    void chooseNoble_ThrowsIfNobleNull() {
        Game game = service.createGame(gameName, numberOfPlayers, john, true);
        game.startGame();

        assertThrows(IllegalArgumentException.class,
                () -> game.handleChooseNoble(john.getName() ,null ));


    }

    @Test
    void reserveCard_ValidCase() {
        Game game = service.createGame(gameName, numberOfPlayers, john, true);
        game.joinGame("Bob", ".");
        game.startGame();

        assertEquals(john.getName(), game.getActivePlayer().getName());

        String cardName = game.getMarket().getCardsAvailableInMarket().getFirst().getFirst().getName();

        Game result = game.handleReserveCard(john.getName(), cardName);

        assertNotNull(result, "Expected reserveCard to return a non-null Game instance");
        assertEquals(1, john.getReserved().size());
    }

    @Test
    void reserveCard_ThrowsIfCardNotFound() {
        Game game = service.createGame(gameName, numberOfPlayers, john, true);
        game.startGame();

//        assertThrows(IllegalArgumentException.class,
            ///    () -> game.reserveCard(john.getName(), null));


    }

    @Test
    void reserveCard_ThrowsIfNotActivePlayer() {
        Game game = service.createGame(gameName, numberOfPlayers, john,true);
        game.startGame();
        String cardName = Market.createAllCards().getFirst().getFirst().getName();

        assertThrows(IllegalArgumentException.class, () -> game.handleReserveCard("NotJohn", cardName));


    }

    @Test
    void testGetStartedGames() {
        Game game1 = service.createGame("Game 1", numberOfPlayers, john, true);
        Game game2 = service.createGame("Game 2", numberOfPlayers, john, true);
        Game game3 = service.createGame("Game 3", numberOfPlayers, john, true);

        game1.startGame();
        game3.startGame();

        List<Game> startedGames = service.getStartedGames();

        assertEquals(2, startedGames.size(), "Expected 2 started games");
        assertTrue(startedGames.contains(game1), "Started games should include game1");
        assertTrue(startedGames.contains(game3), "Started games should include game3");
        assertFalse(startedGames.contains(game2), "Started games should not include game2");

    }

    @Test
    void testGetNonStartedGames() {
        Game game1 = service.createGame("Game 1", numberOfPlayers, john, true);
        Game game2 = service.createGame("Game 2", numberOfPlayers, john, true);
        Game game3 = service.createGame("Game 3", numberOfPlayers, john, true);

        game1.startGame();

        List<Game> nonStartedGames = service.getNonStartedGames();

        assertEquals(2, nonStartedGames.size(), "Expected 2 non-started games");
        assertTrue(nonStartedGames.contains(game2), "Non-started games should include game2");
        assertTrue(nonStartedGames.contains(game3), "Non-started games should include game3");
        assertFalse(nonStartedGames.contains(game1), "Non-started games should not include game1");
    }
}