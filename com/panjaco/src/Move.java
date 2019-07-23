package com.panjaco.src;
import java.awt.Color;

//Diego Urena
public class Move implements Cloneable{
    private String name;
    private int power,pp,basepp;
    private float accuracy;
    private String type;
    private boolean crit;
    
    private static float effective(String t1,String t2){
          float effectiveness=1.0f;
          if(t1.equals("grass")){
              if(t2.equals("grass")||t2.equals("fire")||t2.equals("steel")||t2.equals("poison")||t2.equals("flying")||t2.equals("bug")||t2.equals("dragon")){
                  effectiveness*=0.5;
              }else if(t2.equals("water")||t2.equals("rock")||t2.equals("ground")){
                   effectiveness*=2;
              }
          }else if(t1.equals("water")){
              if(t2.equals("water")||t2.equals("grass")||t2.equals("dragon")){
                 effectiveness*=0.5;
              }else if(t2.equals("fire")||t2.equals("rock")||t2.equals("ground")){
                 effectiveness*=2;
              }
          }else if(t1.equals("fire")){
               if(t2.equals("fire")||t2.equals("water")||t2.equals("rock")||t2.equals("dragon")){
                 effectiveness*=0.5;
               }else if(t2.equals("grass")||t2.equals("steel")||t2.equals("bug")||t2.equals("ice")){
                 effectiveness*=2;
               }
          }else if(t1.equals("normal")){
               if(t2.equals("ghost")){
                  effectiveness*=0;
               }else if(t2.equals("steel")||t2.equals("rock")){
                  effectiveness*=0.5;
               }
          }else if(t1.equals("fighting")){
               if(t2.equals("rock")||t2.equals("steel")||t2.equals("normal")||t2.equals("dark")||t2.equals("ice")){
                  effectiveness*=2;
               }else if(t2.equals("ghost")){
                  effectiveness*=0;
               }else if(t2.equals("psychic")||t2.equals("flying")||t2.equals("poison")||t2.equals("bug")){
                  effectiveness*=0.5;
               }
          }else if(t1.equals("flying")){
               if(t2.equals("rock")||t2.equals("steel")||t2.equals("electric")||t2.equals("fairy")){
                  effectiveness*=0.5;
               }else if(t2.equals("grass")||t2.equals("bug")||t2.equals("fighting")){
                  effectiveness*=2;
               }
          }else if(t1.equals("poison")){
               if(t2.equals("steel")){
                  effectiveness*=0;
               }else if(t2.equals("poison")||t2.equals("ground")||t2.equals("rock")||t2.equals("ghost")){
                  effectiveness*=0.5;
               }else if(t2.equals("grass")||t2.equals("fairy")){
                  effectiveness*=2;
               }
          }else if(t1.equals("ground")){
               if(t2.equals("grass")||t2.equals("bug")){
                  effectiveness*=0.5;
               }else if(t2.equals("flying")){
                  effectiveness*=0;
               }else if(t2.equals("fire")||t2.equals("rock")||t2.equals("steel")||t2.equals("poison")||t2.equals("electric")){
                  effectiveness*=2;
               }
          }else if(t1.equals("rock")){
              if(t2.equals("fighting")||t2.equals("ground")||t2.equals("steel")){
                  effectiveness*=0.5;
              }else if(t2.equals("flying")||t2.equals("fire")||t2.equals("bug")||t2.equals("ice")){
                  effectiveness*=2;
              }
          }else if(t1.equals("bug")){
              if(t2.equals("fire")||t2.equals("fighting")||t2.equals("flying")||t2.equals("poison")||t2.equals("ghost")||t2.equals("steel")||t2.equals("fairy")){
                  effectiveness*=0.5;
              }else if(t2.equals("grass")||t2.equals("psychic")||t2.equals("dark")){
                  effectiveness*=2;
              }
          }else if(t1.equals("ghost")){
              if(t2.equals("normal")){
                  effectiveness*=0;
              }else if(t2.equals("dark")){
                  effectiveness*=0.5;
              }else if(t2.equals("ghost")||t2.equals("psychic")){
                  effectiveness*=2;
              }
          }else if(t1.equals("steel")){
              if(t2.equals("rock")||t2.equals("ice")||t2.equals("fairy")){
                  effectiveness*=2;
              }else if(t2.equals("steel")||t2.equals("fire")||t2.equals("water")||t2.equals("electric")){
                  effectiveness*=0.5;
              }
          }else if(t1.equals("electric")){
              if(t2.equals("ground")){
                  effectiveness*=0;
              }else if(t2.equals("grass")||t2.equals("electric")||t2.equals("dragon")){
                  effectiveness*=0.5;
              }else if(t2.equals("water")||t2.equals("flying")){
                  effectiveness*=2;
              }
          }else if(t1.equals("psychic")){
              if(t2.equals("dark")){
                  effectiveness*=0;
              }else if(t2.equals("poison")||t2.equals("fighting")){
                  effectiveness*=2;
              }else if(t2.equals("steel")||t2.equals("psychic")){
                  effectiveness*=0.5;
              }
          }else if(t1.equals("ice")){
              if(t2.equals("flying")||t2.equals("dragon")||t2.equals("grass")||t2.equals("ground")){
                  effectiveness*=2;
              }else if(t2.equals("fire")||t2.equals("water")||t2.equals("steel")||t2.equals("ice")){
                  effectiveness*=0.5;
              }
          }else if(t1.equals("dragon")){
              if(t2.equals("steel")){
                  effectiveness*=0.5;
              }else if(t2.equals("fairy")){
                  effectiveness*=0;
              }else if(t2.equals("dragon")){
                  effectiveness*=2;
              }
          }else if(t1.equals("dark")){
              if(t2.equals("psychic")||t2.equals("ghost")){
                  effectiveness*=2;
              }else if(t2.equals("fighting")||t2.equals("dark")||t2.equals("fairy")){
                  effectiveness*=0.5;
              }
          }else if(t1.equals("fairy")){
              if(t2.equals("fire")||t2.equals("steel")||t2.equals("poison")){
                  effectiveness*=0.5;
              }else if(t2.equals("dragon")||t2.equals("fighting")||t2.equals("dark")){
                  effectiveness*=2;
              }
          }else if(t1.equals("monkey")){
              if(t2.equals("k-pop")){
                  effectiveness*=-69;
              }else{
                  effectiveness*=2;
              }
          }else if(t1.equals("k-pop")){
              if(t2.equals("monkey")){
                  effectiveness*=1000000000;
              }else{
                  effectiveness*=0;
              }
          }
              
          return effectiveness;
    }
   
    public Move(String name, int power, float accuracy, String type, int pp){
        this.name = name;
        this.power = power;
        this.accuracy = accuracy;
        this.type = type;
        this.pp=pp;
        this.basepp = pp;
        crit = false;
    }
    public Object clone(){
        try{
            return super.clone();
        }catch(CloneNotSupportedException e){
            System.out.println("crud 3");
            return null;
        }
    }
    
    public float getEffectiveness(Pokemon opp){
        return effective(type,opp.getType().get(0))*effective(type,opp.getType().get(1));
    }
    
    public String effectiveMessage(Pokemon opp){
        if(getEffectiveness(opp)>=2){
            return "It's super effective! ";
        }else if(getEffectiveness(opp)==0){
            return "It had no effect... ";
        }else if(getEffectiveness(opp)<1){
            return "It's not very effective... ";
        }else{
            return "";
        }
    }

     public float stab(Pokemon user){
        if(type.equals(user.getType().get(0))||type.equals(user.getType().get(1))){
            return 1.5f;
        }else{
            return 1.0f;
        }
    }
    
    public String getType(){
        return type;
    }
    
    public String getName(){
        return name;
    }
    
    public int getPower(){
        return power;
    }
    
    public float getAccuracy(){
        return accuracy;
    }
    
    public int getPP(){
        return pp;
    }
    public int getBasePP(){
        return basepp;
    }
    
    public void useMove(){
    	if(!name.equals("Struggle")){ 
            pp--;
    	}
    }
    
    public int getCritModifier(){
        double random=Math.random();
        if(random<0.05){
            crit=true;
            return 2;
        }else{
            crit=false;
            return 1;
        }
    }
    
    public boolean getCrit(){
        return crit;
    }
    
    public String toString(){
        return name + " (" + type + "): " + power;
    }
}
