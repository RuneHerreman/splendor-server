package be.howest.ti.game.logic.gameTools;

import be.howest.ti.game.logic.Player;

import java.util.Map;
import java.util.Objects;

public class Development {
    private final String name;
    private final int level;
    private final int prestigePoints;
    private final Map<Token, Integer> cost;
    private final Token bonus;

    //Name	Level	Type	Image	Points	Cost
    public Development(String name, int prestigePoints, Map<Token, Integer>cost , Token bonus , int level) {
        this.name = name;
        this.prestigePoints = prestigePoints;
        this.cost = cost;
        this.bonus = bonus;
        this.level = level;
    }

    public boolean isCardAffordableByPlayer(Player player) {
        Map<Token, Integer> playerTokens = player.generateTokensAndBonuses();
        return hasEnoughTokens(playerTokens);
    }

    public boolean isCardAffordableByPlayerWithGoldToken(Player player) {
        Map<Token, Integer> playerTokens = player.generateTokensAndBonuses();
        return hasEnoughTokensWithGold(playerTokens);
    }

    private boolean hasEnoughTokens(Map<Token, Integer> playerTokens) {
        for (Token token : cost.keySet()) {
            int requiredTokenAmount = cost.get(token);
            int availableTokenAmount = playerTokens.getOrDefault(token, 0);

            if (availableTokenAmount < requiredTokenAmount) {
                return false;
            }
        }
        return true;
    }

    private boolean hasEnoughTokensWithGold(Map<Token, Integer> playerTokens) {
        int availableGoldTokenAmount = playerTokens.getOrDefault(Token.GOLD, 0);
        int missingTokenAmount = 0;

        for (Token token : cost.keySet()) {
            int requiredTokenAmount = cost.get(token);
            int availableTokenAmount = playerTokens.getOrDefault(token, 0);

            if (availableTokenAmount < requiredTokenAmount) {
                missingTokenAmount += requiredTokenAmount - availableGoldTokenAmount;

                if (missingTokenAmount > availableGoldTokenAmount) {
                    return false;
                }
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
    public int hashCode() {return Objects.hash(name, level, prestigePoints, cost, bonus);}
    public String getName() {return name;}
    public int getPrestigePoints() {return prestigePoints;}
    public Map<Token, Integer> getCost() {return cost;}
    public int getLevel() {return level;}
    public Token getBonus() {return bonus;}
    public String toString() {return name;}

}
