package be.howest.ti.game.web.views.response;

public class JoinGameResponse extends  AbstractResponseWithHiddenStatus {
    private final String gameId;
    private final String playerName;

    public JoinGameResponse(String gameId, String playerName) {
        super(200);
        this.gameId = gameId;
        this.playerName = playerName;
    }

    public String getGameId() {
        return gameId;
    }

    public String getPlayerName() {
        return playerName;
    }
    public String getPlayerToken() {
        return gameId + "_" + playerName;
    }
}
