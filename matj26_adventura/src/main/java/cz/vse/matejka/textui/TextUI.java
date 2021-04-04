package cz.vse.matejka.textui;



import cz.vse.matejka.model.IGame;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * Třída představující uživatelské rozhraní hry. Třída čte vstup zadaný
 * uživatelem a předává tento řetězec logice hry, následně vypisuje
 * odpověď logiky na konzoli.
 *
 * @author Michael Kölling
 * @author Luboš Pavlíček
 * @author Jarmila Pavlíčková
 * @author Jan Říha
 * @version LS 2020
 */
public class TextUI
{
    private IGame game;

    /**
     * Konstruktor třídy. Vytváří nové textové rozhraní pro hru.
     *
     * @param game instance logiky hry
     */
    public TextUI(IGame game)
    {
        this.game = game;
    }

    /**
     * Hlavní metoda hry. Vypíše úvodní text a pak v cyklu opakuje načtení
     * a zpracování příkazu od hráče dokud hra neskončí <i>(dokud metoda
     * {@link IGame#isGameOver() isGameOver} z logiky hry nevrátí hodnotu
     * {@code true})</i>. Nakonec vypíše text epilogu.
     */
    public void play()
    {
        System.out.println(game.getPrologue() + "\n");

        // Základní cyklus programu - opakovaně se čtou příkazy z konzole
        // a předávají logice hry ke zpracování, dokud hra neskončí

        while (!game.isGameOver()) {
            String line = readLine();
            System.out.println(game.processCommand(line) + "\n");
        }

        System.out.println(game.getEpilogue());
    }
    
    public void play(String fileName)
    {
        System.out.println("Přehrávám příkazy ze souboru: " + fileName + "\n");
        
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            System.out.println(game.getPrologue() + "\n");

            String line = reader.readLine();
            while(line != null && !game.isGameOver()) {
                System.out.println("*** " + line + " ***");
                System.out.println(game.processCommand(line) + "\n");
                
                line = reader.readLine();
            }
    
            System.out.println(game.getEpilogue());
        } catch (FileNotFoundException e) {
            System.out.println("Soubor '" + fileName + "' se nepodařilo otevřít.");
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println("Došlo k chybě při čtení ze souboru.");
            System.out.println(e.getMessage());
        }
    }

    /**
     * Pomocná metoda pro čtení příkazů z konzole.
     *
     * @return řádek textu z konzole
     */
    private String readLine()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.print("> ");
        return scanner.nextLine();
    }

}
