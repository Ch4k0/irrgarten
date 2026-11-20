/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 *
 * @author ch4k0
 */
public class Player extends LabyrinthCharacter{
    private static final int MAXWEAPONS = 2;
    private static final int MAXSHIELDS = 3;
    private static final int INITIALHEALTH = 1;
    private static final int HITS2LOSE = 3;
    private char number;
    private int consecutiveHits = 0;
    
    private ArrayList<Weapon> weapons = new ArrayList<>();
    private ArrayList<Shield> shields = new ArrayList<>();
    
    Dice dice1 = new Dice();
    
    
    public Player(char number, float intelligence, float strength){
        super("Player #" + number, intelligence, strength, INITIALHEALTH);
        this.number = number;
    }
    
    public Player(Player other){
        super(other);
        this.number = other.number;
    }

    public void resurrect(){
        this.weapons.clear();
        this.shields.clear();
        
        setHealth(INITIALHEALTH);
        
        this.consecutiveHits = 0;
    }
    
    public char getNumber(){
        return this.number;
    }
    
    public Directions move(Directions direction, Directions[] validMoves){
        ArrayList<Directions> moveList = new ArrayList<>(Arrays.asList(validMoves));
        int size = moveList.size();
        boolean contained = moveList.contains(direction);
        Directions result;
        
        if( (size > 0) && (!contained) ){
            Directions firstElement = validMoves[0];
            result = firstElement;
        }
        else{
            result = direction;
        }
        return result;
    }
    
    public float attack(){
        return (getStrength() + sumWeapons());
    }
    
    public boolean defend(float receivedAttack){
        return manageHit(receivedAttack);
    }
 
    public void receiveReward(){
        int wReward = dice1.weaponsReward();
        int sReward = dice1.shieldsReward();     
        Weapon wNew;
        Shield sNew;
       
        for(int i = 1; i<= wReward; i++){
            wNew = newWeapon();
            receiveWeapon(wNew);
        }
        for(int i = 1; i<= sReward; i++){
            sNew = newShield();
            receiveShield(sNew);
        }
        
        float extraHealth = dice1.healthReward() + getHealth(); 
        setHealth(extraHealth);
    }
    
    public String toString(){
        String salida = "";

        salida += super.toString();
        salida += "Golpes consecutivos: " + this.consecutiveHits + "\n";
        
        for(int i=0;i<weapons.size();i++){
            salida += weapons.get(i).toString();
        }
        for(int i=0;i<shields.size();i++){
            salida += shields.get(i).toString();
        }
        return salida;
    }
    
    private void receiveWeapon(Weapon w){
        Weapon wi;
        boolean discard;
        
        for(int i=0;i<weapons.size();i++){
            wi = weapons.get(i);
            discard = wi.discard();
            if(discard){
                weapons.remove(wi);
            }
        }
        
        int size = weapons.size();
        
        if(size < MAXWEAPONS){
            weapons.add(w);
        }  
    }

    private void receiveShield(Shield s){
        Shield si;
        boolean discard;
        for(int i=0;i<shields.size();i++){
            si = shields.get(i);
            discard = si.discard();
            if(discard){
                shields.remove(si);
            } 
        }
        
        int size = shields.size();
        
        if(size < MAXSHIELDS){
            shields.add(s);
        }
    }
    
    private Weapon newWeapon(){
        float pow = dice1.weaponPower();
        int uses = dice1.usesLeft();
        
        Weapon w = new Weapon(pow, uses);
        return w;
    }
    
    private Shield newShield(){
        float prot = dice1.shieldPower();
        int uses = dice1.usesLeft();
    
        Shield s = new Shield(prot, uses);
        return s;
    }
    
    protected float sumWeapons(){
        float sum = 0;
        
        for(int i = 0; i< weapons.size(); i++){
            sum += weapons.get(i).attack();
        }
        return sum;
    }
    
    protected float sumShields(){
        float sum = 0;
        
        for(int i = 0; i< shields.size(); i++){
            sum += shields.get(i).protect();
        }
        return sum;        
    }
    
    protected float defensiveEnergy(){
        return ( getIntelligence() + sumShields() );
    }
    
    private boolean manageHit(float receivedAttack){
        boolean lose;
        float defense = defensiveEnergy();
        if(defense < receivedAttack){
            gotWounded();
            incConsecutiveHits();
        }
        else{
            resetHits();
        }
        if( (consecutiveHits == HITS2LOSE) || (dead()) ){
            resetHits();
            lose = true;
        }
        else{
            lose = false;
        }
        return lose;
    }
    
    private void resetHits(){
        this.consecutiveHits = 0;
    }
    
    private void incConsecutiveHits(){
        this.consecutiveHits += 1;
    }
    
}