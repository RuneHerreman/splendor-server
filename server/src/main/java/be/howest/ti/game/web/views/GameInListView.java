package be.howest.ti.game.web.views;

import be.howest.ti.game.logic.Game;
import be.howest.ti.game.logic.Player;

import java.util.ArrayList;
import java.util.List;

public class GameInListView {
    private final Game game;

    public GameInListView(Game game) {
        this.game = game;
    }

    public List<String> getPlayers() {
        List<String> playerNames = new ArrayList<>();

        for (Player player : game.getPlayers()) {
            playerNames.add(player.getName());
        }

        return playerNames;
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
}
