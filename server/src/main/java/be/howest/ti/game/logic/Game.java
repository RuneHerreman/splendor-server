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
            active = false;
        }
    }

    private Player checkForWinner() {
        List<Player> eligiblePlayers = new ArrayList<>();
        Player winningplayer = null;
        for (Player player : players) {
            if (player.getPrestigePoints() >= 15) {
                eligiblePlayers.add(player);
            }
        }

        if (lastRound && activePlayer.equals(getPlayers().getFirst())) {
            winningplayer = determineWinner();
        }

        if (!lastRound && !eligiblePlayers.isEmpty()) {
            lastRound = true;
        }

        return winningplayer;
    }

    public Player determineWinner() {
        List<List<Player>> playerData = new ArrayList<>(List.of(
                mostPrestige(),
                mostDevelopments(),
                mostNobles()
        ));

        for (List<Player> playerDataList : playerData) {
            if (playerDataList.size() == 1) {
                return playerDataList.getFirst();
            }
        }

        return null;
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

    public List<Player> mostNobles() {
        List<Player> mostNobles = new ArrayList<>();
        int max = 0;

        for (Player player : players) {
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

    public List<Player> mostDevelopments() {
        List<Player> mostDevelopments = new ArrayList<>();
        int max = 0;

        for (Player player : players) {
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
        boolean success = market.areValidTokensPick(tokens) && activePlayer.checkValidTokensToReturn(tokens);
        if (success) {
            activePlayer.removeTokens(tokens );
            market.addTokens(tokens);
           // switchTurn();
        }

        return success;
    }
    public boolean developmentCardPurchase(Development card, boolean reserved, Map<Token, Integer> paymentTokens) {
        if (card.isCardAffordableByPlayer(activePlayer, paymentTokens)) {
            return false;
        }
        int cardLevel = card.getLevel();
        int cardIndex = market.getIndexCardFromMarket(card);
        int cardPrestigePoints = card.getPrestigePoints();

        activePlayer.removeTokens(paymentTokens);
        activePlayer.updatePrestigePoints(cardPrestigePoints);
        activePlayer.bonusIncrementByType(card.getBonus());
        market.addTokens(paymentTokens);
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

    public Noble nobleVisit(Noble noble ) {
        if(noble.isNobleClaimableByPlayer(activePlayer)) {
            activePlayer.addNoble(noble);
            activePlayer.updatePrestigePoints(noble.getPrestigePoints());
            market.removeNobleFromMarket(noble);
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
            Development development = CardUtils.getDevelopmentCardByName(developmentName, this.getMarket().getCardsAvailableInMarket());
            developmentCardPurchase(development, reserved, payment);
        } else {
            throw new IllegalArgumentException(NOT_CURRENT_PLAYER_MESSAGE);
        }

        return this;
    }

    public Noble handleChooseNoble(String playerName, Noble noble) {
        if (noble == null) {
            throw new IllegalArgumentException("Noble is not available");
        }
        boolean isActivePlayer = activePlayer.getName().equals(playerName);
        if (isActivePlayer) {
            return nobleVisit(noble);
        } else {
            throw new IllegalArgumentException(NOT_CURRENT_PLAYER_MESSAGE);
        }
    }

    public Game handleReserveCard(String playerName, String developmentName) {
        if (!playerName.equals(activePlayer.getName())) {
            throw new IllegalArgumentException(NOT_CURRENT_PLAYER_MESSAGE);
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
