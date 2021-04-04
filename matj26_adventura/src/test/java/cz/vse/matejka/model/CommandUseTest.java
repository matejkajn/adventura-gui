package cz.vse.matejka.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Testovací třída slouží ke komplexnímu testování příkazu 'pouzit'.
 *
 * @author  Jonáš Matějka
 * @version LS 2020
 */
public class CommandUseTest
{
    private Game game;
    /***************************************************************************
     * Inicializace předcházející spuštění každého testu a připravující tzv.
     * přípravek (fixture), což je sada objektů, s nimiž budou testy pracovat.
     */
    @Before
    public void setUp()
    {
        game = new Game();
    }

    /**
     * Metoda testuje funkčnost příkazu 'pouzit'.
     *
     */
    @Test
    public void testProcess()
    {
        assertEquals("hostinec", game.getGamePlan().getCurrentArea().getName());
        
        assertEquals("Nevím co mám použít. Musíš zadat název předmětu.", game.processCommand("pouzit"));
        assertEquals("Musíš zadat název předmětu.", game.processCommand("pouzit něco něco"));
        assertEquals("Předmět nemáš v inventáři", game.processCommand("pouzit nic"));
        assertEquals("Tento předmět nemůžeš nějak použít.", game.processCommand("pouzit dopis"));
        
        game.processCommand("jdi stratholme");
        assertEquals("stratholme", game.getGamePlan().getCurrentArea().getName());
        
        game.processCommand("jdi lektvary");
        assertEquals("lektvary", game.getGamePlan().getCurrentArea().getName());
        game.processCommand("promluv obchodnice");
        
        game.processCommand("jdi stratholme");
        assertEquals("stratholme", game.getGamePlan().getCurrentArea().getName());
        
        game.processCommand("jdi roklina");
        assertEquals("roklina", game.getGamePlan().getCurrentArea().getName());
        game.processCommand("jdi jeskyne");
        assertEquals("jeskyne", game.getGamePlan().getCurrentArea().getName());
        game.processCommand("zautoc lupic");
        game.processCommand("seber kosik_bylinek");
        game.processCommand("jdi roklina");
        assertEquals("roklina", game.getGamePlan().getCurrentArea().getName());
        game.processCommand("jdi stratholme");
        assertEquals("stratholme", game.getGamePlan().getCurrentArea().getName());
        game.processCommand("jdi lektvary");
        assertEquals("lektvary", game.getGamePlan().getCurrentArea().getName());
        game.processCommand("dej kosik_bylinek obchodnice");
        assertTrue(game.getGamePlan().getInventory().containsItem("jed"));
        assertEquals("Vylepšil sis svou zbraň jedem na 'otraveny_mec'.", game.processCommand("pouzit jed"));
        assertFalse(game.getGamePlan().getInventory().containsItem("jed"));
    }
}
