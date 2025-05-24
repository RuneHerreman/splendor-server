package be.howest.ti.game.logic.service;


import be.howest.ti.game.logic.Game;
import be.howest.ti.game.logic.gameTools.Player;


import java.util.List;


public interface SplendorService {
    Game createGame(int maxPlayers , Player host, boolean privateStatus);
    Game createGame(String gameName,int maxPlayers , Player host, boolean privateStatus);

    Game deleteGame(int gameID);
    List<Game> deleteAllGames();

    List<Game> getAllGames();
    List<Game> getAllGames(boolean started);
    Game getGameByID(int gameID);

    List<Game> getStartedGames();
    List<Game> getNonStartedGames();

}
