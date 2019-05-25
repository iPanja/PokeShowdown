//Fletcher Henneman
    //-> "Driver" class
    //-> This class initially loads the UI and attaches the UIController class to handle any form of interaction/further changes
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.application.Platform;
import java.net.URL;
import javax.swing.SwingUtilities;
import javafx.embed.swing.JFXPanel;

//Driver Class
public class UI extends Application{
    public static Battle battle = new Battle(); //Original/Only [Battle] Instance
    public static UIController _UIController = new UIController(battle);
    
    public static void main(String[] args){
        new JFXPanel(); //Stops the ****** error: Toolkit not initialized
        final JFXPanel fxPanel = new JFXPanel();
        Application.launch(UI.class, args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        System.out.println("-> Loading application");
        loader.setLocation(getClass().getResource("/Scenes/Application.fxml"));
        loader.setController(this._UIController);
        
        VBox vbox = loader.<VBox>load();
        Scene scene = new Scene(vbox);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Pokemon Showdown");
        primaryStage.show();
    }
}