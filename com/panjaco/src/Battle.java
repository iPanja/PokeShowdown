package com.panjaco.src;
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
        selected = 0;
        eselected = 0;
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
        int critical = userMove.getCritModifier();
        
        float modifier=userMove.getEffectiveness(opp)*userMove.stab(user)*critical;
        return (int) (((((2*user.getLevel())/5+2) * userMove.getPower() * (user.getAttack()/opp.getDefense()))/50 + 2) * modifier);
    }
    
    //Diego Urena
    public Boolean hit(Move userMove, Pokemon user, Pokemon opp){
        double random=Math.random();
        if(random<=userMove.getAccuracy()){
            opp.takeDamage(damageCalc(userMove,user,opp));
            if(userMove.getName().equals("Struggle")){
                user.takeDamage(damageCalc(userMove,user,opp)/2);
            }
            userMove.useMove();
            user.outOfPP();
            return true;
        }else{
            return false;
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
        /*
         * AITurn() AISwitch()
         * User Attacks: hit(move, party[selected], eparty[eselected], _instance);
         * AI Attacks: hit(eparty[eselected].getMove(maxDamageIndex(eparty[eselected],party[selected])),eparty[eselected],party[selected], _instance);
         * 
         */
        
        Decision userDecision = new Decision(move, party[selected].getName(), move.effectiveMessage(eparty[eselected]));
        handleTurn(userDecision, _instance);
    }    
    public void handleSwap(int num, UIController _instance){ //num is between 1 and 6; num-1 signifies the index of the Pokemon chosen
        if(num < 1 || num > 6) return;
        int temp = num-1;
        if(temp == selected) return;
        Boolean wasDead = party[selected].isDead();
        
        handleTurn(new Decision(temp, party[temp].getName(), wasDead), _instance);
    }
    
    private void handleTurn(Decision userDecision, UIController _instance){
        Decision aiDecision = getAIDecision();
        
        //Swapping
        if(userDecision.getType() == type.SWAP){ //If user SWAPS
            selected = userDecision.getSwapIndex();
            if(aiDecision.getType()==type.ATTACK){
                aiDecision.setEffectiveness(aiDecision.getMove().effectiveMessage(party[selected])); //redo effectiveness for new user Pokemon
            }
            
            if(userDecision.isDeadSwap()){ //If user is swapping after their Pokemon died, the AI can not retaliate
                //Update UI
                _instance.refreshUI(userDecision, aiDecision);
                _instance.setFriendlyDescription("Switched out to " + userDecision.getName() + ". ");
                _instance.setEnemyDescription("");
                turnOver(_instance);
                return;
            }
        }
        if(aiDecision.getType() == type.SWAP){
            eselected = aiDecision.getSwapIndex();
            if(userDecision.getType() == type.ATTACK)
                userDecision.setEffectiveness(userDecision.getMove().effectiveMessage(eparty[eselected])); //Redo effectiveness for new enemy target
        }
        //Attacking
        Move userMove = userDecision.getMove();
        Move aiMove = aiDecision.getMove();
        if(userDecision.getType() == type.ATTACK && aiDecision.getType() == type.ATTACK){
            int userSpeed = party[selected].getSpeed();
            int aiSpeed = eparty[eselected].getSpeed();
            Boolean userFirst;
            if(userSpeed > aiSpeed){
                //User first
                userFirst = true;
            }else if(userSpeed == aiSpeed){
                //Roll to see
                if(Math.random() < 0.5){
                    //User first
                    userFirst = true;
                }else{
                    //AI first
                    userFirst = false;
                }
            }else{
                //AI first
                userFirst = false;
            }
            Boolean a = attack(userDecision, aiDecision, userFirst, _instance);
            Boolean b = false;
            if(!a)
                b = attack(userDecision, aiDecision, !userFirst, _instance);
                
            //userFirst = FALSE
            //a = TRUE
            //b = FALSE
                
            //Used a move, then died
            //Decision.setExtra(Decision.getDescription())
            if(b){
                if(userFirst) userDecision.setExtra(userDecision.getDescription());
                else aiDecision.setExtra(aiDecision.getDescription());
            }
        }else if(userDecision.getType() == type.ATTACK){
            Boolean a = attack(userDecision, aiDecision, true, _instance);
            if(a) //AI Died
                aiDecision.setExtra(aiDecision.getDescription());
        }else if(aiDecision.getType() == type.ATTACK){
            Boolean a = attack(userDecision, aiDecision, false, _instance);
            if(a) //User Died
                userDecision.setExtra(userDecision.getDescription());
        }
        
        //Update UI
        _instance.refreshUI(userDecision, aiDecision);
        turnOver(_instance);
    }
    private Boolean attack(Decision userDecision, Decision aiDecision, Boolean userFirst, UIController _instance){
        Move userMove = userDecision.getMove();
        Move aiMove = aiDecision.getMove();
        if(userFirst){
            if(!hit(userMove, party[selected], eparty[eselected]))
                userDecision.setMissed(true);
            else{
                if(eparty[eselected].isDead() && pokemonLeft(eparty) >= 1){
                    int temp = AISwitch();
                    //_instance.setEnemyDescription(eparty[eselected].getName() + " has fainted! Switched to " + eparty[temp].getName());
                    
                    if(eparty[eselected].getName() != "Eric")
                        aiDecision.overrideMessage(eparty[eselected].getName() + " has fainted! Switched to " + eparty[temp].getName() + ". ");
                    else
                        aiDecision.overrideMessage("Eric's gotta go! Switched to " + eparty[temp].getName() + ". ");
                    eselected = temp;
                    return true;
                }
            }
        }else{
            if(!hit(aiMove, eparty[eselected], party[selected]))
                aiDecision.setMissed(true);
            else{
                if(party[selected].isDead()){
                    //_instance.setFriendlyDescription(party[selected].getName() + "has fainted!");
                    if(party[selected].getName() != "Eric")
                        userDecision.overrideMessage(party[selected].getName() + " has fainted! ");
                    else
                        userDecision.overrideMessage("Eric's gotta go! ");
                    return true;
                }
            }
        }
        return false;
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
        int index = maxDamageIndex(user, opp);
        advantage += moveAdv(user.getMove(maxDamageIndex(user, opp)), opp);
        return advantage;
    }
    private static int moveAdv(Move userMove, Pokemon opp){
        //System.out.println("UM: " + userMove);
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
    public Decision getAIDecision(){
        if(AITurn() || pokemonLeft(eparty) == 1 || AISwitch() == eselected){
            //(Attack) Find best move to use
            Move m = eparty[eselected].getMove(maxDamageIndex(eparty[eselected],party[selected]));
            return new Decision(m, eparty[eselected].getName(), m.effectiveMessage(party[selected]));
        }else{
            //(Swap) Find best Pokemon to swap to
            int index = AISwitch();
            return new Decision(index, eparty[index].getName());
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
    
    private int AISwitch(){
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
        return bestIndex == -1 ? eselected : bestIndex;
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