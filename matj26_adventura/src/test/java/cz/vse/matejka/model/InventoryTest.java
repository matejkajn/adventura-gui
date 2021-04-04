package cz.vse.matejka.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Testovací třída slouží ke komplexnímu otestování
 * třídy Inventory.
 *
 * @author  Jonáš Matějka
 * @version LS 2020
 */
public class InventoryTest
{
    private Item loupak, socha, jahoda, pivo, fixa;
    
    private Item helma, brneni, kalhoty, boty, mecZakladni, mecOtraveny;
    
    /***************************************************************************
     * Inicializace předcházející spuštění každého testu a připravující tzv.
     * přípravek (fixture), což je sada objektů, s nimiž budou testy pracovat.
     */
    @Before
    public void setUp()
    {
        loupak = new Item("loupak", "Loupák.");
        socha = new Item("socha", "Socha svobody.", false);
        jahoda = new Item("jahoda", "Zvadlá jahoda");
        pivo = new Item("pivo", "Vychlazená dvanáctka.");
        fixa = new Item("fixa", "Vypsaná fixa.");
        
        helma = new Item("latkova_helma", "Látková helma.", 25, 5);
        brneni = new Item("latkove_brneni", "Látkové brnění.", 25, 20);
        kalhoty = new Item("latkove_kalhoty", "Látkové kalhoty.", 25, 15);
        boty = new Item("latkove_boty", "Látkové boty.", 25, 10);
        mecZakladni = new Item("lehky_mec", "Lehký ostrý meč.", 25);
        mecOtraveny = new Item("otraveny_mec", "Otrávený meč.", 45);
        
        helma.setEquipmentType(EquipmentType.HELMA);
        brneni.setEquipmentType(EquipmentType.BRNENI);
        kalhoty.setEquipmentType(EquipmentType.KALHOTY);
        boty.setEquipmentType(EquipmentType.BOTY);
        mecZakladni.setEquipmentType(EquipmentType.ZBRAN);
        mecOtraveny.setEquipmentType(EquipmentType.ZBRAN);
    }
    
    /**
     * Metoda testuje funkčnost itemizace inventáře.
     * Přidání, odebrání předmětu. Kontrola výskytu předmětu nebo místa v inventáři.
     * 
     */
    @Test
    public void testInventoryItems()
    {
        Inventory inventory = new Inventory();
        
        assertTrue(inventory.isInventoryEmpty());
        
        assertTrue(inventory.addItemForTest(loupak));
        assertTrue(inventory.addItemForTest(jahoda));
        assertFalse(inventory.addItemForTest(socha));
        assertTrue(inventory.addItemForTest(pivo));
        assertFalse(inventory.addItemForTest(fixa));
        
        assertEquals(loupak, inventory.getItem(loupak.getName()));
        assertEquals(null, inventory.getItem(socha.getName()));
        assertEquals(jahoda, inventory.getItem(jahoda.getName()));
        assertEquals(pivo, inventory.getItem(pivo.getName()));
        assertEquals(null, inventory.getItem(fixa.getName()));
        
        assertTrue(inventory.containsItem(loupak.getName()));
        assertTrue(inventory.containsItem(jahoda.getName()));
        assertTrue(inventory.containsItem(pivo.getName()));
        assertFalse(inventory.containsItem(socha.getName()));
        assertFalse(inventory.containsItem(fixa.getName()));
        
        inventory.removeItem(loupak);
        inventory.removeItem(socha);
        inventory.removeItem(jahoda);
        inventory.removeItem(pivo);
        inventory.removeItem(fixa);
        
        assertTrue(inventory.isInventoryEmpty());
        
        assertFalse(inventory.containsItem(loupak.getName()));
        assertFalse(inventory.containsItem(jahoda.getName()));
        assertFalse(inventory.containsItem(pivo.getName()));
        assertFalse(inventory.containsItem(socha.getName()));
        assertFalse(inventory.containsItem(fixa.getName()));
    }
    
    /**
     * Metoda testuje funkčnost itemizace vybavení.
     * Přidání a výskyt vybavení. Výměna vybavení za lepší nebo zda je vybavení celé.
     * 
     */
    @Test
    public void testInventoryEquip()
    {
        Inventory inventory = new Inventory();
        
        assertFalse(inventory.checkEquipFull());
        
        inventory.addEquip(helma);
        inventory.addEquip(brneni);
        inventory.addEquip(kalhoty);
        inventory.addEquip(boty);
        inventory.addEquip(mecZakladni);
        
        assertEquals(helma, inventory.getEquip(EquipmentType.HELMA));
        assertEquals(brneni, inventory.getEquip(EquipmentType.BRNENI));
        assertEquals(kalhoty, inventory.getEquip(EquipmentType.KALHOTY));
        assertEquals(boty, inventory.getEquip(EquipmentType.BOTY));
        assertEquals(mecZakladni, inventory.getEquip(EquipmentType.ZBRAN));
        
        assertTrue(inventory.checkEquipFull());
        
        assertTrue(inventory.containsEquip(EquipmentType.HELMA));
        assertTrue(inventory.containsEquip(EquipmentType.BRNENI));
        assertTrue(inventory.containsEquip(EquipmentType.KALHOTY));
        assertTrue(inventory.containsEquip(EquipmentType.BOTY));
        assertTrue(inventory.containsEquip(EquipmentType.ZBRAN));
        
        inventory.changeEquip(mecOtraveny);
        assertTrue(inventory.containsEquip(EquipmentType.ZBRAN));
        
        assertTrue(inventory.checkEquipFull());
    }
}
