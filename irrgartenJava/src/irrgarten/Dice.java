/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author ch4k0
 */
public class Dice {
    
    private static final int MAX_USES = 5;
    private static final float MAX_INTELLIGENCE = 10.0f;
    private static final float MAX_STRENGHT = 10.0f;
    private static final float RESURRECT_PROB = 0.3f;
    private static final int WEAPONS_REWARD = 2;
    private static final int SHIELDS_REWARD = 3;
    private static final int HEALTH_REWARD = 5;
    private static final int MAX_ATTACK = 3;
    private static final int MAX_SHIELD = 2;
    private Random generator = new Random();
    
    public int randomPos(int max){
        return generator.nextInt(max);
    }
    
    public int whoStarts(int nplayers){
        return generator.nextInt(nplayers);
    }
    
    public float randomIntelligence(){
        return generator.nextFloat(this.MAX_INTELLIGENCE);
    }

    public float randomStrenght(){
        return generator.nextFloat(this.MAX_STRENGHT);
    }
    
    public boolean resurrectPlayer(){
        return generator.nextBoolean();
    }
    
    public int weaponsReward(){
        return generator.nextInt(WEAPONS_REWARD);
    }
    
    public int shieldsReward(){
        return generator.nextInt(SHIELDS_REWARD);
    }

    public int healthReward(){
        return generator.nextInt(HEALTH_REWARD);
    }
    
    public float weaponPower(){
        return generator.nextFloat(MAX_ATTACK);
    }
    
    public float shieldPower(){
        return generator.nextFloat(MAX_SHIELD);
    }
    
    public int usesLeft(){
        return generator.nextInt(MAX_USES);
    }
    
    public float intensity(float competence){
        return generator.nextFloat(competence);
    }
    
    public boolean discardElement(int usesLeft){
        boolean discard;
       
        if(generator.nextFloat() <= (usesLeft / (float)MAX_USES)){
            discard = false;
        }
        else{
            discard = true;
        }
        
        return discard;
    }
    
    public Directions nextStep(Directions preference, Directions[] validMoves,float intelligence){
        
        float prob = intelligence / 10.0f;
        Directions resp;
        if(generator.nextFloat() < prob){
            resp = preference;
        }
        else{
            int i = generator.nextInt(validMoves.length);
            resp = validMoves[i];
        }
        
        return resp;
    }
}
