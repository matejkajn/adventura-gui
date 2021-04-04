package cz.vse.matejka.model;

import java.util.*;

/**
 * Třída představuje lokaci <i>(místo, místnost, prostor)</i> ve scénáři hry.
 * Každá lokace má název, který ji jednoznačně identifikuje. Lokace může mít
 * sousední lokace, do kterých z ní lze odejít. Odkazy na všechny sousední
 * lokace jsou uložené v kolekci.
 *
 * @author Michael Kölling
 * @author Luboš Pavlíček
 * @author Jarmila Pavlíčková
 * @author Jan Říha
 * @author Jonáš Matějka
 * @version LS 2020
 */
public class Area
{
    private String name;
    private String description;
    private boolean locked;
    private Set<Area> exits;  // Obsahuje sousední lokace, do kterých lze z této odejít
    private Map<String, Item> items;  // Obsahuje předměty v lokaci
    private Map<String, Person> persons; //Obsahuje postavy v lokaci

    /**
     * Konstruktor třídy. Vytvoří lokaci se zadaným názvem a popisem.
     *
     * @param name název lokace <i>(jednoznačný identifikátor, musí se jednat o text bez mezer)</i>
     * @param description podrobnější popis lokace
     */
    public Area(String name, String description)
    {
        this.name = name;
        this.description = description;
        this.exits = new HashSet<>();
        this.items = new HashMap<>();
        this.persons = new HashMap<>();
        this.locked = false;
    }

    /**
     * Metoda vrací název lokace, který byl zadán při vytváření instance jako
     * parametr konstruktoru. Jedná se o jednoznačný identifikátor lokace <i>(ve
     * hře nemůže existovat více lokací se stejným názvem)</i>. Aby hra správně
     * fungovala, název lokace nesmí obsahovat mezery, v případě potřeby můžete
     * více slov oddělit pomlčkami, použít camel-case apod.
     *
     * @return název lokace
     */
    public String getName()
    {
        return name;
    }

    /**
     * Metoda přidá další východ z této lokace do lokace předané v parametru.
     * <p>
     * Vzhledem k tomu, že pro uložení sousedních lokací se používá {@linkplain Set},
     * může být přidán pouze jeden východ do každé lokace <i>(tzn. nelze mít dvoje
     * 'dveře' do stejné sousední lokace)</i>. Druhé volání metody se stejnou lokací
     * proto nebude mít žádný efekt.
     * <p>
     * Lze zadat též cestu do sebe sama.
     *
     * @param area lokace, do které bude vytvořen východ z aktuální lokace
     */
    public void addExit(Area area)
    {
        exits.add(area);
    }

    /**
     * Metoda vrací detailní informace o lokaci. Výsledek volání obsahuje název
     * lokace, postavy, které obsahuje a výpis předmětů. 
     * Podrobnější popis a seznam sousedních lokací, do kterých lze odejít.
     *
     * @return detailní informace o lokaci
     */
    public String getFullDescription()
    {
        String exitNames = "Východy:";
        for (Area exitArea : exits) {
            exitNames += "     " + exitArea.getName();
            if (exitArea.isAreaLocked()) {
                exitNames += " (zamknuto)";
            }
        }
        
        String personsNames = "Postavy:";
        for (String personName : persons.keySet()) {
            personsNames += "     " + personName;
        }
        
        String itemNames = "Předměty:";
        for (String itemName : items.keySet()) {
            itemNames += "     " + itemName;
        }

        return "Jsi v lokaci " + name + ".\n"
                + description + "\n\n"
                + personsNames + "\n"
                + itemNames + "\n"
                + exitNames + "\n";
    }

    /**
     * Metoda vrací neupravený popisek dané lokace.
     *
     */

    public String getDescription() {
        return description;
    }
    
    /**
     * Metoda zamyká lokaci, aby do ní nebylo možné jít.
     *
     * @param lock lokace
     */
    public void lockArea(boolean lock) {
        locked = lock;
    }
    
    /**
     * Metoda vrací skutečnost, zda je lokace zamknutá.
     *
     * @return zámek lokace
     */
    public boolean isAreaLocked() {
        return locked;
    }

    /**
     * Metoda vrací lokaci, která sousedí s aktuální lokací a jejíž název
     * je předán v parametru. Pokud lokace s daným jménem nesousedí s aktuální
     * lokací, vrací se hodnota {@code null}.
     * <p>
     * Metoda je implementována pomocí tzv. 'lambda výrazu'. Pokud bychom chtěli
     * metodu implementovat klasickým způsobem, kód by mohl vypadat např. tímto
     * způsobem:
     * <pre> for (Area exitArea : exits) {
     *     if (exitArea.getName().equals(areaName)) {
     *          return exitArea;
     *     }
     * }
     *
     * return null;</pre>
     *
     * @param areaName jméno sousední lokace <i>(východu)</i>
     * @return lokace, která se nachází za příslušným východem; {@code null}, pokud aktuální lokace s touto nesousedí
     */
    public Area getExitArea(String areaName)
    {
        return exits.stream()
                    .filter(exit -> exit.getName().equals(areaName))
                    .findAny().orElse(null);
    }


    /**
     * Metoda vrátí kolekci sousedních prostorů dané lokace.
     *
     * @return Vrátí kolekci prostorů pro čtení, se kterými daná lokace sousdedí.
     */
    public Collection<Area> getExits() {
        return Collections.unmodifiableCollection(exits);
    }

    /**
     * Metoda porovnává dvě lokace <i>(objekty)</i>. Lokace jsou shodné,
     * pokud mají stejný název <i>(atribut {@link #name})</i>. Tato metoda
     * je důležitá pro správné fungování seznamu východů do sousedních
     * lokací.
     * <p>
     * Podrobnější popis metody najdete v dokumentaci třídy {@linkplain Object}.
     *
     * @param o objekt, který bude porovnán s aktuálním
     * @return {@code true}, pokud mají obě lokace stejný název; jinak {@code false}
     *
     * @see Object#equals(Object)
     */
    @Override
    public boolean equals(Object o)
    {
        // Ověříme, že parametr není null
        if (o == null) {
            return false;
        }

        // Ověříme, že se nejedná o stejnou instanci (objekt)
        if (this == o) {
            return true;
        }

        // Ověříme, že parametr je typu (objekt třídy) Area
        if (!(o instanceof Area)) {
            return false;
        }

        // Provedeme 'tvrdé' přetypování
        Area area = (Area) o;

        // Provedeme porovnání názvů, statická metoda equals z pomocné třídy
        // java.util.Objects porovná hodnoty obou parametrů a vrátí true pro
        // stejné názvy a i v případě, že jsou oba názvy null; jinak vrátí
        // false
        return Objects.equals(this.name, area.name);
    }
    
    /**
     * Metoda vrací číselný identifikátor instance, který se používá
     * pro optimalizaci ukládání v dynamických datových strukturách
     * <i>(např.&nbsp;{@linkplain HashSet})</i>. Při překrytí metody
     * {@link #equals(Object) equals} je vždy nutné překrýt i tuto
     * metodu.
     * <p>
     * Podrobnější popis pravidel pro implementaci metody najdete
     * v dokumentaci třídy {@linkplain Object}.
     *
     * @return číselný identifikátor instance
     *
     * @see Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        return Objects.hash(name);
    }

    /**
     * Metoda přidá předmět do lokace.
     * 
     * @param item předmět, který bude do lokace přidán
     */
    public void addItem(Item item)
    {
        items.put(item.getName(), item);
    } 

    /**
     * Metoda odebere předmět z lokace.
     *
     * @param itemName jméno předmětu
     * @return odebere předmět
     */
    public Item removeItem(String itemName)
    {
        return items.remove(itemName);
    }

    /**
     * Metoda vrátí odkaz na předmět z lokace.
     *
     * @param itemName název předmětu
     * @return předmět z lokace
     */
    public Item getItem(String itemName)
    {
        return items.get(itemName);
    }

    /**
     * Metoda vrací kolekci všech předmětů v lokaci.
     *
     *  @return vrátí odkaz na kolekci předmětů
     */
    public Map<String, Item> getAreaItems() {
        return items;
    }

    /**
     * Metoda kontroluje, zda lokace obsahuje daný předmět.
     *
     * @param itemName jméno předmětu
     * @return skutečnost, zda lokace obsahuje předmět
     */
    public boolean containsItem(String itemName)
    {
        return items.containsKey(itemName);
    }
    
    /**
     * Metoda kontroluje, zda se jedná o vybavení.
     *
     * @param itemName jméno vybavení
     * @return skutečnost, zda se jedná o vybavení
     */
    public boolean isEquip(String itemName)
    {
        if (items.get(itemName).getEquipmentType() != null) {
            return true;
        }
        return false;
    }
    
    /**
     * Metoda přidá do lokace osobu.
     *
     * @param person osoba která se přidá do lokace
     */
    public void addPerson(Person person)
    {
        persons.put(person.getName(), person);
    }
    
    /**
     * Metoda odebere osobu z lokace.
     *
     * @param personName jméno osoby
     * @return odebere osobu
     */
    public Person removePerson(String personName)
    {
        return persons.remove(personName);
    }
    
    /**
     * Metoda vrátí odkaz na osobu v lokaci
     *
     * @param personName jméno osoby
     * @return vrátí odkaz na osobu
     */
    public Person getPerson(String personName)
    {
        return persons.get(personName);
    }

    /**
     * Metoda vrací kolekci všech postav v lokaci.
     *
     * @return vrátí odkaz na kolekci osob
     */
    public Map<String, Person> getAreaPersons() {
        return persons;
    }
 
    /**
     * Metoda kontroluje, zda lokace obsahuje osobu.
     *
     * @param personName jméno osoby
     * @return skutečnost, zda lokace obsahuje osobu
     */
    public boolean containsPerson(String personName)
    {
        return persons.containsKey(personName);
    }
    
    /**
     * Metoda kontroluje, zda lokace obsahuje nepřítele.
     *
     * @return skutečnost, zda lokace obsahuje nepřítele
     */
    public boolean containsEnemy() {
        for (Person person : persons.values()) {
            if (person.isEnemy()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Metoda změní popisek lokace, při nějaké akci.
     *
     * @param areaName jméno lokace
     */
    public void changeAreaStatement(String areaName)
    {
        String statement = "";
        switch(areaName) {
            case "les":
                statement = "Jsi v temném hlubokém lese. Strom u kterého jsi viděl třpytivou věc jsi už prohledal a našel jsi zlaté jablko."
                        +  "\nNa konci lesa vidíš polní cestu, kde se kouří z táborového ohně. Pozorně se podíváš a rozpoznáš skupinu templářů."
                        + "\nPolní cesta vede do Ketesu, a proto se budeš muset probojovat. Zkus zvážit jestli nemůžeš sehnat lepší vybavení nebo se vrhni do boje!";;
                break;
            case "roklina":
                statement = "Na konci rokliny vidíš cestu, která vede někam do neznáma a přímo před sebou vchod do hluboké temné jeskyně.\n"
                + "Žebrák ti řekl, že na konci neznámé cesty je tvůj cíl Ketes, ale šance že do cíle dojdeš je 20%. Musíš se rozhodnout, zda chceš pokoušet osud.\n";
                break;
            case "jeskyne":
                statement = "Stojíš v jeskyni, kde se ti podařilo úspěšně porazit lupiče.";
                break;
            case "polni_cesta":
                statement = "Vyšel jsi z lesa na polní cestu, avšak průchod jsi si již úspěšně vybojoval. Už musíš jen doručit dopis do Ketesu.";
                break;
            case "vetesnictvi":
                statement = "Vstoupil jsi do malého obchůdku a za pultem vidíš stát vetešníka.";
                break;
            default:
                statement = "";
                break;
        }
        this.description = statement;
    }
}
