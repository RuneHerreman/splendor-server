package be.howest.ti.game.web.views.response;

public class CreateGameResponse extends AbstractResponseWithHiddenStatus {
    private int gameId;
    private String playerName;
    private String playerToken;

    public CreateGameResponse() {
        super(200);
    }

    public CreateGameResponse(int gameId, String playerName, String playerToken) {
        super(200);
        this.gameId = gameId;
        this.playerName = playerName;
        this.playerToken = playerToken;
    }

    public int getGameId() {
        return gameId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getPlayerToken() {
        return playerToken;
    }
}
