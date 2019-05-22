//Fletcher Henneman
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.io.FileNotFoundException;
import org.json.simple.*;
import org.json.simple.parser.*;
import java.util.*;

public class API{
    //
    private static HashMap<String, Pokemon> pokemon = new HashMap<String, Pokemon>();
    private static HashMap<String, Move> moves = new HashMap<String, Move>();
    
    public static void main(String[] args){
        //For testing purposes
        //importPokemon();
        System.out.println(getMove("psybeam"));
        System.out.println(getMove("ice punch"));
        System.out.println(getMove("psybeam"));
        System.out.println(moves);
    }
    
    //Pokemon.json -> Pokemon[] objects
    private static void importPokemon(){
        if(pokemon != null)
            return;
            
        System.out.println("Parsing pokemon.json");
        JSONParser parser = new JSONParser();
        try{
            FileReader fr = new FileReader("E:\\Fletcher\\Documents\\AP Comp Sci\\PokeShowdown\\pokemon.json");
            JSONObject collection = (JSONObject) parser.parse(fr);
            for(int i = 1; i <= 10; i++){ //151 Pokemon
                JSONObject p = (JSONObject) collection.get("" + i);
                //JSONArray jMoves = p.get("moves");
                
                Pokemon poke = new Pokemon((String)p.get("name"));
                API.pokemon.put((String)p.get("name"), poke);
            }
        }catch(FileNotFoundException e){
            System.out.println(e);
        }catch(IOException e){
            System.out.println(e);
        }catch(ParseException e){
            System.out.println(e);
        }
        System.out.println("151 Pokemon Imported");
    }
    private static Move getMove(String name){
        for(Map.Entry<String, Move> entry : moves.entrySet()){
            if(entry.getKey().equalsIgnoreCase(name)){
                System.out.println("-> Move already imported...");
                return entry.getValue();}
        }
        
        System.out.println("-> Parsing pokemonMoves.json");
        JSONParser parser = new JSONParser();
        try{
            FileReader fr = new FileReader("E:\\Fletcher\\Documents\\AP Comp Sci\\PokeShowdown\\pokemonMoves.json");
            JSONObject collection = (JSONObject) parser.parse(fr);
            JSONObject m = (JSONObject) collection.get(name);
            if(m == null)
                return null;
            //String _name = String.valueOf(m.get("name"));
            //int _power = Integer.valueOf(String.valueOf(m.get("power")));
            //float _accuracy = Float.valueOf(String.valueOf(m.get("accuracy")));
            //String type = String.valueOf(m.get("type"));
            
            Move move = new Move((String) m.get("name"), Integer.valueOf(String.valueOf(m.get("power"))), Float.valueOf(String.valueOf(m.get("accuracy"))), (String) m.get("type"));
            moves.put(String.valueOf(m.get("name")), move);
            return move;
        }catch(FileNotFoundException e){
            System.out.println(e);
            return null;
        }catch(IOException e){
            System.out.println(e);
            return null;
        }catch(ParseException e){
            System.out.println(e);
            return null;
        }
       
    }
}