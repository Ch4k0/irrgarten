/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

/**
 *
 * @author root
 */
public class ShieldCardDeck extends CardDeck<Weapon>{
    protected void addCards(){
        addCard(new Weapon(2.0f, 3));
        addCard(new Weapon(5.2f, 5));
        addCard(new Weapon(1.0f, 2));
        addCard(new Weapon(3.4f, 10));
    }
}
