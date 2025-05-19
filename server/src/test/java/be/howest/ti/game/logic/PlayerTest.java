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

    @Test
    public void testReserveAndBuyReserved() {
        Development dev = new Development("Dev2", 0, new HashMap<>(), Token.EMERALD, 1);
        player.reserveCard(dev);
        assertTrue(player.getReserved().contains(dev));

        player.buyReserved(dev);
        assertTrue(player.getPurchasedDevelopments().contains(dev));
        assertFalse(player.getReserved().contains(dev));
    }
    @Test
    public void testAddNoble() {
        Noble noble = new Noble("Noble1", 3, new HashMap<>());
        player.addNoble(noble);
        assertTrue(player.getNobles().contains(noble));
    }
    @Test
    public void testUpdatePrestigePoints() {
        player.updatePrestigePoints(5);
        assertEquals(5, player.getPrestigePoints());
    }

    @Test
    public void testGenerateTokensAndBonuses() {
        player.addToken(Token.RUBY, 2);
        player.addBonus(Token.RUBY, 3);
        Map<Token, Integer> combined = player.generateTokensAndBonuses();
        assertEquals(5, combined.get(Token.RUBY));
    }
    @Test
    public void testRemoveTokens_withBonus() {
        player.addToken(Token.EMERALD, 5);
        player.addBonus(Token.EMERALD, 2);
        Map<Token, Integer> toRemove = new HashMap<>();
        toRemove.put(Token.EMERALD, 3);
        player.removeTokens(toRemove , true);
        assertEquals(4, player.getTokens().get(Token.EMERALD)); // 3 - 2 (bonus) = 1 verwijderd
    }
    @Test
    public void testRemoveTokens_fullyCoveredByBonus() {
        player.addToken(Token.RUBY, 5);
        player.addBonus(Token.RUBY, 3);
        Map<Token, Integer> toRemove = new HashMap<>();
        toRemove.put(Token.RUBY, 2);
        player.removeTokens(toRemove , true);
        assertEquals(6, player.getTokens().get(Token.RUBY)); // volledig door bonus gedekt
    }


}
