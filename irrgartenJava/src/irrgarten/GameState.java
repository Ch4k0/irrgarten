/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

/**
 *
 * @author root
 */
public class GameState {
    private static String labyrinth;
    private static String players;
    private static String monsters;
    private static int currentPlayer;
    private static boolean winner;
    private static String log;
 
    public GameState(String labyrinth, String players, String monsters, int currentPlayer, boolean winner, String log){
        this.labyrinth = labyrinth;
        this.players = players;
        this.monsters = monsters;
        this.currentPlayer = currentPlayer;
        this.winner = winner;
        this.log = log;
    }

    public static String getLabyrinth() {
        return labyrinth;
    }

    public static String getPlayers() {
        return players;
    }

    public static String getMonsters() {
        return monsters;
    }

    public static int getCurrentPlayer() {
        return currentPlayer;
    }

    public static boolean isWinner() {
        return winner;
    }

    public static String getLog() {
        return log;
    }    
}
