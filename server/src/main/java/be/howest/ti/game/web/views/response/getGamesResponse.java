package be.howest.ti.game.web.views.response;

import be.howest.ti.game.logic.Game;
import be.howest.ti.game.logic.service.GameManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class getGamesResponse extends AbstractResponseWithHiddenStatus {

    private GameManager gameManager;

    public getGamesResponse(GameManager gameManager) {
        super(200);
        this.gameManager = gameManager;
    }

    public List<Game> getGames() {
        return (gameManager.getAllGames());
    }




}