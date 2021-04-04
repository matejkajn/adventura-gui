package cz.vse.matejka.model;
import java.util.*;

/**
 * Třída item představuje ve hře předměty. Ty se dělí podle konstruktorů na několik druhů.
 * Předměty, se kterými lze hýbat a nosit je v inventáři.
 * Předměty, se kterými hýbat nelze a nelze je nosit.
 * Předměty, představující vybavení hráče (brnění, zbraň)
 * @author Jan Říha - něco předdělané na cvičení
 * @author Jonáš Matějka
 * @version LS 2020
 */

public class Item
{
    private String name;
    private String description;
    private boolean moveable;
    private int attack;
    private int health;
    private int armor;
    private EquipmentType equipmentType;
    private boolean inspected = false;

    /**
     * Konstruktor pro nehybné předměty.
     *
     * @param name jméno předmětu
     * @param description popis předmětu
     * @param moveable skutečnost, zda lze s předmětem hýbat
     */
    public Item(String name, String description, boolean moveable)
    {
        this.name = name;
        this.description = description;
        this.moveable = moveable;
    }
    
    /**
     * Konstruktor pro předměty, se kterými lze hýbat.
     *
     * @param name jméno předmětu
     * @param description popis předmětu
     */
    public Item(String name, String description)
    {
        this(name, description, true);
    }
    
    /**
     * Konstruktor pro útočné vybavení.
     *
     * @param name jméno předmětu
     * @param description popis předmětu
     * @param attack hodnota poškození
     */
    public Item(String name, String description, int attack)
    {
        this.name = name;
        this.description = description;
        this.attack = attack;
    }
    
    /**
     * Konstruktor pro obranné vybavení.
     *
     * @param name jméno předmětu
     * @param description popis předmětu
     * @param health hodnota životů
     * @param armor hodnota brnění
     */
    public Item(String name, String description, int health, int armor)
    {
        this.name = name;
        this.description = description;
        this.health = health;
        this.armor = armor;
    }

    /**
     * Metoda vrátí jméno předmětu.
     *
     * @return jméno předmětu
     */
    public String getName()
    {
        return name;
    }

    /**
     * Metoda vrátí popis předmětu.
     *
     * @return popis předmětu
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * Metoda nastaví popis předmětu.
     *
     * @param description nový popis
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * Metoda kontroluje, zda je předmět přenositelný.
     *
     * @return skutečnost, zda je předmět pohyblivý
     */
    public boolean isMoveable()
    {
        return moveable;
    }

    /**
     * Metoda nastavuje předmět na přenositelný nebo naopak.
     *
     * @param moveable nastavení přenositelnosti
     */
    public void setMoveable(boolean moveable)
    {
        this.moveable = moveable;
    }
    
    /**
     * Metoda vrací hodnotu poškození předmětu.
     *
     * @return hodnota poškození
     */
    public int getAttack()
    {
        return attack;
    }
    
    /**
     * Metoda vrací hodnotu životů předmětu.
     *
     * @return hodnota životů
     */    
    public int getHealth()
    {
        return health;
    }
    
    /**
     * Metoda vrací hodnotu brnění předmětu.
     *
     * @return hodnota brnění
     */    
    public int getArmor()
    {
        return armor;
    }
    
    /**
     * Metoda vrátí typ výbavy.
     * HELMA, BRNENI, KALHOTY, BOTY, ZBRAN
     *
     * @return typ výbavy podle přiřazeného enumu
     */
    public EquipmentType getEquipmentType()
    {
        return equipmentType;
    }
    
    /**
     * Metoda nastaví typ výbavy podle enum.
     * HELMA, BRNENI, KALHOTY, BOTY, ZBRAN
     *
     * @param equipmentType enum EquipmentType
     */
    public void setEquipmentType(EquipmentType equipmentType)
    {
        this.equipmentType = equipmentType;
    }
    //Prozkoumávání předmětů
    /**
     * Metoda kontroluje, zda byl předmět prozkoumán.
     *
     * @return skutečnot, zda byl předmět prozkoumán
     */
    public boolean isInspected()
    {
        return inspected;
    }
    
    
    /**
     * Metoda nastaví předmět na prozkoumaný nebo naopak.
     *
     * @param inspected nastavení prozkoumání
     */
    public void inspect(boolean inspected) 
    {
        this.inspected = inspected;
    }
}
