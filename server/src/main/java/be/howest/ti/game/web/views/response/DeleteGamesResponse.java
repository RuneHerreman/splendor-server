package be.howest.ti.game.web.views.response;

import be.howest.ti.game.logic.Game;
import be.howest.ti.game.web.views.request.BaseSplendorRequest;

import java.util.List;

public class DeleteGamesResponse extends AbstractResponseWithHiddenStatus {
    private final List<Game> deletedGames;

    public DeleteGamesResponse(List<Game> deletedGames) {
        super(200);
        this.deletedGames = deletedGames;
    }

    public List<Game> getDeletedGames(){
        return deletedGames;
    }
}
