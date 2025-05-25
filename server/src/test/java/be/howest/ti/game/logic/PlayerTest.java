package be.howest.ti.game.logic;

import be.howest.ti.game.logic.gameTools.Noble;
import be.howest.ti.game.logic.gameTools.Development;
import be.howest.ti.game.logic.gameTools.Player;
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
        player = new Player("Alice", ".");
    }
    @Test
     void testAddTokenAndAddTokens() {
        Map<Token, Integer> batch = new EnumMap<>(Token.class);

        player.addToken(Token.RUBY, 2);
        assertEquals(2, player.getTokens().get(Token.RUBY));
        batch.put(Token.RUBY, 1);
        batch.put(Token.EMERALD, 3);
        player.addTokens(batch);


        assertEquals(3, player.getTokens().get(Token.RUBY));
        assertEquals(3, player.getTokens().get(Token.EMERALD));
    }

    @Test
     void testAddBonus() {
        player.bonusIncrementByType(Token.DIAMOND);
       player.bonusIncrementByType(Token.DIAMOND);
        assertEquals(2, player.getBonuses().get(Token.DIAMOND));
    }

    @Test
     void testAddCard() {
        Map<Token , Integer>  batch = new EnumMap<>(Token.class);
        Development dev = new Development("Dev1", 0, batch, Token.RUBY, 1);
        player.addCard(dev);
        assertTrue(player.getPurchasedDevelopments().contains(dev));
    }

    @Test
     void testReserveAndBuyReserved() {
        Development dev = new Development("Dev2", 0, new EnumMap<>(Token.class), Token.EMERALD, 1);
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
        player.addToken(Token.RUBY, 2);
       for(int i = 0 ; i < 3 ; i ++) {
          player.bonusIncrementByType(Token.RUBY);
       }
        Map<Token, Integer> combined = player.generateTokensAndBonuses();
        assertEquals(5, combined.get(Token.RUBY));
    }
    @Test
     void testRemoveTokens_withBonus() {
        player.addToken(Token.EMERALD, 5);
        player.bonusIncrementByType(Token.EMERALD);
        Map<Token, Integer> toRemove = new EnumMap<>(Token.class);
        toRemove.put(Token.EMERALD, 3);
        player.removeTokens(toRemove );
        assertEquals(2, player.getTokens().get(Token.EMERALD));
    }
    @Test
     void testRemoveTokens_fullyCoveredByBonus() {
        player.addToken(Token.RUBY, 5);

        for (int i = 0 ; i < 3 ; i ++) {
           player.bonusIncrementByType(Token.RUBY);
        }

        Map<Token, Integer> toRemove = new EnumMap<>(Token.class);
        toRemove.put(Token.RUBY, 2);
        player.removeTokens(toRemove);

        assertEquals(3, player.getTokens().get(Token.RUBY));
    }



}
