package be.howest.ti.game.web.views.response;

import be.howest.ti.game.logic.Game;
import be.howest.ti.game.logic.utils.TokenMapConvertor;

import java.util.Map;

public class UpdateTokensResponse extends AbstractResponseWithHiddenStatus {
    private Game game;

    public UpdateTokensResponse(Game game) {
        super(200);
        this.game = game;
    }

    public Map<String, Integer> getTokens() {
        return TokenMapConvertor.convertToStringMap(game.getActivePlayer().getTokens());
    }
}

