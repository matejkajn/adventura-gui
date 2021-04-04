package cz.vse.matejka.model;

/**
 * Tento příkaz slouží k vypsání aktuálního obsahu inventáře a hráčovo vybavení.
 * Příkaz také vypisuje hodnoty brnění, životů nebo poškození hráče.
 *
 * @author Jonáš Matějka
 * @version LS 2020
 */
public class CommandInventory implements ICommand
{
    private static final String NAME = "inventar";
    
    private GamePlan plan;
    
     /**
     * Kosnstruktor příkazu.
     * 
     * @param plan aktuální herní plán
     */
    public CommandInventory(GamePlan plan)
    {
        this.plan = plan;
    }
    
     /**
     * Metoda představuje funkci příkazu "inventar".
     * Příkaz vypíše veškerý obsah inventáře a výbavy hráče.
     * Dále informuje o obranných a útočných hodnotách hráče.
     * 
     * @param parameters správná formulace příkazu - jinak se nevykoná
     * @return zpětná vazba hráči po vykonání příkazu
     */
    @Override
    public String process(String... parameters)
    {
        if (plan.getInventory().isInventoryEmpty()) {
            return "Tvůj inventář je prázdný";
        }
        return plan.getInventory().getInventoryItems();
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
