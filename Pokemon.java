//Placeholder Pokemon class
public class Pokemon{
    String name;
    int pokedex;
    Move[] moveset;
    Boolean alive;
    public Pokemon(String name, int pokedex, Move[] moveset){
        this.name = name;
        this.pokedex = pokedex;
        this.moveset = moveset;
        this.alive = true;
    }
    public Move[] getMoveset(){
        return moveset;
    }
    public Move getMove(int i){
        return this.moveset[i];
    }
    public boolean isDead(){
        return !alive;
    }
}