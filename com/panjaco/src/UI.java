package com.panjaco.src;

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

import java.io.File;
import java.net.URL;
import javax.swing.SwingUtilities;

import com.panjaco.src.multiplayer.GameClient;
import com.panjaco.src.multiplayer.GameServer;

import javafx.embed.swing.JFXPanel;
import java.nio.file.Paths;
import javafx.util.Duration;


//Driver Class
public class UI extends Application{
	
    public static Battle battle = new Battle(); //Original/Only [Battle] Instance
    public static UIController _UIController = new UIController(battle);
    public static Stage pStage;
    public static GameServer serverInstance = null; //Must exist to receive invites
    public static GameClient clientInstance = new GameClient(); //Only exist to send invites
    public static Player player = null;
    public static String ip = null;
    
    public static void main(String[] args){
        new JFXPanel(); //Stops the ****** error: Toolkit not initialized
        final JFXPanel fxPanel = new JFXPanel();
        getAllFiles(new File("."));
        Application.launch(UI.class, args);
    }
    
    private static void getAllFiles(File curDir) {

        File[] filesList = curDir.listFiles();
        for(File f : filesList){
            if(f.isDirectory())
                System.out.println(f.getName());
            if(f.isFile()){
                System.out.println(f.getName());
            }
        }
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception{
        pStage = primaryStage;
        FXMLLoader loader = new FXMLLoader();
        System.out.println("-> Loading application");
        loader.setLocation(UI.this.getClass().getResource("Scenes/Application.fxml").toURI().toURL());
        //loader.setLocation(Paths.get("com/panjaco/Scenes/Application.fxml").toUri().toURL());
        loader.setController(this._UIController);
        
        //Display window
        VBox vbox = loader.<VBox>load();
        Scene scene = new Scene(vbox);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Pokemon Showdown");
        primaryStage.show();
        
        //Music
        //Media media = new Media(Paths.get("./com/panjaco/src/Scenes/music.mp3").toUri().toString());
        Media media = new Media(getClass().getResource("Scenes/music.mp3").toURI().toString());
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
        //Fetch IP
        UI.ip = Player.getIP();
        UI._UIController.setIPLabel("Your IP: " + UI.ip);
        //Multiplayer
        setupServer();
    }
    
    public static void setupServer() {
    	if(UI.serverInstance == null)
    		UI.serverInstance = new GameServer();
    }
    public static void setupClient(String displayName) {
    	if(UI.clientInstance == null) {
    		UI.clientInstance = new GameClient();
    		UI.player = new Player(displayName);
    		UI.clientInstance.player = UI.player;
    	}
    }
    
}