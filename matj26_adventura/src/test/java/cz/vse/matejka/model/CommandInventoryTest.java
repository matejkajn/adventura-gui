package cz.vse.matejka.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tato testovací třída testuje funkčnost příkazu 'inventar'.
 *
 * @author Jonáš Matějka
 * @version LS 2020
 */
public class CommandInventoryTest
{
    private Game game;
    /**
     * Inicializace předcházející spuštění každého testu a připravující tzv.
     * přípravek (fixture), což je sada objektů, s nimiž budou testy pracovat.
     */
    @Before
    public void setUp()
    {
        game = new Game();
    }
    
    /**
     * Metoda testuje fungování příkazu 'inventar'.
     *
     */
    @Test
    public void testProcess()
    {
        assertEquals("hostinec", game.getGamePlan().getCurrentArea().getName());
        
        assertEquals(game.getGamePlan().getInventory().getInventoryItems(), game.processCommand("inventar"));
        
        game.processCommand("vyhod dopis");
        game.processCommand("vyhod mesec_zlatych");
        
        assertTrue(game.getGamePlan().getInventory().isInventoryEmpty());
        assertEquals("Tvůj inventář je prázdný", game.processCommand("inventar"));
    }
}
