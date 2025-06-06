package domain;

import java.awt.Color;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;
public class Battle implements Serializable {
    /** List of trainers participating in the battle */
    ArrayList<Trainer> turnTrainers;
    
    /** Index indicating whose turn it is currently */
    private int turnIndex;
    
    /** Random generator for coin toss and other chance-based mechanics */
    private Random random;
    
    /** Flag indicating if the battle has ended */
    private boolean isOver;
    
    /** Reference to the trainer who won the battle (null if battle ongoing) */
    private Trainer winner;

    /** Stores the last action taken in the battle */
    private String lastAction = "¿Decide?";

    /**
     * Constructor for creating a new battle between two trainers
     * 
     * @param trainer1 The first trainer in the battle
     * @param trainer2 The second trainer in the battle
     */
    public Battle(Trainer trainer1, Trainer trainer2){
        turnTrainers = new ArrayList<>();
        turnTrainers.add(trainer1);
        turnTrainers.add(trainer2);
        turnIndex = 0;
        winner = null;
        isOver = false;
    }
    
    
    /**
     * Executes a movement/attack for the current trainer
     * Updates battle state and determines AI's next decision
     * 
     * @param move The name of the movement to execute
     * @throws PoobkemonException If there is an issue executing the movement
     */
    public void executeMovement(String move) throws PoobkemonException{ 
        Trainer current = getCurrentTrainer();
        Trainer opponent = getOpponentTrainer();
        System.out.println(current.getName());
        current.getPokemonInUse().affectPokemonStatus();
        current.pokemonMovement(move, opponent.getPokemonInUse());
        afterAction();
    }

    /**
     * Changes the current trainer's active Pokemon
     * Updates battle state and determines AI's next decision
     * 
     * @param pokemon The name of the Pokemon to switch to
     * @throws PoobkemonException If there is an issue changing the Pokemon
     */
    public void changePokemon(String pokemon) throws PoobkemonException{
        Trainer current = getCurrentTrainer();
        Trainer opponent = getOpponentTrainer();
        current.getPokemonInUse().affectPokemonStatus();
        current.changePokemon(pokemon);
        afterAction();
    }

    /**
     * Uses an item from the current trainer's inventory
     * Updates battle state afterward
     * 
     * @param item The name of the item to use
     * @throws PoobkemonException If there is an issue using the item
     */
    public void useItem(String item) throws PoobkemonException{
        System.out.println("LLEGAS A BATALLA?" + item);
        System.out.println(getCurrentTrainer().getName());
        getCurrentTrainer().useItem(item);
        afterAction();
    }

    /**
     * Performs post-action checks
     * Checks if the battle state has changed after an action
     */
    public void afterAction() throws PoobkemonException{
        Trainer current = getCurrentTrainer();
        Trainer opponent = getOpponentTrainer();
        lastAction = current.decide(opponent.getPokemonInUse());
        verifyTurnMachine();
        checkBattleState();
    }
    /**
     * Verifies if an AI trainer's turn should be advanced
     * If the last action indicates the AI isn't deciding, advances to the next turn
     */
    public void verifyTurnMachine(){
        Trainer current = getCurrentTrainer();
        Trainer opponent = getOpponentTrainer();
        if (lastAction.equals("Ya decidi")) {
            lastAction = opponent.decide(current.getPokemonInUse());
            System.out.println("Ya jugue maquina?: " + lastAction);
            if (lastAction.equals("Ya decidi")){
                advanceTurn();
            }
        }
        else{
            advanceTurn();
        }
    }

    /**
     * Gets a list of movement names available to the current trainer's Pokemon
     * 
     * @return ArrayList of movement names
     */
    public ArrayList<String> getMovementsStringCurrent(){
        System.out.println(lastAction);
        return getCurrentTrainer().getPokemonInUse().getMovementsString();
    }
    
    /**
     * Gets a list of movement names available to the opponent's Pokemon
     * 
     * @return ArrayList of movement names
     */
    public ArrayList<String> getMovementsStringOponent(){
        return getOpponentTrainer().getPokemonInUse().getMovementsString();
    }

    /**
     * Advances to the next trainer's turn
     * Cycles through the list of trainers
     */
    private void advanceTurn(){
        turnIndex = (turnIndex + 1) % turnTrainers.size();
    }

    /**
     * Sets the turn to the specified trainer
     * 
     * @param trainer The trainer whose turn to set
     */
    private void setTurn(Trainer trainer){
        int index = turnTrainers.indexOf(trainer);
        if (index != -1){
            turnIndex = index;
        }
    }
    
    /**
     * Gets the remaining PP (Power Points) for a specific move in battle
     * 
     * @param name The name of the move
     * @return The remaining PP for the specified move
     * @throws PoobkemonException If the move is not found
     */
    public int getPPInBattle(String name) throws PoobkemonException{
        return getCurrentTrainer().getPokemonInUse().getPPByName(name);
    }

    /**
     * Resets the turn back to the first trainer
     */
    public void resetTurn(){
        turnIndex = 0;
    }
    
    /**
     * Placeholder method for implementing a flee/escape battle mechanic
     */
    public void huir(){
        //
    }

    /**
     * Checks if the battle has ended and determines the winner
     * A trainer loses if they cannot continue fighting
     */
    private void checkBattleState() throws PoobkemonException{  //no extensible??
        Trainer trainer1 = turnTrainers.get(0);
        Trainer trainer2 = turnTrainers.get(1);
        if(!trainer1.canStillFighting()){
            isOver = true;
            winner = trainer2;
        }
        else if(!trainer2.canStillFighting()){
            isOver = true;
            winner = trainer1;
        }
        if(trainer1.getPokemonInUse() == null){//!trainer1.getPokemonInUse().isAlive() || 
            throw new PoobkemonException(PoobkemonException.POKEMON_DIE);
        }
        if( trainer2.getPokemonInUse() == null){//!trainer2.getPokemonInUse().isAlive()||
            throw new PoobkemonException(PoobkemonException.POKEMON_DIE);
        }
        
    }

    /**
     * Checks if the battle is over
     * 
     * @return true if the battle has ended, false otherwise
     */
    public boolean isOver(){
        return isOver;
    }

    /**
     * Gets the winning trainer
     * 
     * @return The trainer who won the battle, or null if the battle is ongoing
     */
    public String getWinner(){
        return winner.getName();
    }

    /**
     * Gets the trainer whose turn it currently is
     * 
     * @return The current trainer
     */
    public Trainer getCurrentTrainer(){
        return turnTrainers.get(turnIndex);
    }

    /**
     * Gets the current turn index
     * 
     * @return The index of the current turn
     */
    private int getTurnIndex(){
        return turnIndex;
    }
    
    /**
     * Gets the name of the current trainer's active Pokemon
     * 
     * @return The name of the current Pokemon
     */
    public String getCurrentPokemonName(){
        return getCurrentTrainer().getPokemonInUse().getName();
    }
    
    /**
     * Gets the level of the current trainer's active Pokemon
     * 
     * @return The level of the current Pokemon
     */
    public int getCurrentPokemonLevel(){
        return getCurrentTrainer().getPokemonInUse().getLevel();
    }
    
    /**
     * Gets the current HP (Hit Points) of the current trainer's active Pokemon
     * 
     * @return The current HP of the current Pokemon
     */
    public int getCurrentPokemonPs(){
        return getCurrentTrainer().getPokemonInUse().getPs();
    }
    
    /**
     * Gets the Pokedex index of the current trainer's active Pokemon
     * 
     * @return The Pokedex index of the current Pokemon
     */
    public int getCurrentPokemonPokedexIndex(){
        return getCurrentTrainer().getPokemonInUse().getPokedexIndex();
    }
    
    /**
     * Gets the maximum HP of the current trainer's active Pokemon
     * 
     * @return The maximum HP of the current Pokemon
     */
    public int getCurrentMaxPs(){
        return getCurrentTrainer().getPokemonInUse().getMaxPs();
    }
    
    /**
     * Gets the color associated with the current trainer
     * 
     * @return The color of the current trainer
     */
    public Color getCurrentColor(){
        return getCurrentTrainer().getColor();
    }

    /**
     * Gets the name of the opponent trainer's active Pokemon
     * 
     * @return The name of the opponent's Pokemon
     */
    public String getOponentPokemonName(){
        return getOpponentTrainer().getPokemonInUse().getName();
    }
    
    /**
     * Gets the level of the opponent trainer's active Pokemon
     * 
     * @return The level of the opponent's Pokemon
     */
    public int getOponentPokemonLevel(){
        return getOpponentTrainer().getPokemonInUse().getLevel();
    }
    
    /**
     * Gets the current HP of the opponent trainer's active Pokemon
     * 
     * @return The current HP of the opponent's Pokemon
     */
    public int getOponentPokemonPs(){
        return getOpponentTrainer().getPokemonInUse().getPs();
    }

    /**
     * Gets the Pokedex index of the opponent trainer's active Pokemon
     * 
     * @return The Pokedex index of the opponent's Pokemon
     */
    public int getOponentPokemonPokedexIndex(){
        return getOpponentTrainer().getPokemonInUse().getPokedexIndex();
    }
    
    /**
     * Gets the maximum HP of the opponent trainer's active Pokemon
     * 
     * @return The maximum HP of the opponent's Pokemon
     */
    public int getOponentMaxPs(){
        return getOpponentTrainer().getPokemonInUse().getMaxPs();
    }
    
    /**
     * Gets the list of trainers participating in the battle
     * 
     * @return ArrayList of trainers
     */
    public ArrayList<Trainer> getTurnTrainers(){
        return turnTrainers;
    }

    /**
     * Gets the opponent of the current trainer
     * 
     * @return The opponent trainer
     */
    public Trainer getOpponentTrainer(){
        return turnTrainers.get((turnIndex + 1) % turnTrainers.size());
    }

    /**
     * Checks if the current trainer's active Pokemon is still alive
     * 
     * @return true if the Pokemon is alive, false otherwise
     */
    public boolean isAliveCurrentPokemon(){
        return getCurrentTrainer().getPokemonInUse().isAlive();
    }

    /**
     * Checks if the opponent trainer's active Pokemon is still alive
     * 
     * @return true if the Pokemon is alive, false otherwise
     */
    public boolean isAliveOpponentPokemon(){
        System.out.println(getOpponentTrainer().getPokemonInUse().isAlive());
        return getOpponentTrainer().getPokemonInUse().isAlive();
    }

    /**
     * Performs a coin toss to randomly determine which trainer goes first
     * Prints the result to the console
     * 
     * @return The result of the coin toss (true for heads, false for tails)
     */
    public boolean coinToss(){ //extensiblr??
        boolean result = random.nextBoolean();
        String coinside = result ? "Cara" : "Cruz";

        if (turnTrainers.size() == 2){
            turnIndex = result ? 0 : 1;
            System.out.println("Lanzamiento de moneda: " + coinside +
                    " - Comienza " + turnTrainers.get(turnIndex).getName());
        }

        return result; // Organizar arreglo dependiendo el resultado
    }
}