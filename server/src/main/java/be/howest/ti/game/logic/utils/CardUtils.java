package be.howest.ti.game.logic.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CardUtils {

    private static final Map<Character, Token> costMap = costLetterToTokenMap();

    public static Set<TokenBundle> getCostTokenSetFromLetters(String costString) {

        Map<Token, Integer> tokenCounts = new HashMap<>();

        for (char c : costString.toCharArray()) {
            Token token = costMap.get(c);

            if(tokenCounts.containsKey(token)) {
                tokenCounts.put(token, tokenCounts.get(token) + 1);
            }else {
                tokenCounts.put(token, 1);
            }
        }

        Set<TokenBundle> bundles = new HashSet<>();
        for (Map.Entry<Token, Integer> entry : tokenCounts.entrySet()) {
            bundles.add(new TokenBundle(entry.getKey(), entry.getValue()));
        }

        return bundles;
    }

    private static Map<Character, Token> costLetterToTokenMap() {
        Map<Character, Token> costMap = new HashMap<>();
        costMap.put('C', Token.DIAMOND);
        costMap.put('S', Token.SAPPHIRE);
        costMap.put('O', Token.ONYX);
        costMap.put('R', Token.RUBY);
        costMap.put('E', Token.EMERALD);
        return costMap;
    }
}
