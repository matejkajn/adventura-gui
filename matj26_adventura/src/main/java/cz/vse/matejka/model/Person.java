package cz.vse.matejka.model;

import java.util.Map;
import java.util.HashMap;

/**
 * Třída person v mé hře bude představovat postavy, se kterými bude moct hlavní postava vést dialog.
 * Postava může obsahovat předměty a odpovídat hráči.
 * @author Jonáš Matějka
 * @version LS 2020
 */

public class Person
{
    private String name;
    private String dialog;
    private boolean enemy;
    private int attack;
    private int armor;
    private int health;
    private int deff;
    private boolean alive = true;
    private Map<String, Item> items; 
    private String itemName;
    //Konstruktor pro nepřátele
    /**
     * Konstruktor třídy person pro nepřátele
     *
     * @param name jméno postavy
     * @param dialog dialog, který postava říká
     * @param enemy skutečnost, zda je postava nepřítel
     * @param attack hodnota poškození
     * @param armor hodnota brnění
     * @param health hodnota životů
     */
    public Person(String name, String dialog, boolean enemy, int attack, int armor, int health)
    {
        this.name = name;
        this.dialog = dialog;
        this.enemy = enemy;
        this.attack = attack;
        this.armor = armor;
        this.health = health;
        this.items = new HashMap<>();
        this.deff = armor + health;
    }
    //Konstruktor pro přátelské postavy
    /**
     * Konstruktor třídy person pro npřátelské postavy
     *
     * @param name jméno postavy
     * @param dialog dialog, který postava říká
     * @param enemy skutečnost, zda je postava přítel
     * @param itemName jméno předměty, který postava chce od hráče
     */
    public Person(String name, String dialog, boolean enemy, String itemName)
    {
        this.name = name;
        this.dialog = dialog;
        this.enemy = enemy;
        this.items = new HashMap<>();
        this.itemName = itemName;
    }
    
    /**
     * Metoda vrátí jméno postavy.
     *
     * @return jméno postavy
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * Metoda vrátí dialog postavy.
     *
     * @return dialog postavy
     */
    public String getDialog()
    {
        return dialog;
    }
    
    /**
     * Metoda kontroluje, zda je postava nepřátelská.
     *
     * @return skutečnost, zda je postava nepřítel
     */
    public boolean isEnemy()
    {
        return enemy;
    }
    
    /**
     * Metoda vrací hodnotu poškození postavy.
     *
     * @return hodnota poškození
     */
    public int getAttack()
    {
        return attack;
    }
    
    /**
     * Metoda vrací hodnotu brnění postavy.
     *
     * @return hodnota brnění
     */
    public int getArmor()
    {
        return armor;
    }
    
    /**
     * Metoda vrací hodnotu životů postavy.
     *
     * @return hodnota životů
     */    
    public int getHealth()
    {
        return health;
    }
    
    /**
     * Metoda vrací hodnotu obraných statů postavy.
     * Hodnota obraných statů je součet brnění a životů
     *
     * @return součet brnění a životů
     */
    public int getDeff()
    {
        return deff;
    }
    
    /**
     * Metoda nastavuje hodnotu obraných statů postavy.
     * Hodnota obraných statů je součet brnění a životů
     * 
     * @param deff hodnota obraných
     */
    public void setDeff(int deff)
    {
        this.deff = deff;
    }
    
    /**
     * Metoda nastavuje hodnotu poškození postavy.
     *
     * @param attack hodnota poškození
     */
    public void setAttack(int attack)
    {
        this.attack = attack;
    }
    
    /**
     * Metoda nastavuje hodnotu brnění postavy.
     *
     * @param armor hodnota brnění
     */
    public void setArmor(int armor)
    {
        this.armor = armor;
    }

    /**
     * Metoda nastavuje hodnotu životů postavy.
     *
     * @param health hodnota životů
     */
    public void setHealth(int health)
    {
        this.health = health;
    }
    
    /**
     * Metoda vrací informaci, zda je postava živá
     *
     * @return skutečnost, zda je postava živá
     */
    public boolean isAlive()
    {
        return alive;
    }
    
    /**
     * Metoda nastavuje informaci, zda je postava živá.
     *
     * @param alive skutečnost, zda je postava živá
     */
    public void setAlive(boolean alive)
    {
        this.alive = alive;
    }
    
    /**
     * Metoda přidá postavě do inventáře předmět.
     *
     * @param item předmět
     */
    public void addItem(Item item)
    {
        items.put(item.getName(), item);
    }

    /**
     * Metoda odebere postavě předmět z inventáře.
     *
     * @param itemName jméno předmětu
     * @return odebere předmět
     */
    public Item removeItem(String itemName)
    {
        return items.remove(itemName);
    }

    /**
     * Metoda vrátí odkaz na předmět z inventáře postavy.
     *
     * @param itemName jméno předmětu
     * @return odkaz na předmět
     */
    public Item getItem(String itemName)
    {
        return items.get(itemName);
    }

    /**
     * Metoda vrací informaci, zda postava má u sebe daný předmět.
     *
     * @param itemName jméno předmětu
     * @return skutečnost, zda postava má u sebe daný předmět
     */
    public boolean containsItem(String itemName)
    {
        return items.containsKey(itemName);
    }
    
    /**
     * Metoda vrací jméno předmětu, který chce postava od hráče.
     *
     * @return jméno předmětu
     */
    public String getWhatHeWants()
    {
        return itemName;
    }
}
