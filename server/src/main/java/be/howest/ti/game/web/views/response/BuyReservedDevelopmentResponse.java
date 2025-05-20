package be.howest.ti.game.web.views.response;

import be.howest.ti.game.logic.Game;
import be.howest.ti.game.logic.gametools.Development;
import be.howest.ti.game.logic.gametools.Token;

import java.util.List;
import java.util.Map;

public class BuyReservedDevelopmentResponse extends AbstractResponseWithHiddenStatus {
    private final Game game;

    public BuyReservedDevelopmentResponse(Game game) {
        super(200);
        this.game = game;
    }

    public Map<Token, Integer> getToken() {
        return game.getActivePlayer().getTokens();
    }

    public List<Development> getDeveloments() {
        return game.getActivePlayer().getPurchasedDevelopments();
    }
}
