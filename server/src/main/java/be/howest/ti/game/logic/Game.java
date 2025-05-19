package be.howest.ti.game.logic;

import be.howest.ti.game.logic.gameTools.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Game {

    private final String gameName;
    private final int gameId;
    private boolean started;
    private final int numberOfPlayers;
    private Player activePlayer;
    private final List<Player> players;
    private Market market;
    private Map<Token, Integer> unclaimedTokens;
    private List<Noble> unclaimedNobles;
    private Player winner;
    private GameState gameState;
    private final boolean returnExcessTokensRequired;
    private final boolean pickNobleRequired;
    private final boolean active;

    public Game(String gameName, int gameId  , int maxPlayer , Player host) {
        this.gameName = gameName;
        this.gameId = gameId;
        this.numberOfPlayers = maxPlayer;
        this.started = false;
        this.players = getHostPlayerOnGameInititalization(host) ;
        this.activePlayer = players.get(0);
        this.market = new Market(numberOfPlayers);
        this.unclaimedTokens = market.getUnclaimedTokens();
        this.unclaimedNobles = market.getNoblesAvailableInMarket();
        this.winner = null;
        this.returnExcessTokensRequired = false;
        this.pickNobleRequired = false;
        this.active = true;
    }

    private List<Player> getHostPlayerOnGameInititalization(Player host ) {
        List<Player> players = new ArrayList<>();
        players.add(host);
        return players;


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

    public Map<Token , Integer> getUnclaimedTokens() {
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

    public boolean isReturnExcessTokensRequired() {
        return returnExcessTokensRequired;
    }
    public boolean areTokensAvailableInMarket(Map<Token, Integer> tokens) {
        return market.areTokensAvailableInMarket(tokens);
    }

    public boolean handleTokenPurchase(Map<Token, Integer> tokens) {
        boolean success = areTokensAvailableInMarket(tokens);
        if (success) {
            activePlayer.addTokens(tokens);
            market.removeTokensFromMarket(tokens);
            switchTurn();
        }

        return success;
    }
    public boolean handleDevelopmentCardPurchase(Development developmentCard , Boolean reserved) {
        boolean success = developmentCard.isCardAffordableByPlayer(activePlayer);
        if (success) {
            int cardLevel = developmentCard.getLevel();
            int cardIndexInMarket = market.getIndexCardFromMarket(developmentCard);
            Map<Token , Integer> costCard = developmentCard.getCost();
            market.removeCardFromMarket(developmentCard);
            if(!reserved){ market.addRandomCardToTheMarket(cardLevel , cardIndexInMarket);}
            activePlayer.removeTokens(costCard);
            activePlayer.addCard(developmentCard);
            switchTurn();
        }
        return success;
    }

    public void joinGame(String playerName) {
        Player newPlayer = new Player(playerName);
        players.add(newPlayer);

        if (players.size() == numberOfPlayers) {
            started = true;
            gameState = GameState.TurnAction;
        }
    }

    public boolean isPickNobleRequired() {
        return pickNobleRequired;
    }

    public boolean getActive() {
        return active;
    }
}
