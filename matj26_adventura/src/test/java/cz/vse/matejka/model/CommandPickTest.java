package cz.vse.matejka.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tato testovací třída testuje funkčnost příkazu 'seber'.
 *
 * @author Jonáš Matějka
 * @version LS 2020
 */
public class CommandPickTest
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
     * Metoda testuje fungování příkazu 'seber'.
     *
     */
    @Test
    public void testProcess()
    {
        assertEquals("hostinec", game.getGamePlan().getCurrentArea().getName());
        
        assertEquals("Nevím, co mám sebrat, musíš zadat název předmětu.", game.processCommand("seber"));
        assertEquals("Tomu nerozumím, neumím sebrat více předmětů současně.", game.processCommand("seber něco něco"));
        assertEquals("Předmět 'zlate_jablko' tady není.", game.processCommand("seber zlate_jablko"));
        
        game.processCommand("jdi les");
        assertEquals("les", game.getGamePlan().getCurrentArea().getName());
        assertEquals("Předmět 'strom' neuneseš.", game.processCommand("seber strom"));
        game.processCommand("prozkoumej strom");
        assertEquals("Sebral jsi předmět 'zlate_jablko' a uložil jsi ho do inventáře.", game.processCommand("seber zlate_jablko"));
        assertTrue(game.getGamePlan().getInventory().containsItem("zlate_jablko"));
        
        game.processCommand("jdi stratholme");
        assertEquals("stratholme", game.getGamePlan().getCurrentArea().getName());
        
        game.processCommand("jdi lektvary");
        assertEquals("lektvary", game.getGamePlan().getCurrentArea().getName());
        game.processCommand("promluv obchodnice");
        
        game.processCommand("jdi vetesnictvi");
        assertEquals("vetesnictvi", game.getGamePlan().getCurrentArea().getName());
        game.processCommand("dej zlate_jablko vetesnik");
        game.processCommand("prozkoumej truhla");
        assertEquals("Předmět 'kozene_brneni' nemůžeš sebrat. Musíš se jím vybavit.", game.processCommand("seber kozene_brneni"));
        
        game.processCommand("jdi stratholme");
        assertEquals("stratholme", game.getGamePlan().getCurrentArea().getName());
        
        game.processCommand("jdi roklina");
        assertEquals("roklina", game.getGamePlan().getCurrentArea().getName());
        
        game.processCommand("jdi jeskyne");
        assertEquals("jeskyne", game.getGamePlan().getCurrentArea().getName());
        assertEquals("Teď jsi v boji! Nemůžeš sbírat věci, musíš se bránit!", game.processCommand("seber neco"));
    }
}
