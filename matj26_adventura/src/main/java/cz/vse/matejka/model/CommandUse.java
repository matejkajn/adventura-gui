package cz.vse.matejka.model;

/**
 * Speciální příkaz pro použití jedu na meč a vylepšení tak předmětu.
 * Tento příkaz má speciální využití pro vylepšení poškození zbraně.
 * 
 *
 * @author Jonáš Matějka
 * @version LS 2020
 */

public class CommandUse implements ICommand
{
    private static final String NAME = "pouzit";
    private GamePlan plan;
    
    /**
     * Kosnstruktor příkazu.
     * 
     * @param plan aktuální herní plán
     */
    public CommandUse(GamePlan plan)
    {
        this.plan = plan;
    }
    
     /**
     * Metoda představuje funkci příkazu "pouzit".
     * Příkaz vylepší aktuální zbraň hráče.
     * Vylepšení proběhne nahrazením předmětu za podobný s lepším poškozením.
     * 
     * @param parameters správná formulace příkazu - jinak se nevykoná
     * @return zpětná vazba hráči po vykonání příkazu
     */
    @Override
    public String process(String... parameters)
    {
        if (parameters.length == 0) {
            return "Nevím co mám použít. Musíš zadat název předmětu.";
        }
        
        if (parameters.length > 1) {
            return "Musíš zadat název předmětu.";
        }
        
        String itemName = parameters[0];
        Inventory inventory = plan.getInventory();
        if (!inventory.containsItem(itemName)) {
            return "Předmět nemáš v inventáři";
        }
        
        Item item = inventory.getItem(itemName);
        if (item.getName().equals("jed")) {
            Item otravenyMec = new Item("otraveny_mec", "Otrávený lehký ostrý meč.", 45);
            otravenyMec.setEquipmentType(EquipmentType.ZBRAN);
            inventory.changeEquip(otravenyMec);
            inventory.removeItem(item);
            return "Vylepšil sis svou zbraň jedem na '" + otravenyMec.getName() + "'.";
        }
        
        return "Tento předmět nemůžeš nějak použít.";
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
