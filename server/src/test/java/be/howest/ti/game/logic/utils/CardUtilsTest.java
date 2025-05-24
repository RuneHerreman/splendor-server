package be.howest.ti.game.logic.utils;
import be.howest.ti.game.logic.gameTools.Noble;
import be.howest.ti.game.logic.gameTools.Development;
import be.howest.ti.game.logic.gameTools.Token;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class CardUtilsTest {

    @Test
    void testGetCostTokenSetFromLetters() {
        Map<Token, Integer> result = CardUtils.getCostTokenSetFromLetters("CCSSR");

        assertEquals(2, result.get(Token.DIAMOND));
        assertEquals(2, result.get(Token.SAPPHIRE));
        assertEquals(1, result.get(Token.RUBY));
        assertFalse(result.containsKey(Token.ONYX)); // Not in string
    }

    @Test
    void testGetTokenFromLetters() {
        assertEquals(Token.DIAMOND, CardUtils.getTokenFromLetters('C'));
        assertEquals(Token.SAPPHIRE, CardUtils.getTokenFromLetters('S'));
        assertEquals(Token.ONYX, CardUtils.getTokenFromLetters('O'));
        assertEquals(Token.RUBY, CardUtils.getTokenFromLetters('R'));
        assertEquals(Token.EMERALD, CardUtils.getTokenFromLetters('E'));
        assertNull(CardUtils.getTokenFromLetters('Z')); // Invalid letter
    }

    @Test
    void testGetDevelopmentCardByNameReturnsCorrectCard() {
        Map<Token, Integer> cost = new EnumMap<>(Token.class);
        cost.put(Token.DIAMOND, 2);

        Development dev1 = new Development("CardA", 1, cost, Token.DIAMOND, 1);
        Development dev2 = new Development("CardB", 2, cost, Token.SAPPHIRE, 2);

        List<Development> level1 = new ArrayList<>();
        level1.add(dev1);
        List<Development> level2 = new ArrayList<>();
        level2.add(dev2);

        List<List<Development>> market = new ArrayList<>();
        market.add(level1);
        market.add(level2);

        Development result = CardUtils.getDevelopmentCardByName("CardB", market);

        assertNotNull(result);
        assertEquals("CardB", result.getName());
        assertEquals(2, result.getPrestigePoints());
    }

    @Test
    void testGetDevelopmentCardByNameReturnsNullIfNotFound() {
        Map<Token, Integer> cost = new EnumMap<>(Token.class);
        cost.put(Token.DIAMOND, 1);

        Development dev1 = new Development("CardA", 1, cost, Token.DIAMOND, 1);
        List<Development> level1 = new ArrayList<>();
        level1.add(dev1);
        List<List<Development>> market = new ArrayList<>();
        market.add(level1);

        Development result = CardUtils.getDevelopmentCardByName("NonExistent", market);

        assertNull(result);
    }

    @Test
    void testGetNobleCardByName() {
        Noble noble1 = new Noble("N1", 3, Map.of(Token.DIAMOND, 3));
        Noble noble2 = new Noble("N2", 2, Map.of(Token.ONYX, 2));
        List<Noble> nobles = List.of(noble1, noble2);

        assertEquals(noble1, CardUtils.getNobleCardByName("N1", nobles));
        assertEquals(noble2, CardUtils.getNobleCardByName("N2", nobles));
        assertNull(CardUtils.getNobleCardByName("N3", nobles));
    }

}
