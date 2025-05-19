package be.howest.ti.game.web.views.response;

import be.howest.ti.game.logic.Market;
import be.howest.ti.game.logic.gameTools.Noble;

import java.util.List;

public class GetNoblesResponse extends AbstractResponseWithHiddenStatus {
    public GetNoblesResponse() {
        super(200);
    }

    public List<Noble> getNobles() {
        return Market.createNobles();
    }
}
