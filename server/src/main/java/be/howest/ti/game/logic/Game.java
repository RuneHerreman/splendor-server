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
    private final boolean privateStatus;
    private static final String NOT_CURRENT_PLAYER_MESSAGE = "You are not the current player";


    public Game(String gameName, int gameId, int maxPlayer, Player host, boolean privateStatus) {
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
        this.privateStatus = privateStatus;
    }

    private List<Player> getHostPlayerOnGameInitialization(Player host ) {
        List<Player> playerList = new ArrayList<>();
        playerList.add(host);
        return playerList;
    }

    public void switchTurn(){
        int currentPlayerIndex = players.indexOf(activePlayer);
        int nextPlayerIndex = (currentPlayerIndex + 1) % numberOfPlayers;
        activePlayer = players.get(nextPlayerIndex);
    }

    public boolean handleTokenPurchase(Map<Token, Integer> tokens) {
        boolean success = market.areTokensAvailableInMarket(tokens);
        if (success) {
            activePlayer.addTokens(tokens);
            market.removeTokensFromMarket(tokens);
            switchTurn();
        }else{
            throw new IllegalArgumentException("Tokens not available in Market");
        }
        return true;
    }

    public boolean handleTokenReturn(Map<Token, Integer> tokens) {
        boolean success = market.areValidTokensPick(tokens) && activePlayer.checkValidTokensToReturn(tokens);
        if (success) {
            activePlayer.removeTokens(tokens , false);
            market.addTokens(tokens);
            switchTurn();
        }

        return success;
    }
    public boolean handleDevelopmentCardPurchase(Development card, boolean reserved, Map<Token, Integer> paymentTokens) {
        if (!card.isCardAffordableByPlayer(activePlayer) && !card.isCardAffordableByPlayerWithGoldToken(activePlayer)) {
            return false;
        }

        int cardLevel = card.getLevel();
        int cardIndex = market.getIndexCardFromMarket(card);
        int goldAvailable = paymentTokens.getOrDefault(Token.GOLD, 0);
        Map<Token, Integer> tokensToRemove = calculateTokensToRemove(card.getCost(), paymentTokens, goldAvailable);

        activePlayer.removeTokens(tokensToRemove, true);
        activePlayer.updatePrestigePoints(card.getPrestigePoints());
        market.addTokens(tokensToRemove);
        market.removeCardFromMarket(card);

        if (reserved) {
            activePlayer.buyReserved(card);
        } else {
            activePlayer.addCard(card);
            market.addRandomCardToTheMarket(cardLevel, cardIndex);
        }

        switchTurn();
        return true;
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

    public Noble handleNobleVisit(Noble noble ) {
        Map<Token , Integer> nobleNeededBonus = noble.getRequiredBonuses();
        if(activePlayer.hasEnoughBonusesForNoble(nobleNeededBonus)){
            activePlayer.addNoble(noble);
            activePlayer.updatePrestigePoints(noble.getPrestigePoints());
            return noble;
        }
        return null;
    }

    public void joinGame(String playerName) {
        if (numberOfPlayers > players.size()) {
            Player newPlayer = new Player(playerName);
            players.add(newPlayer);

            if (players.size() == numberOfPlayers) {
                started = true;
                gameState = GameState.TURN_ACTION;
            }
        } else{
            throw new IllegalStateException("The game is already full!");
        }
    }

    public Game buyDevelopment(String playerName, String developmentName, boolean reserved, Map<Token, Integer> payment) {
        boolean isActivePlayer = activePlayer.getName().equals(playerName);
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

        boolean isActivePlayer = activePlayer.getName().equals(playerName);
        if (isActivePlayer) {
            return this.handleNobleVisit(noble);
        } else {
            throw new IllegalArgumentException(NOT_CURRENT_PLAYER_MESSAGE);
        }
    }

    public Game reserveCard(String playerName, String developmentName) {
        boolean active = playerName.equals(this.getActivePlayer().getName());
        Development development = CardUtils.getDevelopmentCardByName(developmentName, market.getCardsAvailableInMarket());

        if (development == null) {
            throw new IllegalArgumentException("Development card is not available");
        }

        if (active) {
            Development developmentCard = CardUtils.getDevelopmentCardByName(developmentName, this.getMarket().getCardsAvailableInMarket());
            int cardLevel = developmentCard.getLevel();
            int cardIndexInMarket = market.getIndexCardFromMarket(developmentCard);

            activePlayer.reserveCard(development);
            market.removeCardFromMarket(development);
            market.addRandomCardToTheMarket(cardLevel, cardIndexInMarket);

            switchTurn();
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

    public void startGame() {started = true;}
    public void endGame() {active = false;}

    public GameState getGameState() {return gameState;}
    public String getGameName() {return gameName;}
    public int getGameId() {return gameId;}
    public boolean isStarted() {return started;}
    public int getNumberOfPlayers() {return numberOfPlayers;}
    public Player getActivePlayer() {return activePlayer;}
    public List<Player> getPlayers() {return players;}
    public Market getMarket() {return market;}
    public Map<Token , Integer> getUnclaimedTokens() {return unclaimedTokens;}
    public List<Noble> getUnclaimedNobles() {return unclaimedNobles;}
    public Player getWinner() {return winner;}
    public boolean getPrivateStatus() {return privateStatus;}
    public boolean isReturnExcessTokensRequired() {return returnExcessTokensRequired;}
    public boolean isPickNobleRequired() {return pickNobleRequired;}
    public boolean getActive() {return active;}
}
