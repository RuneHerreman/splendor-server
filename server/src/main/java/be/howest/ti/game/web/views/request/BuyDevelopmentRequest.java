package be.howest.ti.game.web.views.request;

import be.howest.ti.game.logic.gameTools.Token;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import java.util.logging.Logger;

import java.util.EnumMap;
import java.util.Map;

public class BuyDevelopmentRequest extends BaseSplendorRequest{
    public BuyDevelopmentRequest(RoutingContext context){
        super(context);
    }

    public String getDevelopment(){
        return params.body().getJsonObject().getJsonObject("development").getString("name");
    }

    public Map<Token, Integer> getPayment(){
        Map<Token, Integer> tokenMap = new EnumMap<>(Token.class);
        JsonObject payment = params.body().getJsonObject().getJsonObject("payment");

        payment.forEach(pair -> {
            Token token = Token.valueOf(pair.getKey().toUpperCase());
            int amount = Integer.parseInt(pair.getValue().toString());
            tokenMap.put(token, amount);
        });

        Logger.getLogger("Payment: " + tokenMap);
        return tokenMap;
    }

    public int getGameId(){
        return params.pathParameter("gameId").getInteger();
    }

    public String getPlayerName(){
        return params.pathParameter("playerName").getString();
    }
}
