package be.howest.ti.game.web.views.request;

import be.howest.ti.game.web.views.response.AbstractResponseWithHiddenStatus;
import io.vertx.ext.web.RoutingContext;

public class UpdateTokensRequest extends BaseSplendorRequest {
    public UpdateTokensRequest(RoutingContext ctx) {
        super(ctx);
    }
}
