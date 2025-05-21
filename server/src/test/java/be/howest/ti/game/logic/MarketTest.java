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
        assertEquals( 36,market.getAllCards().getFirst().size());
        assertEquals(26 ,market.getAllCards().get(1).size());
        assertEquals( 16,market.getAllCards().get(2).size());
        assertEquals(6, market.getAllNobles().size());
        assertEquals(4 , market.getCardsAvailableInMarket().getFirst().size());
        assertEquals(4 , market.getCardsAvailableInMarket().get(1).size());
        assertEquals(4 , market.getCardsAvailableInMarket().get(2).size());
        assertEquals( 4, market.getNoblesAvailableInMarket().size());
    }

    @Test
    void testUnclaimedTokenCountFor3Players() {
        Map<Token, Integer> tokens = market.getUnclaimedTokens();

        for (Map.Entry<Token, Integer> entry : tokens.entrySet()) {
            if (entry.getKey() == Token.GOLD) {
                assertEquals(5, entry.getValue());
            } else {
                assertEquals(5, entry.getValue());
            }
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




}
