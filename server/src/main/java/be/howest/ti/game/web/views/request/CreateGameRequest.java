package be.howest.ti.game.web.views.request;

import io.vertx.ext.web.RoutingContext;

public class CreateGameRequest extends BaseSplendorRequest {

    private int authorizedGameId;
    private String authorizedPlayerName;

    public CreateGameRequest(RoutingContext ctx) {
        super(ctx);
    }

    public int getAuthorizedGameId() {
        return authorizedGameId;
    }

    public void setAuthorizedGameId(int authorizedGameId) {
        this.authorizedGameId = authorizedGameId;
    }

    public String getAuthorizedPlayerName() {
        return authorizedPlayerName;
    }

    public void setAuthorizedPlayerName(String authorizedPlayerName) {
        this.authorizedPlayerName = authorizedPlayerName;
    }


}
