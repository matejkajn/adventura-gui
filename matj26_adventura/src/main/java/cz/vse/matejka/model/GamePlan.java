package cz.vse.matejka.model;
import java.util.*;

/**
 * Třída představující aktuální stav hry. Veškeré informace o stavu hry
 * <i>(mapa lokací, inventář, vlastnosti hlavní postavy, informace o plnění
 * úkolů apod.)</i> by měly být uložené zde v podobě datových atributů.
 * <p>
 * Třída existuje především pro usnadnění potenciální implementace ukládání
 * a načítání hry. Pro uložení rozehrané hry do souboru by mělo stačit uložit
 * údaje z objektu této třídy <i>(např. pomocí serializace objektu)</i>. Pro
 * načtení uložené hry ze souboru by mělo stačit vytvořit objekt této třídy
 * a vhodným způsobem ho předat instanci třídy {@link Game}.
 *
 * @author Michael Kölling
 * @author Luboš Pavlíček
 * @author Jarmila Pavlíčková
 * @author Jan Říha
 * @author Jonáš Matějka
 * @version LS 2020
 *
 * @see <a href="https://java.vse.cz/4it101/AdvSoubory">Postup pro implementaci ukládání a načítání hry na předmětové wiki</a>
 * @see java.io.Serializable
 */
public class GamePlan
{
    private boolean victory = false;
    private Area currentArea;
    private Inventory inventory;
    private EquipmentType equipmentType;
    private Map<String, Area> allAreas;

    /**
     * Konstruktor třídy. Pomocí metody {@link #prepareWorldMap() prepareWorldMap}
     * Vytvoří lokace, postavy, předměty, inventář.
     */
    public GamePlan()
    {
        prepareWorldMap();
    }

    /**
     * Vytvoří lokace, postavy, předměty, inventář.
     * Lokacím přiřadí východy, předměty rozmístí do lokací nebo vloží do inventáře. Postavy vloží do uričitých lokací.
     * Jako výchozí aktuální lokaci následně nastaví hostinec, ve kterém hráč začíná svou cestu.
     */
    private void prepareWorldMap()
    {
        //Vybavení hráče
        Item helma = new Item("latkova_helma", "Látková helma.", 25, 5);
        Item brneni = new Item("latkove_brneni", "Látkové brnění.", 25, 20);
        Item kalhoty = new Item("latkove_kalhoty", "Látkové kalhoty.", 25, 15);
        Item boty = new Item("latkove_boty", "Látkové boty.", 25, 10);
        Item mecZakladni = new Item("lehky_mec", "Lehký ostrý meč.", 25);
        helma.setEquipmentType(equipmentType.HELMA);
        brneni.setEquipmentType(equipmentType.BRNENI);
        kalhoty.setEquipmentType(equipmentType.KALHOTY);
        boty.setEquipmentType(equipmentType.BOTY);
        mecZakladni.setEquipmentType(equipmentType.ZBRAN);        
        inventory = new Inventory();
        inventory.addEquip(helma);
        inventory.addEquip(brneni);
        inventory.addEquip(kalhoty);
        inventory.addEquip(boty);
        inventory.addEquip(mecZakladni);
        // Vytvoříme jednotlivé lokace
        allAreas = new HashMap<>();
        Area hostinec = new Area("hostinec", getAreaDescription("hostinec"));
        Area stratholme = new Area("stratholme", getAreaDescription("stratholme"));
        Area lektvary = new Area("lektvary", getAreaDescription("lektvary"));
        Area hospoda = new Area("hospoda", getAreaDescription("hospoda"));
        Area vetesnictvi = new Area("vetesnictvi", getAreaDescription("vetesnictvi"));        
        Area les = new Area("les",getAreaDescription("les"));
        Area polni_cesta = new Area("polni_cesta", getAreaDescription("polni_cesta"));        
        Area roklina = new Area("roklina",getAreaDescription("roklina"));
        Area jeskyne = new Area("jeskyne", getAreaDescription("jeskyne"));        
        Area ketes = new Area("ketes", getAreaDescription("ketes"));        
        allAreas.put(hostinec.getName(), hostinec);
        allAreas.put(stratholme.getName(), stratholme);
        allAreas.put(lektvary.getName(), lektvary);
        allAreas.put(hospoda.getName(), hospoda);
        allAreas.put(vetesnictvi.getName(), vetesnictvi);
        allAreas.put(les.getName(), les);
        allAreas.put(polni_cesta.getName(), polni_cesta);
        allAreas.put(roklina.getName(), roklina);
        allAreas.put(jeskyne.getName(), jeskyne);
        allAreas.put(ketes.getName(), ketes);        
        // Nastavíme průchody mezi lokacemi (sousední lokace)
        hostinec.addExit(les);
        hostinec.addExit(stratholme);
        hostinec.addExit(roklina);
        stratholme.addExit(les);
        stratholme.addExit(roklina);
        stratholme.addExit(lektvary);
        stratholme.addExit(hospoda);
        stratholme.addExit(vetesnictvi);        
        lektvary.addExit(stratholme);
        lektvary.addExit(hospoda);
        lektvary.addExit(vetesnictvi);        
        hospoda.addExit(stratholme);
        hospoda.addExit(lektvary);
        hospoda.addExit(vetesnictvi);        
        vetesnictvi.addExit(stratholme);
        vetesnictvi.addExit(lektvary);
        vetesnictvi.addExit(hospoda);        
        les.addExit(stratholme);
        les.addExit(polni_cesta);        
        polni_cesta.addExit(les);
        polni_cesta.addExit(ketes);        
        roklina.addExit(stratholme);
        roklina.addExit(jeskyne);        
        jeskyne.addExit(roklina);
        jeskyne.lockArea(true);
        // Vyvoříme předměty do lokací
        Item dopis = new Item("dopis", "Dopis pro krále Arese.");
        Item mesec = new Item("mesec_zlatych", "Měšec plný zlatých mincí");
        Item stul = new Item("stul", "Těžký dubový stůl.", false);
        Item strom = new Item("strom", "Velký dubový strom.", false);
        //Equip, který se v průběhu hry dá vyměnit
        les.addItem(strom);
        vetesnictvi.addItem(stul);
        // Hru začneme v lokaci hostinec a hráči přidáme do inventáře dopis s měšcem zlatýh
        currentArea = hostinec;
        inventory.addItem(dopis);
        inventory.addItem(mesec);        
        // Vytvoření postav
        Person lupic = new Person("lupic", "Co se to opovažuješ lézt ke mně do jeskyně!? Však ty uvidíš!!", true, 20, 25, 100); //utok , armor, hp
        Person templari = new Person("templari", "Podívejte!!! To je ten lovec co byl v hostinici. To on má ten dopis. Rychle ho seberte a jeho zabte!!!", true, 45, 100, 100);
        Person obchodnice = new Person("obchodnice", "\"Potřebuju pomoct. Včera jsem nechala košík s bylinkami venku před obchodem a někdo mi ho ukradl.\n Prý toho lupiče viděli jak utíká s košíkem do jeskyně za roklinou. Přines mi prosím ten košík. Potřebuju ho, abych měla z čeho dělat lektvary.\"", false, "kosik_bylinek");
        Person vetesnik = new Person("vetesnik", "\"Zdravím tě lovče. Ztratil jsem své zlaté jablko někde v lese, pokud mi ho přineseš, štědře se ti odměním.\"", false, "zlate_jablko");
        Person hospodsky = new Person("hospodsky", "\"Dobrý den, co byste si dal pane?\"\nUkážeš na láhev vyzrálého rumu, která stojí na poličce za hospodským.\n\"Tato láhev rumu je unikátní zboží a poslední svého druhu, nebude vůbec levná. Bude vás stát celý měšec zlatých.\"", false, "mesec_zlatych");
        Person zebrak = new Person("zebrak", "Přijdeš k žebrákovi a zeptáš se ho na cestu do Ketesu.\n\"Když mi koupíš v hospodě láhev rumu a přineseš mi jí, tak ti s cestou poradím.\"", false, "rum");
        Person kral = new Person("ares", "\"Strážní, kteří tě přivedli ke mně povídali, že máš pro mě nějaký dopis.... nuže?\"", false, "dopis");        
        lektvary.addPerson(obchodnice);
        vetesnictvi.addPerson(vetesnik);
        jeskyne.addPerson(lupic);
        stratholme.addPerson(zebrak);
        hospoda.addPerson(hospodsky);
        polni_cesta.addPerson(templari);
        ketes.addPerson(kral);
    }

    /**
     * Metoda vrací odkaz na aktuální lokaci, ve které se hráč právě nachází.
     *
     * @return currentArea aktuální lokace
     */
    public Area getCurrentArea()
    {
        return currentArea;
    }

    /**
     * Metoda vrací odkaz na aktuální inventář hráče.
     *
     * @return inventory aktuální inventář
     */
    public Inventory getInventory()
    {
        return inventory;
    }

    /**
     * Metoda vrací součet všech obranných hodnot hráče na základě jeho vybavení.
     *
     * @return aktuální hodnota brnění hráče
     */
    public int getPlayerArmor()
    {

        return inventory.getEquip(EquipmentType.BOTY).getArmor()
        + inventory.getEquip(EquipmentType.HELMA).getArmor()
        + inventory.getEquip(EquipmentType.BRNENI).getArmor()
        + inventory.getEquip(EquipmentType.KALHOTY).getArmor();
    }

    /**
     * Metoda vrací součet hodnot životů hráče na základě jeho vybavení.
     *
     * @return aktuální životy hráče
     */
    public int getPlayerHealth()
    {
        return inventory.getEquip(EquipmentType.BOTY).getHealth()
                + inventory.getEquip(EquipmentType.HELMA).getHealth()
                + inventory.getEquip(EquipmentType.BRNENI).getHealth()
                + inventory.getEquip(EquipmentType.KALHOTY).getHealth();
    }

    /**
     * Metoda vrací hodnotu poškození hráče na základě jeho aktuální zbraně.
     *
     * @return aktuální poškození zbraně, kterou má hráč vybavenou
     */
    public int getPlayerAttack()
    {
        return inventory.getEquip(EquipmentType.ZBRAN).getAttack();
    }

    /**
     * Metoda nastaví aktuální lokaci, používá ji příkaz {@link CommandMove}
     * při přechodu mezi lokacemi.
     *
     * @param area lokace, která bude nastavena jako aktuální
     */
    public void setCurrentArea(Area area)
    {
        currentArea = area;
    }

    /**
     * Metoda vrací popis lokace na základě parametru dané lokace.
     * Cílem bylo zmenšit složitost v metodě prepareWorldMap().
     *
     * @param areaName jméno lokace
     * @return description popis lokace
     */
    public String getAreaDescription(String areaName)
    {
        String description = "";
        switch(areaName) {
            case "hostinec":
                description = "Pivo jsi už dopil, zaplatil a vyrážíš na cestu.";
                break;
            case "stratholme":
                description = "Stojíš na náměstí malého městečka Stratholme a u morového sloupu vidíš sedět žebráka.";
                break;
            case "roklina":
                description = "Na konci rokliny vidíš cestu, která vede někam do neznáma a přímo před sebou vchod do hluboké temné jeskyně.";
                break;
            case "jeskyne":
                description = "Hned jak vstoupíš do jeskyně, všimne si tě zloděj, který na tebe okamžitě zaútočí.";
                break;
            case "les":
                description = "Jsi v temném hlubokém lese. Mezi velkými stromy je jeden, u kterého se na zemi něco třpytí."
                            +  "\nNa konci lesa vidíš polní cestu, kde se kouří z táborového ohně. Pozorně se podíváš a rozpoznáš skupinu templářů."
                            + "\nPolní cesta vede do Ketesu, a proto se budeš muset probojovat. Zkus zvážit jestli nemůžeš sehnat lepší vybavení nebo se vrhni do boje!";
                break;
            case "polni_cesta":
                description = "Jeden z jezdců tě spatří a zakřičí: \"Podívejte chlapi! To je ten lovec, kterému dal zbrojnoš dopis! Zabte ho a dopis mu vemte!\"";
                break;
            case "vetesnictvi":
                description = "Vstoupil jsi do malého obchůdku a za pultem vidíš stát vetešníka.";
                break;
            case "lektvary":
                description = "Vstoupil jsi do obchodu s lektvary. Vidíš starou vyděšenou ženu krčící se u prodejního pultu a pobízející tě ať jdeš k ní.";
                break;
            case "hospoda":
                description = "Vešel jsi do poloprázdné hospody. Za výčepem stojí pyšný výčepní, který leští půllitr a divně na tebe kouká.";
                break;
            case "ketes":
                description = "Přisel jsi k branám obrovského hradu. Strážnému sdělíš oč jde a proč si tu.\nStrážní tě odvedou k jeho výsosti, abys mu dopis osobně předal.\n"
                + "Nyní stojíš před královským trůnem, na kterém sedí samotný král Derionu Ares.";
                break;
            default:
                description = "";
                break;
        }
        return description;
    }

    /**
     * Metoda vrací konkrétní lokaci na základě jejího jména nezávisle na tom, ve které lokaci se hráč nachází.
     *
     * @param name jméno lokace
     * @return konkrétní lokace
     */
    public Area getSpecificArea(String name) 
    {
        return allAreas.get(name);
    }

    /**
     * Metoda nastavuje vítěztví ve prospěch hráče.
     *
     * @param victory hodnota nastavující vítězství či prohru
     */
    public void setVictorious(boolean victory)
    {
        this.victory = victory;
    }

    /**
     * Metoda informuje, zda hráč vyhrál.
     *
     * @return vrací skutěčnost, zda hráč vyhrál
     */
    public boolean isVictorious()
    {
        return victory;
    }

}
