package cz.vse.matejka.model;

/**
 * Příkaz seber vloží do inventáře věc, která je v parametru.
 * Věc musí být schopna přenosu. Musí být také v aktuální lokalitě a musí se vejít do inventáře.
 * 
 * @author Jan Říha
 * @author Jonáš Matějka
 * @version LS 2020
 */

public class CommandPick implements ICommand
{
    private static final String NAME = "seber";
    
    private GamePlan plan;
    
    /**
     * Kosnstruktor příkazu.
     * 
     * @param plan aktuální herní plán
     */
    public CommandPick(GamePlan plan)
    {
        this.plan = plan;
    }
    
    /**
     * Metoda představuje funkci příkazu "seber".
     * Příkaz vloží předmět do inventáře hráče.
     * Předmět musí být v aktuální lokaci a musí se vejít do inventáře.
     * Předmět také musí být přenosu schopný.
     * 
     * @param parameters správná formulace příkazu - jinak se nevykoná
     * @return zpětná vazba hráči po vykonání příkazu
     */
    @Override
    public String process(String... parameters)
    {
        if (parameters.length == 0) {
            return "Nevím, co mám sebrat, musíš zadat název předmětu.";
        }
        
        if (parameters.length > 1) {
            return "Tomu nerozumím, neumím sebrat více předmětů současně.";
        }
        
        String itemName = parameters[0];
        Area area = plan.getCurrentArea();
        //Pokud je v místnosti žijící nepřítel.
        if (area.containsEnemy()) {
            return "Teď jsi v boji! Nemůžeš sbírat věci, musíš se bránit!";
        }
        
        if (!area.containsItem(itemName)) {
            return "Předmět '" + itemName + "' tady není.";
        }
        
        Item item = area.getItem(itemName);
        
        if (area.isEquip(itemName)) {
            return "Předmět '" + itemName + "' nemůžeš sebrat. Musíš se jím vybavit.";
        }
        
        if (!item.isMoveable()) {
            return "Předmět '" + itemName + "' neuneseš.";
        }
        
        Inventory inventory = plan.getInventory();
        if (!inventory.checkInventorySpace()) {
            return "Tvůj inventář je plný, předmět '" + itemName + "' nebylo možné sebrat.";
        }
        
        area.removeItem(itemName);
        inventory.addItem(item);
        return "Sebral jsi předmět '" + itemName + "' a uložil jsi ho do inventáře.";
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
