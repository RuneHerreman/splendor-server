package be.howest.ti.game.logic.gameTools;

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
}
