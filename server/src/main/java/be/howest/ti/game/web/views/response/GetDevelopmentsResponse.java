package be.howest.ti.game.web.views.response;

import be.howest.ti.game.logic.Market;
import be.howest.ti.game.logic.gameTools.Development;
import be.howest.ti.game.web.views.DevelopmentInListView;

import java.util.ArrayList;
import java.util.List;

public class GetDevelopmentsResponse extends AbstractResponseWithHiddenStatus {
    public GetDevelopmentsResponse() {
        super(200);
    }

    public List<DevelopmentInListView> getDevelopments() {
        List<DevelopmentInListView> listView = new ArrayList<>();

        for (List<Development> level: Market.createAllCards()){
            for (Development development: level){
                listView.add(new DevelopmentInListView(development));
            }
        }

        return listView;
    }
}
