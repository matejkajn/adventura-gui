package cz.vse.matejka.model;
import java.util.*;

/**
 * Tato třída představuje inventář hráče. Hráč může do inventáře dávát předměty a předměty
 * z něj také vyhodit.
 * V této třídě bude také definován equip hráče. Ten bude tvořen 5ti předměty, které mohou být pouze nahrazeny za lepší.
 * V equipu bude: helma, brnění, kalhoty, boty a zbraň
 * 
 * @author Jonáš Matějka
 * @version LS 2020
 */
public class Inventory
{
    private Map<String, Item> inventory;
    private Map<EquipmentType, Item> equip;
    private static final int CAPACITY = 3;
    private static final int EQUIP_CAPACITY = 5;
    
    /**
     * Konstruktor inventáře, který vytvoří inventář pro předměty i pro vybavení.
     *
     */
    public Inventory()
    {
        this.inventory = new HashMap<>();
        this.equip = new HashMap<>();
    }

    /**
     * Metoda pro odkaz na stávající inventář s konkrétními předměty.
     * Využívá se pro aktualizaci všech předmětvů v inventáři v grafickém rozhraní.
     * Vrací pouze inventář, vybavení má svou metodu.
     * @return odkaz na inventář
     */
    public Map<String, Item> getItems() {
        return inventory;
    }
    
    /**
     * Pomocná metoda pro testování funkčnosti inventáře.
     * Metoda simuluje přidání předmětu.
     *
     * @param item předmět
     * @return skutečnost, zda se předmět přidal do inventáře
     */
    public boolean addItemForTest(Item item)
    {
        if ( checkInventorySpace()) {
            if (item.isMoveable()) {
                inventory.put(item.getName(), item);
                return true;
            }
        }
        return false;
    }
    
    /**
     * Metoda vrací informaci, zda je v inventáři místo.
     *
     * @return skutečnost, zda je v inventáři místo
     */
    public boolean checkInventorySpace()
    {
        return (inventory.size() < CAPACITY);
    }
    
    /**
     * Metoda vrací informaci, zda je vybavení plné.
     *
     * @return skutečnost, zda je vybavení plné
     */
    public boolean checkEquipFull()
    {
        return (equip.size() == EQUIP_CAPACITY);
    }
    
    /**
     * Metoda přidá předmět do vybavení.
     *
     * @param item předmět
     */
    public void addEquip(Item item)
    {
        equip.put(item.getEquipmentType(), item);
    }
    
    /**
     * Metoda vymění předmět vybavení za lepší.
     *
     * @param item předmět
     */
    public void changeEquip(Item item)
    {
        for (EquipmentType type : equip.keySet()) {
            if (item.getEquipmentType() == type) {
                equip.remove(type);
                equip.put(item.getEquipmentType(), item);
                break;
            }
        }
    }
       
    /**
     * Metoda přidá předmět do inventáře.
     *
     * @param item předmět
     */
    public void addItem(Item item)
    {
        inventory.put(item.getName(), item);
    }
    
    /**
     * Metoda odstraní předmět z inventáře.
     *
     * @param item předmět
     * @return skutečnost, zda šlo předmět odstranit
     */
    public boolean removeItem(Item item)
    {
        if (inventory.containsKey(item.getName())) {
            inventory.remove(item.getName());
            return true;
        }
        return false;
    }
    
    /**
     * Metoda vrací odkaz na předmět z inventáře.
     *
     * @param name jméno předmětu
     * @return předmět v inventáři
     */
    public Item getItem(String name)
    {
        return inventory.get(name);
    }
    
    /**
     * Metoda vrací odkaz na předmět z vybavení.
     *
     * @param equipType typ vybavení
     * @return předmět ve vybavení
     */
    public Item getEquip(EquipmentType equipType)
    {
        return equip.get(equipType);
    }

    /**
     * Metoda vrací odkaz kolekci vybavení.
     * Slouží pro aktualizaci vybavení v grafickém rozhraní.
     *
     * @return odkaz na kolekci vybavení
     */
    public Map<EquipmentType, Item> getEquipment() {
        return equip;
    }

    /**
     * Metoda vrací informaci, zda inventář obsahuje předmět.
     *
     * @param name jméno předmětu
     * @return skutečnost, zda je předmět v inventáři
     */

    public boolean containsItem(String name)
    {
        return (inventory.containsKey(name));
    }
    
    /**
     * Metoda vrací informaci, zda vybavení obsahuje předmět.
     *
     * @param equipType typ vybavení
     * @return skutečnost, zda je předmět ve vybavení 
     */
    public boolean containsEquip(EquipmentType equipType)
    {
        return (equip.containsKey(equipType));
    }
    
    /**
     * Metoda vrací informaci, jestli je inventář prázdný.
     *
     * @return skutečnost, zda je inventář prázdný
     */
    public boolean isInventoryEmpty()
    {
        return (inventory.isEmpty());
    }
    
    /**
     * Metoda vypisuje kompletní seznam inventáře a vybavení.
     * Metoda také vypisuje informace vybavení a hráčovo celkové životy, brnění a poškození.
     *
     * @return kompletní výpis inventáře
     */
    public String getInventoryItems()
    {
        String list = "********************\n" +"V inventáři máš:\n";
        int attack = 0;
        int armor = 0;
        int health = 0;
        for (String name : inventory.keySet())
        {
            list += name + "\n";
        }
        list += "********************\n";
        list += "Tvé vybavení je:\n";
        for (EquipmentType eT : equip.keySet())
        {
            if (eT == EquipmentType.ZBRAN) {
                list += equip.get(eT).getName() + "\t\tPoškození: " + equip.get(eT).getAttack() + "     " + "\n";
                attack = equip.get(eT).getAttack();
            }
            else {
                    list += equip.get(eT).getName() + "\t\tObrana: " + equip.get(eT).getArmor() + "     " + "\n";
                    armor += equip.get(eT).getArmor();
                    health += equip.get(eT).getHealth();
            }
        }
        list += "********************\n";
        list += "Tvé staty:\n";
        list += "Poškození:\t" + attack;
        list += "\nŽivoty: \t" + health;
        list += "\nBrnění: \t" + armor;
        list += "\n********************\n";
        return list;
    }
}
