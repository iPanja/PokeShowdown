//Fletcher Henneman
//Semi-Placeholder Battle class
public class Battle{
    public Pokemon[] party;
    public int selected; //The index of the current Pokemon
    
    public Battle(){
        party = new Pokemon[6];
    }
    public void randomParty(){
        int i = 0;
        while(i < 6){
            int rand = (int) ((Math.random() * 151) + 1);
            Pokemon p = API.getPokemon(rand);
            if(p != null){
                party[i] = p;
                i++;
            }
        }
        party[0] = API.getPokemon(152);
        party[1] = API.getPokemon(153);
        party[2] = API.getPokemon(154);
        party[3] = API.getPokemon(155);
    }
    
    public void handleMove(int num, UIController _instance){ //num is between 1 and 4; num-1 signifies the index of the move chosen
        if(num < 1 || num > 4) return;
        Move move = party[selected].getMove(num-1);
        _instance.showAlert("Attack!", "You have dealt " + move.getPower() + " damage");
        //Handle move
    }
    public void handleSwap(int num, UIController _instance){ //num is between 1 and 6; num-1 signifies the index of the Pokemon chosen
        if(num < 1 || num > 6) return;
        selected = num-1;
        _instance.setFriendly(party[num-1]);
    }
    
}