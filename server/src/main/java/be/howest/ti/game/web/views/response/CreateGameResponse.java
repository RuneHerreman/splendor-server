package be.howest.ti.game.web.views.response;

import be.howest.ti.game.logic.Game;

public class CreateGameResponse extends AbstractResponseWithHiddenStatus {
    private final int gameId;
    private final String playerName;

    public CreateGameResponse(Game game, String playerName) {
        super(200);
        this.gameId = game.getGameId();
        this.playerName = playerName;
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
