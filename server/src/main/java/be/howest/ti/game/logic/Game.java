package be.howest.ti.game.logic;

import be.howest.ti.game.logic.utils.Development;
import be.howest.ti.game.logic.utils.GameState;
import be.howest.ti.game.logic.utils.Noble;
import be.howest.ti.game.logic.utils.TokenBundle;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Game {

    private String gameName;
    private int gameId;
    private boolean started;
    private int numberOfPlayers;
    private Player activePlayer;
    private final List<Player> players;
    private Market market;
    private List<TokenBundle> unclaimedTokens;
    private List<Noble> unclaimedNobles;
    private Player winner;
    private GameState gameState;

    public Game(String gameName, int gameId , int numberOfPlayers){
        this.gameName = gameName;
        this.gameId = gameId;
        this.numberOfPlayers = numberOfPlayers;
        this.started = false;
        this.players = new ArrayList<>() ;
        this.activePlayer = players.getFirst();
        this.market = getMarket();
        this.unclaimedTokens = market.getUnclaimedTokens();
        this.unclaimedNobles = market.getNoblesAvailableInMarket() ;
        this.winner = null;
    }


    public GameState getGameState() {
        return gameState;
    }

    public String getGameName() {
        return gameName;
    }

    public int getGameId() {
        return gameId;
    }

    public boolean isStarted() {
        return started;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public Player getActivePlayer() {
        return activePlayer;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Market getMarket() {
        return market;
    }

    public List<TokenBundle> getUnclaimedTokens() {
        return unclaimedTokens;
    }

    public List<Noble> getUnclaimedNobles() {
        return unclaimedNobles;
    }

    public Player getWinner() {
        return winner;
    }

    public void addPlayer(Player player){
        if(players.size() > numberOfPlayers && !started && !players.contains(player)){
            players.add(player);
        }
    }

    public void switchTurn(){
        int nextIndex = (players.indexOf(activePlayer) + 1) % numberOfPlayers;
        activePlayer = players.get(nextIndex);
    }

    private Market getInitMarket(){
        List<List<Development>> allCards = new ArrayList<>();
        List<List<Development>> cardsAvailableInMarket = new ArrayList<>();
        List<Noble> allNobles = new ArrayList<>();
        List<Noble> noblesInMarket = new ArrayList<>();
        List<TokenBundle> unclaimedTokens = new ArrayList<>();

        return new Market(allCards, noblesInMarket, cardsAvailableInMarket, allNobles, unclaimedTokens);
    }







}
