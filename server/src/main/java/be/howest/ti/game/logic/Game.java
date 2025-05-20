package be.howest.ti.game.logic;

import be.howest.ti.game.logic.gameTools.*;
import be.howest.ti.game.logic.utils.CardUtils;

import java.util.*;


public class Game {

    private final String gameName;
    private final int gameId;
    private boolean started;
    private final int numberOfPlayers;
    private Player activePlayer;
    private final List<Player> players;
    private final Market market;
    private final Map<Token, Integer> unclaimedTokens;
    private final List<Noble> unclaimedNobles;
    private final Player winner;
    private GameState gameState;
    private final boolean returnExcessTokensRequired;
    private final boolean pickNobleRequired;
    private boolean active;
    private static final String NOT_CURRENT_PLAYER_MESSAGE = "You are not the current player";

    public Game(String gameName, int gameId  , int maxPlayer , Player host) {
        this.gameName = gameName;
        this.gameId = gameId;
        this.numberOfPlayers = maxPlayer;
        this.started = false;
        this.players = getHostPlayerOnGameInitialization(host) ;
        this.activePlayer = players.getFirst();
        this.market = new Market(numberOfPlayers);
        this.unclaimedTokens = market.getUnclaimedTokens();
        this.unclaimedNobles = market.getNoblesAvailableInMarket();
        this.winner = null;
        this.returnExcessTokensRequired = false;
        this.pickNobleRequired = false;
        this.active = true;
    }

    private List<Player> getHostPlayerOnGameInitialization(Player host ) {
        List<Player> playerList = new ArrayList<>();
        playerList.add(host);
        return playerList;

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
        if(players.size() < numberOfPlayers && !started && !players.contains(player)){
            players.add(player);
        }
    }

    public void switchTurn(){
        int nextPlayerIndex = (players.indexOf(activePlayer) + 1) % numberOfPlayers;
        activePlayer = players.get(nextPlayerIndex);
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
    public boolean areValidTokensToReturn(Map<Token, Integer> tokens) {
      return   market.areValidTokensPick(tokens);
    }

    public boolean handleTokenReturn(Map<Token, Integer> tokens) {
        boolean success = areValidTokensToReturn(tokens) && activePlayer.checkValidTokensToReturn(tokens);
        if (success) {
            activePlayer.removeTokens(tokens , false);
            market.addTokens(tokens);
            switchTurn();
        }

        return success;
    }
    public boolean handleDevelopmentCardPurchase(Development developmentCard , boolean reserved , Map<Token, Integer> tokens) {
        boolean success = developmentCard.isCardAffordableByPlayer(activePlayer) || developmentCard.isCardAffordableByPlayerWithGoldToken(activePlayer);

        if (success) {
            int cardLevel = developmentCard.getLevel();
            int cardIndexInMarket = market.getIndexCardFromMarket(developmentCard);
            Map<Token, Integer> costCard = developmentCard.getCost();
            int availableGoldTokens = tokens.getOrDefault(Token.GOLD, 0);
            Map<Token, Integer> tokensToBeRemovedFromPlayer = calculateTokensToRemove(costCard , tokens , availableGoldTokens);

            activePlayer.removeTokens(tokensToBeRemovedFromPlayer , true);
            market.removeCardFromMarket(developmentCard);
            activePlayer.updatePrestigePoints(developmentCard.getPrestigePoints());

            if (!reserved) {
                market.addRandomCardToTheMarket(cardLevel, cardIndexInMarket);
                activePlayer.addCard(developmentCard);
            } else {
                activePlayer.buyReserved(developmentCard);
            }

            switchTurn();
        }

        return success;
    }

    public Noble handleNobleVisit(Noble noble ) {
        Map<Token , Integer> nobleNeededBonus = noble.getRequiredBonuses();
        if(activePlayer.hasEnoughBonusesForNoble(nobleNeededBonus)){
            activePlayer.addNoble(noble);
            activePlayer.updatePrestigePoints(noble.getPrestigePoints());
            return noble;
        }

        return null;


    }

    private Map<Token, Integer> calculateTokensToRemove(Map<Token, Integer> costCard, Map<Token, Integer> tokensProvided, int availableGoldTokens) {
        Map<Token, Integer> tokensToDeduct = new HashMap<>();

        for (Token requiredToken : costCard.keySet()) {
            int requiredAmount = costCard.get(requiredToken);
            int playerTokenAmount = tokensProvided.getOrDefault(requiredToken, 0);

            if (playerTokenAmount >= requiredAmount) {
                tokensToDeduct.put(requiredToken, requiredAmount);
            } else {
                int missingAmount = requiredAmount - playerTokenAmount;
                if (missingAmount <= availableGoldTokens) {
                    tokensToDeduct.put(requiredToken, playerTokenAmount);
                    int tempGoldTokens = tokensToDeduct.getOrDefault(requiredToken, 0);
                    tokensToDeduct.put(Token.GOLD, tempGoldTokens + missingAmount);
                    availableGoldTokens -= missingAmount;
                }
            }
        }

        return tokensToDeduct;
    }

    public void joinGame(String playerName) {

        if (numberOfPlayers > players.size()) {
            Player newPlayer = new Player(playerName);
            players.add(newPlayer);
        } else{
            throw new IllegalStateException("The game is already full!");
        }

        if (players.size() == numberOfPlayers) {
            started = true;
            gameState = GameState.TURN_ACTION;
        }
    }

    public boolean isPickNobleRequired() {
        return pickNobleRequired;
    }

    public boolean getActive() {
        return active;
    }

    public void startGame() {
        started = true;
    }

    public void endGame() {
        active = false;
    }

    public Game buyDevelopment(String playerName, String developmentName, boolean reserved, Map<Token, Integer> payment) {
        boolean isActivePlayer = this.getActivePlayer().getName().equals(playerName);

        if (isActivePlayer) {
            Development development = CardUtils
                    .getDevelopmentCardByName(
                            developmentName,
                            this.getMarket().getCardsAvailableInMarket()
                    );
            this.handleDevelopmentCardPurchase(
                    development,
                    reserved,
                    payment
            );
        } else {
            throw new IllegalArgumentException(NOT_CURRENT_PLAYER_MESSAGE);
        }

        return this;
    }

    public Noble chooseNoble(String playerName,Noble noble) {
        if (noble == null) {
            throw new IllegalArgumentException("Noble is not available");
        }

        boolean isActivePlayer = this.getActivePlayer().getName().equals(playerName);
        if (isActivePlayer) {
            return this.handleNobleVisit(noble);
        } else {
            throw new IllegalArgumentException(NOT_CURRENT_PLAYER_MESSAGE);
        }
    }

    public Game reserveCard(String playerName, String developmentName) {
        boolean active = playerName.equals(this.getActivePlayer().getName());
        Development development = CardUtils.getDevelopmentCardByName(developmentName, Market.createAllCards());

        if (development == null) {
            throw new IllegalArgumentException("Development card is not available");
        }

        if (active) {
            this.getActivePlayer().reserveCard(development);
        } else {
            throw new IllegalArgumentException(NOT_CURRENT_PLAYER_MESSAGE);
        }

        return this;
    }

    public Game getTokens(String playerName, Map<Token, Integer> tokens) {
        boolean active = playerName.equals(this.getActivePlayer().getName());

        if (active) {
            this.handleTokenPurchase(tokens);
        } else {
            throw new IllegalArgumentException("You are not the current player");
        }
        return this;
    }
}
