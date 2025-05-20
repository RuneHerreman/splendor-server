package be.howest.ti.game.web.views.request;

import be.howest.ti.game.logic.Market;
import be.howest.ti.game.logic.gametools.Noble;
import be.howest.ti.game.logic.utils.CardUtils;
import io.vertx.ext.web.RoutingContext;

public class ChooseNobleRequest extends BaseSplendorRequest{
    public ChooseNobleRequest(RoutingContext ctx) {
        super(ctx);
    }

    public int getGameID(){
       return params.pathParameter("gameID").getInteger();
    }
    public String getPlayerName(){
        return params.pathParameter("playerName").getString();
    }
    public Noble getNoble(){
        String  nobleName = params.body().getJsonObject().getString("name");
        return CardUtils.getNobleCardByName(nobleName, Market.createNobles());
    }


}
