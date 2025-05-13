package be.howest.ti.game.logic.service;

import be.howest.ti.game.logic.Game;
import be.howest.ti.game.logic.Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GameManager {
    private final Set<Game> games;

    public GameManager() {
        this.games = new HashSet<>();
    }

    public void createGame(String gameName, int gameID, List<Player> players) {
        games.add(new Game(gameName, gameID, players));
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
}
