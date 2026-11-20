package irrgarten;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author root
 */
public abstract class CombatElement {
    
    private float effect;
    private int uses;
    private Dice dice = new Dice();
    
    public CombatElement(float effect, int uses){
        this.effect = effect;
        this.uses = uses;
    }
    
    protected float produceEffect(){
        if(this.uses > 0){
            this.uses--;
            return effect;
        }
        else{
            return (float) 0.0;
        }
    }
    
    public boolean discard(){
        return dice.discardElement(uses);
    }
    
    public String toString(){
        return " [" + effect + "," + uses + "]"; 
    }
    
}
