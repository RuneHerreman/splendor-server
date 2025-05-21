package be.howest.ti.game.logic.utils;

import be.howest.ti.game.logic.gameTools.Noble;
import be.howest.ti.game.logic.gameTools.Token;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import be.howest.ti.game.logic.gameTools.Development;


public class CardUtils {

    private static final Map<Character, Token> costMap = costLetterToTokenMap();

    private static Map<Character, Token> costLetterToTokenMap() {
        Map<Character, Token> costMap = new HashMap<>();
        costMap.put('C', Token.DIAMOND);
        costMap.put('S', Token.SAPPHIRE);
        costMap.put('O', Token.ONYX);
        costMap.put('R', Token.RUBY);
        costMap.put('E', Token.EMERALD);
        return costMap;
    }

    public static Map<Token, Integer> getCostTokenSetFromLetters(String tokensString) {
        Map<Token, Integer> tokenCounts = new HashMap<>();
        char[] tokens = tokensString.toCharArray();

        for (char c : tokens) {
            Token token = costMap.get(c);
            tokenCounts.put(token, tokenCounts.getOrDefault(token, 0) + 1);
        }
        return tokenCounts;
    }

    public static Token getTokenFromLetters(char letter) {
        return costMap.get(letter);
    }

    public static Development getDevelopmentCardByName(String name , List<List<Development>> cardsInMarket) {
        for(List<Development> card : cardsInMarket) {
            for(Development development : card) {
                if(development.getName().equals(name)) {
                    return development;
                }
            }
        }
        return null;
    }
    public static Noble getNobleCardByName(String name , List<Noble> noblesInMarket) {
        for(Noble noble : noblesInMarket) {
            if(noble.getName().equals(name)) {
                return noble;
            }
        }
        return null;
    }
}
