package be.howest.ti.game.logic.service;

import be.howest.ti.game.logic.Game;
import be.howest.ti.game.logic.Market;
import be.howest.ti.game.logic.Player;
import be.howest.ti.game.logic.gameTools.Noble;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        john = new Player("John Doe");
        numberOfPlayers = 4;
    }

    @Test
    void createGame() {
        Game game = service.createGame(numberOfPlayers, john);

        assertEquals(1, service.getAllGames().size());
        assertEquals(4, game.getNumberOfPlayers());
        assertNotNull(game.getPlayers());
    }

    @Test
    void createGameWithName() {
        Game game = service.createGame(gameName, numberOfPlayers, john);

        assertEquals(1, service.getAllGames().size());
        assertEquals("Test Game", game.getGameName());
        assertEquals(4, game.getNumberOfPlayers());
        assertNotNull(game.getPlayers());
    }

    @Test
    void deleteGame() {
        Game game = service.createGame(gameName, numberOfPlayers, john);

        service.deleteGame(game.getGameId());

        assertEquals(0, service.getAllGames().size());
    }

    @Test
    void deleteAllGames() {
        service.createGame(gameName, numberOfPlayers, john);
        service.createGame(gameName, numberOfPlayers, john);
        service.createGame(gameName, numberOfPlayers, john);
        service.createGame(gameName, numberOfPlayers, john);
        service.createGame(gameName, numberOfPlayers, john);

        service.deleteAllGames();

        assertEquals(0, service.getAllGames().size());
    }

    @Test
    void getGameByID() {
        Game game = service.createGame(gameName, numberOfPlayers, john);

        Game retrievedGame = service.getGameByID(game.getGameId());

        assertEquals(game, retrievedGame);
    }

    @Test
    void getAllGames() {
        service.createGame(gameName, numberOfPlayers, john);
        service.createGame(gameName, numberOfPlayers, john);
        service.createGame(gameName, numberOfPlayers, john);
        service.createGame(gameName, numberOfPlayers, john);
        service.createGame(gameName, numberOfPlayers, john);

        assertEquals(5, service.getAllGames().size());
    }

    @Test
    void testGetAllGames() {
        Game startedGame = service.createGame(gameName, numberOfPlayers, john);
        startedGame.startGame();
        service.createGame(gameName, numberOfPlayers, john);
        service.createGame(gameName, numberOfPlayers, john);
        service.createGame(gameName, numberOfPlayers, john);
        service.createGame(gameName, numberOfPlayers, john);

        assertEquals(1, service.getAllGames(true).size());
    }


    @Test
    void chooseNoble_ThrowsIfNobleNull() {
        Game game = service.createGame(gameName, numberOfPlayers, john);
        game.startGame();

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> game.chooseNoble(john.getName(), game.getGameId(), null));

        assertEquals("Noble is not available", ex.getMessage());
    }

    @Test
    void chooseNoble_ThrowsIfNotActivePlayer() {
        Game game = service.createGame(gameName, numberOfPlayers, john);
        game.startGame();
        Noble noble = Market.createNobles().getFirst();
        game.getMarket().setNobleToMarket(noble);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> game.chooseNoble("NotJohn", game.getGameId(), noble));

        assertEquals("You are not the current player", ex.getMessage());
    }

    @Test
    void reserveCard_ValidCase() {
        Game game = service.createGame(gameName, numberOfPlayers, john);
        game.startGame();
        String cardName = Market.createAllCards().getFirst().getFirst().getName();

        Game result = game.reserveCard(john.getName(), game.getGameId(), cardName);

        assertEquals(game, result);
        assertEquals(1, john.getReserved().size());
    }

    @Test
    void reserveCard_ThrowsIfCardNotFound() {
        Game game = service.createGame(gameName, numberOfPlayers, john);
        game.startGame();

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> game.reserveCard(john.getName(), game.getGameId(), "NonexistentCard"));

        assertEquals("Development card is not available", ex.getMessage());
    }

    @Test
    void reserveCard_ThrowsIfNotActivePlayer() {
        Game game = service.createGame(gameName, numberOfPlayers, john);
        game.startGame();
        String cardName = Market.createAllCards().getFirst().getFirst().getName();

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> game.reserveCard("NotJohn", game.getGameId(), cardName));

        assertEquals("You are not the current player", ex.getMessage());
    }
}