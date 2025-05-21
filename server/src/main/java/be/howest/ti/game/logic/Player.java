package be.howest.ti.game.logic;
import be.howest.ti.game.logic.gameTools.Development;
import be.howest.ti.game.logic.gameTools.Noble;
import be.howest.ti.game.logic.gameTools.Token;

import java.util.*;

public class Player {

    private final String name;
    private int prestigePoints;
    private final List<Development> purchasedDevelopments;
    private final List<Development> reserved;
    private final List<Noble> nobles;
    private final Map<Token, Integer> tokens;
    private final Map<Token, Integer> bonuses;

    public Player(String username) {
        this.name = username;
        this.prestigePoints = 0;
        this.purchasedDevelopments = new ArrayList<>();
        this.reserved = new ArrayList<>();
        this.nobles = new ArrayList<>();
        this.tokens = new HashMap<>();
        this.bonuses = new HashMap<>();
    }

    public void addToken(Token token, int amount) {
        int tempTokenAmountInInventory = tokens.getOrDefault(token ,0);
        tokens.put(token, tempTokenAmountInInventory+ amount);
    }

    public void addTokens(Map<Token, Integer> tokens) {
        for(Token token : tokens.keySet()){
            int tokenAmount = tokens.get(token);
            addToken(token, tokenAmount);
        }
    }

    public void addBonus(Token token, int amount) {
        int tempBonusAmount = bonuses.getOrDefault(token, 0);
        bonuses.put(token, tempBonusAmount + amount);
    }

    public void addCard(Development development) {
        purchasedDevelopments.add(development);
        bonuses.put(development.getBonus() , bonuses.getOrDefault(development.getBonus() , 0) + 1);
    }

    public void reserveCard(Development development) {
        reserved.add(development);
        int goldTokenAmount = tokens.getOrDefault(Token.GOLD, 0) + 1;
        addToken(Token.GOLD , goldTokenAmount);
    }

    public void buyReserved(Development development) {
        addCard(development);
        reserved.remove(development);
    }

    public void addNoble(Noble noble) {
        nobles.add(noble);
    }

    public void updatePrestigePoints(int toBeAdded) {
        this.prestigePoints += toBeAdded;
    }

    public Map<Token, Integer> generateTokensAndBonuses() {
        Map<Token, Integer> collectionTokensAndBonuses = tokens;

        for (Token token : bonuses.keySet()) {
            int tempTokenCollection = collectionTokensAndBonuses.getOrDefault(token, 0);
            int tokenCollectionWithBonuses = tempTokenCollection + bonuses.get(token);
            collectionTokensAndBonuses.put(token, tokenCollectionWithBonuses);
        }

        return collectionTokensAndBonuses;
    }

    private void removeToken(Token token, int amount, boolean cardPurchase) {
        int amountTokenToRemove = amount;
        if (cardPurchase) {
            int amountBonus = bonuses.getOrDefault(token, 0);
            amountTokenToRemove = amount - amountBonus;
        }

        tokens.put(token, tokens.getOrDefault(token, 0) - amountTokenToRemove);
    }

    public void removeTokens(Map<Token, Integer> toRemove, boolean cardPurchase) {
        for(Token token : toRemove.keySet()) {
            int amountTokenToRemove = toRemove.getOrDefault(token, 0);
            removeToken(token ,amountTokenToRemove,cardPurchase );
        }
    }

    public boolean hasEnoughBonusesForNoble(Map<Token, Integer> nobleNeededBonuses) {
        for (Token token : nobleNeededBonuses.keySet()) {
            int amount = nobleNeededBonuses.get(token);
            if (bonuses.getOrDefault(token, 0) < amount) {
                return false;
            }
        }
        return true;
    }

    public boolean checkValidTokensToReturn(Map<Token, Integer> toRemove) {
        for (Token toRemoveToken : toRemove.keySet()) {
            int toRemoveAmount = toRemove.get(toRemoveToken);
            int inventoryAmount = tokens.getOrDefault(toRemoveToken, 0);
            if (inventoryAmount < toRemoveAmount) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(name, player.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
    public String getName() {return name;}
    public int getPrestigePoints() {return prestigePoints;}
    public List<Development> getPurchasedDevelopments() {return purchasedDevelopments;}
    public List<Development> getReserved() {return reserved;}
    public List<Noble> getNobles() {return nobles;}
    public Map<Token, Integer> getTokens() {return tokens;}
    public Map<Token, Integer> getBonuses() {return bonuses;}
    @Override public String toString() {return name;}
}
