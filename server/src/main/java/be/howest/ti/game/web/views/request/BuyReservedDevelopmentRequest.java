package be.howest.ti.game.web.views.request;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext; //int string path devgame path , pahment mee body

public class BuyReservedDevelopmentRequest extends BaseSplendorRequest {
    public BuyReservedDevelopmentRequest(RoutingContext context){
        super(context);
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

    public String getDevelopmentName(){
        return params.pathParameter("developmentName").getString();
    }

}
