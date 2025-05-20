package be.howest.ti.game.logic.gameTools;

import be.howest.ti.game.logic.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.EnumMap;
import java.util.Map;


import static org.junit.jupiter.api.Assertions.*;


class NobleTest {
    private Noble noble;
    @BeforeEach
    void setUp() {
        Map<Token,Integer> nobleBonusses = new EnumMap<>(Token.class);
        nobleBonusses.put(Token.Ruby, 3);
        nobleBonusses.put(Token.Diamond, 3);
        nobleBonusses.put(Token.Onyx, 3);
        noble = new Noble("Bob",3,nobleBonusses);

    }

    @Test
    void nobleTest() {
        assertEquals("Bob",noble.getName());
        assertEquals(3,noble.getPrestigePoints());
        assertEquals(3,noble.getNeededBonuses().get(Token.Ruby));
    }

    @Test
    void nobleTestIsClaimableByPlayer_succes() {
        Player player = new Player("Test");
        player.addBonus(Token.Ruby, 3);
        player.addBonus(Token.Diamond, 3);
        player.addBonus(Token.Onyx, 3);
        assertTrue(noble.isNobleClaimableByPlayer(player));

    }

    @Test
    void nobleTestIsClaimableByPlayer_failure() {
        Player player = new Player("Test");
        player.addBonus(Token.Ruby, 3);
        player.addBonus(Token.Diamond, 3);
        player.addBonus(Token.Onyx, 2);
        assertFalse(noble.isNobleClaimableByPlayer(player));

    }
}


