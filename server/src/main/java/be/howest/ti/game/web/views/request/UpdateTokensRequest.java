package be.howest.ti.game.web.views.request;

import be.howest.ti.game.logic.gameTools.Token;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

import java.util.EnumMap;
import java.util.Map;

public class UpdateTokensRequest extends BaseSplendorRequest {

    private final boolean isTake;
    private final Map<Token, Integer> tokenMap;

    public UpdateTokensRequest(RoutingContext ctx) {
        super(ctx);

        JsonObject body = params.body().getJsonObject();
        JsonObject tokenJson;

        if (body.containsKey("take")) {
            tokenJson = body.getJsonObject("take");
            this.isTake = true;
        } else if (body.containsKey("return")) {
            tokenJson = body.getJsonObject("return");
            this.isTake = false;
        } else {
            throw new IllegalArgumentException("Request must contain either 'take' or 'return'");
        }

        this.tokenMap = new EnumMap<>(Token.class);
        tokenJson.forEach(pair -> {
            Token token = Token.valueOf(pair.getKey().toUpperCase());
            int amount = Integer.parseInt(pair.getValue().toString());
            tokenMap.put(token, amount);
        });
    }

    public int getGameId() {
        return params.pathParameter("gameId").getInteger();
    }

    public String getPlayerName() {
        return params.pathParameter("playerName").getString();
    }

    public Map<Token, Integer> getTokens() {
        return tokenMap;
    }

    public boolean isTake() {
        return isTake;
    }
}
