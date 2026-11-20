/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten.UI;

import irrgarten.Directions;
import irrgarten.GameState;

/**
 *
 * @author root
 */
public interface UI {
    public void showGame(GameState gameState);
    public Directions nextMove();
}
