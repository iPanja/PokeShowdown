//Fletcher Henneman
//Semi-Placeholder Battle class
public class Battle{
    public Pokemon[] party;
    public Pokemon[] eparty;
    public int selected; //The index of the current Pokemon
    public int eselected;
    
    public Battle(){
        party = new Pokemon[6];
        eparty = new Pokemon[6];
    }
    private Pokemon[] randomParty(){
        Pokemon[] p = new Pokemon[6];
        int i = 0;
        int[] taken = new int[6];
        while(i < 6){
            int rand = (int) ((Math.random() * (API.amountOfPokemon-1)) + 1);
            Pokemon poke = API.getPokemon(rand);
            if(poke != null && !contains(taken, rand)){
                p[i] = poke;
                taken[i] = rand;
                i++;
            }
        }
        //p[1].takeDamage(30);
        //p[3].takeDamage(60);
        //p[5] = API.getPokemon(p[3].getPokedex());
        //p[5] = API.getPokemon(154);
        return p;
    }
    //Ethan Kim
    private int damageCalc(Move userMove, Pokemon user, Pokemon opp){
        double random=Math.random();
        int critical=1;
        if(random<0.10)
            critical=2;
        float modifier=userMove.getEffectiveness(opp)*userMove.stab(user)*critical;
        return (int) (((((2*user.getLevel())/5+2) * userMove.getPower() * (user.getAttack()/opp.getDefense()))/50 + 2) * modifier);
    }
    public void hit(Move userMove, Pokemon user, Pokemon opp, UIController _instance){
        double random=Math.random();
        if(random<=userMove.getAccuracy()){
            opp.takeDamage(damageCalc(userMove,user,opp));
            if(userMove.getName().equals("Struggle")){
                user.takeDamage(damageCalc(userMove,user,opp)/2);
            }
            userMove.useMove();
            user.outOfPP();
        }else{
            _instance.showAlert("You Missed", "Your attack has missed, woops!");
        }
    }
    
    private Boolean contains(int[] array, int num){
        for(int i : array){
            if(i == num)
                return true;
        }
        return false;
    }
    public void generateTeams(){
        party = randomParty();
        eparty = randomParty();
    }
    
    public void handleMove(int num, UIController _instance){ //num is between 1 and 4; num-1 signifies the index of the move chosen
        if(num < 1 || num > 4) return;
        if(party[selected].isDead()) return;
        Move move = party[selected].getMove(num-1);
        //_instance.showAlert("Attack!", "You have dealt " + move.getPower() + " damage");
        //Handle move
        if(!AITurn()){
            AISwitch();
            hit(move, party[selected], eparty[eselected], _instance);
            if(eparty[eselected].isDead()){
                AISwitch();
            }
        }else{
            int speed = party[selected].getSpeed();
            int espeed = eparty[eselected].getSpeed();
            if(speed > espeed){
                userAttacksFirst(move, _instance);
            }else if(speed == espeed){
                if(Math.random() < 0.5){
                    userAttacksFirst(move, _instance);
                }else{
                    aiAttacksFirst(move, _instance);
                }
            }else{
                aiAttacksFirst(move, _instance);
            }
        }
        _instance.refreshUI();
        turnOver(_instance);
    }
       private void userAttacksFirst(Move move, UIController _instance){
            hit(move, party[selected], eparty[eselected], _instance);
            //Check if AI died
            if(eparty[eselected].isDead()){
                AISwitch();
            }else{
                System.out.println(eparty[eselected].getMove(maxDamageIndex(eparty[eselected],party[selected])));
                hit(eparty[eselected].getMove(maxDamageIndex(eparty[eselected],party[selected])),eparty[eselected],party[selected], _instance);
            }
            if(party[selected].isDead()){
                //Force user to switch
                _instance.showAlert("K.O.", "Please switch out your Pokemon");
            }
        }
        private void aiAttacksFirst(Move userMove, UIController _instance){
            System.out.println(eparty[eselected].getMove(maxDamageIndex(eparty[eselected],party[selected])));
            hit(eparty[eselected].getMove(maxDamageIndex(eparty[eselected],party[selected])),eparty[eselected],party[selected], _instance);
            //Check if User's pokemon died
            if(party[selected].isDead()){
                //Force user to switch
                _instance.showAlert("K.O.", "Please switch out your Pokemon");
            }else{
                hit(userMove, party[selected], eparty[eselected], _instance);
            }
            if(eparty[eselected].isDead()){
                AISwitch();
            }
        }
        
    public void handleSwap(int num, UIController _instance){ //num is between 1 and 6; num-1 signifies the index of the Pokemon chosen
        if(num < 1 || num > 6) return;
        int temp = num-1;
        Boolean wasDead = party[selected].isDead();
        if(!party[temp].isDead()){
            selected = temp;
        }
        if(!AITurn()){
            AISwitch();
        }else if(!wasDead){
             hit(eparty[eselected].getMove(maxDamageIndex(eparty[eselected],party[selected])),eparty[eselected],party[selected], _instance);
        }
        
        if(eparty[eselected].isDead())
            AISwitch();
        
        _instance.refreshUI();
        turnOver(_instance);
    }
    private int typeAdv(Pokemon user, Pokemon opp){
        Move oppTest = new Move("test", 10, 1.0f, opp.getType().get(0), 1);
        Move oppTest2 = null;
        if(!opp.getType().get(1).equals("")){
            oppTest2 = new Move("test2", 10, 1.0f, opp.getType().get(1), 1);
        }
        int advantage = (-1) * moveAdv(oppTest, user);
        if(oppTest2 != null){
            advantage += (-1) * moveAdv(oppTest2, user);
        }
        System.out.println("MDI: " + maxDamageIndex(user, opp));
        advantage += moveAdv(user.getMove(maxDamageIndex(user, opp)), opp);
        return advantage;
    }
    private static int moveAdv(Move userMove, Pokemon opp){
        System.out.println("UM: " + userMove);
        if(Math.abs(userMove.getEffectiveness(opp)-4)<0.1){
            return 2;
        }else if(Math.abs(userMove.getEffectiveness(opp)-2)<0.1){
            return 1;
        }else if(Math.abs(userMove.getEffectiveness(opp)-1)<0.1){
            return 0;
        }else if(Math.abs(userMove.getEffectiveness(opp)-0.5)<0.1){
            return -1;
        }else if(Math.abs(userMove.getEffectiveness(opp)-0.25)<0.1){
            return -2;
        }else{
            return -3;
        }
    }
    private int advantage(Pokemon user, Pokemon opp){
        return typeAdv(user,opp)+HPAdv(user)+speedAdv(user,opp)-HPAdv(opp);
    }
    
    private boolean hitOrSwap(double chances){
        double random=Math.random();
        if(random<=chances){
            return true;
        }else{
            return false;
        }
    }
    
    private int maxDamageIndex(Pokemon user, Pokemon opp){
        int maxDamage=0;
        int moveIndex=0;
        
        for(int i=0;i<user.getMoveset().length;i++){
            if(user.getMove(i) == null)
                break;
            if(user.getMove(i).getPP()>0){
                moveIndex=i;
                maxDamage=damageCalc(user.getMove(i),user,opp);
                break;
            }
        }
        for(int i=moveIndex;i<user.getMoveset().length;i++){
            if(user.getMove(i) == null)
                break;
            if(user.getMove(i).getPP()>0){
                if(damageCalc(user.getMove(i),user,opp)>maxDamage){
                    moveIndex=i;
                    maxDamage=damageCalc(user.getMove(i),user,opp);
                }
            }
        }
        
        return moveIndex;
    }
    public boolean AITurn(){
        int adv=advantage(party[selected],eparty[eselected]);
        if(pokemonLeft(eparty) == 1) return true;
        if(adv>0){
            return hitOrSwap(1.0);
        }else if(adv<=-9){
            return hitOrSwap(-1.0);
        }else if(adv<=-6){
            return hitOrSwap(0.0125);
        }else if(adv<=-3){
            return hitOrSwap(0.025);
        }else if(adv<0){
            return hitOrSwap(0.05);
        }else{
            return hitOrSwap(0.90);
        }
    }
    
    private static int speedAdv(Pokemon user, Pokemon opp){
        if(user.getSpeed()>opp.getSpeed()){
            return 1;
        }else if(user.getSpeed()==opp.getSpeed()){
            return 0;
        }else{
            return -1;
        }
    }
    
    private static int HPAdv(Pokemon user){
        if(user.getCurrentHP()/user.getBaseHP()==1){
            return 2;
        }else if(1.0*user.getCurrentHP()/user.getBaseHP()>=0.75){
            return 1;
        }else if(1.0*user.getCurrentHP()/user.getBaseHP()>=0.5){
            return 0;
        }else if(1.0*user.getCurrentHP()/user.getBaseHP()>=0.25){
            return -1;
        }else{
            return -2;
        }
    }
    
    private void AISwitch(){
        int maxAdvantage=advantage(party[selected],eparty[eselected]);
        int bestIndex = -1;
        for(int i=0;i<eparty.length;i++){
            if(!eparty[i].isDead()){
                if(advantage(eparty[i],party[selected])>maxAdvantage){
                    maxAdvantage=advantage(eparty[i],party[selected]);
                    bestIndex=i;
                    //currentOpp=eparty[currentOppIndex];
                }else{
                    if(bestIndex == -1)
                        bestIndex = i;
                }
            }
        }
        eselected = bestIndex == -1 ? eselected : bestIndex;
    }
    
    
    //Fletcher
    public void turnOver(UIController _instance){
        checkEndgame(_instance);
    }
    public int pokemonLeft(Pokemon[] array){
        int i = 0;
        for(Pokemon poke : array){
            if(!poke.isDead())
                i++;
        }
        return i;
    }
    public void checkEndgame(UIController _instance){
        if(pokemonLeft(party) == 0){
            if(_instance.confirmAlert("Game Over", "AI Wins! Would you like to play again?")){
                //Restart game
                _instance.restart();
            }else{
                _instance.quit();
            }
        }else if(pokemonLeft(eparty) == 0){
            if(_instance.confirmAlert("Game Over", "You Win! Would you like to play again?")){
                //Restart the game
                _instance.restart();
            }else{
                _instance.quit();
            }
        }
    }
    
}