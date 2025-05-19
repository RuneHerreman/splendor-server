package be.howest.ti.game.logic.service;


import be.howest.ti.game.logic.Game;
import be.howest.ti.game.logic.Player;
import be.howest.ti.game.logic.gameTools.Noble;
import be.howest.ti.game.logic.gameTools.Token;

import javax.smartcardio.Card;
import java.util.List;
import java.util.Map;

public interface SplendorService {
    Game createGame(int maxPlayers , Player host);
    Game createGame(String gameName,int maxPlayers , Player host);

    Game deleteGame(int gameID);
    List<Game> deleteAllGames();

    List<Game> getAllGames();
    List<Game> getAllGames(boolean started);
    Game getGameByID(int gameID);

    List<Game> getStartedGames();
    List<Game> getNonStartedGames();

    Game buyDevelopment(int gameID, String playerName, String developmentName, boolean reserved, Map<Token, Integer> payment);
    Noble chooseNoble(String playerName, int gameID, Noble noble);
    Game reserveCard(String playerName, int gameID, String developmentName);
}
