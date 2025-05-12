package be.howest.ti.game.logic.utils;

import java.util.Set;

public class Development {
    private final String name;
    private int level;
    private int prestigePoints;
    private Set<TokenBundle> cost;
    private final Token bonus;

    public Development(String name, int prestigePoints, Set<TokenBundle> cost , Token bonus , int level) {
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
    public Token getBonus() {
        return bonus;
    }

}
