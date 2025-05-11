package be.howest.ti.game.logic;

import be.howest.ti.game.logic.utils.*;

import java.util.*;
import java.util.stream.Collectors;

public class Market {

    private final List<List<Development>> allCards;
    private final List<Noble> allNobles;
    private final List<List<Development>> cardsAvailableInMarket ;
    private final List<Noble> noblesAvailableInMarket;
    private List<TokenBundle> unclaimedTokens;

    public Market(int amountOfPlayers) {
        allCards = createAllCards();
        allNobles = createNobles();
        this.cardsAvailableInMarket = getInitDevelopmentCardsForMarket();
        this.noblesAvailableInMarket = getInitNoblesForMarket(amountOfPlayers);
        this.unclaimedTokens = createInitTokens(amountOfPlayers);
    }

    private List<TokenBundle> createInitTokens(int amountOfPlayers) {
    }

    private List<Noble> getInitNoblesForMarket(int amountOfPlayers) {
        return null;
    }

    private List<List<Development>> getInitDevelopmentCardsForMarket() {
        return null;
    }

    private List<Noble> createNobles() {
        return null;
    }

    private List<List<Development>> createAllCards() {
        List<List<Development>> allCards = new ArrayList<>();

        // Dummy data / moet alle cards ophalen van een file >recorces > developmentCards
        //todo
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

    public void setCardToMarket(Development developmentCard){
        int cardLevel = developmentCard.getLevel();
        int cardLevelIndex = cardLevel - 1;
        cardsAvailableInMarket.get(cardLevelIndex).add(developmentCard);
        allCards.get(cardLevelIndex).remove(developmentCard);
    }

    public void setNobleToMarket(Noble noble){;
        noblesAvailableInMarket.add(noble);
        allNobles.remove(noble);
    }

    public void removeCardFromMarket(Development developmentCard){
        int cardLevel = developmentCard.getLevel();
        int cardLevelIndex = cardLevel - 1;
        cardsAvailableInMarket.get(cardLevelIndex).remove(developmentCard);
    }

    public void removeNobleFromMarket(Noble noble){;
        noblesAvailableInMarket.remove(noble);
    }





}
