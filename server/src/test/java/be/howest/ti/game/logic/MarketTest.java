package be.howest.ti.game.logic;

import be.howest.ti.game.logic.gameTools.Development;
import be.howest.ti.game.logic.gameTools.Noble;
import be.howest.ti.game.logic.gameTools.Token;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MarketTest {

    private Market market;

    @BeforeEach
    void setUp() {
        market = new Market(3);
    }
    @Test
    void testInitialMarketSetup_checkSize() {
        List<List<Development>> allCards = market.getAllCards();
        assertEquals(3, allCards.size());
        assertEquals( 40,market.getAllCards().getFirst().size());
        assertEquals(30 ,market.getAllCards().get(1).size());
        assertEquals( 20,market.getAllCards().get(2).size());
        assertEquals(10, market.getAllNobles().size());
        assertEquals(4 , market.getCardsAvailableInMarket().getFirst().size());
        assertEquals(4 , market.getCardsAvailableInMarket().get(1).size());
        assertEquals(4 , market.getCardsAvailableInMarket().get(2).size());
        assertEquals( 4, market.getNoblesAvailableInMarket().size());
    }

    @Test
    void testUnclaimedTokenCountFor3Players() {
        Map<Token, Integer> tokens = market.getUnclaimedTokens();

        for (Map.Entry<Token, Integer> entry : tokens.entrySet()) {
            if (entry.getKey() == Token.Gold) {
                assertEquals(5, entry.getValue());
            } else {
                assertEquals(5, entry.getValue());
            }
        }
    }

    @Test
    void testSetCardToMarket() {
        List<Development> level1Cards = market.getAllCards().getFirst();
        if (!level1Cards.isEmpty()) {
            Development dev = level1Cards.getFirst();
            market.setCardToMarket(dev);

            assertTrue(market.getCardsAvailableInMarket().getFirst().contains(dev));
            assertFalse(market.getAllCards().getFirst().contains(dev));
        }
    }

    @Test
    void testSetNobleToMarket() {
        List<Noble> nobles = market.getAllNobles();
        if (!nobles.isEmpty()) {
            Noble noble = nobles.getFirst();
            market.setNobleToMarket(noble);

            assertTrue(market.getNoblesAvailableInMarket().contains(noble));
            assertFalse(market.getAllNobles().contains(noble));
        }
    }

    @Test
    void testRemoveCardFromMarket() {
        List<Development> marketCards = market.getCardsAvailableInMarket().getFirst();
        if (!marketCards.isEmpty()) {
            Development dev = marketCards.getFirst();
            market.removeCardFromMarket(dev);
            assertFalse(market.getCardsAvailableInMarket().getFirst().contains(dev));
        }
    }

    @Test
    void testRemoveNobleFromMarket() {
        List<Noble> nobles = market.getNoblesAvailableInMarket();
        if (!nobles.isEmpty()) {
            Noble noble = nobles.getFirst();
            market.removeNobleFromMarket(noble);
            assertFalse(market.getNoblesAvailableInMarket().contains(noble));
        }
    }


}
