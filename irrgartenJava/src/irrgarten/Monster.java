/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

/**
 *
 * @author ch4k0
 */
public class Monster extends LabyrinthCharacter{
    private static final int INITIALHEALTH = 5;
    
    Dice dice = new Dice();
    
    public Monster(String name, float intelligence, float strength){
        super(name,intelligence,strength, INITIALHEALTH);
    }
    
    public float attack(){
        return dice.intensity(getStrength());
    }
    
    public boolean defend(float attack){
        boolean isDead = dead();
        
        if(!isDead){
            float defensiveEnergy = dice.intensity(getIntelligence());
            if(defensiveEnergy < attack){
                gotWounded();
                isDead = dead();
            }
        }
        return isDead;
    }

    public String toString(){
        return "\tMonstruo\n" + super.toString();
    }
}
    