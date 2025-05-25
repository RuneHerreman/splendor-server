package be.howest.ti.game.web.views;

import be.howest.ti.game.logic.gameTools.Player;
import be.howest.ti.game.logic.gameTools.Development;
import be.howest.ti.game.logic.gameTools.Noble;
import be.howest.ti.game.logic.utils.TokenMapConvertor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PlayerInListView {
    private final Player player;
    public PlayerInListView(Player player) {
        this.player = player;
    }

    public String getName() {
        return player.getName();
    }

    public int getPrestigePoints() {
        return player.getPrestigePoints();
    }

    public List<DevelopmentInListView> getReserved() {
        List<DevelopmentInListView> listView = new ArrayList<>();

        for (Development development : player.getReserved()) {
            listView.add(new DevelopmentInListView(development));
        }
        return listView;
    }

    public List<DevelopmentInListView> getBuilt() {
        List<DevelopmentInListView> listView = new ArrayList<>();

        for (Development development : player.getPurchasedDevelopments()) {
            listView.add(new DevelopmentInListView(development));
        }
        return listView;
    }

    public List<Noble> getNobles() {
        return player.getNobles();
    }

    public Map<String, Integer> getTokens() {
        return TokenMapConvertor.convertToStringMap(player.getTokens());
    }

    public Map<String, Integer> getBonuses() {
        return TokenMapConvertor.convertToStringMap(player.getBonuses());
    }

    public String getIconPath() {
        return player.getIconPath();
    }
}
