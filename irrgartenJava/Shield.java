/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

/**
 *
 * @author ch4k0
 */
public class Shield extends CombatElement{
    private float protection;
    private int uses;
    
    public Shield(float protection, int uses){
        super(protection,uses);
    }
    
    public float protect(){
        return this.produceEffect();
    }               
    
    public String toString() {
        return "S" + super.toString();
    }
}
