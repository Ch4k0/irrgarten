package irrgarten.Main;

import irrgarten.Game;
import irrgarten.UI.Interfaz;
import irrgarten.UI.TextUI;
import irrgarten.UI.UI;
import irrgarten.controller.Controller;

public class Main {

    public static void main(String[] args) {
        Game game = new Game(3);

        UI view;
        if (args.length > 0 && args[0].equals("--text")) {
            view = new TextUI();
        } else {
            view = new Interfaz();
        }

        Controller controller = new Controller(game, view);
        controller.play();

        System.out.println("Game over!");
    }
}