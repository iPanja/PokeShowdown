//Fletcher Henneman
    //-> This class handles all of the UI interaction
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Window;;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.*;
import javafx.scene.layout.*;

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
    //Health Bars
    @FXML
    private ProgressBar pHealthProgressBar;
    @FXML
    private ProgressBar epHealthProgressBar;
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
        battle.randomParty();
        setParty(battle.party);
        setFriendly(battle.party[0]);
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
    //Useful methods
    public void showAlert(String header, String message){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Notification");
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public void setFriendly(Pokemon p){
        pName.setText(p.getName());
        pImage.setImage(new Image("/sprites/" + p.getPokedex() + ".png"));
        setMove(p.getMoveset());
        setHealthBar(pHealthProgressBar, p);
    }
    public void setEnemy(Pokemon p){
        epName.setText(p.getName());
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
                if(moveset[i] != null){
                    b.setText(moveset[i].getName());
                    b.setDisable(false);
                }else{
                    b.setText("");
                    b.setDisable(true);
                }
                i++;
            }
        }
    }
}