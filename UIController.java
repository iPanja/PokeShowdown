import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Window;

public class UIController{
    @FXML
    private Button move1Button;
    
    @FXML
    private void onMove1(ActionEvent event){
        Window owner = move1Button().getWindow();
        AlertHelper.showAlert("Move 1 Activated!");
    }
}