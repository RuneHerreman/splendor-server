package be.howest.ti.game.logic;
import be.howest.ti.game.logic.gameTools.Development;
import be.howest.ti.game.logic.gameTools.Noble;
import be.howest.ti.game.logic.gameTools.Token;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Player {

    private final String name;
    private int prestigePoints;
    private final List<Development> purchasedDevelopments;
    private final List<Development> reserved;
    private final List<Noble> nobles;
    private final Map<Token, Integer> tokens;
    private final Map<Token, Integer> bonuses;

    public Player(String username){
        this.name = username;
        this.prestigePoints = 0;
        this.purchasedDevelopments = new ArrayList<>();
        this.reserved = new ArrayList<>();
        this.nobles = new ArrayList<>();
        this.tokens = new HashMap<>();
        this.bonuses = new HashMap<>();
    }

    public void addToken(Token token, int amount) {
        tokens.put(token, tokens.getOrDefault(token, 0) + amount);
    }

    public void addTokens(Map<Token, Integer> tokens) {
        for (Map.Entry<Token, Integer> entry : tokens.entrySet()) {
            addToken(entry.getKey(), entry.getValue());
        }
    }

    public void addBonus(Token token, int amount) {
        bonuses.put(token, bonuses.getOrDefault(token, 0) + amount);
    }

    public void addCard(Development development) {
        purchasedDevelopments.add(development);
    }

    public void reserveCard(Development development) {
        reserved.add(development);
    }

    public void buyReserved(Development development) {
        purchasedDevelopments.add(development);
        reserved.remove(development);
    }

    public void addNoble(Noble noble) {
        nobles.add(noble);
    }

    public void updatePrestigePoints(int toBeAdded) {
        this.prestigePoints += toBeAdded;
    }
    public Map<Token, Integer> generateTokensAndBonuses() {
        Map<Token, Integer> result = new HashMap<>(tokens);

        for (Map.Entry<Token, Integer> bonus : bonuses.entrySet()) {
            result.put(bonus.getKey(), result.getOrDefault(bonus.getKey(), 0) + bonus.getValue());
        }

        return result;
    }

    public String getName() {
        return name;
    }

    public int getPrestigePoints() {
        return prestigePoints;
    }

    public List<Development> getPurchasedDevelopments() {
        return purchasedDevelopments;
    }

    public List<Development> getReserved() {
        return reserved;
    }

    public List<Noble> getNobles() {
        return nobles;
    }

    public Map<Token , Integer> getTokens() {
        return tokens;
    }

    public Map<Token , Integer>  getBonuses() {
        return bonuses;
    }



    @Override
    public String toString() {
        return name;
    }

    private void removeToken(Token token, int amount, boolean cardPurchase) {
        int amountToRemove = amount;
        if (cardPurchase) {
            int amountBonus = bonuses.getOrDefault(token, 0);
            amountToRemove = amount - amountBonus;
        }
        tokens.put(token, tokens.getOrDefault(token, 0) - amountToRemove);
    }




    public void removeTokens(Map<Token, Integer> toRemove , boolean cardPurchase) {
        for (Map.Entry<Token, Integer> entry : toRemove.entrySet()) {
            removeToken(entry.getKey(), entry.getValue() , cardPurchase);
        }
    }

    public boolean hasEnoughTokensToRemove(Map<Token, Integer> toRemove) {
        for (Map.Entry<Token, Integer> token : toRemove.entrySet()) {
            Token toRemoveToken = token.getKey();
            int toRemoveAmount = token.getValue();

            int inventoryAmount = tokens.getOrDefault(toRemoveToken, 0);

            if(inventoryAmount < toRemoveAmount  ) {
                return false;
            }

        }
        return true;
    }

}
