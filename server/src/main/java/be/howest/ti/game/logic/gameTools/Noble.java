package be.howest.ti.game.logic.gameTools;

import be.howest.ti.game.logic.Player;
import java.util.Map;
import java.util.Objects;

public class Noble {

    private final String name;
    private final int prestigePoints;
    private final Map<Token, Integer> requiredBonuses;

    public Noble(String name, int prestigePoints, Map<Token, Integer> requiredBonuses) {
        this.name = name;
        this.prestigePoints = prestigePoints;
        this.requiredBonuses = requiredBonuses;
    }

    public boolean isNobleClaimableByPlayer(Player player) {
        Map<Token, Integer> playerBonuses = player.getBonuses();

        for (Token token : requiredBonuses.keySet()) {
            int playerBonusAmount = playerBonuses.getOrDefault(token, 0);
            boolean hasEnoughBonusForToken = hasRequiredBonusForToken(token, playerBonusAmount);

            if (!hasEnoughBonusForToken) {
                return false;
            }
        }
        return true;
    }

    private boolean hasRequiredBonusForToken(Token token, int playerBonusAmount) {
        int requiredAmount = requiredBonuses.get(token);
        return playerBonusAmount >= requiredAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Noble noble = (Noble) o;
        return prestigePoints == noble.prestigePoints &&
                Objects.equals(name, noble.name) &&
                Objects.equals(requiredBonuses, noble.requiredBonuses);
    }

    @Override public int hashCode() {return Objects.hash(name, prestigePoints, requiredBonuses);}
    public String getName() {return name;}
    public int getPrestigePoints() {return prestigePoints;}
    public Map<Token, Integer> getRequiredBonuses() {return requiredBonuses;}
    @Override public String toString() {return name;}
}
