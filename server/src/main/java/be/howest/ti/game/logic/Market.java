package be.howest.ti.game.logic;

import be.howest.ti.game.logic.gameTools.Development;
import be.howest.ti.game.logic.gameTools.Noble;
import be.howest.ti.game.logic.gameTools.Token;
import be.howest.ti.game.logic.utils.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.security.SecureRandom;


public class Market {
    private static final SecureRandom RANDOM = new SecureRandom();
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

    public void removeCardFromMarket(Development developmentCard) {
        int cardLevel = developmentCard.getLevel();
        int cardLevelIndex = cardLevel - 1;
        cardsAvailableInMarket.get(cardLevelIndex).remove(developmentCard);
    }

    public static List<List<Development>> createAllCards() {
        List<List<Development>> allCards = new ArrayList<>();

        List<Development> level1Cards = new ArrayList<>();
        List<Development> level2Cards = new ArrayList<>();
        List<Development> level3Cards = new ArrayList<>();

        try {
            File developmentCards = new File("src/main/resources/data/developments.txt");
            Scanner scanner = new Scanner(developmentCards);
            if (scanner.hasNextLine()){ scanner.nextLine();}

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

                if (level == 1) {level1Cards.add(development);
                } else if (level == 2) {level2Cards.add(development);
                } else if (level == 3) {level3Cards.add(development);
                } else {throw new IllegalArgumentException("Unexpected level: " + level);}
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
            if (scanner.hasNextLine()) scanner.nextLine();

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
        Map<Token, Integer> initTokens = new EnumMap<>(Token.class);;
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
        if(totalPlayers == 2) {
            return 4;
        }else if (totalPlayers == 3) {
            return 5;
        }else{
            return 7;
        }
    }

    private List<Noble> getInitNoblesForMarket(int amountOfPlayers) {
        List<Noble> noblesForMarket = new ArrayList<>();
        int amountOfNoblesToSelect = amountOfPlayers + 1;

        Set<Integer> selectedIndexes = new HashSet<>();

        while (noblesForMarket.size() < amountOfNoblesToSelect && selectedIndexes.size() < allNobles.size()) {
            int index = RANDOM.nextInt(allNobles.size());
            if (selectedIndexes.add(index)) {
                noblesForMarket.add(allNobles.get(index));
            }
        }

        allNobles.removeAll(noblesForMarket);
        return noblesForMarket;
    }



    private List<List<Development>> getInitDevelopmentCardsForMarket() {
        List<List<Development>> developmentCardsForMarket = new ArrayList<>();

        for (List<Development> cardsByLevel : allCards) {
            List<Development> marketCardsByLevel = new ArrayList<>();
            Set<Integer> selectedIndexes = new HashSet<>();
            int amountOfCardsByLevel = 4;

            while (marketCardsByLevel.size() < amountOfCardsByLevel && selectedIndexes.size() < cardsByLevel.size()) {
                int index = RANDOM.nextInt(cardsByLevel.size());
                if (selectedIndexes.add(index)) {
                    marketCardsByLevel.add(cardsByLevel.get(index));
                }
            }

            cardsByLevel.removeAll(marketCardsByLevel);
            developmentCardsForMarket.add(marketCardsByLevel);
        }

        return developmentCardsForMarket;
    }

    public boolean canReserveCard(){
        int availableGoldTokens = unclaimedTokens.getOrDefault(Token.GOLD, 0);
        return  availableGoldTokens > 0;
    }

    public void decrementTokenGold(){
        int amountTokenGold = unclaimedTokens.get(Token.GOLD);
        int newTokenGold = amountTokenGold - 1;
        unclaimedTokens.replace(Token.GOLD,newTokenGold);
    }


    public boolean areValidTokensPick(Map<Token, Integer> tokens) {
        if (!tokens.isEmpty() && !tokens.containsKey(Token.GOLD)){
            return checkValueBySize(tokens);
        }
        return false;
    }

    private boolean checkValueBySize(Map<Token, Integer> tokens) {
        int size = tokens.size();
        int expectedCount;

        if (size == 3) {expectedCount = 1;
        } else if (size == 1) {expectedCount = 2;
        } else {return false;}

        for (int count : tokens.values()) {
            if (count != expectedCount) {
                return false;
            }
        }
        return true;
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
            removeTokenFromMarket(token,tokens.get(token));
        }
    }
    private void removeTokenFromMarket(Token token, int amount) {
        int tempAmount = unclaimedTokens.get(token);
        unclaimedTokens.replace(token , tempAmount - amount);
    }

    public void removeNobleFromMarket(Noble noble) {
        noblesAvailableInMarket.remove(noble);
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
        if (levelIndex >= 0 && levelIndex < cardsAvailableInMarket.size()) {
            cardsAvailableInMarket.get(levelIndex).add(cardIndexInMarket, getRandomCardFromMarket(cardLevel));
        };

    }

    private Development getRandomCardFromMarket(int cardLevel) {
        int cardLevelIndex = cardLevel - 1;
        List<Development> cardsByLevel = allCards.get(cardLevelIndex);
        int indexNewCard = RANDOM.nextInt(cardsByLevel.size());
        return cardsByLevel.get(indexNewCard);
    }

    public void addToken(Token token, int amount) {
        int tempTokenAmount = unclaimedTokens.getOrDefault(token, 0);
        unclaimedTokens.replace(token, tempTokenAmount + amount);
    }

    public void addTokens(Map<Token, Integer> tokens) {
        for (Token token : tokens.keySet()) {
            int tokenAmount = tokens.get(token);
            addToken(token, tokenAmount);
        }
    }

    public List<Noble> getAllNobles() {return allNobles;}
    public List<List<Development>> getAllCards() {return allCards;}
    public List<List<Development>> getCardsAvailableInMarket() {return cardsAvailableInMarket;}
    public List<Noble> getNoblesAvailableInMarket() {return noblesAvailableInMarket;}
    public Map<Token , Integer> getUnclaimedTokens() {return unclaimedTokens;}
}
