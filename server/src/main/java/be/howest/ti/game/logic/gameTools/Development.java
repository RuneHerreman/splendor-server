package be.howest.ti.game.logic.gameTools;

import java.util.Set;

public class Development {
    private final String name;
    private int level;
    private int prestigePoints;
    private Set<TokenBundle> cost;
    private final TokenBundle bonus;

    public Development(String name, int prestigePoints, Set<TokenBundle> cost , TokenBundle bonus , int level) {
        this.name = name;
        this.prestigePoints = prestigePoints;
        this.cost = cost;
        this.bonus = bonus;
        this.level = level;
    }

    public String getName() {
        return name;
    }
    public int getPrestigePoints() {
        return prestigePoints;
    }
    public Set<TokenBundle> getCost() {
        return cost;
    }
    public int getLevel() {
        return level;
    }
    public TokenBundle getBonus() {
        return bonus;
    }

}
