package be.howest.ti.game.logic.gameTools;

import java.util.Objects;

public  class TokenBundle {
    private final Token tokenName;
    private int amount;

    public TokenBundle(Token token, int amount) {
        this.tokenName = token;
        this.amount = amount;
    }

    public Token getToken() {
        return tokenName;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TokenBundle that = (TokenBundle) o;
        return amount == that.amount && tokenName == that.tokenName;
    }

    @Override
    public int hashCode() {
        return Objects.hash(tokenName, amount);
    }

    @Override
    public String toString() {
        return
                tokenName +" " + amount ;
    }

}
