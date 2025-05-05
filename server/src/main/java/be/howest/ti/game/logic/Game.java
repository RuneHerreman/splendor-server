package be.howest.ti.game.logic;

import be.howest.ti.game.logic.utils.GameState;
import be.howest.ti.game.logic.utils.Noble;
import be.howest.ti.game.logic.utils.TokenBundle;

import java.util.List;
import java.util.Set;

public class Game {

    private String gameName;
    private int gameId;
    private boolean started;
    private int numberOfPlayers;
    private Player activePlayer;
    private Set<Player> players;
    private Market market;
    private Set<TokenBundle> unclaimedTokens;
    private List<Noble> unclaimedNobles;
    private Player winner;
    private GameState gameState;
}
