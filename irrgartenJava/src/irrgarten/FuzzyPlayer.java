/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

import java.util.ArrayList;

/**
 *
 * @author root
 */
public class FuzzyPlayer extends Player{
 
    Dice dice = new Dice();
    
    public FuzzyPlayer(Player other){
        super(other);
    }
    
    public Directions move(Directions direction, Directions[] validMoves){
        Directions finalMove = dice.nextStep(direction, validMoves, getIntelligence());
        return super.move(finalMove, validMoves);
    }
    
    public float attack(){
        return sumWeapons() + dice.intensity(getStrength());
    }
    
    protected float defensiveEnergy(){
        return sumShields() + dice.intensity(getIntelligence());
    }
    
    public String toString(){
        return "Fuzzy" + super.toString();
    }
    
}
