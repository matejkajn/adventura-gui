package cz.vse.matejka.model;

/**
 * Instance třídy představuje příkaz na předání předmětu postavám ve hře.
 * Dát předmět postavě musí projít několika podmínkami.
 * Musí být v inventáři nebo daná postava musí předmět vyžadovat.
 * Příkaz může také vyhrát hráči hru, jelikož jeho hlavní úkol
 * je předat dopis postavě Ares.
 * 
 * @author  Jonáš Matějka
 * @version LS 2020
 */
public class CommandGive implements ICommand
{
    private static final String NAME = "dej";
    private GamePlan plan;
    
    /**
     * Kosnstruktor příkazu.
     *
     * @param plan aktuální herní plán
     */
    public CommandGive(GamePlan plan)
    {
        this.plan = plan;
    }
    
    /**
     * Metoda představuje funkci příkazu "dej".
     * Příkaz vloží předmět do inventáře osoby, která jej požaduje.
     * Příkaz také vykoná příkazy na základě příběhu.
     * Příkaz může vyhrát hru předáním dopisu Aresovi, jelikož je to hráčův hlavní úkol.
     *
     * @param parameters správná formulace příkazu - jinak se nevykoná
     * @return zpětná vazba hráči po vykonání příkazu
     */
    @Override
    public String process(String... parameters)
    {
        if (parameters.length == 0) {
            return "Nevím komu chceš co dát. Musíš zadat jméno postavy a název předmětu.";
        }
        else if (parameters.length == 1) {
            return "Musíš zadat jméno postavy a název předmětu.";
        }
        else if (parameters.length >= 3) {
            return "Nemůžeš dávat víc věcí.";
        }
        
        String personName = parameters[1];
        String itemName= parameters[0];
        Area area = plan.getCurrentArea();
        
        Inventory inventory = plan.getInventory();
        //Pokud je v místnosti žijící nepřítel.
        if (area.containsEnemy()) {
            return "Teď jsi v boji! Nemůžeš dávat věci, musíš se bránit!";
        }
        
        if (!area.containsPerson(personName)) {
            return "Postava '" + personName + "' tady není.";
        }
        
        Person person = plan.getCurrentArea().getPerson(personName);
        
        if (inventory.containsItem(itemName)) {
            Item item = inventory.getItem(itemName);
            String tradeText = "Dal jsi předmět '" + itemName + "' do inventáře postavy '" + personName + "'.";
            if (person.getWhatHeWants().equals(itemName)) {
                if (person.getName().equals("vetesnik")) {
                    give(person, inventory, item);
                    area.addItem(new Item("truhla", "Prázdná truhla.", false));
                    return tradeText + "\n\"Děkuji ti mockrát! Už jsem se bál, že své zlaté jablko nikdy neuvidím. Za tebou je 'truhla' a v ní je vybavení. Vem si jej jako svou odměnu.\"";
                }
                else if (area.getPerson(personName).getName().equals("obchodnice")) {
                    trade(person, inventory, item, new Item("jed", "Lahvička jedu."));
                    return tradeText + "\n\"Jsi můj zachránce! Za to, že jsi mi pomohl ti daruju toto.\"\nPodává ti předmět 'jed'.\n"
                           + "\"Když si svůj meč potřeš tímto jedem, tvá zbraň se stane velmi silnou a nebezpečnou.\"";
                }
                else if (area.getPerson(personName).getName().equals("hospodsky")) {
                    trade(person, inventory, item, new Item("rum", "Láhev vyzrálého rumu."));
                    return tradeText
                           + "\n\"Tady máte ten rum. Doufám, že se rozhodnete správně, s kým ho vypijete, protože takové zboží už nikdy neokusíte.\"";
                }
                else if (area.getPerson(personName).getName().equals("zebrak")) {
                    give(person, inventory, item);
                    plan.getSpecificArea("roklina").addExit(plan.getSpecificArea("ketes"));
                    plan.getSpecificArea("roklina").changeAreaStatement("roklina");
                    return "\"Děkuji ti za rum. Do Ketesu vedou dvě cesty.\nPrvní je přes les a polní cestu, která vede přímo do ketesu.\n" 
                            + "Druhá cesta je přes roklinu, avšak tato cesta je velmi nebezpečná, protože v roklině na tebe můžou spadnout kameny.\n"
                            + "Cestou přes roklinu máš pouze 20% šanci,že se dostaneš do Ketesu! Tak se radši rozhodni, kterou cestu zvolíš!\"";
                }
                else if (area.getName().equals("ketes") &&  area.getPerson(personName).getName().equals("ares"))
                {
                    plan.setVictorious(true);
                }
            }
            else {
                return "\"Promiň, ale předmět '" + itemName + "' od tebe nechci, nevím co bych s ním dělal.\"";
            }
            give(person, inventory, item);
            return tradeText;
        }
        return "Předmět '" + itemName + "' nemáš ve svém inventáři.";
    }
    
    /**
     * Metoda vytvořena pro zjednodušení vložení předmětu postavě do inventáře.
     * Cílem této metody bylo zjednodušit metodu process.
     *
     * @param person postava, které předmět dáváme
     * @param inventory inventář hráče
     * @param item předmět, který dáváme
     */
    private void give(Person person, Inventory inventory, Item item)
    {
        person.addItem(item);
        inventory.removeItem(item);
    }
    
    /**
     * Metoda vytvořena pro zjednodušení výměny předmětů s postavou.
     * Cílem této metody bylo zjednodušit metodu process.
     *
     * @param person postava, které předmět dáváme
     * @param inventory inventář hráče
     * @param item1 předmět, který dáváme
     * @param item2 předmět, který dostaneme
     */
    private void trade(Person person, Inventory inventory, Item item1, Item item2)
    {
        person.addItem(item1);
        inventory.removeItem(item1);
        inventory.addItem(item2);
    }
    
     /**
     * Metoda vrací název příkazu. Jedná se o slovo, které hráč používá pro vyvolání
     * příkazu, např. 'napoveda', 'konec', 'dej', 'vyhod', 'promluv' apod.
     *
     * @return název příkazu
     */
    @Override
    public String getName()
    {
        return NAME;
    }
}
