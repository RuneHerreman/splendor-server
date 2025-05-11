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

    public Market(List<List<Development>> allCards, List<Noble> noblesAvailableInMarket , List<List<Development>> cardsAvailableInMarket , List<Noble> nobles , List<TokenBundle> unclaimedTokens) {
        this.allCards = allCards;
        this.allNobles = noblesAvailableInMarket;
        this.cardsAvailableInMarket = cardsAvailableInMarket;
        this.noblesAvailableInMarket = noblesAvailableInMarket;
        this.unclaimedTokens =unclaimedTokens;
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
}
