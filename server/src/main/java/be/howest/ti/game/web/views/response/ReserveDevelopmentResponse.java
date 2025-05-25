package be.howest.ti.game.web.views.response;

import be.howest.ti.game.logic.gameTools.Player;
import be.howest.ti.game.logic.gameTools.Development;
import be.howest.ti.game.logic.gameTools.Token;
import be.howest.ti.game.logic.utils.TokenMapConvertor;
import be.howest.ti.game.web.views.DevelopmentInListView;

import java.util.ArrayList;
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

    public List<DevelopmentInListView> getReserve() {
        List<DevelopmentInListView> listView = new ArrayList<>();

        for (Development development : reserve) {
            listView.add(new DevelopmentInListView(development));
        }

        return listView;
    }

    public Map<String, Integer> getTokens() {
        return TokenMapConvertor.convertToStringMap(tokens);
    }
}
