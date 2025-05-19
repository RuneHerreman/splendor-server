package be.howest.ti.game.web.views.request;

import io.vertx.ext.web.RoutingContext;

public class DeleteGamesRequest extends BaseSplendorRequest{
    public DeleteGamesRequest(RoutingContext context) {
        super(context);
    }

    public boolean bodyIsEmpty() {
        return params.body().getJsonObject().isEmpty();
    }

    public int getGameID() {
        return params.body().getJsonObject().getInteger("gameID");
    }

    public String getScope() {
        return params.body().getJsonObject().getString("scope");
    }
}
