package be.howest.ti.game.logic.gameTools;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
        cost.put(Token.EMERALD, 0);

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
    @DisplayName("Player has exactly enough tokens with no bonuses - Return exact cost")
    void testValidatePayment_EnoughTokens() {
        Player player = new Player("bob", "path/to/icon");
        player.addToken(Token.DIAMOND, 2);
        player.addToken(Token.RUBY, 1);
        player.addToken(Token.EMERALD, 5);

        Map<Token, Integer> payment = new EnumMap<>(Token.class);
        payment.put(Token.DIAMOND, 2);
        payment.put(Token.RUBY, 1);

        Map<Token, Integer> expectedTake = new EnumMap<>(Token.class);
        expectedTake.put(Token.DIAMOND, 2);
        expectedTake.put(Token.RUBY, 1);

        Map<Token, Integer> actualTake = development.validatePayment(player, payment);

        assertEquals(expectedTake, actualTake);
    }

    @Test
    @DisplayName("Player has enough tokens, with bonus cost - Cost should go down")
    void testValidatePayment_WithBonus() {
        Player player = new Player("Alice", "icon/alice.png");
        player.addToken(Token.DIAMOND, 1); // Needs to pay 1 DIAMOND due to bonus
        player.addToken(Token.RUBY, 1);
        player.bonusIncrementByType(Token.DIAMOND);

        Map<Token, Integer> payment = new EnumMap<>(Token.class);
        payment.put(Token.DIAMOND, 1);
        payment.put(Token.RUBY, 1);

        Map<Token, Integer> expectedTake = new EnumMap<>(Token.class);
        expectedTake.put(Token.DIAMOND, 1);
        expectedTake.put(Token.RUBY, 1);

        Map<Token, Integer> actualTake = development.validatePayment(player, payment);
        assertEquals(expectedTake, actualTake);
    }

    @Test
    @DisplayName("Player overpays with specific tokens - Only use needed tokens")
    void testValidatePayment_Overpay() {
        Player player = new Player("Charlie", "icon/charlie.png");
        player.addToken(Token.DIAMOND, 5); // Has more than needed
        player.addToken(Token.RUBY, 5);

        Map<Token, Integer> payment = new EnumMap<>(Token.class);
        payment.put(Token.DIAMOND, 5);
        payment.put(Token.RUBY, 5);

        Map<Token, Integer> expectedTake = new EnumMap<>(Token.class);
        expectedTake.put(Token.DIAMOND, 2);
        expectedTake.put(Token.RUBY, 1);

        Map<Token, Integer> actualTake = development.validatePayment(player, payment);

        assertEquals(expectedTake, actualTake);
    }

    @Test
    @DisplayName("Player has enough tokens, but wants to use gold - Should use gold for missing tokens")
    void testValidatePayment_WithGold() {
        // Card cost (from setUp): 2 DIAMOND, 1 RUBY
        Player player = new Player("David", "icon/david.png");
        player.addToken(Token.DIAMOND, 3);
        player.addToken(Token.RUBY, 2);
        player.addToken(Token.GOLD, 1);

        Map<Token, Integer> payment = new EnumMap<>(Token.class);
        payment.put(Token.DIAMOND, 1);
        payment.put(Token.RUBY, 1);
        payment.put(Token.GOLD, 1);


        Map<Token, Integer> expectedTake = new EnumMap<>(Token.class);
        expectedTake.put(Token.DIAMOND, 1);
        expectedTake.put(Token.RUBY, 1);
        expectedTake.put(Token.GOLD, 1);

        Map<Token, Integer> actualTake = development.validatePayment(player, payment);

        assertEquals(expectedTake, actualTake);
    }
}
