package be.howest.ti.game.web.views.request;

import be.howest.ti.game.logic.gameTools.Token;
import be.howest.ti.game.logic.utils.TokenMapConvertor;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

import java.util.HashMap;
import java.util.Map;

public class UpdateTokensRequest extends BaseSplendorRequest {
    public UpdateTokensRequest(RoutingContext ctx) {
        super(ctx);
    }

    public int getGameId() {
        return params.pathParameter("gameId").getInteger();
    }

    public String getPlayerName() {
        return params.pathParameter("playerName").getString();
    }

    public Map<Token, Integer> getTokens() {
        Map<Token, Integer> tokenMap = new HashMap<>();
        JsonObject take = params.body().getJsonObject().getJsonObject("take");

        take.forEach(pair -> {
            Token token = Token.valueOf(pair.getKey().toUpperCase());
            int amount = Integer.parseInt(pair.getValue().toString());
            tokenMap.put(token, amount);
        });

        return tokenMap;
    }
}
