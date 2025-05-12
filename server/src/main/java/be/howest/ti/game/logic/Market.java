package be.howest.ti.game.logic;

import be.howest.ti.game.logic.gameTools.Development;
import be.howest.ti.game.logic.gameTools.Noble;
import be.howest.ti.game.logic.gameTools.Token;
import be.howest.ti.game.logic.gameTools.TokenBundle;
import be.howest.ti.game.logic.utils.*;

import java.util.*;

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

        // Dummy data / moet alle cards halen van een file >recorces > developmentCards
        List<Development> level1Cards = Arrays.asList(
                new Development("Emerald Mine", 0, CardUtils.getCostTokenSetFromLetters("EEECC"), new TokenBundle(Token.EMERALD, 1), 1),
                new Development("Sapphire Workshop", 0, CardUtils.getCostTokenSetFromLetters("SSCC"), new TokenBundle(Token.SAPPHIRE, 1), 1),
                new Development("Onyx Quarry", 0, CardUtils.getCostTokenSetFromLetters("OOCR"), new TokenBundle(Token.ONYX, 1), 1),
                new Development("Ruby Vein", 0, CardUtils.getCostTokenSetFromLetters("RRCC"), new TokenBundle(Token.RUBY, 1), 1)
        );

        List<Development> level2Cards = Arrays.asList(
                new Development("Emerald Exchange", 1, CardUtils.getCostTokenSetFromLetters("EEECCRR"), new TokenBundle(Token.EMERALD, 2), 2),
                new Development("Sapphire Market", 1, CardUtils.getCostTokenSetFromLetters("SSCCOO"), new TokenBundle(Token.SAPPHIRE, 2), 2),
                new Development("Onyx Workshop", 1, CardUtils.getCostTokenSetFromLetters("OOCRRR"), new TokenBundle(Token.ONYX, 2), 2),
                new Development("Ruby Guild", 1, CardUtils.getCostTokenSetFromLetters("RRCCOO"), new TokenBundle(Token.RUBY, 2), 2)
        );

        List<Development> level3Cards = Arrays.asList(
                new Development("Emerald Vault", 2, CardUtils.getCostTokenSetFromLetters("EEEERRRRCC"), new TokenBundle(Token.EMERALD, 3), 3),
                new Development("Sapphire Grand Market", 2, CardUtils.getCostTokenSetFromLetters("SSSCCOOO"), new TokenBundle(Token.SAPPHIRE, 3), 3),
                new Development("Onyx Treasure Hall", 2, CardUtils.getCostTokenSetFromLetters("OOOCCCERR"), new TokenBundle(Token.ONYX, 3), 3),
                new Development("Ruby Master Mine", 2, CardUtils.getCostTokenSetFromLetters("RRRCCCOOO"), new TokenBundle(Token.RUBY, 3), 3)
        );

        allCards.add(new ArrayList<>(level1Cards));
        allCards.add(new ArrayList<>(level2Cards));
        allCards.add(new ArrayList<>(level3Cards));

        return allCards;
    }

    public static List<Noble> createNobles() {
        List<Noble> allNobles = new ArrayList<>();

        allNobles.add(new Noble("Mary Stuart", 3, CardUtils.getCostTokenSetFromLetters("EEEERRRR")));
        allNobles.add(new Noble("Suleiman the Magnificent", 3, CardUtils.getCostTokenSetFromLetters("SSSSEEEE")));
        allNobles.add(new Noble("Niccolo Machiavelli", 3, CardUtils.getCostTokenSetFromLetters("CCCCSSSS")));
        allNobles.add(new Noble("Isabella of Castile", 3, CardUtils.getCostTokenSetFromLetters("CCCCOOOO")));
        allNobles.add(new Noble("Henry VIII", 3, CardUtils.getCostTokenSetFromLetters("RRRROOOO")));
        allNobles.add(new Noble("Elizabeth of Austria", 3, CardUtils.getCostTokenSetFromLetters("CCCSSSOOO")));
        allNobles.add(new Noble("Francois the 1st", 3, CardUtils.getCostTokenSetFromLetters("EEERRROOO")));
        allNobles.add(new Noble("Charles the Fifth", 3, CardUtils.getCostTokenSetFromLetters("CCCRRROOO")));
        allNobles.add(new Noble("Catherine de Medici", 3, CardUtils.getCostTokenSetFromLetters("SSSEEERRR")));
        allNobles.add(new Noble("Anne of Brittany", 3, CardUtils.getCostTokenSetFromLetters("CCCSSSEEE")));

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
        List<Integer> selectedIndexes = new ArrayList<>();

        while (noblesForMarket.size() < noblesToSelect && selectedIndexes.size() < allNobles.size()) {
            int index = random.nextInt(allNobles.size());
            if (!selectedIndexes.contains(index)) {
                selectedIndexes.add(index);
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
            Set<Integer> selectedIndexes = new HashSet<>();
            int cardsToSelect = Math.min(4, levelCards.size());

            while (marketCards.size() < cardsToSelect && selectedIndexes.size() < levelCards.size()) {
                int index = random.nextInt(levelCards.size());
                if (!selectedIndexes.contains(index)) {
                    selectedIndexes.add(index);
                    marketCards.add(levelCards.get(index));
                }
            }

            developmentCardsForMarket.add(marketCards);
        }

        return developmentCardsForMarket;
    }

    @Override public String toString() { return "Market{" + "allCards=" + allCards + ", allNobles=" + allNobles + ", cardsAvailableInMarket=" + cardsAvailableInMarket + ", noblesAvailableInMarket=" + noblesAvailableInMarket + ", unclaimedTokens=" + unclaimedTokens + '}'; }

}







}
