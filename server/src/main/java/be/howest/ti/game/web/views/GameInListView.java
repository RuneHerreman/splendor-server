package be.howest.ti.game.web.views;

import be.howest.ti.game.logic.Game;
import be.howest.ti.game.logic.gameTools.Player;

import java.util.ArrayList;
import java.util.List;

public class GameInListView {
    private final Game game;

    public GameInListView(Game game) {
        this.game = game;
    }

    public List<PlayerInListView> getPlayers() {
        List<PlayerInListView> listView = new ArrayList<>();

        for (Player player : game.getPlayers()) {
            listView.add(new PlayerInListView(player));
        }

        return listView;
    }

    public boolean isStarted() {
        return game.isStarted();
    }

    public int getGameId() {
        return game.getGameId();
    }

    public String getGameName() {
        return game.getGameName();
    }

    public int getNumberOfPlayers() {
        return game.getNumberOfPlayers();
    }

    public boolean getReturnExcessTokensRequired() {
        return game.isReturnExcessTokensRequired();
    }

    public boolean getPickNobleRequired() {
        return game.isPickNobleRequired();
    }

    public boolean getIsPrivateGame() {
        return game.getPrivateStatus();
    }
}
