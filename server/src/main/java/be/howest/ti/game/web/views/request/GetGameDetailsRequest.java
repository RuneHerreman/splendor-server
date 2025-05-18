package be.howest.ti.game.web.views.request;

import io.vertx.ext.web.RoutingContext;

public class GetGameDetailsRequest extends BaseSplendorRequest {
    public GetGameDetailsRequest(RoutingContext context) {
        super(context);
    }

    public int getGameID() {
        return params.pathParameter("gameId").getInteger();
    }
}
