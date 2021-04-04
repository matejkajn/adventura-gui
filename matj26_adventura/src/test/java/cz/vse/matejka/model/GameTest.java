package cz.vse.matejka.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Testovací třída pro komplexní otestování herního příběhu.
 *
 * @author Jarmila Pavlíčková
 * @author Jan Říha
 * @author Jonáš Matějka
 * @version LS 2020
 */
public class GameTest
{
    private Game game;

    /**
     * Vytvoření instance třídy game.
     *
     */
    @Before
    public void setUp()
    {
        game = new Game();
    }

    /**
     * Metoda testuje verzi hry, kdy hráč ukončí hru příkazem 'konec'.
     *
     */
    @Test
    public void testPlayerQuit()
    {
        assertEquals("hostinec", game.getGamePlan().getCurrentArea().getName());

        game.processCommand("jdi les");
        assertEquals("les", game.getGamePlan().getCurrentArea().getName());
        assertFalse(game.isGameOver());

        game.processCommand("jdi stratholme");
        assertEquals("stratholme", game.getGamePlan().getCurrentArea().getName());
        assertFalse(game.isGameOver());

        game.processCommand("konec");
        assertTrue(game.isGameOver());
    }

    /**
     * Metoda testuje verzi hry, kdy hráč ukončí hru vítězstvím.
     *
     */
    @Test
    public void testWinGame()
    {
        assertEquals("hostinec", game.getGamePlan().getCurrentArea().getName());
        
        game.processCommand("jdi les");
        assertEquals("les", game.getGamePlan().getCurrentArea().getName());
        assertFalse(game.isGameOver());
        
        game.processCommand("prozkoumej strom");
        game.processCommand("seber zlate_jablko");
        game.processCommand("jdi stratholme");
        assertEquals("stratholme", game.getGamePlan().getCurrentArea().getName());
        assertFalse(game.isGameOver());
        
        game.processCommand("jdi vetesnictvi");
        assertEquals("vetesnictvi", game.getGamePlan().getCurrentArea().getName());
        game.processCommand("dej zlate_jablko vetesnik");
        game.processCommand("prozkoumej truhla");
        game.processCommand("vybavit kozena_helma");
        game.processCommand("vybavit kozene_brneni");
        assertFalse(game.isGameOver());
        
        game.processCommand("jdi lektvary");
        assertEquals("lektvary", game.getGamePlan().getCurrentArea().getName());
        game.processCommand("promluv obchodnice");
        assertFalse(game.isGameOver());
        
        game.processCommand("jdi stratholme");
        assertEquals("stratholme", game.getGamePlan().getCurrentArea().getName());
        assertFalse(game.isGameOver());
        
        game.processCommand("jdi roklina");
        assertEquals("roklina", game.getGamePlan().getCurrentArea().getName());
        assertFalse(game.isGameOver());
        
        game.processCommand("jdi jeskyne");
        assertEquals("jeskyne", game.getGamePlan().getCurrentArea().getName());
        game.processCommand("zautoc lupic");
        game.processCommand("prozkoumej pytel");
        game.processCommand("vybavit kozene_boty");
        game.processCommand("vybavit kozene_kalhoty");
        game.processCommand("seber kosik_bylinek");
        assertFalse(game.isGameOver());
        
        game.processCommand("jdi roklina");
        assertEquals("roklina", game.getGamePlan().getCurrentArea().getName());
        assertFalse(game.isGameOver());
        
        game.processCommand("jdi stratholme");
        assertEquals("stratholme", game.getGamePlan().getCurrentArea().getName());
        assertFalse(game.isGameOver());
        
        game.processCommand("jdi lektvary");
        assertEquals("lektvary", game.getGamePlan().getCurrentArea().getName());
        game.processCommand("dej kosik_bylinek obchodnice");
        game.processCommand("pouzit jed");
        assertFalse(game.isGameOver());
        
        game.processCommand("jdi stratholme");
        assertEquals("stratholme", game.getGamePlan().getCurrentArea().getName());
        assertFalse(game.isGameOver());
        
        game.processCommand("jdi les");
        assertEquals("les", game.getGamePlan().getCurrentArea().getName());
        assertFalse(game.isGameOver());
        
        game.processCommand("jdi polni_cesta");
        assertEquals("polni_cesta", game.getGamePlan().getCurrentArea().getName());
        game.processCommand("zautoc templari");
        assertFalse(game.isGameOver());
        
        game.processCommand("jdi ketes");
        assertEquals("ketes", game.getGamePlan().getCurrentArea().getName());
        game.processCommand("dej dopis ares");
        assertTrue(game.isGameOver());
    }
    
    /**
     * Metoda testuje verzi hry, kdy hráč ukončí hru prohrou.
     *
     */
    @Test
    public void testLoseGame()
    {
        assertEquals("hostinec", game.getGamePlan().getCurrentArea().getName());
        
        game.processCommand("jdi les");
        assertEquals("les", game.getGamePlan().getCurrentArea().getName());
        assertFalse(game.isGameOver());
        
        game.processCommand("jdi polni_cesta");
        assertEquals("polni_cesta", game.getGamePlan().getCurrentArea().getName());
        game.processCommand("zautoc templari");
        assertTrue(game.isGameOver());
    }
}
