//Fletcher Henneman
    //-> This class handles all of the UI interaction
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Window;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.media.*;
import java.awt.Desktop;
import java.net.URI;
import java.util.Optional;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.*;

public class UIController{
    //Parent Class
    private Battle battle;
    //Labels and Images
    @FXML
    private Label pName;
    @FXML
    private Label epName;
    @FXML
    private ImageView pImage;
    @FXML
    private ImageView epImage;
    @FXML
    private Label pDecision;
    @FXML
    private Label epDecision;
    //Health Bars & Team Indicator
    @FXML
    private ProgressBar pHealthProgressBar;
    @FXML
    private ProgressBar epHealthProgressBar;
    @FXML
    private HBox ePartyIndicatorBox;
    //Music Player View
    @FXML
    private MediaView mediaView;
    @FXML
    private Button toggleMusicButton;
    private Boolean enabled;
    //Select a Move button (Battle)
    @FXML
    private Pane movePane;
    @FXML
    private Button move1Button;
    @FXML
    private Button move2Button;
    @FXML
    private Button move3Button;
    @FXML
    private Button move4Button;
    //Select a Pokemon to Switch to (Party)
    @FXML
    private VBox swapBox;
    @FXML
    private Button swap1Button;
    @FXML
    private Button swap2Button;
    @FXML
    private Button swap3Button;
    @FXML
    private Button swap4Button;
    @FXML
    private Button swap5Button;
    @FXML
    private Button swap6Button;
    
    public UIController(Battle battleInstance){
        this.battle = battleInstance;
    }
    @FXML
    public void initialize(){   
        battle.generateTeams();
        setParty(battle.party);
        setFriendly(battle.party[0]);
        setEnemy(battle.eparty[0]);
        enabled = false;
    }
    public void restart(){
        System.out.println("Restarting!");
        /*
        try{
            quit();
            
            UI ui = new UI();
            ui.battle = new Battle();
            ui._UIController = new UIController(battle);
            Stage stage = new Stage();
            Platform.runLater(new Runnable() {
                @Override
                public void run(){
                    try{
                        ui.start(stage);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }); 
        }catch(Exception e){
            e.printStackTrace();
        }
        */
       quit();
       try{
           Process proc = Runtime.getRuntime().exec("java -jar Game.jar");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void quit(){
        UI.pStage.close();
    }
    // [Event] onSelectMove
    @FXML
    private void onMove1(ActionEvent event){
        battle.handleMove(1, this);
    }
    @FXML
    private void onMove2(ActionEvent event){
        battle.handleMove(2, this);
    }
    @FXML
    private void onMove3(ActionEvent event){
        battle.handleMove(3, this);
    }
    @FXML
    private void onMove4(ActionEvent event){
        battle.handleMove(4, this);
    }
    // [Event] onSelectPokemonToSwitchTo
    @FXML
    private void onSwap1(ActionEvent event){
        battle.handleSwap(1, this);
    }
    @FXML
    private void onSwap2(ActionEvent event){
        battle.handleSwap(2, this);
    }
    @FXML
    private void onSwap3(ActionEvent event){
        battle.handleSwap(3, this);
    }
    @FXML
    private void onSwap4(ActionEvent event){
        battle.handleSwap(4, this);
    }
    @FXML
    private void onSwap5(ActionEvent event){
        battle.handleSwap(5, this);
    }
    @FXML
    private void onSwap6(ActionEvent event){
        battle.handleSwap(6, this);
    }
    //Music Toggling
    @FXML
    private void onToggleMusic(ActionEvent event){
        toggleMusic();
    }
    //Useful methods
    public void refreshUI(Decision userDecision, Decision aiDecision){
        setFriendly(battle.party[battle.selected]);
        setEnemy(battle.eparty[battle.eselected]);
        setParty(battle.party);
        setEnemyPartyIndicator(battle.eparty);
        setTurnDescription(userDecision, aiDecision);
    }
    private void setTurnDescription(Decision userDecision, Decision aiDecision){
        //User
        pDecision.setText(userDecision.toString());
        //AI
        epDecision.setText(aiDecision.toString());
    }
    public void setFriendlyDescription(String message){
        pDecision.setText(message);
    }
    public void setEnemyDescription(String message){
        epDecision.setText(message);
    }
    public void showAlert(String header, String message){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Notification");
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public Boolean confirmAlert(String header, String message){
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Notification");
        alert.setHeaderText(header);
        alert.setContentText(message);
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK)
            return true;
        return false;
    }
    public void setFriendly(Pokemon p){
        pName.setText(p.getName() + " L" + p.getLevel());
        pImage.setImage(new Image("/sprites/" + p.getPokedex() + ".png"));
        setMove(p.getMoveset());
        setHealthBar(pHealthProgressBar, p);
    }
    public void setEnemy(Pokemon p){
        epName.setText(p.getName() + " L" + p.getLevel());
        epImage.setImage(new Image("/sprites/" + p.getPokedex() + ".png"));
        setHealthBar(epHealthProgressBar, p);
    }
    private void setHealthBar(ProgressBar pb, Pokemon p){
        pb.setProgress(p.getCurrentHP()/((double)p.getBaseHP()));
    }
    public void setParty(Pokemon[] party){
        //showAlert("what the", "heck is going on");
        int i = 0;
        for(Node node : swapBox.getChildren()){
            if(node instanceof Button){
                Button b = (Button) node;
                b.setText(battle.party[i].getName());
                if(battle.party[i].isDead()){
                    b.setDisable(true);
                }
                i++;
            }
        }
    }
    private void setMove(Move[] moveset){
        int i = 0;
        for(Node node : movePane.getChildren()){
            if(node instanceof Button){
                Button b = (Button) node;
                if(moveset[i] != null && moveset[i].getPP() > 0){
                    b.setText(moveset[i].getName() + ": " + moveset[i].getPower() + " (" + moveset[i].getPP() + "/" + moveset[i].getBasePP() + ")");
                    b.setDisable(false);
                }else if(moveset[i] != null && moveset[i].getPP() == 0){
                    b.setText(moveset[i].getName() + ": " + moveset[i].getPower() + " (" + moveset[i].getPP() + "/" + moveset[i].getBasePP() + ")");
                    b.setDisable(true);
                }else{
                    b.setText("");
                    b.setDisable(true);
                }
                i++;
            }
        }
    }
    public void setMusicPlayer(MediaPlayer mediaPlayer){
        mediaView.setMediaPlayer(mediaPlayer);
    }
    public void setEnemyPartyIndicator(Pokemon[] party){
        int i = 0;
        for(Node node : ePartyIndicatorBox.getChildren()){
            if(node instanceof ImageView){
                ImageView img = (ImageView) node;
                if(party[i].isDead()){
                    img.setImage(new Image("/Scenes/Pokeball_fainted.png"));
                }else{
                    img.setImage(new Image("/Scenes/Pokeball.png"));
                }
                i++;
            }
        }
    }
    public void toggleMusic(){
        enabled = !enabled;
        if(enabled){
            mediaView.getMediaPlayer().play();
            toggleMusicButton.setText("Pause Music");
        }else{
            mediaView.getMediaPlayer().pause();
            toggleMusicButton.setText("Resume Music");
        }
    }
    public void openGithub(){
        if(Desktop.isDesktopSupported() && Desktop.getDesktop() != null){
            try{
                Desktop.getDesktop().browse(new URI("https://github.com/iPanja/PokeShowdown"));
            }catch(Exception e){
                System.out.println("Github URL broken");
            }
        }
    }
}