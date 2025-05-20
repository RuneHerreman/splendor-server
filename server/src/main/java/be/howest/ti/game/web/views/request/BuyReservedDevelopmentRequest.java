package be.howest.ti.game.web.views.request;

import be.howest.ti.game.logic.gameTools.Token;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

import java.util.HashMap;
import java.util.Map;

public class BuyReservedDevelopmentRequest extends BaseSplendorRequest {
    public BuyReservedDevelopmentRequest(RoutingContext ctx) {
        super(ctx);
    }

    public int getGameId() {
        return params.pathParameter("gameId").getInteger();
    }

    public String getPlayerName() {
        return params.pathParameter("playerName").getString();
    }

    public String getDevelopment() {
        return params.pathParameter("developmentName").getString();
    }

    public Map<Token, Integer> getPayment(){
        Map<Token, Integer> tokenMap = new HashMap<>();
        JsonObject payment = params.body().getJsonObject().getJsonObject("payment");

        payment.forEach(pair -> {
            Token token = Token.valueOf(pair.getKey().toUpperCase());
            int amount = Integer.parseInt(pair.getValue().toString());
            tokenMap.put(token, amount);
        });

        return tokenMap;
    }

}
