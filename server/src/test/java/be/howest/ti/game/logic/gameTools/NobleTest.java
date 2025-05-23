package be.howest.ti.game.logic.gameTools;

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
        nobleBonusses.put(Token.RUBY, 3);
        nobleBonusses.put(Token.DIAMOND, 3);
        nobleBonusses.put(Token.ONYX, 3);
        noble = new Noble("Bob",3,nobleBonusses);

    }

    @Test
    void nobleTest() {
        assertEquals("Bob",noble.getName());
        assertEquals(3,noble.getPrestigePoints());
        assertEquals(3,noble.getRequiredBonuses().get(Token.RUBY));
    }

    @Test
    void nobleTestIsClaimableByPlayer_succes() {
        Player player = new Player("Test", ".");
        player.bonusIncrementByType(Token.RUBY, 3);
        player.bonusIncrementByType(Token.DIAMOND, 3);
        player.bonusIncrementByType(Token.ONYX, 3);
        assertTrue(noble.isNobleClaimableByPlayer(player));
    }

    @Test
    void nobleTestIsClaimableByPlayer_failure() {
        Player player = new Player("Test", ".");
        player.bonusIncrementByType(Token.RUBY, 3);
        player.bonusIncrementByType(Token.DIAMOND, 3);
        player.bonusIncrementByType(Token.ONYX, 2);
        assertFalse(noble.isNobleClaimableByPlayer(player));
    }

}


