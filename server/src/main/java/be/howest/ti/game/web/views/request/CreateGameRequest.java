package be.howest.ti.game.web.views.request;

import io.vertx.ext.web.RoutingContext;

public class CreateGameRequest extends BaseSplendorRequest {

    private final String name;
    private final int numberOfPlayers;

    public CreateGameRequest(RoutingContext ctx) {
        super(ctx);
        name = params.body().getJsonObject().getString("playerName");
        numberOfPlayers = params.body().getJsonObject().getInteger("numberOfPlayers");
    }

    public String getPlayerName() {
        return params.body().getJsonObject().getString("playerName");
    }

    public int getNumberOfPlayers() {
        return params.body().getJsonObject().getInteger("numberOfPlayers");
    }

    public String getGameName() {
        return params.body().getJsonObject().getString("gameName");
    }
}
