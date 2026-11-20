/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package irrgarten.Main;
import irrgarten.UI.Interfaz;
import irrgarten.controller.Controller;
import irrgarten.Game;

/**
 *
 * @author ch4k0
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {

        Game game = new Game(3);

            Interfaz view = new Interfaz(); 

            Controller controller = new Controller(game, view);

            controller.play();

            System.out.println("Game over!");
            
    }
}