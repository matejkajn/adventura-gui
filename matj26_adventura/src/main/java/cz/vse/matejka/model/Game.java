package cz.vse.matejka.model;

/**
 * Hlavní třída logiky aplikace. Třída vytváří instanci třídy {@link GamePlan},
 * která inicializuje lokace hry, a vytváří seznam platných příkazů a instance
 * tříd provádějících jednotlivé příkazy.
 *
 * Během hry třída vypisuje uvítací a ukončovací texty a vyhodnocuje jednotlivé
 * příkazy zadané uživatelem.
 *
 * @author Michael Kölling
 * @author Luboš Pavlíček
 * @author Jarmila Pavlíčková
 * @author Jan Říha
 * @author Jonáš Matějka
 * @version LS 2020
 */
public class Game implements IGame
{
    private ListOfCommands listOfCommands;
    private GamePlan gamePlan;
    private boolean gameOver;

    /**
     * Konstruktor třídy. Vytvoří hru, inicializuje herní plán udržující
     * aktuální stav hry a seznam platných příkazů.
     */
    public Game()
    {
        gameOver = false;
        gamePlan = new GamePlan();
        listOfCommands = new ListOfCommands();

        listOfCommands.addCommand(new CommandHelp(listOfCommands));
        listOfCommands.addCommand(new CommandMove(gamePlan, this));
        listOfCommands.addCommand(new CommandPick(gamePlan));
        listOfCommands.addCommand(new CommandDrop(gamePlan));
        listOfCommands.addCommand(new CommandInventory(gamePlan));
        listOfCommands.addCommand(new CommandEquip(gamePlan));
        listOfCommands.addCommand(new CommandInspect(gamePlan));
        listOfCommands.addCommand(new CommandGive(gamePlan));
        listOfCommands.addCommand(new CommandAttack(gamePlan, this));
        listOfCommands.addCommand(new CommandTalk(gamePlan));
        listOfCommands.addCommand(new CommandUse(gamePlan));
        listOfCommands.addCommand(new CommandTerminate(this));
    }


    /**
     * Metoda vrací úvod do příběhu na začátku hry.
     *
     * @return úvod do příběhu hry
     */
    @Override
    public String getPrologue()
    {
        return "Vítejte ve hře Dopis pro Krále!\n"
                + "Jsi nájemný lovec jménem Halt. Nájemní lovci jsou proslulí tím, že zabijejí monstra, která sužují\n"
                + "města a vesnice. Jsou spolehliví, diskrétní a neodmítají žádnou práci. Sedíš v hostinci,\n"
                + "do kterého běží posel s dopisem, pronásledován skupinou templářských jezdců s úkolem mu dopis\n"
                + "vzít a odvést ho ke svému vůdci Balnazarovi. Posel ví, že jeho záchrana je marná, avšak\n"
                + "dopisu se templáři nesmí zmocnit. Vřítí se do hostince a porozhlédne se. V okamžiku, kdy tě\n"
                + "spatří se vydá směrem k tobě, protože pozná, že jsi nájemný lovec a také si je vědom kvalit\n"
                + "známých nájemných lovců. Sedne si k tobě a povídá:\n"
                + "\n"
                + "\"Potřebuji tvou pomoc! Pronásleduje mě skupina templářských jezdců, která mi chce vzít tento dopis.\"\n"
                + "\n"
                + "Podává ti dopis a měšec plný zlatých.\n"
                + "\n"
                + "\"Já ten dopis nezvládnu předat, protože jezdci jsou rychlejší. Vezmi si ho a doruč ho ty.\n"
                + "Takto templáře zmatu a dám ti čas. Na doručení tohoto dopisu závisí budoucnost našeho i sousedního\n"
                + "království. V sousedním království totiž onemocněl král a vlády se ujala templářská sekta, která chce\n"
                + "převzít vládu nad celým kontinentem. Dopis musíš doručit přímo do ruky derionskému králi Aresovi!\n"
                + "Nikdo ti dopis nesmí vzít a s nikým o něm nemluv!\"\n"
                + "\n"
                + "Schováváš si dopis s měšcem do kapsy a najednou uslyšíš venku koňský dupot.\n"
                + "Než se stačíš rozkoukat, vrthnou do hostince jezdci a posel jim uteče oknem.\n"
                + "Jezdci vyběhnou ven na své koně a pokračují v pronásledování bez povšimnutí, že si dopis převzal.\n"
                + "Tvým úkolem je tedy doručit dopis králi Aresovi, který sídlí v hlavním městě Derionu Ketesu.\n"
                + "***********************************************************************************************************************\n"
                + gamePlan.getCurrentArea().getFullDescription();
                
    }

    /**
     * Metoda vrací závěrečný text.
     * Závěrečný text je závislý na tom, zda hráč vyhrál či prohrál.
     *
     * @return závěrečný text
     */
    @Override
    public String getEpilogue()
    {
        String epilogue = "PROHRÁL JSI!!!\nCestu do Ketesu jsi nezvládl.\nPro novou hru využij menu vlevo nahoře.";

        if (gamePlan.isVictorious()) {
            epilogue = "VYHRÁL JSI!!!\nPodařilo se ti doručit dopis králi Aresovi a zachránit tak spousty životů.\nPro novou hru využij menu vlevo nahoře.";
        }
        
        return epilogue;
    }

    /**
     * Metoda vrací informaci o konci hry.
     *
     * @return skutečnost, zda hra skončila
     */
    @Override
    public boolean isGameOver()
    {
        return gameOver;
    }

    /**
     * Metoda zpracovává zadaný příkaz.
     *
     * @param line zadaný příkaz
     * @return výsledek daného příkazu
     */
    @Override
    public String processCommand(String line)
    {
        String[] words = line.split("[ \t]+");

        String cmdName = words[0];
        String[] cmdParameters = new String[words.length - 1];

        for (int i = 0; i < cmdParameters.length; i++) {
            cmdParameters[i] = words[i + 1];
        }

        String result = null;
        if (listOfCommands.checkCommand(cmdName)) {
            ICommand command = listOfCommands.getCommand(cmdName);
            result = command.process(cmdParameters);
        } else {
            result = "Nechápu, co po mně chceš. Tento příkaz neznám.";
        }

        if (gamePlan.isVictorious()) {
            gameOver = true;
        }

        return result;
    }
    
    
    /**
     * Metoda vrací aktuální herní plán.
     *
     * @return gamePlan aktuální herní plán
     */
    @Override
    public GamePlan getGamePlan()
    {
        return gamePlan;
    }

    /**
     * Metoda nastaví příznak indikující, že nastal konec hry. Metodu
     * využívá třída {@link CommandTerminate}, mohou ji ale použít
     * i další implementace rozhraní {@link ICommand}.
     *
     * @param gameOver příznak indikující, zda hra již skončila
     */
    void setGameOver(boolean gameOver)
    {
        this.gameOver = gameOver;
    }

}
