package cz.vse.matejka.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tato testovací třída testuje funkčnost příkazu 'prozkoumej'.
 *
 * @author Jonáš Matějka
 * @version LS 2020
 */
public class CommandInspectTest
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
     * Metoda testuje fungování příkazu 'prozkoumej'.
     *
     */
    @Test
    public void testProcess()
    {
        assertEquals("hostinec", game.getGamePlan().getCurrentArea().getName());
        
        assertEquals("Nevím, co mám prozkoumat, musíš zadat název předmětu.", game.processCommand("prozkoumej"));
        assertEquals("Tomu nerozumím, neumím prozkoumat více předmětů současně.", game.processCommand("prozkoumej něco něco"));
        assertEquals(game.getGamePlan().getInventory().getItem("dopis").getDescription(), game.processCommand("prozkoumej dopis"));
        assertEquals("Předmět 'něco' tady není a nebo ho nelze prozkoumat.", game.processCommand("prozkoumej něco"));
        
        game.processCommand("jdi les");
        assertEquals("les", game.getGamePlan().getCurrentArea().getName());
        
        assertEquals("Přistoupil jsi ke stromu a z listí si vyhrabal předmět 'zlate_jablko'.", game.processCommand("prozkoumej strom"));
        assertTrue(game.getGamePlan().getCurrentArea().containsItem("zlate_jablko"));
        assertEquals(game.getGamePlan().getCurrentArea().getItem("strom").getDescription(), game.processCommand("prozkoumej strom"));
        
        game.processCommand("jdi polni_cesta");
        assertEquals("polni_cesta", game.getGamePlan().getCurrentArea().getName());
        assertEquals("Teď jsi v boji! Nemůžeš prozkoumávat věci, musíš se bránit!", game.processCommand("prozkoumej něco"));
    }
}
