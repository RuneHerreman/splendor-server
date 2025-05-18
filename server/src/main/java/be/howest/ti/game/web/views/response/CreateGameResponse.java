package be.howest.ti.game.web.views.response;

import be.howest.ti.game.logic.Game;
import be.howest.ti.game.logic.Player;

import java.util.List;

public class CreateGameResponse extends AbstractResponseWithHiddenStatus {


    private final String playerName;
    private final int gameId;

    public CreateGameResponse(Game game, String playerName) {
        super(200);
        this.playerName = playerName;
        this.gameId = game.getGameId();
    }

    public int getGameId() {
        return gameId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getPlayerToken() {
        return gameId + "_" + playerName;
    }
}
