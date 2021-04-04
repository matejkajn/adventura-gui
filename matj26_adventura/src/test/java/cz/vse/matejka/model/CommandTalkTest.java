package cz.vse.matejka.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tato testovací třída testuje funkčnost příkazu 'promluv'.
 *
 * @author Jonáš Matějka
 * @version LS 2020
 */
public class CommandTalkTest
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
     * Metoda testuje fungování příkazu 'promluv'.
     *
     */
    @Test
    public void testProcess()
    {
        assertEquals("hostinec", game.getGamePlan().getCurrentArea().getName());
         
        assertEquals("Nevím s kým mám mluvit, musíš zadat jméno postavy.", game.processCommand("promluv"));
        assertEquals("Tomu nerozumím, neumím mluvit s více postavami současně.", game.processCommand("promluv něco něco"));
        assertEquals("Postava 'zebrak' tady není.", game.processCommand("promluv zebrak"));
        
        game.processCommand("jdi stratholme");
        assertEquals("stratholme", game.getGamePlan().getCurrentArea().getName());
        
        game.processCommand("jdi lektvary");
        assertEquals("lektvary", game.getGamePlan().getCurrentArea().getName());
        assertEquals(game.getGamePlan().getCurrentArea().getPerson("obchodnice").getDialog(), game.processCommand("promluv obchodnice"));
        
        game.processCommand("jdi stratholme");
        assertEquals("stratholme", game.getGamePlan().getCurrentArea().getName());
        
        game.processCommand("jdi roklina");
        assertEquals("roklina", game.getGamePlan().getCurrentArea().getName());
        
        game.processCommand("jdi jeskyne");
        assertEquals("jeskyne", game.getGamePlan().getCurrentArea().getName());
        assertEquals("S 'lupic' si necheš povídat, když ti jde po krku. Musíš s ní bojovat.", game.processCommand("promluv lupic"));
        game.processCommand("zautoc lupic");
        game.processCommand("seber kosik_bylinek");
        
        game.processCommand("jdi roklina");
        assertEquals("roklina", game.getGamePlan().getCurrentArea().getName());
        
        game.processCommand("jdi stratholme");
        assertEquals("stratholme", game.getGamePlan().getCurrentArea().getName());
        
        game.processCommand("jdi lektvary");
        assertEquals("lektvary", game.getGamePlan().getCurrentArea().getName());
        assertEquals(game.getGamePlan().getCurrentArea().getPerson("obchodnice").getDialog(), game.processCommand("promluv obchodnice"));
        game.processCommand("dej kosik_bylinek obchodnice");
        assertEquals("\"Za tvou pomoc ti děkuji, ale odměnu jsem ti už dala.\"", game.processCommand("promluv obchodnice"));
    }
}
