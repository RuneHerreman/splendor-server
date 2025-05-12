package be.howest.ti.game.logic;

import be.howest.ti.game.logic.utils.Development;
import be.howest.ti.game.logic.utils.GameState;
import be.howest.ti.game.logic.utils.Noble;
import be.howest.ti.game.logic.utils.TokenBundle;

import java.util.List;


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

    public Game(String gameName, int gameId , int numberOfPlayers , List<Player> players) {
        this.gameName = gameName;
        this.gameId = gameId;
        this.numberOfPlayers = numberOfPlayers;
        this.started = false;
        this.players = players ;
        this.activePlayer = players.getFirst();
        this.market = new Market(numberOfPlayers);
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

    @Override
    public String toString() {
        return "Game{" +
                "gameName='" + gameName + '\'' +
                ", gameId=" + gameId +
                ", started=" + started +
                ", numberOfPlayers=" + numberOfPlayers +
                ", activePlayer=" +  activePlayer.getName()  +
                ", players=" + players +
                ", market=" + market +
                ", winner=" + (winner != null ? winner.getName() : "None") +
                ", gameState=" + gameState +
                '}';
    }

    @Override
    public String toString() {
        return "Game{" +
                "gameName='" + gameName + '\'' +
                ", gameId=" + gameId +
                ", started=" + started +
                ", numberOfPlayers=" + numberOfPlayers +
                ", activePlayer=" + activePlayer +
                ", players=" + players +
                ", market=" + market +
                ", unclaimedTokens=" + unclaimedTokens +
                ", unclaimedNobles=" + unclaimedNobles +
                ", winner=" + winner +
                ", gameState=" + gameState +
                '}';
    }
}
