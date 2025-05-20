package be.howest.ti.game.web.views;

import be.howest.ti.game.logic.gameTools.Development;
import be.howest.ti.game.logic.utils.TokenMapConvertor;

import java.util.List;
import java.util.Map;

public class DevelopmentInListView {
    private final Development development;

    public DevelopmentInListView(Development development) {
        this.development = development;
    }

    public String getName() {
        return development.getName();
    }

    public int getLevel() {
        return development.getLevel();
    }

    public int getPrestigePoints() {
        return development.getPrestigePoints();
    }

    public Map<String, Integer> getCost() {
        return TokenMapConvertor.convertToStringMap(development.getCost());
    }

    public String getBonus() {
        return development.getBonus().toString();
    }
}
