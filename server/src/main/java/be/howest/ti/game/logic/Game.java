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
    private Player winner;
    private GameState gameState;
    private final boolean returnExcessTokensRequired;
    private final boolean pickNobleRequired;
    private boolean active;
    private final boolean privateStatus;
    private static final String NOT_CURRENT_PLAYER_MESSAGE = "You are not the current player";
    private boolean lastRound = false;


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

        winner = checkForWinner();

        if (winner != null) {
            gameState = GameState.WINNER_IS_FOUND;
            endGame();
        }
    }

    private Player checkForWinner() {
        boolean playerHasEnoughPrestige = false;

        for (Player player : players) {
            if (player.getPrestigePoints() >= 15) {
                playerHasEnoughPrestige = true;
                break;
            }
        }

        if (playerHasEnoughPrestige && !lastRound) {
            lastRound = true;
            return null;
        }

        if (lastRound && activePlayer.equals(players.getFirst())) {
            return determineWinner();
        }
        return null;
    }

    public Player determineWinner() {
        List<Player> mostPrestige = mostPrestige();

        if (mostPrestige.size() == 1) {
            return mostPrestige.getFirst();
        }

        List<Player> mostDevelopments = mostDevelopments(mostPrestige);

        if (mostDevelopments.size() == 1) {
            return mostDevelopments.getFirst();
        }

        List<Player> mostNobles = mostNobles(mostDevelopments);

        if (mostNobles.size() == 1) {
            return mostNobles.getFirst();
        }

        return mostPrestige.getFirst();
    }

    public List<Player> mostPrestige() {
        List<Player> mostPrestige = new ArrayList<>();
        int max = 0;

        for (Player player : players) {
            int prestigeCount = player.getPrestigePoints();

            if (prestigeCount > max) {
                max = prestigeCount;
                mostPrestige.clear();
                mostPrestige.add(player);
            } else if (prestigeCount == max) {
                mostPrestige.add(player);
            }
        }

        return mostPrestige;
    }

    public List<Player> mostNobles(List<Player> remainingPlayers) {
        List<Player> mostNobles = new ArrayList<>();
        int max = 0;

        for (Player player : remainingPlayers) {
            int fieldLength = player.getNobles().size();

            if (fieldLength > max) {
                max = fieldLength;
                mostNobles.clear();
                mostNobles.add(player);
            } else if (fieldLength == max) {
                mostNobles.add(player);
            }
        }

        return mostNobles;
    }

    public List<Player> mostDevelopments(List<Player> remainingPlayers) {
        List<Player> mostDevelopments = new ArrayList<>();
        int max = 0;

        for (Player player : remainingPlayers) {
            int developmentCount = player.getPurchasedDevelopments().size();

            if (developmentCount > max) {
                max = developmentCount;
                mostDevelopments.clear();
                mostDevelopments.add(player);
            } else if (developmentCount == max) {
                mostDevelopments.add(player);
            }
        }

        return mostDevelopments;
    }

    public boolean tokenPurchase(Map<Token, Integer> tokens) {
        boolean success = market.areTokensAvailableInMarket(tokens);
        if (success) {
            activePlayer.addTokens(tokens);
            market.removeTokensFromMarket(tokens);
            switchTurn();
        }
        return success;
    }

    public boolean tokenReturn(Map<Token, Integer> tokens) {
        boolean success = activePlayer.checkValidTokensToReturn(tokens);
        if (success) {
            activePlayer.removeTokens(tokens );
            market.addTokens(tokens);
//            switchTurn();
        }

        return success;
    }

    public void developmentCardPurchase(Development card, boolean reserved, Map<Token, Integer> paymentTokens) {
        int cardLevel = card.getLevel();
        int cardIndex = market.getIndexCardFromMarket(card);
        int cardPrestigePoints = card.getPrestigePoints();
        Map<Token, Integer> cardPaymentTokens = card.validatePayment(activePlayer, paymentTokens);

        activePlayer.removeTokens(cardPaymentTokens);
        activePlayer.updatePrestigePoints(cardPrestigePoints);
        activePlayer.bonusIncrementByType(card.getBonus());
        market.addTokens(cardPaymentTokens);


        if (reserved) {
            activePlayer.buyReserved(card);
        } else {
            activePlayer.addCard(card);
            market.removeCardFromMarket(card);
            market.addRandomCardToTheMarket(cardLevel, cardIndex);
        }

        if (eligibleForNobles()) {
            gameState = GameState.CHOOSE_NOBLE;
        } else {
            gameState = GameState.TURN_ACTION;
            switchTurn();
        }
    }

    public boolean eligibleForNobles() {
        List<Noble> nobles = market.getNoblesAvailableInMarket();
        for (Noble noble : nobles) {
            if (noble.isNobleClaimableByPlayer(activePlayer)) {
                return true;
            }
        }
        return false;
    }

    private boolean reserveCard(Development developmentCard){
        if (developmentCard == null || !market.canReserveCard()) {
            return false;
        }

        int cardLevel = developmentCard.getLevel();
        int cardIndexInMarket = market.getIndexCardFromMarket(developmentCard);

        activePlayer.reserveCard(developmentCard);
        market.decrementTokenGold();
        market.removeCardFromMarket(developmentCard);
        market.addRandomCardToTheMarket(cardLevel, cardIndexInMarket);

        switchTurn();
        return true;
    }

    public Noble nobleVisit(Noble noble) {
        if(noble.isNobleClaimableByPlayer(activePlayer)) {
            activePlayer.addNoble(noble);
            activePlayer.updatePrestigePoints(noble.getPrestigePoints());
            market.removeNobleFromMarket(noble);

            gameState = GameState.TURN_ACTION;
            switchTurn();

            return noble;
        }
        return null;
    }

    public void joinGame(String playerName, String iconPath) {
        if (numberOfPlayers > players.size()) {
            Player newPlayer = new Player(playerName, iconPath);
            players.add(newPlayer);

            if (players.size() == numberOfPlayers) {
                started = true;
                gameState = GameState.TURN_ACTION;
            }
        } else{
            throw new IllegalStateException("The game is already full!");
        }
    }

    public Game handleDevelopmentCardPurchase(String playerName, String developmentName, boolean reserved, Map<Token, Integer> payment) {
        boolean isActivePlayer = activePlayer.getName().equals(playerName);
        if (isActivePlayer) {
            Development development = CardUtils.getDevelopmentCardByName(developmentName, market.getCardsAvailableInMarket());
            developmentCardPurchase(development, reserved, payment);
        } else {
            throw new IllegalArgumentException(NOT_CURRENT_PLAYER_MESSAGE);
        }

        return this;
    }

    public Noble handleChooseNoble(String playerName, Noble noble) {
        if (noble == null) {
            throw new IllegalArgumentException("Noble is not found");
        }

        boolean isActivePlayer = activePlayer.getName().equals(playerName);
        if (!isActivePlayer) {
            throw new IllegalArgumentException(NOT_CURRENT_PLAYER_MESSAGE);

        }

        if (gameState != GameState.CHOOSE_NOBLE) {
            throw new IllegalStateException("You cannot choose a noble at this time.");
        }

        return nobleVisit(noble);
    }

    public Game handleReserveCard(String playerName, String developmentName) {
        if (!playerName.equals(activePlayer.getName())) {
            throw new IllegalArgumentException(NOT_CURRENT_PLAYER_MESSAGE);
        }

        if (activePlayer.getReserved().size() == 3) {
            throw new IllegalStateException("You cannot have more than 3 reserved cards");
        }

        Development developmentCard = CardUtils.getDevelopmentCardByName(developmentName, market.getCardsAvailableInMarket());

        boolean success = reserveCard(developmentCard);
        if (!success) {
            throw new IllegalArgumentException("Card cannot be reserved.");
        }

        return this;
    }

    public Game handleTokenPurchase(String playerName, Map<Token, Integer> tokens) {
        boolean active = playerName.equals(activePlayer.getName());

        if (active) {
            tokenPurchase(tokens);
        } else {
            throw new IllegalArgumentException(NOT_CURRENT_PLAYER_MESSAGE);
        }
        return this;
    }

    public Game handleTokenReturn(String playerName, Map<Token, Integer> tokens) {
        boolean active = playerName.equals(activePlayer.getName());

        if (active) {
            tokenReturn(tokens);
        } else {
            throw new IllegalArgumentException(NOT_CURRENT_PLAYER_MESSAGE);
        }
        return this;
    }

    public Player getPlayerWithLastAction() {
        int activeIndex = players.indexOf(activePlayer);

        if (activeIndex == 0) {
            return players.get(numberOfPlayers - 1);
        } else {
            return players.get(activeIndex - 1);
        }
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
