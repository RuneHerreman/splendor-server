package be.howest.ti.game.logic;

import be.howest.ti.game.logic.utils.*;

import java.util.*;
import java.util.stream.Collectors;

public class Market {

    private final List<List<Development>> allCards;
    private final List<Noble> allNobles;
    private final List<List<Development>> cardsAvailableInMarket ;
    private final List<Noble> noblesAvailableInMarket;

    public Market(List<List<Development>> allCards, List<Noble> noblesAvailableInMarket , List<List<Development>> cardsAvailableInMarket , List<Noble> nobles) {
        this.allCards = allCards;
        this.allNobles = noblesAvailableInMarket;
        this.cardsAvailableInMarket = cardsAvailableInMarket;
        this.noblesAvailableInMarket = noblesAvailableInMarket;
    }

    public List<List<Development>> getAllCards() {
        return allCards;
    }

    public List<Noble> getAllNobles() {
        return allNobles;
    }

    public List<List<Development>> getCardsForMarket() {
        return cardsAvailableInMarket;
    }

    public List<Noble> getNoblesForMarket() {
        return noblesAvailableInMarket;
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
