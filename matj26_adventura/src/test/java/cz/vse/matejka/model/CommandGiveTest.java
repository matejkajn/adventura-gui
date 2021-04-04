package cz.vse.matejka.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Testovací třída slouží ke komplexnímu otestování příkazu 'dej'.
 *
 * @author  Jonáš Matějka
 * @version LS 2020
 */
public class CommandGiveTest
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
     * Metoda testuje funkčnost příkazu 'dej'.
     *
     */
    @Test
    public void testProcess()
    {
        assertEquals("hostinec", game.getGamePlan().getCurrentArea().getName());
        
        assertEquals("Nevím komu chceš co dát. Musíš zadat jméno postavy a název předmětu.", game.processCommand("dej "));
        assertEquals("Musíš zadat jméno postavy a název předmětu.", game.processCommand("dej něco"));
        assertEquals("Nemůžeš dávat víc věcí.", game.processCommand("dej něco něco někomu"));
        
        game.processCommand("jdi stratholme");
        assertEquals("stratholme", game.getGamePlan().getCurrentArea().getName());
        game.processCommand("jdi hospoda");
        assertEquals("hospoda", game.getGamePlan().getCurrentArea().getName());
        
        assertEquals("Postava 'zebrak' tady není.", game.processCommand("dej mesec_zlatych zebrak"));
        assertEquals("\"Promiň, ale předmět 'dopis' od tebe nechci, nevím co bych s ním dělal.\"", game.processCommand("dej dopis hospodsky"));
        assertEquals("Předmět 'nic' nemáš ve svém inventáři.", game.processCommand("dej nic hospodsky"));
        assertEquals("Dal jsi předmět 'mesec_zlatych' do inventáře postavy 'hospodsky'."
                    + "\n\"Tady máte ten rum. Doufám, že se rozhodnete správně, s kým ho vypijete, protože takové zboží už nikdy neokusíte.\""
                    , game.processCommand("dej mesec_zlatych hospodsky"));
        assertFalse(game.getGamePlan().getInventory().containsItem("mesec_zlatych"));
        assertTrue(game.getGamePlan().getCurrentArea().getPerson("hospodsky").containsItem("mesec_zlatych"));
        game.processCommand("jdi lektvary");
        assertEquals("lektvary", game.getGamePlan().getCurrentArea().getName());      
        game.processCommand("promluv obchodnice");
        
        game.processCommand("jdi stratholme");
        assertEquals("stratholme", game.getGamePlan().getCurrentArea().getName());
        game.processCommand("jdi roklina");
        assertEquals("roklina", game.getGamePlan().getCurrentArea().getName());
        game.processCommand("jdi jeskyne");
        assertEquals("jeskyne", game.getGamePlan().getCurrentArea().getName());
        assertEquals("Teď jsi v boji! Nemůžeš dávat věci, musíš se bránit!", game.processCommand("dej dopis lupic"));
    }
}
