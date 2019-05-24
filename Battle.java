//Fletcher Henneman

public class Battle{
    public Pokemon[] party;
    public int selected; //The index of the current Pokemon
    
    public void handleMove(int num){
        if(num < 1 || num > 4) return;
        //Move move = party[selected].getMove(i-1);
        //Handle move
        UI._UIController.showAlert("Move Selected:", "You have selected move " + num);
    }
    public void handleSwap(int num){
        if(num < 1 || num > 6) return;
        selected = num-1;
    }
    
}