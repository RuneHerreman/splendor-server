package be.howest.ti.game.web.views;

import be.howest.ti.game.logic.gameTools.Noble;
import be.howest.ti.game.logic.utils.TokenMapConvertor;

import java.util.Map;

public class NobleInListView {
    private final Noble noble;

    public NobleInListView(Noble noble) {
        this.noble = noble;
    }

    public String getName() {
        return noble.getName();
    }

    public int getPrestigePoints() {
        return noble.getPrestigePoints();
    }

    public Map<String, Integer> getNeededBonuses() {
        return TokenMapConvertor.convertToStringMap(noble.getRequiredBonuses());
    }
}
