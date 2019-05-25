//Fletcher Henneman
    //-> This class handles the importing of the JSON files (both Pokemon and their moves) as well as providing public methods to access that information
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
    public static final int amountOfPokemon = 155;
    
    public static void main(String[] args){
        //For testing purposes
        //importPokemon();
        //System.out.println(getMove("psybeam"));
        //System.out.println(getMove("ice punch"));
        //System.out.println(getMove("psybeam"));
        //System.out.println(moves);
    }
    
    //Pokemon.json -> Pokemon[] objects
    private static void importPokemon(){
        if(pokemon.size() != 0)
            return;
            
        System.out.println("Parsing pokemon.json (" + pokemon.size() + ")");
        JSONParser parser = new JSONParser();
        try{
            FileReader fr = new FileReader("E:\\Fletcher\\Documents\\AP Comp Sci\\PokeShowdown\\pokemon.json");
            JSONObject collection = (JSONObject) parser.parse(fr);
            for(int i = 1; i <= amountOfPokemon; i++){ //151 Pokemon
                try{
                    JSONObject p = (JSONObject) collection.get("" + i);
                    JSONArray jMoves = (JSONArray) p.get("moves");
                    Move[] moveset = new Move[4];
                    for(int x = 0; x < jMoves.size(); x++){
                        moveset[x] = getMove((String)jMoves.get(x));
                    }
                    /*
                    String[] types = new String[2];
                    for(int x = 0; x < 2; x++){
                        types[x] = (String) ((JSONArray)p.get("types")).get(x);
                    }
                    */
                    
                    Pokemon poke = new Pokemon((String)p.get("name"), 100/*Integer.valueOf((String)p.get("hp"))*/, Integer.valueOf(String.valueOf(p.get("attack"))), Integer.valueOf(String.valueOf(p.get("defense"))), 85/*Integer.valueOf(String.valueOf(p.get("speed")))*/, new String[]{"flying", "fire"}/*types*/, moveset, i);
                    API.pokemon.put((String)p.get("name"), poke);
                }catch(NullPointerException e){
                    //Do nothing - Some Pokemon IDs are missing so just skip over them
                    System.out.println("Missing Pokedex entry: " + i);
                    //e.printStackTrace();
                    //return;
                    continue;
                }
            }
        }catch(FileNotFoundException e){
            System.out.println(e);
        }catch(IOException e){
            System.out.println(e);
        }catch(ParseException e){
            System.out.println(e);
        }
        System.out.println(amountOfPokemon + " Pokemon Imported");
    }
    private static Move getMove(String name){
        for(Map.Entry<String, Move> entry : moves.entrySet()){
            if(entry.getKey().equalsIgnoreCase(name)){
                //System.out.println("-> Move already imported...");
                return entry.getValue();}
        }
        
        //System.out.println("-> Parsing pokemonMoves.json");
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
            
            Move move = new Move((String) m.get("name"), Integer.valueOf(String.valueOf(m.get("power"))), Float.valueOf(String.valueOf(m.get("accuracy"))), (String) m.get("type"), 5/*Integer.valueOf((String)m.get("pp"))*/);
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
    //Getter/Setter - Accessible API methods
    public static Pokemon getPokemon(String name){
        if(pokemon.size() == 0) importPokemon();
        for(Pokemon p : pokemon.values()){
            if(p.getName().equals(name))
                return p;
        }
        System.out.println("crud 1");
        return null;
    }
    public static Pokemon getPokemon(int pokedex){
        if(pokemon.size() == 0) importPokemon();
        for(Pokemon p : pokemon.values()){
            if(p.getPokedex() == pokedex)
                return p;
        }
        System.out.println("crud 2 (" + pokedex + ")");
        return null;
    }
}