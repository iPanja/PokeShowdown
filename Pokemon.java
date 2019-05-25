//Diego Urena
import java.util.ArrayList;
public class Pokemon{
    private String name, type1,type2;
    private int level,hpo,hpc,attack,defense,speed;
    private Move[] moveset;
    private int pokedex;
    private boolean alive;
    
    public Pokemon(String name, int hp,int attack, int defense, int speed, String[] types,Move[] moves,int pokedex){
        this.name=name;
        type1=types[0];
        type2=(types.length==1)?"":types[1];
        hpo=hp;
        hpc=hp;
        this.attack=attack;
        this.defense=defense;
        this.speed=speed;
        level=100;
        moveset=new Move[moves.length];
        for(int i=0;i<moveset.length;i++){
            moveset[i]=moves[i];
        }
        this.pokedex=pokedex;
        alive=true;
    }
    
    public String getName(){
        return name;
    }
    
    public ArrayList<String> getType(){
        ArrayList<String> types=new ArrayList<String>();
        types.add(type1);
        types.add(type2);
        return types;
    }
    
    public int getLevel(){
        return level;
    }
    
    public void takeDamage(int damage){
        hpc=(damage>=hpc)?0:hpc-damage;
        alive=(hpc==0)?false:true;
    }
    
    public boolean isDead(){
        return !alive;
    }
    
    public Move[] getMoveset(){
        return moveset;
    }
    
    public Move getMove(int index){
        return moveset[index];
    }
    
    public int getPokedex(){
        return pokedex;
    }
    
    public int getBaseHP(){
        return hpo;
    }
    
    public int getCurrentHP(){
        return hpc;
    }
}
