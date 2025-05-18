package be.howest.ti.game.web.views.request;

import io.vertx.ext.web.RoutingContext;

public class DeleteGamesRequest extends BaseSplendorRequest{
    public DeleteGamesRequest(RoutingContext context) {
        super(context);
    }

    public void DeleteGame(int gameID) {
        // Logic to delete the game with the given gameID
        // This could involve calling a service method to remove the game from the database or in-memory storage
    }
}
