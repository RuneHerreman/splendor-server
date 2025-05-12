package be.howest.ti.game.logic.gameTools;

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


}
