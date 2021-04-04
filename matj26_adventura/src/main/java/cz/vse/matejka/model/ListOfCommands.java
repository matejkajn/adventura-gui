package cz.vse.matejka.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Třída představuje seznam přípustných příkazů, které lze ve hře používat.
 * Používá se pro rozpoznávání příkazů dle jejich názvů a vrácení odkazu
 * na instanci třídy implementující konkrétní příkaz. Každý nový příkaz
 * <i>(instance implementující rozhraní {@link ICommand})</i> se musí
 * do seznamu přidat pomocí metody {@link #addCommand(ICommand) addCommand}.
 *
 * @author Michael Kölling
 * @author Luboš Pavlíček
 * @author Jarmila Pavlíčková
 * @author Jan Říha
 * @author Jonáš Matějka
 * @version LS 2020
 */
public class ListOfCommands
{
    private  Map<String, ICommand> commands;  // Mapa pro uložení přípustných příkazů

    /**
     * Konstruktor třídy.
     */
    public ListOfCommands()
    {
        this.commands = new HashMap<>();
    }

    /**
     * Metoda vkládá nový příkaz do seznamu.
     *
     * @param command instance třídy implementující rozhraní {@link ICommand}, která představuje herní příkaz
     */
    public void addCommand(ICommand command)
    {
        commands.put(command.getName(), command);
    }

    /**
     * Metoda vrací odkaz na instanci třídy implementující příkaz s daným názvem.
     *
     * @param name název příkazu, který chce hráč zavolat
     * @return instance třídy představující implementaci daného příkazu; {@code null}, pokud takový příkaz neexistuje
     */
    public ICommand getCommand(String name)
    {
        return commands.get(name);
    }

    /**
     * Metoda zkontroluje, zda je možné ve hře použít příkaz se zadaným názvem.
     *
     * @param name název příkazu
     * @return {@code true}, pokud je možné příkaz ve hře použít; jinak {@code false}
     */
    public boolean checkCommand(String name)
    {
        return commands.containsKey(name);
    }

    /**
     * Metoda vrací seznam názvů všech přípustných příkazů ve hře.
     * Jednotlivé příkazy jsou odděleny mezerou.
     *
     * @return seznam názvů přípustných příkazů
     */
    public String getNames()
    {
        String list = "";
        for (String commandName : commands.keySet()) {
            list += commandName + "     ";
        }
        return list;
    }

}
