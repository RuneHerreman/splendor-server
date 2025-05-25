package be.howest.ti.game.web.views.response;
import be.howest.ti.game.logic.gameTools.Token;

public class GetGemsResponse extends AbstractResponseWithHiddenStatus{

    public GetGemsResponse() {
        super(200);
    }

    public Token[] getGems() {
        return Token.values();
    }

}
