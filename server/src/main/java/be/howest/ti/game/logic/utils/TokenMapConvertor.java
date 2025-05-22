package be.howest.ti.game.logic.utils;

import be.howest.ti.game.logic.gameTools.Token;

import java.util.HashMap;
import java.util.Map;

public class TokenMapConvertor {
    public static Map<String, Integer> convertToStringMap(Map<Token, Integer> tokenMap) {
        Map<String, Integer> stringMap = new HashMap<>();
        for (Map.Entry<Token, Integer> entry : tokenMap.entrySet()) {
            stringMap.put(entry.getKey().toString(), entry.getValue());
        }
        return stringMap;

    }

    public static Map<Token, Integer> convertToTokenMap(Map<String, Integer> stringMap) {
        Map<Token, Integer> tokenMap = new HashMap<>();
        for (Map.Entry<String, Integer> entry : stringMap.entrySet()) {
            tokenMap.put(Token.valueOf(entry.getKey()), entry.getValue());
        }
        return tokenMap;

    }
}
