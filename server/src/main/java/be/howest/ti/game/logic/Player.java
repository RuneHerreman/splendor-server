package be.howest.ti.game.logic;
import be.howest.ti.game.logic.utils.Development;
import be.howest.ti.game.logic.utils.Noble;
import be.howest.ti.game.logic.utils.Token;
import be.howest.ti.game.logic.utils.TokenBundle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Player {

    private final String name;
    private final int gameId;
    private int prestigePoints;
    private final List<Development> purchasedDevelopments;
    private final List<Development> reserved;
    private final List<Noble> nobles;
    private final List<TokenBundle>tokens;
    private final List<TokenBundle> bonuses;

    public Player(String username , int gameId){
        this.name = username;
        this.gameId = gameId;
        this.prestigePoints = 0;
        this.purchasedDevelopments = new ArrayList<>();
        this.reserved = new ArrayList<>();
        this.nobles = new ArrayList<>();
        this.tokens = new ArrayList<>();
        this.bonuses = new ArrayList<>();
    }

    public void addToken(TokenBundle token){
        for(TokenBundle tokensInInventory : tokens){
            if(tokensInInventory.getToken().equals(token.getToken())){
                int newTokenAmount = tokensInInventory.getAmount() + token.getAmount();
                tokensInInventory.setAmount(newTokenAmount);
                return;
            }
        }

        tokens.add(token);
    }

    public void addBonus(TokenBundle bonus){
        for(TokenBundle bonusesInInventory : bonuses){
            if(bonusesInInventory.getToken().equals(bonus.getToken())){
                int newBonusAmount = bonusesInInventory.getAmount() + bonus.getAmount();
                bonusesInInventory.setAmount(newBonusAmount);
                return;
            }
        }

        tokens.add(bonus);
    }

    public void buyCard(Development development){
        purchasedDevelopments.add(development);
    }

    public void reserveCard(Development development){
        reserved.add(development);
    }

    public void buyReserved(Development development){
        purchasedDevelopments.add(development);
        reserved.remove(development);
    }

    public void getNoble(Noble noble){
        nobles.add(noble);
    }

    public void updatePrestigePoints(int toBeAdded){
        this.prestigePoints += toBeAdded;
    }

    public int getPrestigePoints(){
        return prestigePoints;
    }

}
