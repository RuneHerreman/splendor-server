package be.howest.ti.game.logic.gameTools;

public enum Token {
    GOLD("Gold"),
    RUBY("Ruby"),
    ONYX("Onyx"),
    DIAMOND("Diamond"),
    SAPPHIRE("Sapphire"),
    EMERALD("Emerald");

    /**
     * SOURCE
     * https://www.baeldung.com/java-enum-values
     * */
    private final String label;

    Token (String label) {
        this.label = label;
    }

    public String toString() {
        return label;
    }
}
