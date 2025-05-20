package be.howest.ti.game.logic;

import be.howest.ti.game.logic.gameTools.Development;
import be.howest.ti.game.logic.gameTools.Noble;
import be.howest.ti.game.logic.gameTools.Token;
import be.howest.ti.game.logic.utils.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.security.SecureRandom;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Market {
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final Logger LOGGER = Logger.getLogger(Market.class.getName());
    private static List<List<Development>> allCards;
    private static List<Noble> allNobles;
    private final List<List<Development>> cardsAvailableInMarket;
    private final List<Noble> noblesAvailableInMarket;
    private final Map<Token , Integer> unclaimedTokens;

    public Market(int amountOfPlayers) {
        allCards = createAllCards();
        allNobles = createNobles();
        this.cardsAvailableInMarket = getInitDevelopmentCardsForMarket();
        this.noblesAvailableInMarket = getInitNoblesForMarket(amountOfPlayers);
        this.unclaimedTokens = createInitTokens(amountOfPlayers);
    }

    public List<Noble> getAllNobles() {
        return allNobles;
    }

    public List<List<Development>> getAllCards() {
        return allCards;
    }

    public List<List<Development>> getCardsAvailableInMarket() {
        return cardsAvailableInMarket;
    }

    public List<Noble> getNoblesAvailableInMarket() {
        return noblesAvailableInMarket;
    }

    public Map<Token , Integer> getUnclaimedTokens() {
        return unclaimedTokens;
    }
    /*
    public void setCardToMarket(Development developmentCard) {
        int cardLevel = developmentCard.getLevel();
        int cardLevelIndex = cardLevel - 1;
        cardsAvailableInMarket.get(cardLevelIndex).add(developmentCard);
        allCards.get(cardLevelIndex).remove(developmentCard);
    }*/

    public void setNobleToMarket(Noble noble) {
        noblesAvailableInMarket.add(noble);
        allNobles.remove(noble);
    }

    public void removeCardFromMarket(Development developmentCard) {
        int cardLevel = developmentCard.getLevel();
        int cardLevelIndex = cardLevel - 1;
        cardsAvailableInMarket.get(cardLevelIndex).remove(developmentCard);
    }

    public void removeNobleFromMarket(Noble noble) {
        noblesAvailableInMarket.remove(noble);
    }

    public static List<List<Development>> createAllCards() {
        List<List<Development>> allCards = new ArrayList<>();

        List<Development> level1Cards = new ArrayList<>();
        List<Development> level2Cards = new ArrayList<>();
        List<Development> level3Cards = new ArrayList<>();

        try {
            File developmentCards = new File("src/main/resources/data/developments.txt");
            Scanner scanner = new Scanner(developmentCards);
            if (scanner.hasNextLine()) scanner.nextLine();

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] token = line.split("\\t");

                String cardName = token[0];
                int level = Integer.parseInt(token[1]);
                char tokenSymbol = token[2].charAt(0);
                Token cardType = CardUtils.getTokenFromLetters(tokenSymbol);
                int points = Integer.parseInt(token[4]);
                Map<Token, Integer> tokenBundles = CardUtils.getCostTokenSetFromLetters(token[5]);

                Development development = new Development(cardName, points, tokenBundles, cardType, level);

                if (level == 1) {
                    level1Cards.add(development);
                } else if (level == 2) {
                    level2Cards.add(development);
                } else if (level == 3) {
                    level3Cards.add(development);
                } else {
                    throw new IllegalArgumentException("Unexpected level: " + level);
                }

            }

            scanner.close();

        } catch (FileNotFoundException e) {
           throw new IllegalArgumentException("Could not find development cards file");
        }

        allCards.add(level1Cards);
        allCards.add(level2Cards);
        allCards.add(level3Cards);

        return allCards;
    }

    public static List<Noble> createNobles() {
        List<Noble> allNobles = new ArrayList<>();

        try {
            File noblesFile = new File("src/main/resources/data/nobles.txt");
            Scanner scanner = new Scanner(noblesFile);
            if (scanner.hasNextLine()) scanner.nextLine(); // Skip header

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] token = line.split("\\t");

                String cardName = token[0];
                Map<Token, Integer> tokenBundles = CardUtils.getCostTokenSetFromLetters(token[1]);
                int point = Integer.parseInt(token[2]);

                Noble noble = new Noble(cardName, point, tokenBundles);
                allNobles.add(noble);
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Could not find nobles file");
        }

        return allNobles;
    }

    public static Map<Token, Integer> createInitTokens(int totalPlayers) {
        Map<Token, Integer> initTokens = new HashMap<>();
        for (Token token : Token.values()) {
            if (token == Token.GOLD) {
                initTokens.put(token, 5);
            } else {
                initTokens.put(token, getTokenCountByPlayer(totalPlayers));
            }
        }
        return initTokens;
    }
    private static int getTokenCountByPlayer(int totalPlayers) {
        Map<Integer, Integer> getTokenCountByPlayer = new HashMap<>();
        getTokenCountByPlayer.put(4, 7);
        getTokenCountByPlayer.put(3, 5);
        getTokenCountByPlayer.put(2, 4);

        return getTokenCountByPlayer.get(totalPlayers);
    }

    public List<Noble> getInitNoblesForMarket(int amountOfPlayers) {
        List<Noble> noblesForMarket = new ArrayList<>();
        int noblesToSelect = amountOfPlayers + 1;

        List<Integer> selectedIndexes = new ArrayList<>();

        while (noblesForMarket.size() < noblesToSelect && selectedIndexes.size() < allNobles.size()) {
            int index = RANDOM.nextInt(allNobles.size());
            if (!selectedIndexes.contains(index)) {
                selectedIndexes.add(index);
                noblesForMarket.add(allNobles.get(index));
            }
        }

        return noblesForMarket;
    }

    public List<List<Development>> getInitDevelopmentCardsForMarket() {
        List<List<Development>> developmentCardsForMarket = new ArrayList<>();

        for (List<Development> levelCards : allCards) {
            List<Development> marketCards = new ArrayList<>();
            Set<Integer> selectedIndexes = new HashSet<>();
            int cardsToSelect = Math.min(4, levelCards.size());

            while (marketCards.size() < cardsToSelect && selectedIndexes.size() < levelCards.size()) {
                int index = RANDOM.nextInt(levelCards.size());
                if (!selectedIndexes.contains(index)) {
                    selectedIndexes.add(index);
                    marketCards.add(levelCards.get(index));
                }
            }

            developmentCardsForMarket.add(marketCards);
        }

        return developmentCardsForMarket;
    }

    public boolean areValidTokensPick(Map<Token, Integer> tokens) {
        if (tokens.isEmpty() || (tokens.size() != 1 && tokens.size() != 3)) {
            return false;
        }
        if (tokens.containsKey(Token.GOLD)) {
            return false;
        }
        if (tokens.size() == 3) {
            for (int count : tokens.values()) {
                if (count != 1) {
                    return false;
                }
            }
            return true;
        }

        if (tokens.size() == 1) {
            for(int count : tokens.values()){
                return count == 2;
            }
        }

        return false;
    }


    public boolean areTokensAvailableInMarket(Map<Token , Integer> tokens) {
        if (!areValidTokensPick(tokens)) { return false; }

        for (Token token : tokens.keySet()) {
            int requestedTokenAmount = tokens.get(token);
            if (token == Token.GOLD) { return false; }
            if (tokens.size() == 1 && !checkTakeDoubleTokenPossibility(token)) { return false; }
            int available = unclaimedTokens.getOrDefault(token, 0);
            if (available < requestedTokenAmount) { return false; }
        }
        return true;
    }

    private boolean checkTakeDoubleTokenPossibility(Token token) {
        return unclaimedTokens.getOrDefault(token, 0) >= 4;
    }

    public void removeTokensFromMarket(Map<Token, Integer> tokens) {
        for (Token token : tokens.keySet()) {
            removeTokensFromMarket(token,tokens.get(token));
        }
    }

    private void removeTokensFromMarket(Token token, int amount) {
        int tempAmount = unclaimedTokens.get(token);
        unclaimedTokens.replace(token , tempAmount - amount);
    }



    @Override
    public String toString() {
        return "Market{" +
                "allCards=" + allCards +
                ", allNobles=" + allNobles +
                ", cardsAvailableInMarket=" + cardsAvailableInMarket +
                ", noblesAvailableInMarket=" + noblesAvailableInMarket +
                ", unclaimedTokens=" + unclaimedTokens +
                '}';
    }

    public int getIndexCardFromMarket(Development developmentCard) {
        for (List<Development> levelCards : cardsAvailableInMarket) {
            if (levelCards.contains(developmentCard)) {
                return levelCards.indexOf(developmentCard);
            }
        }
        return -1;
    }

    public void addRandomCardToTheMarket(int cardLevel, int cardIndexInMarket) {
        int levelIndex = cardLevel - 1;
        if (levelIndex < 0 || levelIndex >= cardsAvailableInMarket.size()) return;
        cardsAvailableInMarket.get(levelIndex).add(cardIndexInMarket, getRandomCardFromMarket(cardLevel));
    }

    private Development getRandomCardFromMarket(int cardLevel) {
        List<Development> deck = allCards.get(cardLevel - 1);
        return deck.get(RANDOM.nextInt(deck.size()));
    }

    public void addToken(Token token, int amount) {
        unclaimedTokens.put(token, unclaimedTokens.getOrDefault(token, 0) + amount);
    }

    public void addTokens(Map<Token, Integer> tokens) {
        for (Token token : tokens.keySet()) {
            int tokenAmount = tokens.get(token);
            addToken(token, tokenAmount);
        }
    }
}
