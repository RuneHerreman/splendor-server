package be.howest.ti.game.web.views.response;

import be.howest.ti.game.logic.Game;
import be.howest.ti.game.logic.Player;
import be.howest.ti.game.logic.gameTools.Development;
import be.howest.ti.game.logic.gameTools.GameState;
import be.howest.ti.game.logic.gameTools.Noble;
import be.howest.ti.game.logic.utils.TokenMapConvertor;
import be.howest.ti.game.web.views.CardLevelsInListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GetGameDetailsResponse extends AbstractResponseWithHiddenStatus{
    private final Game game;

    public GetGameDetailsResponse(Game game) {
        super(200);
        this.game = game;
    }

    public int getGameId() {
        return game.getGameId();
    }

    public String getGameName() {
        return game.getGameName();
    }

    public int getNumberOfPlayers() {
        return game.getNumberOfPlayers();
    }

    public boolean getReturnExcessTokensRequired() {
        return game.isReturnExcessTokensRequired();
    }

    public boolean getPickNobleRequired() {
        return game.isPickNobleRequired();
    }

    public List<Player> getPlayers() {
        return game.getPlayers();
    }

    public List<CardLevelsInListView> getMarket() {
        List<CardLevelsInListView> listView = new ArrayList<>();

        for (List<Development> cardLevel : game.getMarket().getCardsAvailableInMarket()) {
            listView.add(new CardLevelsInListView(cardLevel, game.getMarket()));
        }

        return listView;
    }

    public Map<String, Integer> getUnclaimedTokens() {
        return TokenMapConvertor.convertToStringMap(game.getUnclaimedTokens());
    }

    public List<Noble> getUnclaimedNobles() {
        return game.getUnclaimedNobles();
    }

    public boolean getStarted() {
        return game.isStarted();
    }

    public GameState getGameState() {
        return game.getGameState();
    }

    public String getCurrentPlayer() {
        return game.getActivePlayer().getName();
    }

    public String getWinner() {
        Player winner = game.getWinner();
        if (winner == null) {
            return null;
        }
        return winner.getName();
    }

    public boolean getActive() {
        return game.getActive();
    }
}
