package cz.vse.matejka.model;

/**
 * Rozhraní definující metody nutné pro implementaci herního příkazu. Pro každý
 * příkaz, který je možné ve hře použít, existuje třída zajišťující jeho obsluhu.
 * Třída musí implementovat metody definované tímto rozhraním.
 *
 * @author Jarmila Pavlíčková
 * @author Jan Říha
 * @version LS 2020
 */
public interface ICommand
{
    /**
     * Metoda zajišťující provedení herního příkazu. Počet parametrů <i>(očekávaných
     * prvků v poli)</i> je závislý na konkrétním příkazu, např. příkazy 'napoveda'
     * a 'konec' žádné parametry nemají, příkaz pro přesun mezi lokacemi patrně bude
     * mít jeden parametr <i>(cílovou lokaci)</i>, příkazy pro obchodování, používání
     * nebo kombinování předmětů mohou mít parametrů více.
     * <p>
     * Metoda musí zkontrolovat parametry <i>(počet, korektnost)</i>, provést změny
     * ve stavu hry a následně vrátit text, který se hráči vypíše na konzoli.
     *
     * @param parameters parametry příkazu <i>(počet závisí na konkrétním příkazu)</i>
     * @return výsledek zpracování <i>(informace pro hráče, které se vypíšou na konzoli)</i>
     */
    String process(String... parameters);

    /**
     * Metoda vrací název příkazu. Jedná se o slovo, které hráč používá pro vyvolání
     * příkazu, např. 'napoveda', 'konec' apod.
     *
     * @return název příkazu
     */
    String getName();

}
