package cz.vse.matejka.model;

/**
 * Tato třída představuje příkaz na vyhození věci z inventáře.
 * Z inventáře lze vyhodit pouze věci, které v něm jsou.
 * Hráč nemůže vyhodi z inventáře vybavení.
 *
 * @author Jonáš Matějka
 * @version LS 2020
 */
public class CommandDrop implements ICommand
{
    private static final String NAME = "vyhod";
    
    private GamePlan plan;
    
    /**
     * Kosnstruktor příkazu.
     *
     * @param plan aktuální herní plán
     */
    public CommandDrop(GamePlan plan)
    {
        this.plan = plan;
    }
    
    /**
     * Metoda představuje funkci příkazu "vyhod".
     * Příkaz vyhodí předmět z ivnentáře.
     * 
     *
     * @param parameters správná formulace příkazu - jinak se nevykoná
     * @return zpětná vazba hráči po vykonání příkazu
     */
    @Override
    public String process(String... parameters)
    {
        if (parameters.length == 0) {
            return "Nevím, co mám vyhodit, musíš zadat název předmětu.";
        }
        
        if (parameters.length > 1) {
            return "Tomu nerozumím, neumím vyhodit více předmětů současně.";
        }
        
        String itemName = parameters[0];
        Area area = plan.getCurrentArea();
        Inventory inventory = plan.getInventory();
        Item item = plan.getInventory().getItem(itemName);
        //Pokud je v místnosti žijící nepřítel.
        if (area.containsEnemy()) {
            return "Teď jsi v boji! Nemůžeš vyhazovat věci z inventáře, musíš se bránit!";
        }
        
        if (inventory.isInventoryEmpty()) {
            return "Inventář je prázdný, není z něj co vyhazovat.";
        }
        
        if (!inventory.containsItem(itemName)) {
            return "Předmět '" + itemName + "' v inventáři není.";
        }
        
        area.addItem(item);
        inventory.removeItem(item);
        return "Vyhodil jsi z inventáře '" + itemName + "'.";
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
