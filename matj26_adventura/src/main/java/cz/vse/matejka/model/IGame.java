package cz.vse.matejka.model;

/**
 * Rozhraní definující metody nutné pro implementaci hry. Základní třída představující
 * logiku hry musí implementovat metody definované tímto rozhraním, je na ně navázané
 * uživatelské rozhraní.
 *
 * @author Michael Kölling
 * @author Luboš Pavlíček
 * @author Jarmila Pavlíčková
 * @author Jan Říha
 * @version LS 2020
 */
public interface IGame
{
    /**
     * Metoda vrací úvodní text pro hráče, který se vypíše na konzoli ihned po zahájení
     * hry.
     *
     * @return úvodní text
     */
    String getPrologue();

    /**
     * Metoda vrací závěrečný text pro hráče, který se vypíše na konzoli po ukončení
     * hry. Metoda se zavolá pro všechna možná ukončení hry <i>(hráč vyhrál, hráč
     * prohrál, hráč ukončil hru příkazem 'konec')</i>. Tyto stavy je vhodné v metodě
     * rozlišit.
     *
     * @return závěrečný text
     */
    String getEpilogue();

    /**
     * Metoda vrací informaci, zda hra již skončila <i>(je jedno, jestli výhrou,
     * prohrou nebo příkazem 'konec')</i>.
     *
     * @return {@code true}, pokud hra již skončila; jinak {@code false}
     */
    boolean isGameOver();

    /**
     * Metoda zpracuje herní příkaz <i>(jeden řádek textu zadaný na konzoli)</i>.
     * Řetězec uvedený jako parametr se rozdělí na slova. První slovo je považováno
     * za název příkazu, další slova za jeho parametry.
     * <p>
     * Metoda nejprve ověří, zda první slovo odpovídá hernímu příkazu <i>(např.
     * 'napoveda', 'konec', 'jdi' apod.)</i>. Pokud ano, spustí obsluhu tohoto
     * příkazu a zbývající slova předá jako parametry.
     *
     * @param line text, který hráč zadal na konzoli jako příkaz pro hru
     * @return výsledek zpracování <i>(informace pro hráče, které se vypíšou na konzoli)</i>
     */
    String processCommand(String line);

    /**
     * Metoda vrací odkaz na herní plán <i>(objekt třídy {@link GamePlan} udržující
     * aktuální stav hry)</i>. Využívá se především v automatizovaných testech.
     *
     * @return herní plán
     */
    GamePlan getGamePlan();

}
