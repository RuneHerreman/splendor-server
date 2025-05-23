package be.howest.ti.game.web.views;

import be.howest.ti.game.logic.gameTools.Market;
import be.howest.ti.game.logic.gameTools.Development;

import java.util.ArrayList;
import java.util.List;

public class CardLevelsInListView {
    private final List<Development> cardLevel;
    private final Market market;

    public CardLevelsInListView(List<Development> cardLevel, Market market) {
        this.cardLevel = cardLevel;
        this.market = market;
    }

    public int getLevel() {
        return cardLevel.getFirst().getLevel();
    }

    public int getCardStackSize() {
        return market.getAllCards().get(getLevel() - 1).size();
    }

    public List<DevelopmentInListView> getVisibleCards() {
        List<DevelopmentInListView> listView = new ArrayList<>();

        for (Development card : cardLevel) {
            listView.add(new DevelopmentInListView(card));
        }

        return listView;
    }
}
