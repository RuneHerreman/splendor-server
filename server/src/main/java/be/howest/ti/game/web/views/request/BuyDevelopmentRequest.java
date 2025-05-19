package be.howest.ti.game.web.views.request;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class BuyDevelopmentRequest extends BaseSplendorRequest{
    public BuyDevelopmentRequest(RoutingContext context){
        super(context);
    }

    public String getDevelopment(){
        return params.body().getJsonObject().getJsonObject("development").getString("name");
    }

    public JsonObject getPayment(){
        return params.body().getJsonObject().getJsonObject("payment");
    }

    public int getGameId(){
        return params.pathParameter("gameId").getInteger();
    }

    public String getPlayerName(){
        return params.pathParameter("playerName").getString();
    }
}
