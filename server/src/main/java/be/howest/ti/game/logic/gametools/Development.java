package be.howest.ti.game.logic.gametools;

import be.howest.ti.game.logic.Player;

import java.util.Map;
import java.util.Objects;

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

    public  boolean isCardAffordableByPlayerWithGoldToken(Player player) {
        Map<Token, Integer> playerTokens = player.generateTokensAndBonuses();
        int availableGoldTokens = playerTokens.getOrDefault(Token.GOLD, 0);
        int goldTokensNeeded = 0;

        for (Map.Entry<Token, Integer> cardCost : cost.entrySet()) {
            Token requiredToken = cardCost.getKey();
            int requiredAmount = cardCost.getValue();
            int playerTokenAmount = playerTokens.getOrDefault(requiredToken, 0);

            if (playerTokenAmount < requiredAmount) {
                goldTokensNeeded += (requiredAmount - playerTokenAmount);
            }

            if (goldTokensNeeded > availableGoldTokens) {
                return false;
            }
        }

        return true;

    }
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Development that = (Development) o;
        return level == that.level && prestigePoints == that.prestigePoints && Objects.equals(name, that.name) && Objects.equals(cost, that.cost) && bonus == that.bonus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, level, prestigePoints, cost, bonus);
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
