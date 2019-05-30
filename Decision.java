public class Decision{
    private type choice;
    private Move move;
    private int pokemonIndex;
    private Boolean deadSwap;
    private String effectiveness;
    private Boolean missed;
    private String name;
    private String message;
    private String extra;
    public Decision(Move move, String name, String effectiveness){
        this.choice = type.ATTACK;
        move.getName();
        
        this.move = move;
        this.pokemonIndex = -1;
        this.deadSwap = false;
        this.effectiveness = effectiveness;
        this.name = name;
        this.missed = false;
        this.message = null;
        this.extra = "";
    }
    public Decision(int pokemonIndex, String name, Boolean deadSwap){
        this.choice = type.SWAP;
        this.move = null;
        this.pokemonIndex = pokemonIndex;
        this.deadSwap = deadSwap;
        this.name = name;
        this.message = null;
        this.extra = "";
    }
    public Decision(int pokemonIndex, String name){
        this(pokemonIndex, name, false);
    }
    
    public void overrideMessage(String message){
        this.message = message;
    }
    public type getType(){
        return choice;
    }
    public Move getMove(){
        return move;
    }
    public String getEffectiveness(){
        return effectiveness;
    }
    public int getSwapIndex(){
        return pokemonIndex;
    }
    public Boolean isDeadSwap(){
        return deadSwap;
    }
    public void setMissed(Boolean missed){
        this.missed = missed;
    }
    public Boolean didMiss(){
        return missed;
    }
    public void setEffectiveness(String e){
        effectiveness = e;
    }
    public String getName(){
        return name;
    }
    public void setExtra(String extra){
        this.extra = extra;
    }
    
    @Override
    public String toString(){
        if(message != null) {
            if(getType() == type.ATTACK && didMiss()) return message;
            return extra + message;
        }
        return getDescription();
    }
    public String getDescription(){
        String s = "";
        if(getType() == type.SWAP)
            if(name == "George")
                s = "George has pulled up to the function. ";
            else
                s = "Switched to " + name + ". ";
        else
            if(getType() == type.ATTACK && didMiss())
                s = "The move (" + getMove().getName() + ") missed! ";
            else{
                s = name + " used " + getMove().getName() + ". ";
                s += effectiveness;
                if(getMove().getCrit() && !effectiveness.equals("It had no effect..."))
                    s += "Critical Hit! ";
            }
            
        return s;
    }
}