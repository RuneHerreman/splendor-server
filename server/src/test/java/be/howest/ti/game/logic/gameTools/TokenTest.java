package be.howest.ti.game.logic.gameTools;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TokenTest {

    @Test
    void testToString() {
        assertEquals("Gold", Token.GOLD.toString());
        assertEquals("Ruby", Token.RUBY.toString());
        assertEquals("Onyx", Token.ONYX.toString());
        assertEquals("Diamond", Token.DIAMOND.toString());
        assertEquals("Sapphire", Token.SAPPHIRE.toString());
        assertEquals("Emerald", Token.EMERALD.toString());
    }

}