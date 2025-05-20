package be.howest.ti.game.logic.service;


import be.howest.ti.game.logic.Game;
import be.howest.ti.game.logic.Market;
import be.howest.ti.game.logic.Player;
import be.howest.ti.game.logic.gameTools.Development;
import be.howest.ti.game.logic.gameTools.Noble;
import be.howest.ti.game.logic.gameTools.Token;
import be.howest.ti.game.logic.utils.CardUtils;

import javax.smartcardio.Card;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    public Game deleteGame(int gameID){
        Game deletedGame = getGameByID(gameID);
        games.remove(deletedGame);

        return deletedGame;
    }

    public List<Game> deleteAllGames(){
        List<Game> deletedGames = new ArrayList<>(games);
        games.clear();

        return deletedGames;
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

    public ArrayList<Game> getStartedGames(){
        ArrayList<Game> startedGames = new ArrayList<>();
        for (Game game : games) {
            if (game.isStarted()) {
                startedGames.add(game);
            }
        }
        return startedGames;
    }

    public ArrayList<Game> getNonStartedGames(){
        ArrayList<Game> nonStartedGames = new ArrayList<>();
        for (Game game : games) {
            if (!game.isStarted()) {
                nonStartedGames.add(game);
            }
        }
        return nonStartedGames;
    }

    public Game buyDevelopment(int gameID, String playerName, String developmentName, boolean reserved, Map<Token, Integer> payment) {
        Game game = getGameByID(gameID);
        boolean isActivePlayer = game.getActivePlayer().getName().equals(playerName);

        if (isActivePlayer) {
            Development development = CardUtils
                    .getDevelopmentCardByName(
                            developmentName,
                            game.getMarket().getCardsAvailableInMarket()
                    );
            game.handleDevelopmentCardPurchase(
                    development,
                    reserved,
                    payment
            );
        } else{
            throw new IllegalArgumentException("You are not the current player");
        }

        return game;
    }

    public Noble chooseNoble(String playerName, int gameID, Noble noble) {
        Game game = getGameByID(gameID);

        if (noble == null) {
            throw new IllegalArgumentException("Noble is not available");

        }

        boolean isActivePlayer = game.getActivePlayer().getName().equals(playerName);
        if (isActivePlayer) {
            return game.handleNobleVisit(noble);
        } else {
            throw new IllegalArgumentException("You are not the current player");
        }
    }

    public Game reserveCard(String playerName, int gameID, String developmentName) {
        Game game = getGameByID(gameID);
        boolean active = playerName.equals(game.getActivePlayer().getName());
        Development development = CardUtils.getDevelopmentCardByName(developmentName, Market.createAllCards());

        if (development == null) {
            throw new IllegalArgumentException("Development card is not available");
        }
        if (active) {
            game.getActivePlayer().reserveCard(development);
        } else {
            throw new IllegalArgumentException("You are not the current player");
        }
        return game;
    }

    public Game getTokens(String playerName, int gameID, Map<Token, Integer> tokens) {
        Game game = getGameByID(gameID);
        boolean active = playerName.equals(game.getActivePlayer().getName());

        if (active) {
            game.handleTokenPurchase(tokens);
        } else {
            throw new IllegalArgumentException("You are not the current player");
        }
        return game;
    }
}
