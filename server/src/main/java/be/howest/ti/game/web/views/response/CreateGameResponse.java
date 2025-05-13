package be.howest.ti.game.web.views.response;

public class CreateGameResponse extends AbstractResponseWithHiddenStatus {

    private String playerName;
    private int gameId;


    public CreateGameResponse(String playerName, int gameId) {
        super(200);
        this.playerName = playerName;
        this.gameId = gameId;

    }

    public int getGameId() {
        return gameId;
    }

    public String getPlayerToken() {
        return gameId + "_" + playerName;
    }

    public String getPlayerName() {
        return playerName;
    }

}
