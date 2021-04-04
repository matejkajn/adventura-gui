package cz.vse.matejka.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tato testovací třída testuje funkčnost příkazu 'zautoc'.
 *
 * @author Jonáš Matějka
 * @version LS 2020
 */
public class CommandAttackTest
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
     * Metoda testuje správnost funkcionality příkazu 'zautoc'.
     *
     */
    @Test
    public void testProcess()
    {
        assertEquals("hostinec", game.getGamePlan().getCurrentArea().getName());
        
        assertEquals("Nevím na koho chceš zaútočit. Musíš zadat jméno protivníka.", game.processCommand("zautoc"));
        assertEquals("Musíš zadat jméno protivníka.", game.processCommand("zautoc něco něco"));
        assertEquals("Postava 'templari' tady není.", game.processCommand("zautoc templari"));
        
        game.processCommand("jdi stratholme");
        assertEquals("stratholme", game.getGamePlan().getCurrentArea().getName());
        assertEquals("Postava 'zebrak' není tvůj nepřítel.", game.processCommand("zautoc zebrak"));
        
        game.processCommand("jdi lektvary");
        assertEquals("lektvary", game.getGamePlan().getCurrentArea().getName());
        game.processCommand("promluv obchodnice");
        
        game.processCommand("jdi stratholme");
        assertEquals("stratholme", game.getGamePlan().getCurrentArea().getName());
        game.processCommand("jdi roklina");
        assertEquals("roklina", game.getGamePlan().getCurrentArea().getName());
        game.processCommand("jdi jeskyne");
        assertEquals("jeskyne", game.getGamePlan().getCurrentArea().getName());
        assertTrue(game.getGamePlan().getCurrentArea().containsEnemy());
        game.processCommand("zautoc lupic");
        assertFalse(game.getGamePlan().getCurrentArea().containsEnemy());
        
    }
}
