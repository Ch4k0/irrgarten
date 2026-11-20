/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

/**
 *
 * @author root
 */
public class WeaponCardDeck extends CardDeck<Shield>{
    
    protected void addCards(){
        addCard(new Shield(2.0f, 10));
        addCard(new Shield(5.2f, 5));
        addCard(new Shield(1.0f, 20));
        addCard(new Shield(0.4f, 15));
    }
}
