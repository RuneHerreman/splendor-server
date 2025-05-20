package be.howest.ti.game.web.views.response;

import be.howest.ti.game.logic.Market;
import be.howest.ti.game.logic.gametools.Development;

import java.util.ArrayList;
import java.util.List;

public class GetDevelopmentsResponse extends AbstractResponseWithHiddenStatus {
    public GetDevelopmentsResponse() {
        super(200);
    }

    public List<Development> getDevelopments() {
        List<Development> developments = new ArrayList<>();

        for (List<Development> level: Market.createAllCards()){
            developments.addAll(level);
        }

        return developments;
    }

}
