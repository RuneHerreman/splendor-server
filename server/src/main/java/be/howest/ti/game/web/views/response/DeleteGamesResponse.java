package be.howest.ti.game.web.views.response;

import java.io.Serializable;

public class DeleteGamesResponse extends AbstractResponseWithHiddenStatus {
    public DeleteGamesResponse() {
        super(200);
    }

    public String getLunte(int LunteID) {
        return "Lunte with ID " + LunteID + " has been deleted.";
    }
}

