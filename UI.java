//Fletcher Henneman
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.application.Platform;
import java.net.URL;
import javax.swing.SwingUtilities;
import javafx.embed.swing.JFXPanel;

public class UI extends Application{
    public static void main(String[] args){
        new JFXPanel(); //Stops the ****** error Toolkit not initialized?
        final JFXPanel fxPanel = new JFXPanel();
        Application.launch(UI.class, args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        System.out.println("-> Loading application");
        loader.setLocation(getClass().getResource("/Scenes/Application.fxml"));
        
        VBox vbox = loader.<VBox>load();
        Scene scene = new Scene(vbox);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Pokemon Showdown");
        primaryStage.show();
        
        /*
        Parent root = FXMLLoader.load(new URL("E:\\Fletcher\\Documents\\AP Comp Sci\\PokeShowdown\\Scenes\\Application.fxml"));
        if(root == null)
            System.out.println("ROOT IS NULL, #BRUH");
        primaryStage.setTitle("Pokemon Showdown");
        primaryStage.setScene(new Scene(root, 500, 500));
        primaryStage.show();
        */
    }
}