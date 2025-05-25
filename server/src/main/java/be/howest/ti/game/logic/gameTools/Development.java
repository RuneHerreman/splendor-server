package be.howest.ti.game.logic.gameTools;

import java.util.EnumMap;
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

    public Map<Token, Integer> validatePayment(Player player, Map<Token, Integer> costWithGoldSubstitutes) {
        Map<Token, Integer> playerTokens = player.getTokens();
        Map<Token, Integer> actualCost = getEffectiveCost(player);

        Map<Token, Integer> tokensToTake = new EnumMap<>(Token.class);
        int goldUsed = costWithGoldSubstitutes.getOrDefault(Token.GOLD, 0);

        int goldNeeded = calculateGoldNeeded(actualCost, costWithGoldSubstitutes, tokensToTake);
        validateTokens(playerTokens, actualCost, goldNeeded);

        if (goldUsed < goldNeeded) {
            throw new IllegalArgumentException("Not enough gold in payment");
        }

        if (goldNeeded > 0) {
            tokensToTake.put(Token.GOLD, goldNeeded);
        }

        checkForNegativeTokens(tokensToTake);

        return tokensToTake;
    }

    public Map<Token, Integer> getEffectiveCost(Player player) {
        Map<Token, Integer> playerBonuses = player.getBonuses();
        Map<Token, Integer> actualCost = new EnumMap<>(Token.class);

        for (Token token: Token.values()) {
            int baseCost = cost.getOrDefault(token, 0);
            int bonusAmount = playerBonuses.getOrDefault(token, 0);
            actualCost.put(token, Math.max(0, baseCost - bonusAmount));
        }
        return actualCost;
    }

    public void validateTokens(Map<Token, Integer> playerTokens, Map<Token, Integer> actualCost, int missingGold) {
        int missingCount = 0;
        for (Token token : actualCost.keySet()) {
            int providedAmount = actualCost.getOrDefault(token, 0);
            int amountOwed = playerTokens.getOrDefault(token, 0);
            if (amountOwed < providedAmount) {
                missingCount += providedAmount - amountOwed;
            }
            if (missingCount > missingGold) {
                throw new IllegalArgumentException("Not enough tokens in payment. Token: " + token);
            }
        }
    }

    public int calculateGoldNeeded(Map<Token, Integer> actualCost, Map<Token, Integer> payment, Map<Token, Integer> tokensToTake) {
        int totalGoldNeeded = 0;

        for (Token token : Token.values()) {
            if (token != Token.GOLD) {
                int neededAmount = actualCost.getOrDefault(token, 0);
                int providedAmount = payment.getOrDefault(token, 0);

                int paidAmount = Math.min(neededAmount, providedAmount);
                if (paidAmount > 0) {
                    tokensToTake.put(token, paidAmount);
                }

                int deficit = neededAmount - paidAmount;

                if (deficit > 0) {
                    totalGoldNeeded += deficit;
                }
            }
        }

        return totalGoldNeeded;
    }

    public void checkForNegativeTokens(Map<Token, Integer> tokensToTake) {
        for (Token token : tokensToTake.keySet()) {
            if (tokensToTake.get(token) < 0) {
                throw new IllegalArgumentException("Payment contains more tokens than needed for token: " + token);
            }
        }
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
