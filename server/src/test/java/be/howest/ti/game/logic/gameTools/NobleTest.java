package be.howest.ti.game.logic.gameTools;
import be.howest.ti.game.logic.Player;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class NobleTest {

        // DummyPlayer die alleen getBonuses() overschrijft
        static class DummyPlayer extends Player {
            private final Map<Token, Integer> bonuses;
            public DummyPlayer(Map<Token, Integer> bonuses) {
                super("dummy");
                this.bonuses = bonuses;
            }
            @Override
            public Map<Token, Integer> getBonuses() {
                return bonuses;
            }
        }


}


