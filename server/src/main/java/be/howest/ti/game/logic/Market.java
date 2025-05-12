package be.howest.ti.game.logic;

import be.howest.ti.game.logic.utils.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class Market {
    private static List<List<Development>> allCards;
    private static List<Noble> allNobles;
    private List<List<Development>> cardsAvailableInMarket;
    private List<Noble> noblesAvailableInMarket;
    private List<TokenBundle> unclaimedTokens;

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

    public List<TokenBundle> getUnclaimedTokens() {
        return unclaimedTokens;
    }

    public void setUnclaimedTokens(List<TokenBundle> unclaimedTokens) {
        this.unclaimedTokens = unclaimedTokens;
    }

    public void setCardToMarket(Development developmentCard) {
        int cardLevel = developmentCard.getLevel();
        int cardLevelIndex = cardLevel - 1;
        cardsAvailableInMarket.get(cardLevelIndex).add(developmentCard);
        allCards.get(cardLevelIndex).remove(developmentCard);
    }

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

        List<Development>level1Cards = new ArrayList<>();
        List<Development> level2Cards = new  ArrayList<>();
        List<Development> level3Cards = new  ArrayList<>();

        try {
            File developmentCards = new File("resources/data/developments.txt");
            Scanner scanner = new Scanner(developmentCards);
            if (scanner.hasNextLine()) scanner.nextLine(); // Skip header

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] token = line.split("\\t");

                String cardName = token[0];
                int level = Integer.parseInt(token[1]);
                Token cardType = CardUtils.getTokenFromLetters(token[2]);
                int points = Integer.parseInt(token[4]);
                Set<TokenBundle> tokenBundles = CardUtils.getCostTokenSetFromLetters(token[5]);

                Development development = new Development(cardName , points , tokenBundles ,  cardType  , level);

                if(level == 1){
                    level1Cards.add(development);
                }else if (level == 2){
                    level2Cards.add(development);
                }else{
                    level3Cards.add(development);
                }
            }

            scanner.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        allCards.add(level1Cards);
        allCards.add(level2Cards);
        allCards.add(level3Cards);

        return allCards;
    }

    public static List<Noble> createNobles() {
        List<Noble> allNobles = new ArrayList<>();

        try {
            File noblesFile = new File("resources/data/nobles.txt");
            Scanner scanner = new Scanner(noblesFile);
            if (scanner.hasNextLine()) scanner.nextLine(); // Skip header

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] token = line.split("\\t");

                String cardName = token[0];
                Set<TokenBundle> tokenBundles = CardUtils.getCostTokenSetFromLetters(token[1]);
                int point = Integer.parseInt(token[2]);

                Noble noble = new Noble(cardName ,point ,  tokenBundles);
                allNobles.add(noble);

            }

            scanner.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return allNobles;
    }

    public static List<TokenBundle> createInitTokens(int totalPlayers) {
        List<TokenBundle> initTokens = new ArrayList<>();

        for (Token token : Token.values()) {
            if (token == Token.GOLD) {
                initTokens.add(new TokenBundle(token, 5));
            } else {
                initTokens.add(new TokenBundle(token, getAmountOfTokenAccourdingToPlayer(totalPlayers)));
            }
        }

        return initTokens;
    }

    private static int getAmountOfTokenAccourdingToPlayer(int totalPlayers) {
        /*  4 spelers : 7 van elk tokens
            3 : 5
            2 : 4   */
        Map<Integer, Integer> tokenAccourding = new HashMap<>();
        tokenAccourding.put(4, 7);
        tokenAccourding.put(3, 5);
        tokenAccourding.put(2, 4);

        return tokenAccourding.get(totalPlayers);
    }

    public List<Noble> getInitNoblesForMarket(int amountOfPlayers) {
        List<Noble> noblesForMarket = new ArrayList<>();
        int noblesToSelect = amountOfPlayers + 1;

        Random random = new Random();
        List<Integer> selectedIndices = new ArrayList<>();

        while (noblesForMarket.size() < noblesToSelect && selectedIndices.size() < allNobles.size()) {
            int index = random.nextInt(allNobles.size());
            if (!selectedIndices.contains(index)) {
                selectedIndices.add(index);
                noblesForMarket.add(allNobles.get(index));
            }
        }

        return noblesForMarket;
    }

    public List<List<Development>> getInitDevelopmentCardsForMarket() {
        List<List<Development>> developmentCardsForMarket = new ArrayList<>();
        Random random = new Random();

        for (List<Development> levelCards : allCards) {
            List<Development> marketCards = new ArrayList<>();
            Set<Integer> selectedIndices = new HashSet<>();
            int cardsToSelect = Math.min(4, levelCards.size());

            while (marketCards.size() < cardsToSelect && selectedIndices.size() < levelCards.size()) {
                int index = random.nextInt(levelCards.size());
                if (!selectedIndices.contains(index)) {
                    selectedIndices.add(index);
                    marketCards.add(levelCards.get(index));
                }
            }

            developmentCardsForMarket.add(marketCards);
        }

        return developmentCardsForMarket;
    }

    @Override public String toString() { return "Market{" + "allCards=" + allCards + ", allNobles=" + allNobles + ", cardsAvailableInMarket=" + cardsAvailableInMarket + ", noblesAvailableInMarket=" + noblesAvailableInMarket + ", unclaimedTokens=" + unclaimedTokens + '}'; }

}








