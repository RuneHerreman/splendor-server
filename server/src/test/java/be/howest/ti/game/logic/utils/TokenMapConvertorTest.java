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
        tokenMap.put(Token.DIAMOND, 2);
        tokenMap.put(Token.EMERALD, 3);

        Map<String, Integer> result = TokenMapConvertor.convertToStringMap(tokenMap);

        assertNotNull(result);
        System.out.println(result);
        assertEquals(2, result.size());
        assertEquals(2, result.get("DIAMOND"));
        assertEquals(3, result.get("EMERALD"));
        assertFalse(result.containsKey("GOLD"));
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