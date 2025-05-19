package be.howest.ti.game.web.views.response;

import be.howest.ti.game.logic.Game;
import be.howest.ti.game.logic.gameTools.Development;
import be.howest.ti.game.logic.gameTools.Token;

import java.util.List;
import java.util.Map;

public class BuyDevelopmentResponse extends AbstractResponseWithHiddenStatus{
    private final Game game;

    public BuyDevelopmentResponse(Game game){
        super(200);
        this.game = game;
    }

    public List<Development> getDevelopments(){
        return game.getActivePlayer().getPurchasedDevelopments();
    }

    public Map<Token, Integer> getTokens(){
        return game.getActivePlayer().getTokens();
    }

    public int getGameId() {
        return game.getGameId();
    }
}
