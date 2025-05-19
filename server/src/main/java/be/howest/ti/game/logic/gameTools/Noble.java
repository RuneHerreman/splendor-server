package be.howest.ti.game.logic.gameTools;

import be.howest.ti.game.logic.Player;

import java.util.Map;
import java.util.Objects;

public class Noble {

    private final String name;
    private final int prestigePoints;
    private final Map<Token, Integer> neededBonuses;

    public Noble(String name, int points, Map<Token, Integer> bonus) {
        this.name = name;
        this.prestigePoints = points;
        this.neededBonuses = bonus;
    }

    public String getName() {
        return name;
    }

    public int getPrestigePoints() {
        return prestigePoints;
    }

    public boolean isNobleClaimableByPlayer(Player player) {
        Map<Token, Integer> playerBonuses = player.getBonuses();

        for (Map.Entry<Token, Integer> neededBonus : neededBonuses.entrySet()) {
            Token bonusToken = neededBonus.getKey();
            int bonusTokenAmount = neededBonus.getValue();
            int playerBonusAmount = playerBonuses.getOrDefault(bonusToken, 0);

            if (playerBonusAmount < bonusTokenAmount) {
                return false;
            }
        }
        return true;
    }
    public Map<Token, Integer> getNeededBonuses() {
        return neededBonuses;
    }

    public String toString() {
        return name;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Noble noble = (Noble) o;
        return prestigePoints == noble.prestigePoints && Objects.equals(name, noble.name) && Objects.equals(neededBonuses, noble.neededBonuses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, prestigePoints, neededBonuses);
    }
}
