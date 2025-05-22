package be.howest.ti.game.web.views.request;

import io.vertx.ext.web.RoutingContext;

public class JoinGameRequest extends BaseSplendorRequest {
    public JoinGameRequest(RoutingContext ctx) {
        super(ctx);
    }

    public int getGameId() {
        return params.pathParameter("gameId").getInteger();
    }

    public String getPlayerName() {
        return params.pathParameter("playerName").getString().split("000")[0];
    }

    public String getIconPath() {
        return params.pathParameter("playerName").getString().split("000")[1];
    }
}
