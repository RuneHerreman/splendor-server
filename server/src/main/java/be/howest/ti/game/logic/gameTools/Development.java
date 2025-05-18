package be.howest.ti.game.logic.gameTools;

import be.howest.ti.game.logic.Player;

import java.util.Map;

public class Development {
    private final String name;
    private final int level;
    private final int prestigePoints;
    private final Map<Token, Integer> cost;
    private final Token bonus;

    public Development(String name, int prestigePoints, Map<Token, Integer>cost , Token bonus , int level) {
        this.name = name;
        this.prestigePoints = prestigePoints;
        this.cost = cost;
        this.bonus = bonus;
        this.level = level;
    }
    public boolean isCardAffordableByPlayer(Player player) {
        Map<Token, Integer> playerTokens = player.generateTokensAndBonuses();

        for (Map.Entry<Token, Integer> cardCost : this.cost.entrySet()) {
            Token requiredToken = cardCost.getKey();
            int requiredAmount = cardCost.getValue();
            int playerTokenAmount = playerTokens.getOrDefault(requiredToken, 0);

            if (playerTokenAmount < requiredAmount) {
                return false;
            }
        }
        return true;
    }



    public String getName() {
        return name;
    }
    public int getPrestigePoints() {
        return prestigePoints;
    }
    public Map<Token, Integer> getCost() {
        return cost;
    }
    public int getLevel() {
        return level;
    }
    public Token getBonus() {
        return bonus;
    }
    public String toString() {
        return name;
    }

}
