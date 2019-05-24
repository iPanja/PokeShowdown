//Fletcher Henneman
    //-> This class handles all of the UI interaction
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Window;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class UIController{
    //Parent Class
    private Battle battle;
    //Select a Move button (Battle)
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
    // [Event] onSelectMove
    @FXML
    private void onMove1(ActionEvent event){
        battle.handleMove(1);
    }
    @FXML
    private void onMove2(ActionEvent event){
        battle.handleMove(2);
    }
    @FXML
    private void onMove3(ActionEvent event){
        battle.handleMove(3);
    }
    @FXML
    private void onMove4(ActionEvent event){
        battle.handleMove(4);
    }
    // [Event] onSelectPokemonToSwitchTo
    @FXML
    private void onSwap1(ActionEvent event){
        battle.handleSwap(1);
    }
    @FXML
    private void onSwap2(ActionEvent event){
        battle.handleSwap(2);
    }
    @FXML
    private void onSwap3(ActionEvent event){
        battle.handleSwap(3);
    }
    @FXML
    private void onSwap4(ActionEvent event){
        battle.handleSwap(4);
    }
    @FXML
    private void onSwap5(ActionEvent event){
        battle.handleSwap(5);
    }
    @FXML
    private void onSwap6(ActionEvent event){
        battle.handleSwap(6);
    }
    //Useful methods
    public void showAlert(String header, String message){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Notification");
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }
}