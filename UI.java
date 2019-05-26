//Fletcher Henneman
    //-> "Driver" class
    //-> This class initially loads the UI and attaches the UIController class to handle any form of interaction/further changes
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.media.*;
import javafx.stage.*;
import javafx.application.Platform;
import java.net.URL;
import javax.swing.SwingUtilities;
import javafx.embed.swing.JFXPanel;
import java.nio.file.Paths;
import javafx.util.Duration;

//Driver Class
public class UI extends Application{
    public static Battle battle = new Battle(); //Original/Only [Battle] Instance
    public static UIController _UIController = new UIController(battle);
    public static Stage pStage;
    
    public static void main(String[] args){
        new JFXPanel(); //Stops the ****** error: Toolkit not initialized
        final JFXPanel fxPanel = new JFXPanel();
        Application.launch(UI.class, args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception{
        pStage = primaryStage;
        FXMLLoader loader = new FXMLLoader();
        System.out.println("-> Loading application");
        loader.setLocation(getClass().getResource("/Scenes/Application.fxml"));
        loader.setController(this._UIController);
        
        //Display window
        VBox vbox = loader.<VBox>load();
        Scene scene = new Scene(vbox);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Pokemon Showdown");
        primaryStage.show();
        
        //Music
        Media media = new Media(Paths.get("./Scenes/music.mp3").toUri().toString());
        MediaPlayer player = new MediaPlayer(media);
        player.setOnEndOfMedia(new Runnable(){
            public void run(){
                player.seek(Duration.ZERO);
            }
        });
        player.setVolume(0.1);
        player.setAutoPlay(true);
        this._UIController.setMusicPlayer(player);
        this._UIController.toggleMusic();
    }
    
}