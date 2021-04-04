package cz.vse.matejka.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Testovací třída slouží ke komplexnímu otestování příkazu 'vybavit'.
 *
 * @author  Jonáš Matějka
 * @version LS 2020
 */
public class CommandEquipTest
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
     * Metoda testuje funkčnost příkazu 'vybavit'.
     *
     */
    @Test
    public void testProcess()
    {
        assertEquals("hostinec", game.getGamePlan().getCurrentArea().getName());
        
        assertEquals("Nevím, kterou věc si mám vybavit, musíš zadat název předmětu.", game.processCommand("vybavit "));
        assertEquals("Tomu nerozumím, neumím se vybavit více předměty současně.", game.processCommand("vybavit něco něco"));
        
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
        
        assertEquals("Teď jsi v boji! Nemůžeš se vybavovat lepším vybavením, musíš se bránit!", game.processCommand("vybavit kozene_kalhoty"));
        game.processCommand("zautoc lupic");
        assertEquals("Předmět 'kozene_kalhoty' tady není.", game.processCommand("vybavit kozene_kalhoty"));
        assertEquals("Tento předmět není vybavení.", game.processCommand("vybavit kosik_bylinek"));
        game.processCommand("prozkoumej pytel");
        assertTrue(game.getGamePlan().getCurrentArea().containsItem("kozene_kalhoty"));
        assertEquals("Vybavil jsi se lepším předmětem 'kozene_kalhoty'.", game.processCommand("vybavit kozene_kalhoty"));
    }
}
