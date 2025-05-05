package be.howest.ti.game.web.views.response;

import java.util.Map;

public class UpdateTokensResponse extends AbstractResponseWithHiddenStatus {
    private Map<String, Integer> tokens;

    public UpdateTokensResponse() {
        super(200);
    }

    public Map<String, Integer> getTokens() {
        return tokens;
    }

    public void setTokens(Map<String, Integer> tokens) {
        this.tokens = tokens;
    }
}

