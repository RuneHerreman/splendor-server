package be.howest.ti.game.logic.utils;

import be.howest.ti.game.logic.gameTools.Token;
import org.junit.jupiter.api.Test;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TokenMapConvertorTest {

    @Test
    void testConvertToStringMap() {
        Map<Token, Integer> tokenMap = new EnumMap<>(Token.class);
        tokenMap.put(Token.GOLD, 3);
        tokenMap.put(Token.DIAMOND, 5);

        Map<String, Integer> stringMap = TokenMapConvertor.convertToStringMap(tokenMap);

        assertEquals(2, stringMap.size());
        assertEquals(3, stringMap.get("Gold"));
        assertEquals(5, stringMap.get("Diamond"));
    }

    @Test
    void testConvertToTokenMap() {
        Map<String, Integer> stringMap = new HashMap<>();
        stringMap.put("DIAMOND", 2);
        stringMap.put("RUBY", 3);

        Map<Token, Integer> result = TokenMapConvertor.convertToTokenMap(stringMap);

        assertEquals(2, result.size());
        assertEquals(2, result.get(Token.DIAMOND));
        assertEquals(3, result.get(Token.RUBY));
        assertFalse(result.containsKey(Token.EMERALD));
    }
}