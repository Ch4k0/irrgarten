/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

import java.util.ArrayList;

/**
 *
 * @author ch4k0
 */
public class Game {
    private static final int MAXROUNDS = 10;
    private int currentPlayerIndex;
    private String log;
    
    private ArrayList<Player> players = new ArrayList<>();
    private Player currentPlayer;
    private ArrayList<Monster> monsters = new ArrayList<>();
    private Labyrinth labyrinth;
    
    Dice dice1 = new Dice();
    
    public Game(int nPlayers){
        
        log = "";
        
        for(int i = 0;i<nPlayers; i++){
            Player auxP = new Player((char) (i+48), dice1.randomIntelligence(), dice1.randomStrenght());
            players.add(auxP);
        }
        
        this.currentPlayerIndex = 0;
        currentPlayer = players.get(this.currentPlayerIndex);
        
//        labyrinth = new Labyrinth(dice1.randomPos(15), dice1.randomPos(15), dice1.randomPos(15), dice1.randomPos(15)); 
        
        labyrinth = new Labyrinth(10,10,1,0); 

        configureLabyrinth();
        labyrinth.spreadPlayers(players);
    }
    
    public boolean finished(){
        return labyrinth.haveAWinner();
    }
    
    public boolean nextStep(Directions preferredDirection){
        log = "";
        boolean dead = currentPlayer.dead();
        
        if(!dead){
            Directions direction = actualDirection(preferredDirection);
            if(direction != preferredDirection){
                logPlayerNoOrders();
            }
            Monster monster = labyrinth.putPlayer(direction, currentPlayer);
            
            if(monster == null){
                logNoMonster();
            }
            else{
                GameCharacter winner = combat(monster);
                manageReward(winner);
                
            }
        }
        else{
            manageResurrection();
        }
        boolean endGame = finished();
        
        if(!endGame){
            nextPlayer();
        }
        return endGame;
    }
    
    public GameState getGameState(){
        
        String sPlayers = "";
        String sMonsters = "";
        
        for(int i = 0;i < monsters.size(); i++){
            sMonsters += monsters.get(i).toString();
            sMonsters += "\n";
        }
        for(int i = 0;i < players.size(); i++){
            sPlayers += players.get(i).toString();
            sPlayers += "\n";
        }
        
        GameState auxGS = new GameState(labyrinth.toString(), sPlayers, sMonsters, currentPlayerIndex, finished(), log);
        
        return auxGS;
    }
    
    private void configureLabyrinth(){
        labyrinth.addBlock( Orientation.HORIZONTAL, 2, 1, 3);
        labyrinth.addBlock( Orientation.VERTICAL, 2, 4, 5);
        labyrinth.addBlock( Orientation.HORIZONTAL, 5, 2, 5);
        labyrinth.addBlock( Orientation.VERTICAL, 3, 7, 4);
        labyrinth.addBlock( Orientation.VERTICAL, 3, 6, 2);
        labyrinth.addBlock( Orientation.VERTICAL, 8, 5, 1);
        
        Monster m1 = new Monster("Pepe", 3, 5);
        Monster m2 = new Monster("Luca", 2, 6);
        Monster m3 = new Monster("Juan", 4, 3);
        
        labyrinth.addMonster(7, 1, m1);
        labyrinth.addMonster(8, 8, m2);
        labyrinth.addMonster(5, 2, m3);
        
//        ArrayList<Player> players = new ArrayList<>();
//        
//        Player p1 = new Player('1', 10, 5);
//        Player p2 = new Player('2', 5 , 3);
//        Player p3 = new Player('3', 6, 5);
//        
//        players.add(p1);
//        players.add(p2);
//        players.add(p3);
//        
//        
//        labyrinth.spreadPlayers(players);
    }
    
    private void nextPlayer(){
        this.currentPlayerIndex += 1;
        if(this.currentPlayerIndex == players.size()){
            this.currentPlayerIndex = 0;
        }
        currentPlayer = players.get(this.currentPlayerIndex);        
    }
    
    private Directions actualDirection(Directions preferredDirection){
        int currentRow = currentPlayer.getRow();
        int currentCol = currentPlayer.getCol();
        
        Directions[] validMoves = labyrinth.validMoves(currentRow, currentCol);
        
        Directions output = currentPlayer.move(preferredDirection, validMoves);
        
        return output;
    }
    
    private GameCharacter combat(Monster monster){
        int rounds = 0;
        GameCharacter winner = GameCharacter.PLAYER;
        boolean lose = false;
        
        float playerAttack = currentPlayer.attack();
        lose = monster.defend(playerAttack);
        
        while( (!lose) && (rounds < MAXROUNDS) ){
            winner = GameCharacter.MONSTER;
            rounds++;
            
            float monsterAttack = monster.attack();
            lose = currentPlayer.defend(monsterAttack);
            
            if(!lose){
                playerAttack = currentPlayer.attack();
                winner = GameCharacter.PLAYER;
                lose = monster.defend(playerAttack);
            }
        }
        logRounds(rounds, MAXROUNDS);
        return winner;
    }
    
    private void manageReward(GameCharacter winner){
        if(winner == GameCharacter.PLAYER){
            currentPlayer.receiveReward();
            logPlayerWon();
        }
        else{
            logMonsterWon();
        }
    }
   
    private void manageResurrection(){
        boolean resurrect = dice1.resurrectPlayer();
        
        if(resurrect){
            currentPlayer.resurrect();
            Player fuzzy = new FuzzyPlayer(currentPlayer);
            players.set(currentPlayerIndex, fuzzy);
            currentPlayer = fuzzy;
            logResurrected();
        }
        else{
            logPlayerSkipTurn();
        }
    }
    
    private void logPlayerWon(){
        log += "El jugador " + currentPlayer.getNumber() + " ha ganado el combate.\n";
    }
    
    private void logMonsterWon(){
        log += "El monstruo ha ganado el combate.\n";
    }    
    
    private void logResurrected(){
        log += "El jugado " + currentPlayer.getNumber() + " ha resucitado.\n";
    }    
    
    private void logPlayerSkipTurn(){
        log += "El jugador " + currentPlayer.getNumber() + " ha perdido el turno por estar muerto.\n";
    }    
    
    private void logPlayerNoOrders(){
        log += "El jugador " + currentPlayer.getNumber() + " no ha podido seguir las ordenes.\n";
    }    
    
    private void logNoMonster(){
        log += "El jugador " + currentPlayer.getNumber() + " se ha movido a una celda vacía o no se ha podido mover.\n";
    }    
    
    private void logRounds(int rounds, int max){
        log += "Se han producido " + rounds + " de " + max + " rondas de combate.\n";
    }
}
