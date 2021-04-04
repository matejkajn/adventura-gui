package cz.vse.matejka.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tato testovací třída testuje funkčnost příkazu 'jdi'.
 *
 * @author Jonáš Matějka
 * @version LS 2020
 */
public class CommandMoveTest
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
     * Metoda testuje fungování příkazu 'jdi'.
     *
     */
    @Test
    public void testProcess()
    {
        assertEquals("hostinec", game.getGamePlan().getCurrentArea().getName());
         
        assertEquals("Nechápu, kam mám jít. Musíš mi zadat nějaký cíl.", game.processCommand("jdi"));
        assertEquals("Nechápu, co po mě chceš. Neumím se 'rozdvojit' a jít na více míst současně.", game.processCommand("jdi něco něco"));
        assertEquals("Tam se ale odsud jít nedá!", game.processCommand("jdi nikam"));
        
        game.processCommand("jdi roklina");
        assertEquals("roklina", game.getGamePlan().getCurrentArea().getName());
        
        assertEquals("Jeskyně tě láká svou tajemností, avšak svůj úkol máš zadaný. Proč bys zbytečně lezl do strašidelné jeskyně?", game.processCommand("jdi jeskyne"));
        assertEquals("roklina", game.getGamePlan().getCurrentArea().getName());
        
        game.processCommand("jdi stratholme");
        assertEquals("stratholme", game.getGamePlan().getCurrentArea().getName());
        
        game.processCommand("jdi les");
        assertEquals("les", game.getGamePlan().getCurrentArea().getName());
        
        game.processCommand("jdi polni_cesta");
        assertEquals("polni_cesta", game.getGamePlan().getCurrentArea().getName());
        assertEquals("Teď jsi v boji! Nemůžeš utíkat, musíš se bránit!", game.processCommand("jdi les"));
        assertEquals("polni_cesta", game.getGamePlan().getCurrentArea().getName());
    }
}
