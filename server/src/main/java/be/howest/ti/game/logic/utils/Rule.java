package be.howest.ti.game.logic.utils;

public class Rule {
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
}
