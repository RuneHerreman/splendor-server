package be.howest.ti.game.logic.service;


import be.howest.ti.game.logic.Game;
import be.howest.ti.game.logic.Player;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class SplendorServiceImpl implements SplendorService {
    private final List<Game> games;

    public SplendorServiceImpl() {
        this.games = new ArrayList<>();
    }

    public Game createGame(String gameName, int maxPlayers , Player host) {
        int gameID = getRandomID();
        Game game = new Game(gameName, gameID, maxPlayers, host);
        games.add(game);
        return game;
    }

    public Game createGame(int maxPlayers , Player host) {
        int gameID = getRandomID();
        Game game = new Game(null, gameID, maxPlayers, host);
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

    public void deleteGame(int gameID){
        games.remove(getGameByID(gameID));
    }

    public void deleteAllGames(){
        games.clear();
    }

    public Game getGameByID(int gameID){
        for (Game game : games) {
            if (game.getGameId() == gameID) {
                return game;
            }
        }
        return null;
    }

    public ArrayList<Game> getAllGames(){
        return new ArrayList<>(games);
    }

    public ArrayList<Game> getAllGames(boolean started){
        ArrayList<Game> filteredGames = new ArrayList<>();
        for (Game game : games) {
            if (game.isStarted()) {
                filteredGames.add(game);
            }
        }
        return filteredGames;
    }
}
