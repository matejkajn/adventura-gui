package cz.vse.matejka.model;

/**
 * Třída implementující příkaz pro zobrazení nápovědy ke hře.
 *
 * @author Jarmila Pavlíčková
 * @author Luboš Pavlíček
 * @author Jan Říha
 * @author Jonáš Matějka
 * @version LS 2020
 */
public class CommandHelp implements ICommand
{
    private static final String NAME = "napoveda";

    private ListOfCommands listOfCommands;

    /**
     * Konstruktor příkazu.
     *
     * @param listOfCommands odkaz na seznam příkazů, které je možné ve hře použít
     */
    public CommandHelp(ListOfCommands listOfCommands)
    {
        this.listOfCommands = listOfCommands;
    }

    /**
     * Metoda vrací obecnou nápovědu ke hře. Vypisuje vcelku primitivní
     * zprávu o herním příběhu a seznam dostupných příkazů, které může hráč
     * používat.
     *
     * @param parameters správná formulace příkazu - jinak se nevykoná
     * @return zpětná vazba hráči po vykonání příkazu
     */
    @Override
    public String process(String... parameters)
    {
        return  "Tvým úkolem je tedy doručit dopis králi Aresovi, který sídlí v hlavním městě Derionu Ketesu.\n"
                + "Dopis nesmíš ztratit ani při cestě zemřít.\n"
                + "Hru lze vyhrát pouze doručením dopisu!\n\n"
                + "Ve hře můžeš používat tyto příkazy:\n"
                + listOfCommands.getNames();
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
