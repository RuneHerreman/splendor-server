package be.howest.ti.game.web.views;

import be.howest.ti.game.logic.Market;
import be.howest.ti.game.logic.gametools.Development;

import java.util.List;

public class CardLevelsInListView {
    private final List<Development> cardLevel;
    private final Market market;

    public CardLevelsInListView(List<Development> cardLevel, Market market) {
        this.cardLevel = cardLevel;
        this.market = market;
    }

    public int getLevel() {
        return cardLevel.get(0).getLevel();
    }

    public int getCardStackSize() {
        return market.getAllCards().get(getLevel() - 1).size();
    }

    public List<Development> getVisibleCards() {
        return cardLevel;
    }


}
