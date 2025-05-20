package be.howest.ti.game.web.views.response;

import be.howest.ti.game.logic.Player;
import be.howest.ti.game.logic.gameTools.Development;
import be.howest.ti.game.logic.gameTools.Token;

import java.util.List;
import java.util.Map;

public class ReserveDevelopmentResponse extends AbstractResponseWithHiddenStatus{
    private final Map<Token, Integer> tokens;
    private final List<Development> reserve;

    public ReserveDevelopmentResponse(Player player) {
        super(200);
        this.tokens = player.getTokens();
        this.reserve = player.getReserved();
    }

    public List<Development> getReserve() {
        return reserve;
    }

    public Map<Token, Integer> getTokens() {
        return tokens;
    }
}
