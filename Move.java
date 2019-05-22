public class Move{
    private String name;
    private int power;
    private float accuracy;
    private String type;
    
    public Move(String name, int power, float accuracy, String type){
        this.name = name;
        this.power = power;
        this.accuracy = accuracy;
        this.type = type;
    }
    
    public String getName(){
        return name;
    }
    
    public String toString(){
        return name + " (" + type + "): " + power;
    }
}