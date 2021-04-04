package cz.vse.matejka.model;

/**
 * Tento příkaz slouží k výměně výbavy. Inventář je rozdělen na 2 části. První jsou věci a 
 * druhá je vybavení. Druhá část (vybavení) nebude nikdy prázdná. Vždy bude zaplněno všemi
 * předměty. Předměty se budou pouze vyměňovat.
 * 
 * @author Jonáš Matějka
 * @version LS 2020
 */
public class CommandEquip implements ICommand
{
    private static final String NAME = "vybavit";
    
    private GamePlan plan;
    
    /**
     * Kosnstruktor příkazu.
     *
     * @param plan aktuální herní plán
     */
    public CommandEquip(GamePlan plan)
    {
        this.plan = plan;
    }
    
    /**
     * Metoda představuje funkci příkazu "vybavit".
     * Příkaz vybaví hráče lepším vybavením.
     *
     * @param parameters správná formulace příkazu - jinak se nevykoná
     * @return zpětná vazba hráči po vykonání příkazu
     */
    @Override
    public String  process(String... parameters)
    {
        if (parameters.length == 0) {
            return "Nevím, kterou věc si mám vybavit, musíš zadat název předmětu.";
        }
        
        if (parameters.length > 1) {
            return "Tomu nerozumím, neumím se vybavit více předměty současně.";
        }
        
        //helma_kuze
        String itemName = parameters[0];
        Area area = plan.getCurrentArea();
        Item item = area.getItem(itemName);
        Inventory inventory = plan.getInventory();
        
        //Pokud je v místnosti žijící nepřítel.
        if (area.containsEnemy()) {
            return "Teď jsi v boji! Nemůžeš se vybavovat lepším vybavením, musíš se bránit!";
        }
        
        if (!area.containsItem(itemName)) {
            return "Předmět '" + itemName + "' tady není.";
        }
        
        //Metoda porovná zda se jedná o věc z vybavení
        if (!area.isEquip(itemName)) {
            return "Tento předmět není vybavení.";
        }
        
        //Porovnat zda to je lepší 
        if ((item.getArmor() < inventory.getEquip(item.getEquipmentType()).getArmor()) || (item.getAttack() < inventory.getEquip(item.getEquipmentType()).getAttack())) {
            return "Předmět, který máš momentálně vybavený je lepší, a proto nemá smysl si ho měnit za horší.";
        }
        
        inventory.changeEquip(item);
        area.removeItem(itemName);
        return "Vybavil jsi se lepším předmětem '" + itemName + "'.";
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
