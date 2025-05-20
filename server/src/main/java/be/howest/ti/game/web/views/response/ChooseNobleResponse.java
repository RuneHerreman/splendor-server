package be.howest.ti.game.web.views.response;

import be.howest.ti.game.logic.gameTools.Noble;
import be.howest.ti.game.web.views.NobleInListView;

public class ChooseNobleResponse extends AbstractResponseWithHiddenStatus{
    private final Noble noble;

    public ChooseNobleResponse(Noble noble) {
        super(200);
        this.noble = noble;
    }

    public NobleInListView getNoble() {
        return new NobleInListView(noble);
    }
}
