package be.howest.ti.game.web.views.response;

import be.howest.ti.game.logic.Game;
import be.howest.ti.game.logic.Player;
import be.howest.ti.game.logic.gameTools.Noble;
import be.howest.ti.game.logic.gameTools.TokenBundle;

import java.util.ArrayList;
import java.util.List;

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

    public List<MarketInListView> getMarket() {
        return game.getMarket().getMarketInListView();
    }

    public List<TokenBundle> getUnclaimedTokens() {
        return game.getUnclaimedTokens();
    }

    public List<Noble> getUnclaimedNobles() {
        return game.getUnclaimedNobles();
    }

    public boolean getActive() {
        return game.isStarted();
    }



}
