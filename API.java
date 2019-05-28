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
    public static final int amountOfPokemon = 151;
    
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
            FileReader fr = new FileReader("pokemon.json");
            JSONObject collection = (JSONObject) parser.parse(fr);
            for(int i = 1; i <= amountOfPokemon; i++){ //151 Pokemon
                try{
                    JSONObject p = (JSONObject) collection.get("" + i);
                    JSONArray jMoves = (JSONArray) p.get("moves");
                    Move[] moveset = new Move[4];
                    for(int x = 0; x < jMoves.size(); x++){
                        moveset[x] = getMove((String)jMoves.get(x));
                    }
                    
                    JSONArray jTypes = (JSONArray) p.get("type");
                    String[] types = new String[2];
                    for(int x = 0; x < jTypes.size(); x++){
                        types[x] = (String) jTypes.get(x);
                    }
                    
                    
                    Pokemon poke = new Pokemon((String)p.get("name"), Integer.valueOf(String.valueOf(p.get("hp"))), Integer.valueOf(String.valueOf(p.get("attack"))), Integer.valueOf(String.valueOf(p.get("defense"))), Integer.valueOf(String.valueOf(p.get("speed"))), /*new String[]{"flying", "fire"}*/types, moveset, i);
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
        System.out.println(moves.size() + " Moves Imported");
    }
    private static Move getMove(String name){
        for(Map.Entry<String, Move> entry : moves.entrySet()){
            if(entry.getKey().equalsIgnoreCase(name)){
                //System.out.println("-> Move already imported...");
                return (Move) entry.getValue().clone();
            }
        }
        
        //System.out.println("-> Parsing pokemonMoves.json");
        JSONParser parser = new JSONParser();
        try{
            FileReader fr = new FileReader("pokemonMoves.json");
            JSONObject collection = (JSONObject) parser.parse(fr);
            JSONObject m = (JSONObject) collection.get(name);
            if(m == null)
                return null;
            //String _name = String.valueOf(m.get("name"));
            //int _power = Integer.valueOf(String.valueOf(m.get("power")));
            //float _accuracy = Float.valueOf(String.valueOf(m.get("accuracy")));
            //String type = String.valueOf(m.get("type"));
            
            Move move = new Move((String) m.get("name"), Integer.valueOf(String.valueOf(m.get("power"))), Float.valueOf(String.valueOf(m.get("accuracy"))), (String) m.get("type"), Integer.valueOf(String.valueOf(m.get("pp"))));
            moves.put(String.valueOf(m.get("name")), (Move) move.clone());
            return (Move) move.clone();
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
                return (Pokemon) p.clone();
        }
        System.out.println("crud 1");
        return null;
    }
    public static Pokemon getPokemon(int pokedex){
        if(pokemon.size() == 0) importPokemon();
        for(Pokemon p : pokemon.values()){
            if(p.getPokedex() == pokedex)
                return (Pokemon) p.clone();
        }
        //System.out.println("crud 2 (" + pokedex + ")");
        return null;
    }
    public static String getHexColor(String type) {
    	switch(type) {
    	case "bug": return "#8A9B02";
    	case "dark": return "#322626";
    	case "dragon": return "#6250C1";
    	case "electric": return "#F8BC20";
    	case "fairy": return "#D994DD";
    	case "fighting": return "#522717";
    	case "fire": return "#C92208";
    	case "flying": return "#5B6FD1";
    	case "ghost": return "#343569";
    	case "grass": return "#3F9715";
    	case "ground": return "#8B8247";
    	case "ice": return "#69D0F4";
    	//case "normal": return "A8A499";
    	case "poison": return "#652B64";
    	case "psychic": return "#E32965";
    	case "rock": return "#9A7C30";
    	case "steel": return "#9290A2";
    	case "water": return "#0A68C9";
    	default: return "black";
    	}
    }
}