package cz.vse.matejka.model;

/**
 * Třída implementující příkaz pro ukončení hry.
 *
 * @author Jarmila Pavlíčková
 * @author Jan Říha
 * @version LS 2020
 */
public class CommandTerminate implements ICommand
{
    private static final String NAME = "konec";

    private Game game;

    /**
     * Kosnstruktor příkazu.
     *
     * @param game odkaz na hru, která má být příkazem ukončena
     */
    public CommandTerminate(Game game)
    {
        this.game = game;
    }

    /**
     * V případě, že je metoda zavolána bez parametrů <i>(hráč na konzoli zadá
     * pouze slovo {@value NAME})</i>, ukončí hru. Jinak vrátí chybovou zprávu
     * a hra pokračuje.
     *
     * @param parameters parametry příkazu <i>(očekává se prázdné pole)</i>
     * @return informace pro hráče, která se vypíše na konzoli
     */
    @Override
    public String process(String... parameters)
    {
        if (parameters.length > 0) {
            return "Nechápu, co mám ukončit. Příkaz '" + NAME + "' se používá bez parametrů a ukončuje celou hru.";
        }

        game.setGameOver(true);
        return "Hra byla ukončena příkazem '" + NAME + "'.";
    }

    /**
     * Metoda vrací název příkazu tj.&nbsp; slovo {@value NAME}.
     *
     * @return název příkazu
     */
    @Override
    public String getName()
    {
        return NAME;
    }

}
