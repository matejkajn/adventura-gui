package cz.vse.matejka;

import cz.vse.matejka.model.*;
import javafx.application.Platform;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import java.io.InputStream;
import java.util.Collection;

/**
 * Třída představuje logiku grafického rozhraní adventury a spolupracuje se souborem "scene.fxml", kde pracuje s jeho prvky.
 * Třída pracuje s aktuálními informacemi o hře, které ovlivňují její fungování.
 * Např. výpisy předmětů nebo lokací se mění na základě naší lokace ve hře.
 * @author Jonáš Matějka
 * @version ZS 2020
 */
public class MainController {

    public MenuItem endButton;
    public MenuItem newGame;
    public MenuItem helpButton;
    public TextArea textOutput;
    public TextField textInput;

    public BorderPane background;
    public Label areaName;
    public Label areaDescription;
    public VBox exits;
    public VBox persons;
    public VBox items;
    public VBox inventory;
    public VBox equipment;
    public VBox playerStats;
    public VBox tradeButtons;

    public HBox leftBox;
    public VBox textAreas;
    public VBox rightBox;
    public VBox headBox;
    public Label endInfo;

    private IGame game;

    /**
     * Metoda zajišťující propojení kontroleru s běžící hrou, která je vytvořena třídou Start.
     * Tato metoda získá odkaz na běžící hru, ze které využívá informace pro fungování grafického rozhraní.
     *
     * @param game hra, která se při spuštění vytvoří
     */
    public void init(IGame game) {
        this.game = game;
        textAreas.setVisible(true);
        leftBox.setVisible(true);
        rightBox.setVisible(true);
        headBox.setVisible(true);
        endInfo.setVisible(false);
        textOutput.setText(game.getPrologue());
        endGame();
        newGame();
        help();
        update();
    }

    /**
     * Metoda přiřazuje ukončení programu tlačítku "Konec" v menu aplikace
     */
    public void endGame() {
        endButton.setOnAction(event -> {
            Platform.exit();
            System.exit(0);
        });
    }

    /**
     * Metoda přiřazuje tlačítku v menu "Nová hra" akci pro vytvoření nové hry.
     * Současně vypíše prolog hry a vymaže texty.
     */
    public void newGame() {
        newGame.setOnAction(event -> {
            init(new Game());
            textOutput.clear();
            textOutput.setText(game.getPrologue());
            textInput.clear();
        });
    }

    /**
     * Metoda přiřazuje tlačítku "Nápověda" akci pro zobrazení nápovědy.
     * Zobrazí se nové okno.
     */
    public void help() {
        helpButton.setOnAction(event -> {
            WebView webView = new WebView();
            WebEngine engine = webView.getEngine();
            engine.load(getClass().getResource("/help.html").toString());
            Scene sceneHelp = new Scene(webView);
            Stage stageHelp = new Stage();
            stageHelp.setTitle("Nápověda");
            stageHelp.setScene(sceneHelp);
            //Ikona
            InputStream streamIcon = getClass().getClassLoader().getResourceAsStream("information.png");
            Image imageIcon = new Image(streamIcon);
            stageHelp.getIcons().add(imageIcon);
            stageHelp.show();
        });
    }

    /**
     * Hlavní metoda pro aktualizaci veškerého grafického prostředí.
     * Aktualizuje hlavičku grafického rozhraní, kterou tvoří název aktualní lokace a její popis.
     * Využívá další metody, které jsou popsány níže.
     */
    private void update() {
        String name = getArea().getName();
        areaName.setText(getAreaNameProperly(name));
        String description = getArea().getDescription();
        areaDescription.setText(description);

        updateExits();
        updateItems();
        updatePersons();
        updateInventory();
        updateEquip();
        updateStats();
        gameCheck();
    }

    /**
     * Metoda kontroluje, zda je hra pořád v běhu.
     * Pokud hra skončila zneviditelní veškeré ovládací prvky grafického prostředí
     * a zobrazí pouze inforamtivní label s epilogem hry a návodem na další postup.
     */
    private void gameCheck() {
        if(game.isGameOver()) {
            textAreas.setVisible(false);
            leftBox.setVisible(false);
            rightBox.setVisible(false);
            headBox.setVisible(false);
            endInfo.setVisible(true);
            endInfo.setText(game.getEpilogue());
        }
    }

    /**
     * Metoda je využívána v hlavní metodě update() a slouží pro aktualizaci východů
     * naší aktuální lokace, které se následné zobrazují vpravo nahoře a lze do nich vstoupit po kliknutí.
     * Metoda také aktualizuje pozadí naší aplikace, které se mění v závislosti na aktuální lokaci.
     */
    private void updateExits() {
        Collection<Area> exitAreas = getArea().getExits();
        exits.getChildren().clear();
        for (Area area : exitAreas) {
            Label element = createObject(area.getName(),"areas");
            element.setOnMouseClicked(event -> {
                executeCommand("jdi " + element.getText());
            });
            element.setTooltip(new Tooltip("Kliknutím půjdeš do lokace " + element.getText() + "."));
            element.setCursor(Cursor.HAND);
            exits.getChildren().add(element);
        }
        String style = new String("-fx-background-image: url(areas/" + getArea().getName() +".jpg)");
        background.setStyle(style);
    }

    /**
     *  Metoda je využívána v hlavní metodě update().
     *  Její účel je aktualizovat veškeré předměty, které se nacházejí v naší aktuální lokaci.
     *  Metoda se nezabývá inventářem, protože na to je určena jíná samostatná metoda.
     *  Přiřazuje předmětům event na kliknutí, který se liší podle typu předmětu.
     *  Pokud předmět zle sebrat, kliknutím ho sebereme.
     *  Pokud předmět nelze sebrat, kliknutím ho prozkoumáme.
     *  Pokud předmět je vybavení, kliknutím se jím vybavíme.
     */
    private void updateItems() {
        Collection<Item> areaItems = getArea().getAreaItems().values();
        items.getChildren().clear();
        for (Item item : areaItems) {
            String directory = "items";
            if(getArea().isEquip(item.getName())) {
                directory = "equip";
            }
            Label element = createObject(item.getName(), directory);
            if(item.isMoveable()) {
                element.setTooltip(new Tooltip("Kliknutím sebereš předmět " + element.getText() + "."));
                element.setCursor(Cursor.HAND);
                element.setOnMouseClicked(event -> {
                    executeCommand("seber " + element.getText());
                });
            } else {
                element.setTooltip(new Tooltip( "Předmět '" + element.getText() + "' neuneseš, ale zkus jej prozkoumat."));
                element.setCursor(Cursor.HAND);
                element.setOnMouseClicked(event -> {
                    executeCommand("prozkoumej " + element.getText());
                });
            }
            if(getArea().isEquip(item.getName())) {
                element.setTooltip(new Tooltip("Kliknutím se vybavíš přemdětem " + element.getText() + "."));
                element.setCursor(Cursor.HAND);
                element.setOnMouseClicked(event -> {
                    executeCommand("vybavit " + element.getText());
                });
            }
            items.getChildren().add(element);
        }
    }

    /**
     * Metoda je využívána v hlavní metodě update().
     * Slouží pro aktualizaci všech postav, které se nacházejí v naší aktuální lokaci.
     * Poté rozlišuje, zda je postava přátelská či nikoliv.
     * Pokud je postava přátelská, kliknutím s ní promluvíme a text se vypíše v dolním textovém poli.
     * Pokud je postava nepřítel, kliknutím s ní provedeme souboj, jehož detaily se vypíšou v dolním textovém poli.
     */
    private void updatePersons() {
        Collection<Person> areaPersons = getArea().getAreaPersons().values();
        persons.getChildren().clear();
        for (Person person : areaPersons) {
            Label element = createObject(person.getName(),"persons");
            if(person.isEnemy()) {
                element.setTooltip(new Tooltip("Kliknutím zaútočíš na " + element.getText() + "."));
                element.setCursor(Cursor.HAND);
                element.setOnMouseClicked(event -> {
                    executeCommand("zautoc " + element.getText());
                });
            } else {
                element.setTooltip(new Tooltip("Kliknutím promluvíš s " + element.getText() + "."));
                element.setCursor(Cursor.HAND);
                element.setOnMouseClicked(event -> {
                    executeCommand("promluv " + element.getText());
                });
            }
            persons.getChildren().add(element);
        }
    }

    /**
     * Metoda je využívána v hlavní metodě update().
     * Slouží pro aktualizaci inventáře hráče, který nosí do všech lokací.
     * Věci v inventáři mají dvě možnosti využití podle kliknutí.
     * Pokud se jedná o jeden speciální předmět "JED", kliknutím si vylepšíme zbraň.
     * V ostatních případech předmět z inventáře vyhodíme na "zem" do lokace, ve které se právě nacházíme.
     */
    private void updateInventory() {
        Collection<Item> playerInventory = game.getGamePlan().getInventory().getItems().values();
        inventory.getChildren().clear();
        for (Item item : playerInventory) {
            Label element = createObject(item.getName(),"items");
            if(item.getName().equals("jed")) {
                element.setTooltip(new Tooltip("Kliknutím použiješ předmět " + element.getText() + "."));
                element.setCursor(Cursor.HAND);
                element.setOnMouseClicked(event -> {
                    executeCommand("pouzit " + element.getText());
                });
            } else {
                element.setTooltip(new Tooltip("Kliknutím vyhodíš předmět " + element.getText() + " z inventáře."));
                element.setCursor(Cursor.HAND);
                element.setOnMouseClicked(event -> {
                    executeCommand("vyhod " + element.getText());
                });
            }
            inventory.getChildren().add(element);
        }
        updateTrading();
    }

    /**
     * Metoda je využívána v hlavní metodě update().
     * Tato metoda aktualizuje vybavení hráče.
     * Slouží pouze jako zobrazení vybavení, takže nemá žádné vlastnosti na kliknutí.
     * Jelikož v původní adventuře je inventář a vybavení hráče odděleno, jsou
     * vytvořené metody na akutalizaci inventáře a vybavení také oddělené.
     */
    private void updateEquip() {
        Collection<Item> playerEquip = game.getGamePlan().getInventory().getEquipment().values();
        equipment.getChildren().clear();
        for (Item item : playerEquip) {
            Label element = createObject(item.getName(), "equip");
            equipment.getChildren().add(element);
        }
    }

    /**
     * Metoda je využívána v hlavní metodě update().
     * Metoda slouží pro aktualizaci obranných a útočných hodnot hráče.
     * Účel této metody je zobrazit aktuální životy, brnění a poškození hráče.
     */
    private void updateStats() {
        int health = game.getGamePlan().getPlayerHealth();
        int armor = game.getGamePlan().getPlayerArmor();
        int attack = game.getGamePlan().getPlayerAttack();
        playerStats.getChildren().clear();
        Label stats = new Label();
        String info = "Životy: " + health
                    + "\nBrnění: " + armor
                    + "\nPoškození: " + attack;
        stats.setText(info);
        playerStats.getChildren().add(stats);
    }

    /**
     * Metoda je spjata s metoudou pro aktualizaci inventáře.
     * Když se aktualizuje inventář, zavolá se tato metoda, která inventář "okopíruje"
     * tím způsobem, že pro každý předmět v inventáři vytvoří RadioButton.
     * Metoda také vytvoří tlačítko, které bude sloužit na provedení obchodu.
     * Poté, když se hráč nachází v místnosti s přátelskou postavou, může zaškrtnout jeden
     * RadioButton, představující předmět, s kterým chce hráč obchodovat a stisknutím tlačítka
     * "Obchodovat" obchod provést.
     */
    private void updateTrading() {
        Collection<Item> playerInventory = game.getGamePlan().getInventory().getItems().values();
        ToggleGroup toggleGroup = new ToggleGroup();
        tradeButtons.getChildren().clear();
        for(Item item : playerInventory) {
            RadioButton tradeButton = new RadioButton();
            tradeButton.setToggleGroup(toggleGroup);
            tradeButton.setText(item.getName());
            tradeButtons.getChildren().add(tradeButton);
            tradeButton.setStyle("-fx-padding: 10 10 10 10");
        }
        Button trade = new Button();
        trade.setText("Obchodovat");
        if(!getArea().containsEnemy() && !getArea().getAreaPersons().isEmpty()) {
            trade.setDisable(false);
        } else {
            trade.setDisable(true);
        }
        trade.setOnMouseClicked(event -> {
            RadioButton selectedRb = (RadioButton) toggleGroup.getSelectedToggle();
            String selectedRbText = selectedRb.getText();
            executeCommand("dej " + selectedRbText + " " + getPersonNameProperly(getArea().getName()));
        });
        tradeButtons.getChildren().add(trade);
    }

    /**
     * Důležitá metoda pro vytvoření jednotlivých předmětů, lokací, postav v grafickém rozhraní.
     * Metodu využívají metody pro aktualizaci východů, postav, předmětů, výbavy.
     * Účel této metody je zjednodušit kód programu.
     * @param name jméno objektu, který chceme vytvořit v metodě updateExits to budou východy u inventáře zase předměty
     * @param directory podle metody se vyplní parametr, aby byly obrázky projektu přehledně uspořádny
     * @return objekt label, představující element(východu, předmětu, postavy) s příslušným popisem a obrázkem
     */
    private Label createObject(String name, String directory) {
        Label label = new Label(name);

        InputStream stream = getClass().getClassLoader().getResourceAsStream(directory + "/" + name + ".jpg");
        if(directory.equals("equip")) {
            stream = getClass().getClassLoader().getResourceAsStream(directory + "/" + name + ".png");
        }
        Image img = new Image(stream);
        ImageView imageView = new ImageView(img);
        imageView.setFitWidth(100);
        imageView.setFitHeight(60);
        label.setGraphic(imageView);
        label.setStyle("-fx-padding: 0 0 5 5");
        return label;
    }

    /**
     * Další metoda pro zjednodušení kódu v programu.
     * Slouží na to, aby hra provedla určitý příkaz, který po ní vyžadujeme.
     * @param command příkaz, který chceme aby hra provedla
     */
    private void executeCommand(String command) {
        String result = game.processCommand(command);
        textOutput.appendText(result + "\n\n");
        update();
    }

    /**
     * Metoda přižazuje event našemu textovému poli pro "odeslání/potvrzení" příkazu.
     * Po stiknutí klávesy ENTER hra zpracuje příkaz, který byl zadán námi do textového pole.
     * @param keyEvent v tomto případě je to stiknutý ENTER v naší aplikaci
     */
    public void onInputKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            executeCommand(textInput.getText());
            textInput.setText("");
        }
    }

    /**
     * Metoda slouží pro získání odkazu na místnost, ve které se právě nacházíme.
     * @return odkaz na aktuální pozici
     */
    private Area getArea() {
        return game.getGamePlan().getCurrentArea();
    }

    /**
     * Metoda sloužící pro zkrášlení uřivatelského rozhraní.
     * Volá se při výpisu názvu aktuální lokace.
     * Při zavolání metody se podle názvu lokace změní její výpis, aby neobsahoval podtržítka a začínal velkými písmeny.
     * @param areaName název lokace, které chceme změnit výpis
     * @return změněný název lokace
     */
    private String getAreaNameProperly(String areaName) {
        String name = "";
        switch(areaName) {
            case "hostinec":
                name = "Hostinec";
                break;
            case "stratholme":
                name = "Náměstí Stratholme";
                break;
            case "roklina":
                name = "Roklina";
                break;
            case "jeskyne":
                name = "Jeskyně";
                break;
            case "les":
                name = "Les";
                break;
            case "polni_cesta":
                name = "Polní cesta";
                break;
            case "vetesnictvi":
                name = "Vetešnictví";
                break;
            case "lektvary":
                name = "Obchod s lektvary";
                break;
            case "hospoda":
                name = "Hospoda";
                break;
            case "ketes":
                name = "Královské město Ketes";
                break;
            default:
                name = "";
                break;
        }
        return name;
    }

    /**
     * Metoda pro zjednodušený nález konkrétní postavy v lokaci.
     * Podle názvu lokace nám zavolaná metoda pošle jméno přátelské
     * postavy, kterou využijeme pro obchodování.
     * Ve hře se nenachází více postav v jedné lokaci.
     * @param areaName název lokace, ve které hledáme postavu
     * @return jméno postavy, se kterou budeme v budoucnu obchodovat
     */
    private String getPersonNameProperly(String areaName) {
        String name = "";
        switch(areaName) {
            case "stratholme":
                name = "zebrak";
                break;
            case "vetesnictvi":
                name = "vetesnik";
                break;
            case "lektvary":
                name = "obchodnice";
                break;
            case "hospoda":
                name = "hospodsky";
                break;
            case "ketes":
                name = "ares";
                break;
            default:
                name = "";
                break;
        }
        return name;
    }
}
