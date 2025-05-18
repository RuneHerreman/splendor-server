package be.howest.ti.game.logic.service;


import be.howest.ti.game.logic.Game;
import be.howest.ti.game.logic.Player;

import java.util.List;

public interface SplendorService {
    Game createGame(int maxPlayers , Player host);
    Game createGame(String gameName,int maxPlayers , Player host);

    void deleteGame(int gameID);
    void deleteAllGames();

    List<Game> getAllGames();
    List<Game> getAllGames(boolean started);
    Game getGameByID(int gameID);
}
