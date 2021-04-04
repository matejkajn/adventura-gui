package cz.vse.matejka.model;

/**
 * Třída představuje příkaz zaútoč. Příkaz slouží k přoběhnutí
 * boje mezi hráčem a nepřátelskou postavou v lokaci.
 * Ve hře se hráč setká se dvěma nepřáteli.
 * První nepřítel je lupič, kterého hráč porazí bez lepšího vybavení,
 * avšak druhý nepřítel je těžší. Na toho hráč potřebuje nejlepší možnou výbavu
 * ve hře. Toto má nutit hráče hrát hru pořádně.
 * Nepřítel může hráče zabít, tím však hráč prohrává a hra končí.
 *
 * @author  Jonáš Matějka
 * @version LS 2020
 */
public class CommandAttack implements ICommand
{
    private static final String NAME = "zautoc";
    private GamePlan plan;
    private Game game;
    
     /**
     * Kosnstruktor příkazu.
     * 
     * @param plan aktuální herní plán
     * @param game aktuální stav hry
     */
    public CommandAttack(GamePlan plan, Game game)
    {
        this.plan = plan;
        this.game = game;
    }
    
     /**
     * Metoda představuje funkci příkazu "zautoc".
     * Příkaz spustí souboj mezi hráčem a nepřítelem.
     * Souboj může proběhnout jen s postavou, která je nepřítel.
     * Ve hře jsou dva souboje. První souboj je pro hráče jednoduchý,
     * ale na druhý souboj musí být hráč patřičně vybaven.
     * Výsledek souboje závisí na vybavení hráče (poškození, životy, obrana).
     * 
     * @param parameters správná formulace příkazu - jinak se nevykoná
     * @return zpětná vazba hráči po vykonání příkazu
     */
    @Override
    public String process(String... parameters)
    {
        if (parameters.length == 0) {
            return "Nevím na koho chceš zaútočit. Musíš zadat jméno protivníka.";
        }
        
        if (parameters.length > 1) {
            return "Musíš zadat jméno protivníka.";
        }
        
        String enemyName = parameters[0];
        Area area = plan.getCurrentArea();
        int playerAttack = plan.getPlayerAttack();
        int playerDeff = plan.getPlayerArmor() + plan.getPlayerHealth();
        
        if (!area.containsPerson(enemyName)) {
            return "Postava '" + enemyName + "' tady není.";
        }
        Person enemy = plan.getCurrentArea().getPerson(enemyName);
        
        if (!enemy.isEnemy()) {
            return "Postava '" + enemyName +"' není tvůj nepřítel.";
        }
        String fight = "Zaútočil jsi na '" + enemyName + "' a začal lítý boj.\n";
        
        while(playerDeff > 0 || enemy.isAlive()) {
            enemy.setDeff(enemy.getDeff() - playerAttack);
            if (enemy.getDeff() > 0) {
                fight += "Udělil jsi: " + playerAttack + " poškození '" + enemyName + "'. Jeho brnění a životy jsou: " + enemy.getDeff() +"\n\n";
            }
            else {
                fight += "Podařilo se ti ubránit se a zabít '" + enemyName +"'.";
                enemy.setAlive(false);
                area.removePerson(enemyName);
                if (enemyName.equals("lupic")) {
                    fight += "Po lupičovi zůstal předmět 'pytel' s jeho kořistí a 'kosik_bylinek'.";
                    area.addItem(new Item("kosik_bylinek", "Ukradený košík s bylinkami."));
                    area.addItem(new Item("pytel", "Lupičův pytel s kořistí.", false));
                    area.changeAreaStatement(area.getName());
                }
                
                if (enemyName.equals("templari")) {
                    fight += "Podařilo se ti probojovat si cestu přes templáře. Teď už musíš jen předat dopis králi.";
                    area.changeAreaStatement(area.getName());
                }
                break;
            }
            playerDeff -= enemy.getAttack();
            if (playerDeff > 0) {
                fight += "Postava '" + enemyName + "' ti udělil poškození: " + enemy.getAttack() + "'. Tvé brnění a životy jsou: " + playerDeff +"\n\n";
            }
            else {
                fight += "Tvůj nepřítel '" + enemyName +"' tě zabil.";
                game.setGameOver(true);
                break;
            }
        }
        
        return fight;
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
