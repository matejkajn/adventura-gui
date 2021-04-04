package cz.vse.matejka.main;

import cz.vse.matejka.MainController;
import cz.vse.matejka.model.Game;
import cz.vse.matejka.model.IGame;
import cz.vse.matejka.textui.TextUI;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

import javax.swing.text.html.ImageView;
import java.io.InputStream;

/**
 * Hlavní třída určená pro spuštění hry. Obsahuje pouze statickou metodu
 * {@linkplain #main(String[]) main}, která vytvoří instance logiky hry
 * a uživatelského rozhraní, propojí je a zahájí hru.
 *
 * @author Jarmila Pavlíčková
 * @author Jan Říha
 * @version LS 2020
 */
public final class Start extends Application
{
    /**
     * Metoda pro spuštění celé aplikace.
     *
     * @param args parametry aplikace z příkazového řádku
     */
    public static void main(String[] args)
    {
        if(args.length > 0 && args[0].equals("text")) {
            IGame game = new Game();
            TextUI ui = new TextUI(game);
            ui.play();
        } else {
            launch();
        }
    }

    /**
     * Metoda start, sloužící pro spuštění aplikace v řežimu grafického rozhraní.
     *
     * @param primaryStage parametr okna, které zobrazujeme
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Dopis pro krále");
        primaryStage.setMaximized(true);

        FXMLLoader loader = new FXMLLoader();
        InputStream stream = getClass().getClassLoader().getResourceAsStream("scene.fxml");
        Parent root = loader.load(stream);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);

        //Ikona
        InputStream streamIcon = getClass().getClassLoader().getResourceAsStream("icon.png");
        Image imageIcon = new Image(streamIcon);
        primaryStage.getIcons().add(imageIcon);

        MainController controller = loader.getController();
        IGame game = new Game();

        controller.init(game);
        primaryStage.show();
    }
}
