package be.howest.ti.game.logic.service;

import be.howest.ti.game.logic.Game;
import be.howest.ti.game.logic.Market;
import be.howest.ti.game.logic.Player;
import be.howest.ti.game.logic.gameTools.Development;
import be.howest.ti.game.logic.gameTools.Noble;
import be.howest.ti.game.logic.gameTools.Token;
import be.howest.ti.game.logic.utils.CardUtils;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SplendorServiceImpl implements SplendorService {
    private final List<Game> games;

    public SplendorServiceImpl() {
        this.games = new ArrayList<>();
    }

    public Game createGame(String gameName, int maxPlayers, Player host, boolean privateStatus) {
        int gameID = getRandomID();
        Game game = new Game(gameName, gameID, maxPlayers, host, privateStatus);
        games.add(game);
        return game;
    }

    public Game createGame(int maxPlayers, Player host, boolean privateStatus) {
        int gameID = getRandomID();
        Game game = new Game(null, gameID, maxPlayers, host, privateStatus);
        games.add(game);
        return game;
    }

    private boolean gameIDisUnique(int randomID) {
        for (Game game : games) {
            if (game.getGameId() == randomID) {
                return false;
            }
        }
        return true;
    }

    private int getRandomID() {
        SecureRandom random = new SecureRandom();
        int randomID;

        do {
            randomID = random.nextInt(100000);
        } while (!gameIDisUnique(randomID));

        return randomID;
    }

    public Game deleteGame(int gameID) {
        Game deletedGame = getGameByID(gameID);
        games.remove(deletedGame);
        return deletedGame;
    }

    public List<Game> deleteAllGames() {
        List<Game> deletedGames = new ArrayList<>(games);
        games.clear();
        return deletedGames;
    }

    public Game getGameByID(int gameID) {
        for (Game game : games) {
            if (game.getGameId() == gameID) {
                return game;
            }
        }
        return null;
    }

    public ArrayList<Game> getAllGames() {
        return new ArrayList<>(games);
    }

    public ArrayList<Game> getAllGames(boolean started) {
        ArrayList<Game> filteredGames = new ArrayList<>();
        for (Game game : games) {
            if (game.isStarted()) {
                filteredGames.add(game);
            }
        }
        return filteredGames;
    }

    public ArrayList<Game> getStartedGames() {
        ArrayList<Game> startedGames = new ArrayList<>();
        for (Game game : games) {
            if (game.isStarted()) {
                startedGames.add(game);
            }
        }
        return startedGames;
    }

    public ArrayList<Game> getNonStartedGames() {
        ArrayList<Game> nonStartedGames = new ArrayList<>();
        for (Game game : games) {
            if (!game.isStarted()) {
                nonStartedGames.add(game);
            }
        }
        return nonStartedGames;
    }
}
