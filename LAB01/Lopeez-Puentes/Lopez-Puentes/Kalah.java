import java.awt.*;
import java.util.*;
import javax.swing.JOptionPane;
/**
 * Kalah Game
 * 
 * @author (Juan Sebastian Puentes Julio and Julian Camilo Lopez Barrero) 
 * @version 1.0 (05/02/2025)
 */
public class Kalah {

    private Rectangle board;
    private ArrayList<Pit> Pits = new ArrayList<>();
    private ArrayList<Pit> Pits2 = new ArrayList<>();
    private int player2;
    private boolean existingPlayer;
    private int pits = 6;
    private int limitPits = 1;
    private boolean isPlayerOneTurn = true;
    
    /**
     * Constructor
     */
    public Kalah() {
        
        board = new Rectangle();
        board.changeColor("white");
        board.changeSize(1800, 1800);
        board.moveLeft();
        board.makeVisible();
        putPitsUp(pits);
        putPitsDown(pits);
        putPitsRight(limitPits);
        putPitsLeft(limitPits);
        
    } 
    //Cycle 1 Interface
    /**
    Put a specific number of pits at the top
    */
    private void putPitsUp(int pits){
        
    int countX = 1325;
    int countY = 0;
    
    for(int i = 0 ; i< pits; i++){
        Pit gap = new Pit(false);
        gap.putSeeds(3);
        Pits.add(gap);
        gap.moveTo(countX,0);
        gap.makeVisible();
        
        for(int j = 0; j < pits;j++){
            Pit gap2 = new Pit(false);
            gap.makeVisible();
            gap.moveTo(countX,100);
            countX -= 30;
            }
    }
}
       
    /**
     * Put a specific number of pits at the bottom
     */
    private void putPitsDown(int pitsDown){
        
        int auxX = 125;
        int auxY = 125;
        
        for(int i = 0 ; i< pitsDown; i++){
            Pit gap = new Pit(false);
            gap.putSeeds(3);
            Pits2.add(gap);
            gap.changeColors("green","black");
            gap.moveTo(auxX,auxY);
            gap.makeVisible();
            
            for(int j = 0; j < pitsDown; j++){
                Pit gap3 = new Pit(false);
                gap.makeVisible();
                gap.moveTo(auxX,600);
                auxX += 30;
                }   
        }  
    }
        
    /**
    * Put a the pit of the left(Is bigger)
    */
    private void putPitsLeft(int pitsLat){
        
        Pit gap = new Pit(true);
        Pits.add(gap);
        gap.moveTo(120,300);
        gap.makeVisible();    
    }
        
    /**
    * Put a the pit of the right (Is bigger)
    */
    private void putPitsRight(int pitsLat){
        
        Pit gap = new Pit(true);
        Pits2.add(gap);
        gap.changeColors("green","black");
        gap.moveTo(1250,300);
        gap.makeVisible(); 
    }
    
    //Cycle 2 Player´s Movements And Reset The game
     /**
     * Movements Player 1
     */
    public void resetGame(){
        
        for(Pit pit: Pits){
            pit.makeInvisible();
        }
        Pits.clear();
        for(Pit pit: Pits2){
            pit.makeInvisible();
        }
        
        Pits2.clear();
        JOptionPane.showMessageDialog(null, "The game has been reset");
        board = new Rectangle();
        board.changeColor("white");
        board.changeSize(1800, 1800);
        board.moveLeft();
        board.makeVisible();
        putPitsUp(pits);
        putPitsDown(pits);
        putPitsRight(limitPits);
        putPitsLeft(limitPits);
    }
    
    /**
     * Method to change the turns of the players
     */
    public void moveTo(int hole) {
    if (existingPlayer) {
        playerOne(hole);
    } 
    
    else {
        playerTwo(hole);
        }
    }
    
    /**
     * Movements Player1
     */
    private void playerOne(int casa) {
        if (casa < 0 || casa >= Pits.size() - 1) {
            System.out.println("Invalid. Not existing gap.");
            return;
        }
        Pit selectedPit = Pits.get(casa);
        if (selectedPit.seeds() == 0) {
            System.out.println("No seeds in this pit.");
            return;
        }
        int seeds = selectedPit.seeds();
        selectedPit.removeSeeds(seeds);
        int currentPosition = casa + 1;
        int lastPosition = auxPlayer1(currentPosition, seeds);
        if (lastPosition == Pits.size() - 1) {
            existingPlayer = true;
        } else {
            existingPlayer = false;
        }
    }
    
    /**
     * Movements Player2
     */
    private void playerTwo(int casa) {
        if (casa < 0 || casa >= Pits2.size() - 1) { 
            System.out.println("Invalid. Not existing gap.");
            return;
        }
        Pit selectedPit = Pits2.get(casa);
        if (selectedPit.seeds() == 0) {
            System.out.println("No seeds in this pit.");
            return;
        }
        int seeds = selectedPit.seeds();
        selectedPit.removeSeeds(seeds);
        int currentPosition = casa + 1;
        int lastPosition = auxPlayer2(currentPosition, seeds);
        if (lastPosition == Pits2.size() - 1) {
            existingPlayer = false; 
        } else {
            existingPlayer = true;
        }
    }   
    
    /**
     * Auxiliar Method Movements Player1
     */
    private int auxPlayer1(int currentPosition, int seeds) {
    int lastPosition = -1;
    while (seeds > 0) {
        while (currentPosition < Pits.size() && seeds > 0) {
            Pits.get(currentPosition).putSeeds(1);
            lastPosition = currentPosition;
            seeds -= 1;
            currentPosition += 1;
        }
        if (seeds > 0) {
            currentPosition = 0;
            while (currentPosition < Pits2.size() - 1 && seeds > 0) {
                Pits2.get(currentPosition).putSeeds(1);
                lastPosition = currentPosition;
                seeds -= 1;
                currentPosition += 1;
            }
            if (seeds > 0) {
                currentPosition = 0;
            }
        }
    }
    return lastPosition;
    }
    
    /**
     * Auxiliar Method Movements Player2
     */
    private int auxPlayer2(int currentPosition, int seeds) {
        
    int lastPosition = -1;
    
    while (seeds > 0) {
        while (currentPosition < Pits2.size() && seeds > 0) {
            Pits2.get(currentPosition).putSeeds(1);
            lastPosition = currentPosition;
            seeds -= 1;
            currentPosition += 1;
        }
        if (seeds > 0) {
            currentPosition = 0;
            while (currentPosition < Pits.size() - 1 && seeds > 0) {
                Pits.get(currentPosition).putSeeds(1);
                lastPosition = currentPosition;
                seeds -= 1;
                currentPosition += 1;
            }
            if (seeds > 0) {
                currentPosition = 0;
            }
        }
    }
    return lastPosition;
    }

    
    //Cycle 3 Rules
    /**
     * Axiliar Method To Verify if the pits of the player1 are emptys
     */
    private boolean isPlayer1Empty() {
        
    for (int i = 0; i < 6; i++) {
        if (Pits.get(i).seeds() > 0) {
            return false;
        }
    }
        return true;
    }
    
    /**
     * Axiliar Method To Verify if the pits of the player2 are emptys
     */
    private boolean isPlayer2Empty() {
        
    for (int i = 0; i < 6; i++) {
        if (Pits2.get(i).seeds() > 0) {
            return false;
        }
    }
        return true;
    }
    
    /**
     * Method To verify The Game Status
     */
    public void statusGame(){
        System.out.println("Almacen N :" + Pits.get(6).seeds()  + "       " +  "Almacen S :" + Pits2.get(6).seeds());
    }
    
    /**
     * Method to verify Win
     */
    public void win() {
        
    boolean player1Empty = isPlayer1Empty();
    boolean player2Empty = isPlayer2Empty();
    
    if(player1Empty || player2Empty){
        int player1Score = Pits.get(6).seeds();
        int player2Score = Pits2.get(6).seeds();
        if (player1Score > player2Score) {
            JOptionPane.showMessageDialog(null, "Player 1 Wins Congratulations");
        } else if (player2Score > player1Score) {
            JOptionPane.showMessageDialog(null, "Player 2 Wins Congratulations");
        } else {
            JOptionPane.showMessageDialog(null, "Tie Play Again");
            }
        resetGame();
    }
    else{
        JOptionPane.showMessageDialog(null, "No Conditions To Verify Win");
        }
    }
}





