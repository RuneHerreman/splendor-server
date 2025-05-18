package be.howest.ti.game.web.views.response;
import java.util.List;
public class GetGemsResponse extends AbstractResponseWithHiddenStatus{

    public GetGemsResponse() {
        super(200);
    }

    public List<String> getGems() {
        return List.of(
                "Diamond",
                "Sapphire",
                "Emerald",
                "Ruby",
                "Onyx",
                "Gold"
        );
    }

}
