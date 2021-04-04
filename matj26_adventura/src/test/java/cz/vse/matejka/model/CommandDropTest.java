package cz.vse.matejka.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tato testovací třída testuje funkčnost příkazu 'vyhod'.
 *
 * @author Jonáš Matějka
 * @version LS 2020
 */
public class CommandDropTest
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
     * Metoda testuje správnost funkcionality příkazu 'vyhod'.
     *
     */
    @Test
    public void testProcess()
    {
        assertEquals("hostinec", game.getGamePlan().getCurrentArea().getName());
        
        assertEquals("Nevím, co mám vyhodit, musíš zadat název předmětu.", game.processCommand("vyhod"));
        assertEquals("Tomu nerozumím, neumím vyhodit více předmětů současně.", game.processCommand("vyhod něco něco"));
        assertEquals("Předmět 'něco' v inventáři není.", game.processCommand("vyhod něco"));
        assertEquals("Vyhodil jsi z inventáře 'mesec_zlatych'.", game.processCommand("vyhod mesec_zlatych"));
        assertFalse(game.getGamePlan().getInventory().containsItem("mesec_zlatych"));
        assertEquals("Vyhodil jsi z inventáře 'dopis'.", game.processCommand("vyhod dopis"));
        assertFalse(game.getGamePlan().getInventory().containsItem("dopis"));
        assertEquals("Inventář je prázdný, není z něj co vyhazovat.", game.processCommand("vyhod dopis"));
        
        game.processCommand("jdi les");
        assertEquals("les", game.getGamePlan().getCurrentArea().getName());
        game.processCommand("prozkoumej strom");
        game.processCommand("seber zlate_jablko");
        game.processCommand("jdi polni_cesta");
        assertEquals("Teď jsi v boji! Nemůžeš vyhazovat věci z inventáře, musíš se bránit!", game.processCommand("vyhod zlate_jablko"));
    }
}
