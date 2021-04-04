package cz.vse.matejka.model;

/**
 * Příkaz představuje vedení dialogu s postavami.
 * Hráč může mluvit jen s přátelskými postavami. 
 * Dialogy postav se mění na základě toho, zda hráč vykonal nějakou akci,
 * která podnítila změnu dialogu.
 * Např. postava chce, aby jí hráč něco dal a ve chvili, kdy předmět
 * obdrží, tak se změní její dialog, aby v příběhu dával smysl.
 *
 * @author  Jonáš Matějka
 * @version LS 2020
 */
public class CommandTalk implements ICommand
{
    private static final String NAME = "promluv";
    
    private GamePlan plan;
    
     /**
     * Kosnstruktor příkazu.
     * 
     * @param plan aktuální herní plán
     */
    public CommandTalk(GamePlan plan)
    {
        this.plan = plan;
    }
    
     /**
     * Metoda představuje funkci příkazu "promluv".
     * Příkaz slouží pro vedení dialogu s přátelskými postavami.
     * Dialogy postav závisí na činech hráče.
     * Postavy mění dialogy na základě toho, zda hráč vykonal nějakou akci.
     * 
     * @param parameters správná formulace příkazu - jinak se nevykoná
     * @return zpětná vazba hráči po vykonání příkazu
     */
    public String process(String... parameters)
    {
        if (parameters.length == 0) {
            return "Nevím s kým mám mluvit, musíš zadat jméno postavy.";
        }
        else if (parameters.length > 1) {
            return "Tomu nerozumím, neumím mluvit s více postavami současně.";
        }
        
        String personName = parameters[0];
        Area area = plan.getCurrentArea();
        //Pokud není v lokaci
        if (!area.containsPerson(personName)) {
            return "Postava '" + personName + "' tady není.";
        }
        
        Person person = area.getPerson(personName);
        if (person.isEnemy()) {
            return "S '" + personName + "' si necheš povídat, když ti jde po krku. Musíš s ní bojovat.";
        }
        else {
            if (person.getName().equals("obchodnice")) {
                if (plan.getSpecificArea("jeskyne").isAreaLocked()) {
                    plan.getSpecificArea("jeskyne").lockArea(false);
                }
                else if (person.containsItem("kosik_bylinek")) {
                    return "\"Za tvou pomoc ti děkuji, ale odměnu jsem ti už dala.\"";
                }
            }
            else if (person.getName().equals("hospodsky") && person.containsItem("mesec_zlatych")) {
                return "\"Nemám vám co nabídnout. Můj poslední rum jsem prodal vám a pivo už nám došlo.\"";
            }
            else if (person.getName().equals("zebrak") && person.containsItem("rum")) {
                return "\"Už jsi se rozhodl, kterou cestu zvolíš?.\nPrvní je přes les a polní cestu, která vede přímo do Ketesu.\n" 
                        + "Druhá cesta je přes roklinu, avšak tato cesta je velmi nebezpečná, protože v roklině na tebe můžou spadnout kameny.\n"
                        + "Pamatuj si, že cestou přes roklinu máš pouze 20% šanci, že se dostaneš do cíle!!!\"";
            }
            else if (person.getName().equals("vetesnik") && person.containsItem("zlate_jablko")) {
                return "\"Vyzvedl jsi si už svou odměnu? Že ne? Za tebou je 'truhla' a v ní je vybavení. Prozkoumej ji a vybavení si vem jako svou odměnu.\"";
            }
        }
        return area.getPerson(personName).getDialog();
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
