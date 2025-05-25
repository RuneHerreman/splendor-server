package be.howest.ti.game.web.views.response;

import be.howest.ti.game.logic.Game;
import be.howest.ti.game.logic.gameTools.Development;
import be.howest.ti.game.logic.utils.TokenMapConvertor;
import be.howest.ti.game.web.views.DevelopmentInListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BuyReservedDevelopmentResponse extends AbstractResponseWithHiddenStatus {
    private final Game game;

    public BuyReservedDevelopmentResponse(Game game) {
        super(200);
        this.game = game;
    }

    public Map<String, Integer> getToken() {
        return TokenMapConvertor.convertToStringMap(game.getPlayerWithLastAction().getTokens());
    }

    public List<DevelopmentInListView> getDevelopments() {
        List<DevelopmentInListView> listView = new ArrayList<>();

        for (Development development : game.getPlayerWithLastAction().getPurchasedDevelopments()) {
            listView.add(new DevelopmentInListView(development));
        }

        return listView;
    }
}
