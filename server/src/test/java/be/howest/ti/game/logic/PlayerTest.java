package be.howest.ti.game.logic;

import be.howest.ti.game.logic.gameTools.Noble;
import be.howest.ti.game.logic.gameTools.Development;
import be.howest.ti.game.logic.gameTools.Token;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    private Player player; // <-- veld, zichtbaar in alle methodes

    @BeforeEach
    public void setUp() {
        player = new Player("Alice"); // <-- vul het veld in
    }

    @Test
    public void testAddTokenAndAddTokens() {
        player.addToken(Token.RUBY, 2);
        assertEquals(2, player.getTokens().get(Token.RUBY));

        Map<Token, Integer> batch = new HashMap<>();
        batch.put(Token.RUBY, 1);
        batch.put(Token.EMERALD, 3);
        player.addTokens(batch);

        assertEquals(3, player.getTokens().get(Token.RUBY));
        assertEquals(3, player.getTokens().get(Token.EMERALD));
    }

    @Test
    public void testAddBonus() {
        player.addBonus(Token.DIAMOND, 2);
        assertEquals(2, player.getBonuses().get(Token.DIAMOND));
    }

    @Test
    public void testAddCard() {
        Development dev = new Development("Dev1", 0, new HashMap<>(), Token.RUBY, 1);
        player.addCard(dev);
        assertTrue(player.getPurchasedDevelopments().contains(dev));
    }

}
