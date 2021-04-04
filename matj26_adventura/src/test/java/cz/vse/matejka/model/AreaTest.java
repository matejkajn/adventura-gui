package cz.vse.matejka.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Testovací třída pro komplexní otestování třídy {@link Area} Area.
 *
 * @author Jarmila Pavlíčková
 * @author Jan Říha
 * @author Jonáš Matějka
 * @version LS 2020
 */
public class AreaTest
{
    /**
     * Metoda testuje funkčnost přidání východů do lokace.
     *
     */
    @Test
    public void testAreaExits()
    {
        Area area1 = new Area("hala", "Toto je vstupní hala budovy VŠE na Jižním městě.");
        Area area2 = new Area("bufet", "Toto je bufet, kam si můžete zajít na svačinu.");

        area1.addExit(area2);
        area2.addExit(area1);

        assertEquals(area1, area2.getExitArea(area1.getName()));
        assertEquals(area2, area1.getExitArea(area2.getName()));

        assertNull(area1.getExitArea("pokoj"));
        assertNull(area2.getExitArea("pokoj"));
    }

    /**
     * Metoda testuje funkčnost itemizace v lokaci.
     * Tj. odebrání, přidání předmětů.
     * Také zda se předmět nachází v lokaci 
     */
    @Test
    public void testItems()
    {
        Area area = new Area("hala", "Toto je vstupní hala budovy VŠE na Jižním městě.");
        
        Item item1 = new Item("stul", "Těžký dubový stůl.", false);
        Item item2 = new Item("rum", "Láhev vyzrálého rumu.");

        assertFalse(area.containsItem(item1.getName()));
        assertFalse(area.containsItem(item2.getName()));
        assertFalse(area.containsItem("pc"));

        area.addItem(item1);
        area.addItem(item2);

        assertEquals(item1, area.getItem(item1.getName()));
        assertEquals(item2, area.getItem(item2.getName()));
        assertNull(area.getItem("pc"));

        assertTrue(area.containsItem(item1.getName()));
        assertTrue(area.containsItem(item2.getName()));
        assertFalse(area.containsItem("pc"));

        assertEquals(item1, area.removeItem(item1.getName()));
        assertEquals(item2, area.removeItem(item2.getName()));
        assertNull(area.removeItem("pc"));

        assertFalse(area.containsItem(item1.getName()));
        assertFalse(area.containsItem(item2.getName()));
        assertFalse(area.containsItem("pc"));
    }   

    /**
     * Metoda testuje funkčnost postav v lokaci.
     * Tj. odebrání, přidání postav.
     * Také zda lokace obsahuje postavu nebo nepřítele.
     *
     */
    @Test
    public void testPersons()
    {
        Area area = new Area("test", "Toto je testovací area.");
        
        Person person1 = new Person("test1", "Testový dialog 1.", true, 45, 100, 100);
        Person person2 = new Person("test2", "Testový dialog 2", false, "kosik_bylinek");
        
        assertFalse(area.containsPerson(person1.getName()));
        assertFalse(area.containsPerson(person2.getName()));
        assertFalse(area.containsPerson("nikdo"));

        area.addPerson(person1);
        area.addPerson(person2);

        assertEquals(person1, area.getPerson(person1.getName()));
        assertEquals(person2, area.getPerson(person2.getName()));
        assertNull(area.getItem("nikdo"));

        assertTrue(area.containsPerson(person1.getName()));
        assertTrue(area.containsPerson(person2.getName()));
        assertFalse(area.containsPerson("nikdo"));
        
        assertTrue(area.containsEnemy());

        assertEquals(person1, area.removePerson(person1.getName()));
        assertEquals(person2, area.removePerson(person2.getName()));
        assertNull(area.removePerson("nikdo"));
        
        assertFalse(area.containsEnemy());

        assertFalse(area.containsPerson(person1.getName()));
        assertFalse(area.containsPerson(person2.getName()));
        assertFalse(area.containsPerson("nikdo"));
    }
}
