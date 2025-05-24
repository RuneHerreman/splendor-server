package be.howest.ti.game.web.views.response;

import be.howest.ti.game.logic.gameTools.Market;
import be.howest.ti.game.logic.gameTools.Noble;
import be.howest.ti.game.web.views.NobleInListView;

import java.util.ArrayList;
import java.util.List;

public class GetNoblesResponse extends AbstractResponseWithHiddenStatus {
    public GetNoblesResponse() {
        super(200);
    }

    public List<NobleInListView> getNobles() {
        List<NobleInListView> listView = new ArrayList<>();

        for (Noble noble : Market.createNobles()) {
            listView.add(new NobleInListView(noble));
        }

        return listView;
    }
}
