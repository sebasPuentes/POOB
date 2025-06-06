package domain;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import java.awt.Color;
import java.io.*;
import java.util.Random;
public class POOBkemon implements Serializable{
    private TreeMap<String, Pokemon> pokedex = new TreeMap<>(); // Pokemones sin movimientos, los movimientos los pone y se añaden al jugador
    private TreeMap<String, Trainer> entrenadores = new TreeMap<>();

    private TreeMap<String, Item> items = new TreeMap<>(); //los items necsrios
    private TreeMap<String, Movement> movements = new TreeMap<>(); //movimientos predefinidos qu epuede escoger el usuario

    private Battle battle;
    

    /**
     * Default constructor for POOBkemon
     * Initializes a new POOBkemon game instance
     */
    public POOBkemon() {}

    /**
     * Gets all the pokemons available in the game
     * @return TreeMap containing all pokemons, with names as keys
     */
    public TreeMap<String, Pokemon> getPokemons(){
        return pokedex;
    }

    /**
     * Gets all movements available in the game
     * @return TreeMap containing all movements, with names as keys
     */
    public TreeMap<String, Movement> getMovements(){
        return movements;
    }

    //-------------------------------------------------------------------------------------
    /**
     * Gets the PP (Power Points) of a specific movement in the current battle
     * @param name Name of the movement
     * @return The PP value of the specified movement
     * @throws PoobkemonException If the movement doesn't exist or cannot be performed
     */
    public int getPPInBattle(String name) throws PoobkemonException{
        return battle.getPPInBattle(name);
    }

    /**
     * Gets the list of movement names available for the current player's pokemon
     * @return ArrayList of movement names as strings
     */
    public ArrayList<String> getMovementsStringCurrent(){
        return battle.getMovementsStringCurrent();
    }

    /**
     * Gets the list of movement names available for the opponent's pokemon
     * @return ArrayList of movement names as strings
     */
    public ArrayList<String> getMovementsStringOponent(){
        return battle.getMovementsStringOponent();
    }
    
    /**
     * Executes a movement in the current battle
     * @param mov Name of the movement to perform
     * @throws PoobkemonException If the movement cannot be performed or doesn't exist
     */
    public void movementPerformed(String mov) throws PoobkemonException{
        battle.executeMovement(mov);
    }

    /**
     * Changes the current pokemon during battle
     * @param pok Name of the pokemon to switch to
     * @throws PoobkemonException If the pokemon cannot be switched or doesn't exist
     */
    public void actionCambiar(String pok) throws PoobkemonException{
        battle.changePokemon(pok);
    }

    /**
     * Uses an item during battle
     * @param item Name of the item to use
     * @throws PoobkemonException If the item cannot be used or doesn't exist
     */
    public void actionuseItem(String item) throws PoobkemonException{
        System.out.println("LLEGAS A POOBKEMON?");
        battle.useItem(item);
    }

    /**
     * Attempts to flee from the current battle
     * @throws PoobkemonException If escape is not possible
     */
    public void actuinHuir() throws PoobkemonException{
        battle.huir();
        //o resetiar batalla  battle = null para luego crear la nueva
    }
    
    /**
     * Initializes a new battle between two trainers
     * @param player1 Name of the first trainer
     * @param player2 Name of the second trainer
     */
    public void inicializateBattle(String player1, String player2){ //mirar pues
        Trainer trainer1 = entrenadores.get(player1);
        Trainer trainer2 = entrenadores.get(player2);
        battle = new Battle(trainer1, trainer2);
        
    }

    /**
     * Gets the color of the current trainer
     * @return Color object representing the current trainer's color
     */
    public Color getCurrentColor(){
        return battle.getCurrentColor();
    }

    /**
     * Sets the initial pokemon for a trainer
     * @param trainer Name of the trainer
     * @param pokemon Name of the pokemon to set as initial
     * @throws PoobkemonException If the trainer or pokemon don't exist
     */
    public void inicialTrainerPokemon(String trainer, String pokemon) throws PoobkemonException{
        Trainer trainer1 = entrenadores.get(trainer);
        trainer1.setPokemonInUse(pokemon);
    }

    /**
     * Gets the movement names of the initial pokemon for a trainer
     * @param trainer Name of the trainer
     * @return ArrayList of movement names as strings
     */
    public ArrayList<String> inicialTrainerMovements(String trainer){
        Trainer trainer1 = entrenadores.get(trainer);
        return trainer1.getPokemonInUse().getMovementsString();
    }
    
    /**
     * Gets the name of the current pokemon in battle
     * @return Name of the current pokemon
     */
    public String getCurrentPokemonName(){
        return battle.getCurrentPokemonName();
    }

    /**
     * Gets the level of the current pokemon in battle
     * @return Level of the current pokemon
     */
    public int getCurrentPokemonLevel(){
        return battle.getCurrentPokemonLevel();
    }

    /**
     * Gets the current health points of the current pokemon in battle
     * @return Current health points of the current pokemon
     */
    public int getCurrentPokemonPs(){
        return battle.getCurrentPokemonPs();
    }

    /**
     * Gets the pokedex index of the current pokemon in battle
     * @return Pokedex index of the current pokemon
     */
    public int getCurrentPokemonPokedexIndex(){
        return battle.getCurrentPokemonPokedexIndex();
    }

    /**
     * Gets the name of the opponent's pokemon in battle
     * @return Name of the opponent's pokemon
     */
    public String getOponentPokemonName(){
        return battle.getOponentPokemonName();
    }

    /**
     * Gets the level of the opponent's pokemon in battle
     * @return Level of the opponent's pokemon
     */
    public int getOponentPokemonLevel(){
        return battle.getOponentPokemonLevel();
    }

    /**
     * Gets the current health points of the opponent's pokemon in battle
     * @return Current health points of the opponent's pokemon
     */
    public int getOponentPokemonPs(){
        return battle.getOponentPokemonPs();
    }

    /**
     * Gets the pokedex index of the opponent's pokemon in battle
     * @return Pokedex index of the opponent's pokemon
     */
    public int getOponentPokemonPokedexIndex(){
        return battle.getOponentPokemonPokedexIndex();
    }

    /**
     * Gets the maximum health points of the current pokemon in battle
     * @return Maximum health points of the current pokemon
     */
    public int getcurrentMaxPs(){
        return battle.getCurrentMaxPs();
    }

    /**
     * Gets the maximum health points of the opponent's pokemon in battle
     * @return Maximum health points of the opponent's pokemon
     */
    public int getOponentMaxPs(){
        return battle.getOponentMaxPs();
    }

    /**
     * Checks if the current pokemon in battle is alive
     * @return true if the current pokemon is alive, false otherwise
     */
    public boolean isAliveCurrentPokemon(){
        return battle.isAliveCurrentPokemon();
    }

    /**
     * Checks if the opponent's pokemon in battle is alive
     * @return true if the opponent's pokemon is alive, false otherwise
     */
    public boolean isAliveOpponentPokemon(){
        return battle.isAliveOpponentPokemon();
    }

    /**
     * Adds a new trainer to the game
     * @param name Name of the new trainer
     * @param color Color associated with the trainer
     * @throws PoobkemonException If a trainer with the same name already exists
     */
    public void addNewTrainer(String name, Color color) throws PoobkemonException{
        if (entrenadores.containsKey(name)) throw new PoobkemonException(PoobkemonException.TRAINER_EXIST);
        entrenadores.put(name, new PlayerTrainer(name,color));
    }

    /**
     * Checks if the current battle is over
     * @return true if the battle is over, false otherwise
     */
    public boolean GameIsOVer(){
        return battle.isOver();
    }
        /**
     * Checks if the current battle is over
     * @return the winner
     */
    public String getWinner(){
        return battle.getWinner();
    }

    /**
     * Gets all items available in the game
     * @return TreeMap containing all items, with names as keys
     */
    public TreeMap<String, Item> getItems() {
        return items;
    }

    /**
     * Adds a new item to the game
     * @param item Item to add
     */
    public void addItem(Item item){
        System.out.println(item.getName() + "HOLA");
        items.put(item.getName(),item);
    }

    /**
     * Adds a new movement to the game
     * @param mov Movement to add
     * @throws PoobkemonException If a movement with the same name already exists
     */
    public void addMovement(Movement mov) throws PoobkemonException{
        if (movements.containsValue(mov)) throw new PoobkemonException(PoobkemonException.MOVEMENT_ALREADY_EXIST);
        movements.put(mov.getName(), mov);
    }

    /**
     * Adds a new pokemon to the pokedex
     * @param pokemon Pokemon to add
     */
    public void addPokemon(Pokemon pokemon) {
        pokedex.put(pokemon.getName(), pokemon);
    }

    /**
     * Adds a new trainer to the game
     * @param trainer Trainer to add
     */
    public void addTrainer(Trainer trainer) {
        entrenadores.put(trainer.getName(),trainer);
    }

    /**
     * Gets a pokemon by its name
     * @param name Name of the pokemon
     * @return Pokemon object, or null if not found
     */
    public Pokemon getPokemon(String name) {
        return pokedex.get(name);
    }

    /**
     * Gets a trainer by their name
     * @param name Name of the trainer
     * @return Trainer object, or null if not found
     */
    public Trainer getTrainer(String name) {
        return entrenadores.get(name);
    }

    /**
     * Gets the complete pokedex
     * @return TreeMap containing all pokemons, with names as keys
     */
    public TreeMap<String, Pokemon> getPokedex(){
        return pokedex;
    }

    /**
     * Gets all trainers in the game
     * @return TreeMap containing all trainers, with names as keys
     */
    public TreeMap<String,Trainer> getTrainers(){
        return entrenadores;
    }

    /**
     * Checks if a trainer name is already used
     * @param name Name to check
     * @return The name if it's not used
     * @throws PoobkemonException If the trainer name already exists
     */
    public String isTrainerIsed(String name) throws PoobkemonException{
        if(entrenadores.containsKey(name)) throw new PoobkemonException(PoobkemonException.TRAINER_EXIST);
        return name;
    }

    /**
     * Adds a new pokemon with specific movements to a trainer
     * @param entrenador Name of the trainer
     * @param pokemon Name of the pokemon to add
     * @param m1 First movement
     * @param m2 Second movement
     * @param m3 Third movement
     * @param m4 Fourth movement
     * @throws PoobkemonException If the trainer or pokemon don't exist
     */
    public void addNewPokemon(String entrenador, String pokemon,Movement m1,Movement m2, Movement m3, Movement m4)throws PoobkemonException{
        Pokemon pokemon1 = pokedex.get(pokemon).copy();
        pokemon1.setMovements(new Movement[]{m1,m2,m3,m4});
        entrenadores.get(entrenador).addPokemon(pokemon1);
    }

    /**
     * Gets all valid movements for a specific pokemon
     * @param pokemon Pokemon to check movements for
     * @return TreeMap containing valid movements, with names as keys
     */
    public TreeMap<String,Movement> validMovements(Pokemon pokemon){
        TreeMap<String,Movement> movementsForPokemon = new TreeMap<>();
        for(Movement movement : movements.values()) {
            double multiplicador = movement.getMultiplicator(pokemon.getPrincipalType());
            if (multiplicador <= 1.0){
                movementsForPokemon.put(movement.getName(), movement);
            }
        }
        return movementsForPokemon;
    }

    public void generateRandomSelectionPokemon(String trainerEscogido){
        Random random = new Random();
        ArrayList<String> generalLista = new ArrayList<>(pokedex.keySet());
        ArrayList<String> pokemonesEscogidos = new ArrayList<>();
        int count = 0;
        while (count < 6){
            int number = random.nextInt(generalLista.size());
            ArrayList<Movement> p = generateRandomMovementForPokemons();
            pokemonesEscogidos.add(generalLista.get(number));
            try{
                addNewPokemon(trainerEscogido, generalLista.get(number), p.get(0), p.get(1), p.get(2), p.get(3));
                count++;
            }catch(PoobkemonException e){
                e.getMessage();
            }
        }
        try{
            inicialTrainerPokemon(trainerEscogido,pokemonesEscogidos.get(0));
        }catch (PoobkemonException h){
            h.getMessage();
        }
    }
    public String getFirstPokemonOfThelist(ArrayList<String> pokemonesEscogidos){
        return pokemonesEscogidos.get(0);
    }

    public ArrayList<Movement> generateRandomMovementForPokemons(){
        Random random = new Random();
        ArrayList<Movement> listaMovimientos = new ArrayList<>(movements.values());
        ArrayList<Movement> listRandom = new ArrayList<>();
        while (listRandom.size() < 4){
            int number = random.nextInt(listaMovimientos.size());
            listRandom.add(listaMovimientos.get(number));
        }
        return listRandom;
    }


    /**
     * Saves the current game state to a file
     * @param fileName Name of the file to save to
     */
    private void serializateGame(String fileName) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("gameData"));
            out.writeObject(this);
            out.close();
        } catch (NotSerializableException e) {
            System.out.println("Error al serializar: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error al guardar el juego: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Saves the current game state to the default file
     */
    public void serializateGame(){
        String fileName = "gameData";
        serializateGame(fileName);
    }

    /**
     * Loads a game state from a file
     * @param fileName Name of the file to load from
     * @return Loaded POOBkemon object, or null if loading fails
     */
    private POOBkemon deserializateGame(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            System.err.println("El archivo " + fileName + " no existe.");
            return null;
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            Object deserializedObject = in.readObject();
            if (deserializedObject instanceof POOBkemon) {
                POOBkemon poobkemon = (POOBkemon) deserializedObject;
                System.out.println("Juego cargado exitosamente desde " + fileName);
                return poobkemon;
            } else {
                System.err.println("El archivo no contiene un objeto de tipo POOBkemon.");
                return null;
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("Error: Clase no encontrada durante la deserialización: " + e.getMessage());
        }
        return null;
    }

    /**
     * Loads a game state from the default file
     * @return Loaded POOBkemon object, or null if loading fails
     */
    public POOBkemon deserializateGame(){
        String fileName = "gameData";
        return deserializateGame(fileName);
    }
}