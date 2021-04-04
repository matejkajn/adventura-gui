package cz.vse.matejka.model;

import java.util.Random;

/**
 * Třída implementující příkaz pro pohyb mezi herními lokacemi.
 *
 * @author Jarmila Pavlíčková
 * @author Luboš Pavlíček
 * @author Jan Říha
 * @author Jonáš Matějka
 * @version LS 2020
 */
public class CommandMove implements ICommand
{
    private static final String NAME = "jdi";

    private GamePlan plan;
    private Game game;

    /**
     * Kosnstruktor příkazu.
     *
     * @param plan odkaz na herní plán s aktuálním stavem hry
     */
    public CommandMove(GamePlan plan, Game game)
    {
        this.plan = plan;
        this.game = game;
    }

    /**
     * Metoda se pokusí přesunout hráče do jiné lokace. Nejprve zkontroluje počet
     * parametrů. Pokud nebyl zadán žádný parametr <i>(tj. neznáme cíl cesty)</i>,
     * nebo bylo zadáno dva a více parametrů <i>(tj. hráč chce jít na více míst
     * současně)</i>, vrátí chybové hlášení. Pokud byl zadán právě jeden parametr,
     * zkontroluje, zda s aktuální lokací sousedí jiná lokace s daným názvem <i>(tj.
     * zda z aktuální lokace lze jít do požadovaného cíle)</i>. Pokud ne, vrátí
     * chybové hlášení. Pokud všechny kontroly proběhnou v pořádku, provede přesun
     * hráče do cílové lokace a vrátí její popis.
     *
     * @param parameters parametry příkazu <i>(očekává se pole s jedním prvkem)</i>
     * @return informace pro hráče, které se vypíšou na konzoli
     */
    @Override 
    public String process(String... parameters)
    {
        if (parameters.length == 0) {
            return "Nechápu, kam mám jít. Musíš mi zadat nějaký cíl.";  // Pokud hráč nezadá žádný parametr (cíl cesty)
        } else if (parameters.length > 1) {
            return "Nechápu, co po mě chceš. Neumím se 'rozdvojit' a jít na více míst současně.";  // Pokud hráč zadá více parametrů
        }

        // Název cílové lokace si uložíme do pomocné proměnné
        String exitName = parameters[0];

        // Zkusíme získat odkaz na cílovou lokaci
        Area exitArea = plan.getCurrentArea().getExitArea(exitName);
        //Pokud je v místnosti žijící nepřítel.
        if (plan.getCurrentArea().containsEnemy()) {
            return "Teď jsi v boji! Nemůžeš utíkat, musíš se bránit!";
        }

        if (exitArea == null) {
            return "Tam se ale odsud jít nedá!";  // Pokud hráč zadal název lokace, která nesousedí s aktuální
        }

        if (exitArea.isAreaLocked()) {
            return getAreaStatement(exitArea);
        }

        if (plan.getCurrentArea().getName().equals("roklina") && exitArea.getName().equals("ketes")) {
            Random rnd = new Random();
            int chance = rnd.nextInt(101);
            if (chance < 21) {
                plan.setCurrentArea(exitArea);
                return "Měl jsi štěstí a cestou do Ketesu tě žádný balvan nezavalil.\n" + getAreaStatement(exitArea);
            }
            else {
                game.setGameOver(true);
                return "Měl jsi smůlu a cestou do Ketesu tě zavalil balvan. Měl jsi dát na žebráka a nepokoušet svůj osud.\n";
            }
        }

        // Změníme aktuální lokaci (přesuneme hráče) a vrátíme popis nové lokace
        plan.setCurrentArea(exitArea);
        return getAreaStatement(exitArea);
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

    /**
     * Metoda vrací popis lokace v případě, že jsme v lokaci udělali nějakou změnu.
     * Např. jsme v lokaci prozkoumali strom, tak se změní popis lokace.
     *
     * @param exitArea lokace, do které jdeme
     * @return popis lokace
     */
    public String getAreaStatement(Area exitArea) {
        String description = "";

        switch(exitArea.getName())
        {
            case "jeskyne":
            if (exitArea.isAreaLocked()) {
                description = "Jeskyně tě láká svou tajemností, avšak svůj úkol máš zadaný. Proč bys zbytečně lezl do strašidelné jeskyně?";
            }
            else {
                if (exitArea.containsPerson("lupic")) {
                    description = "Před jeskyní vidíš kousky bylinek, které nejspíš vypadli z košíku s bylinkami, a proto se jdeš podívat do jeskyně.\n" + exitArea.getFullDescription();
                }
                else {
                    description = exitArea.getFullDescription();
                }
            }
            break;
            default:
            description = exitArea.getFullDescription();
            break;
        }

        return description;
    }
}
