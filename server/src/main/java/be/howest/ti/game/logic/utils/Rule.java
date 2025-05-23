package be.howest.ti.game.logic.utils;

public class Rule {

    private static final int MAX_RESERVED_CARDS = 3;
    /**
    * Returns the number of tokens per type based on the number of players.
    * Game rules:
    * - 2 players: 4 tokens of each type
    * - 3 players: 5 tokens of each type
    * - 4 or more players: 7 tokens of each type
    */
    public static int getTokenCountByPlayer(int totalPlayers) {
        if (totalPlayers == 2) {
            return 4;
        } else if (totalPlayers == 3) {
            return 5;
        } else {
            return 7;
        }
    }
    /**
    * Returns the expected number of tokens a player may take based on how many unique token types are selected.
     * * * Game rules:
    * - If the player takes 3 different token types, they may take 1 of each (returns 1).
    * - If the player takes only 1 type, they may take 2 of that type (returns 2).
    */
    public static int getExpectedTokenCount(int tokenTypeCount) {
        if (tokenTypeCount == 3) {
            return 1;
        } else if (tokenTypeCount == 1) {
            return 2;
        } else {
            return -1;
        }
    }
    /*** the maximum number of cards a player can reserve during the game.*/
    public static int getMaxReservedCards() {
        return MAX_RESERVED_CARDS;
    }
    /// /*** * Determines the maximum number of reserved cards that can be visible in the market  based on the number of players.
    public static int getMaxReservedCardsForMarket(int amountOfPlayers) {
        return amountOfPlayers + 1;
    }
    /** the initial number of gold (wild) tokens in the game./*/
    public static int initGoldToken() {
        return 5 ;
    }
    /** * Returns how many cards should be available per level in the market.*/
    public static int getAmountOfCardsByLevel() {
        return 4;
    }
    /** the minimum number of tokens of a single type that must be present in the market to allow a player to take 2 of that type.*/
    public static int getDoubleTokenPossibility() {
        return 4;
    }
}
