package be.howest.ti.game.logic;

import be.howest.ti.game.logic.gameTools.*;

import java.util.ArrayList;
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
        if(players.size() < numberOfPlayers && !started && !players.contains(player)){
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
    public boolean handleDevelopmentCardPurchase(Development developmentCard , Boolean reserved , Map<Token, Integer> tokens) {
        boolean success = developmentCard.isCardAffordableByPlayer(activePlayer) || developmentCard.isCardAffordableByPlayerWithGoldToken(activePlayer);

        if (success) {
            int cardLevel = developmentCard.getLevel();
            int cardIndexInMarket = market.getIndexCardFromMarket(developmentCard);
            Map<Token, Integer> costCard = developmentCard.getCost();
            int availableGoldTokens = tokens.getOrDefault(Token.Gold, 0);
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
        Map<Token , Integer> nobleNeededBonus = noble.getNeededBonuses();
        if(activePlayer.hasEnoughBonusesForNoble(nobleNeededBonus)){
            activePlayer.addNoble(noble);
            activePlayer.updatePrestigePoints(noble.getPrestigePoints());
            return noble;
        }

        return null;


    }

    private Map<Token, Integer> calculateTokensToRemove(Map<Token, Integer> costCard, Map<Token, Integer> tokensProvided, int availableGoldTokens) {
        Map<Token, Integer> tokensToDeduct = new HashMap<>();

        for (Map.Entry<Token, Integer> entry : costCard.entrySet()) {
            Token requiredToken = entry.getKey();
            int requiredAmount = entry.getValue();
            int playerTokenAmount = tokensProvided.getOrDefault(requiredToken, 0);

            if (playerTokenAmount >= requiredAmount) {
                tokensToDeduct.put(requiredToken, requiredAmount);
            } else {
                int missingAmount = requiredAmount - playerTokenAmount;
                if (missingAmount <= availableGoldTokens) {
                    tokensToDeduct.put(requiredToken, playerTokenAmount);
                    tokensToDeduct.put(Token.Gold, tokensToDeduct.getOrDefault(Token.Gold, 0) + missingAmount);
                    availableGoldTokens -= missingAmount;
                }
            }
        }

        return tokensToDeduct;
    }

    public void joinGame(String playerName) {
        if (numberOfPlayers != players.size()) {
            Player newPlayer = new Player(playerName);
            players.add(newPlayer);
        } else{
            throw new IllegalStateException("The game is already full!");
        }

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

    public void startGame() {
        started = true;
    }

    public String gameEnd(){
        String result;
        if(!active && started){
            result = "This game is ended. The winner is " + getWinner();

        } else{
            result = "This game has not ended.";
        }
        return result;
    }
}
