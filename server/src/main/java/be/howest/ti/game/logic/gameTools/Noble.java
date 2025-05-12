package be.howest.ti.game.logic.gameTools;

import java.util.Objects;
import java.util.Set;

public class Noble {

    private final String name;
    private int prestigePoints;
    private final Set<TokenBundle> neededBonuses;

    public Noble(String name, int points, Set<TokenBundle> bonus) {
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

    public Set<TokenBundle> getNeededBonuses() {
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
