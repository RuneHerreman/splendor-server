package be.howest.ti.game.logic.gameTools;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.EnumMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DevelopmentTest {

    private Development development;
    private Map<Token, Integer> cost;

    @BeforeEach
    void setUp() {
        cost = new EnumMap<>(Token.class);
        cost.put(Token.DIAMOND, 2);
        cost.put(Token.RUBY, 1);

        development = new Development("Card1", 3, cost, Token.SAPPHIRE, 2);
    }

    @Test
    void testGetters() {
        assertEquals("Card1", development.getName());
        assertEquals(3, development.getPrestigePoints());
        assertEquals(2, development.getLevel());
        assertEquals(Token.SAPPHIRE, development.getBonus());
        assertEquals(cost, development.getCost());
    }

    @Test
    void testToString() {
        assertEquals("Card1", development.toString());
    }

    @Test
    void testEqualsAndHashCode() {
        Development same = new Development("Card1", 3, cost, Token.SAPPHIRE, 2);
        Development different = new Development("Card2", 1, cost, Token.GOLD, 1);

        assertEquals(development, same);
        assertEquals(development.hashCode(), same.hashCode());
        assertNotEquals(development, different);
    }

    @Test
    void testIsCardAffordableByPlayer_Affordable() {
        Player player = new Player("TestPlayer", ".");
        player.addToken(Token.DIAMOND, 1);
        player.addToken(Token.RUBY, 1);
        player.bonusIncrementByType(Token.DIAMOND, 1);

        assertTrue(development.isCardAffordableByPlayer(player));
    }

    @Test
    void testIsCardAffordableByPlayer_NotAffordable() {
        Player player = new Player("TestPlayer", ".");
        player.addToken(Token.DIAMOND, 1);
        player.addToken(Token.RUBY, 0);

        assertTrue(!development.isCardAffordableByPlayer(player));
    }
}
