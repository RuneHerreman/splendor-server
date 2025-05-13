package be.howest.ti.game.logic;

import be.howest.ti.game.logic.gameTools.Development;
import be.howest.ti.game.logic.gameTools.Noble;
import be.howest.ti.game.logic.gameTools.Token;
import be.howest.ti.game.logic.gameTools.TokenBundle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MarketTest {

    private Market market;

    @BeforeEach
    void setUp() {
        market = new Market(3);
    }

    @Test
    void testInitialMarketSetup() {
        assertNotNull(market.getAllCards(), "All cards should be initialized");
        assertNotNull(market.getAllNobles(), "All nobles should be initialized");
        assertNotNull(market.getCardsAvailableInMarket(), "Market cards should be initialized");
        assertNotNull(market.getNoblesAvailableInMarket(), "Market nobles should be initialized");
        assertNotNull(market.getUnclaimedTokens(), "Unclaimed tokens should be initialized");
    }

    @Test
    void testTokenCountFor3Players() {
        List<TokenBundle> tokens = market.getUnclaimedTokens();

        for (TokenBundle bundle : tokens) {
            if (bundle.getToken() == Token.GOLD) {
                assertEquals(5, bundle.getAmount(), "Gold tokens should always be 5");
            } else {
                assertEquals(5, bundle.getAmount(), "Non-gold tokens should be 5 for 3 players");
            }
        }
    }

    @Test
    void testSetCardToMarket() {
        List<Development> level1Cards = market.getAllCards().getFirst();
        if (!level1Cards.isEmpty()) {
            Development dev = level1Cards.getFirst();
            market.setCardToMarket(dev);

            assertTrue(market.getCardsAvailableInMarket().getFirst().contains(dev), "Card should be added to market");
            assertFalse(market.getAllCards().getFirst().contains(dev), "Card should be removed from allCards");
        }
    }

    @Test
    void testSetNobleToMarket() {
        List<Noble> nobles = market.getAllNobles();
        if (!nobles.isEmpty()) {
            Noble noble = nobles.getFirst();
            market.setNobleToMarket(noble);

            assertTrue(market.getNoblesAvailableInMarket().contains(noble), "Noble should be added to market");
            assertFalse(market.getAllNobles().contains(noble), "Noble should be removed from allNobles");
        }
    }

    @Test
    void testRemoveCardFromMarket() {
        List<Development> marketCards = market.getCardsAvailableInMarket().getFirst();
        if (!marketCards.isEmpty()) {
            Development dev = marketCards.getFirst();
            market.removeCardFromMarket(dev);
            assertFalse(market.getCardsAvailableInMarket().getFirst().contains(dev), "Card should be removed from market");
        }
    }

    @Test
    void testRemoveNobleFromMarket() {
        List<Noble> nobles = market.getNoblesAvailableInMarket();
        if (!nobles.isEmpty()) {
            Noble noble = nobles.getFirst();
            market.removeNobleFromMarket(noble);
            assertFalse(market.getNoblesAvailableInMarket().contains(noble), "Noble should be removed from market");
        }
    }

    @Test
    void testDevelopmentCardLevelsAreSeparated() {
        List<List<Development>> allCards = market.getAllCards();
        assertEquals(3, allCards.size(), "There should be 3 levels of development cards");
    }

    @Test
    void testNumberOfNoblesInMarketIsCorrect() {
        int expectedNobles = 3 + 1;
        assertEquals(expectedNobles, market.getNoblesAvailableInMarket().size(),
                "Market should have amountOfPlayers + 1 nobles");
    }
}
