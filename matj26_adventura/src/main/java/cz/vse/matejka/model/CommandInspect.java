package cz.vse.matejka.model;

import java.util.*;

/**
 * Příkaz prozkoumej slouží k prozkoumání předmětu.
 * Prokoumat lze předměty v inventáři nebo v lokaci. Tím se vypíše jejich popis.
 * Určité předměty (v mé hře konkrétně tři) po prozkoumání vygenerují další předměty do lokace.
 * Např. otevření truhly nebo prozkoumání pytle s kořistí.
 * Prozkoumání předmětů také může změnit popis lokace.
 *
 * @author Jan Říha 
 * @author Jonáš Matějka
 * @version LS 2020
 */
public class CommandInspect implements ICommand
{
    private static final String NAME = "prozkoumej";

    private GamePlan plan;

    /**
     * Kosnstruktor příkazu.
     *
     * @param //game odkaz na hru, která má být příkazem ukončena
     */
    public CommandInspect(GamePlan plan)
    {
        this.plan = plan;
    }

     /**
     * Metoda představuje funkci příkazu "prozkoumej".
     * Příkaz vypíše popis věci.
     * V případě, že se jedná o jeden ze speciálních předmětů (strom, truhla, pytel),
     * příkaz vygeneruje nové předměty do lokace nebo změní popis lokace.
     * 
     * @param parameters správná formulace příkazu - jinak se nevykoná
     * @return zpětná vazba hráči po vykonání příkazu
     */
    public String process(String... parameters)
    {
        if (parameters.length == 0) {
            return "Nevím, co mám prozkoumat, musíš zadat název předmětu.";
        } 
        else if (parameters.length > 1) {
            return "Tomu nerozumím, neumím prozkoumat více předmětů současně.";
        }

        String itemName = parameters[0];
        Area area = plan.getCurrentArea();
        Inventory inventory = plan.getInventory();
        //Pokud je v místnosti žijící nepřítel.
        if (area.containsEnemy()) {
            return "Teď jsi v boji! Nemůžeš prozkoumávat věci, musíš se bránit!";
        }

        //Prozkoumávaný předmět může být v aktuální lokaci, ale i v inventáři
        if (inventory.containsItem(itemName)) {
            return inventory.getItem(itemName).getDescription();
        }

        //Pokud není v lokaci
        if (!area.containsItem(itemName)) {
            return "Předmět '" + itemName + "' tady není a nebo ho nelze prozkoumat.";
        }
        Item item = area.getItem(itemName);
        //Pokud byl předmět prozkoumán
        String ret = area.getItem(itemName).getDescription();
        if  (!item.isMoveable() && !item.isInspected()) {
            switch (itemName) {
                case "strom":
                    area.addItem(new Item("zlate_jablko", "Zlaté jablko, komu asi patří?"));
                    area.changeAreaStatement(area.getName());
                    ret = "Přistoupil jsi ke stromu a z listí si vyhrabal předmět 'zlate_jablko'.";
                    area.getItem(itemName).inspect(true);
                    break;
                case "truhla":
                    openChest();
                    ret = "Otevřel jsi truhlu a vybavení, které obsahovala jsi vyndal na stůl. V truhle bylo 'kozene_brneni' a 'kozena_helma'.";
                    area.getItem(itemName).inspect(true);
                    break;
                case "pytel":
                    openSack();
                    ret = "Prohledal jsi lupičovo pytel s kořistí a našel jsi v něm 'kozene_boty' a 'kozene_kalhoty'.";
                    area.getItem(itemName).inspect(true);
                    break;
                default:
                    area.changeAreaStatement(area.getName());
                    //ret = area.getItem(itemName).getDescription();
                    break;
            }
            //return ret;
        }
        return ret;
    }
    
    /**
     * Metoda slouží k přidání předmětů do lokace po prozkoumání truhly.
     * Cílem této metody bylo zjednodušit metodu process.
     * Prozkoumání truhly přidá do lokace nové vybavení.
     *
     */
    private void openChest()
    {
        plan.getCurrentArea().addItem(new Item("kozena_helma", "Kožená helma.", 25, 10));
        plan.getCurrentArea().getItem("kozena_helma").setEquipmentType(EquipmentType.HELMA);
        plan.getCurrentArea().addItem(new Item("kozene_brneni", "Kožené brnění.", 25, 40));
        plan.getCurrentArea().getItem("kozene_brneni").setEquipmentType(EquipmentType.BRNENI);
    }
    
     /**
     * Metoda slouží k přidání předmětů do lokace po prozkoumání pytle.
     * Cílem této metody bylo zjednodušit metodu process.
     * Prozkoumání pytle přidá do lokace nové vybavení.
     *
     */
    private void openSack()
    {
        Item kozene_boty = new Item("kozene_boty", "Kožené boty.", 25, 20);
        kozene_boty.setEquipmentType(EquipmentType.BOTY);
        Item kozene_kalhoty = new Item("kozene_kalhoty", "Kožené kalhoty", 25, 30);
        kozene_kalhoty.setEquipmentType(EquipmentType.KALHOTY);
        plan.getCurrentArea().addItem(kozene_boty);
        plan.getCurrentArea().addItem(kozene_kalhoty);
    }

     /**
     * Metoda vrací název příkazu. Jedná se o slovo, které hráč používá pro vyvolání
     * příkazu, např. 'napoveda', 'konec', 'dej', 'vyhod', 'promluv' apod.
     *
     * @return název příkazu
     */
    public String getName()
    {
        return NAME;
    }
   
}
