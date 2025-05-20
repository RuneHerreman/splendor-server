package be.howest.ti.game.logic;

import be.howest.ti.game.logic.gameTools.Noble;
import be.howest.ti.game.logic.gameTools.Development;
import be.howest.ti.game.logic.gameTools.Token;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.EnumMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

 class PlayerTest {

    private Player player;

    @BeforeEach
     void setUp() {
        player = new Player("Alice");
    }
    @Test
     void testAddTokenAndAddTokens() {
        Map<Token, Integer> batch = new EnumMap<>(Token.class);

        player.addToken(Token.Ruby, 2);
        assertEquals(2, player.getTokens().get(Token.Ruby));
        batch.put(Token.Ruby, 1);
        batch.put(Token.Emerald, 3);
        player.addTokens(batch);


        assertEquals(3, player.getTokens().get(Token.Ruby));
        assertEquals(3, player.getTokens().get(Token.Emerald));
    }

    @Test
     void testAddBonus() {
        player.addBonus(Token.Diamond, 2);
        assertEquals(2, player.getBonuses().get(Token.Diamond));
    }

    @Test
     void testAddCard() {
        Map<Token , Integer>  batch = new EnumMap<>(Token.class);
        Development dev = new Development("Dev1", 0, batch, Token.Ruby, 1);
        player.addCard(dev);
        assertTrue(player.getPurchasedDevelopments().contains(dev));
    }

    @Test
     void testReserveAndBuyReserved() {
        Development dev = new Development("Dev2", 0, new EnumMap<>(Token.class), Token.Emerald, 1);
        player.reserveCard(dev);
        assertTrue(player.getReserved().contains(dev));

        player.buyReserved(dev);
        assertTrue(player.getPurchasedDevelopments().contains(dev));
        assertFalse(player.getReserved().contains(dev));
    }
    @Test
     void testAddNoble() {
        Noble noble = new Noble("Noble1", 3, new EnumMap<>(Token.class));
        player.addNoble(noble);
        assertTrue(player.getNobles().contains(noble));
    }
    @Test
     void testUpdatePrestigePoints() {
        player.updatePrestigePoints(5);
        assertEquals(5, player.getPrestigePoints());
    }

    @Test
     void testGenerateTokensAndBonuses() {
        player.addToken(Token.Ruby, 2);
        player.addBonus(Token.Ruby, 3);
        Map<Token, Integer> combined = player.generateTokensAndBonuses();
        assertEquals(5, combined.get(Token.Ruby));
    }
    @Test
     void testRemoveTokens_withBonus() {
        player.addToken(Token.Emerald, 5);
        player.addBonus(Token.Emerald, 2);
        Map<Token, Integer> toRemove = new EnumMap<>(Token.class);
        toRemove.put(Token.Emerald, 3);
        player.removeTokens(toRemove , true);
        assertEquals(4, player.getTokens().get(Token.Emerald));
    }
    @Test
     void testRemoveTokens_fullyCoveredByBonus() {
        player.addToken(Token.Ruby, 5);
        player.addBonus(Token.Ruby, 3);
        Map<Token, Integer> toRemove = new EnumMap<>(Token.class);
        toRemove.put(Token.Ruby, 2);
        player.removeTokens(toRemove , true);
        assertEquals(6, player.getTokens().get(Token.Ruby));
    }


}
