package domain;
import java.io.*;
public abstract class Item implements Serializable{
    protected String name;
    protected String description;
    
    /**
     * Constructor for creating a new Item
     * 
     * @param newName The name of the item
     * @param newDescription A description of what the item does
     */
    public Item(String newName, String newDescription) {
        name = newName;
        description = newDescription;
    }
    
    /**
     * Gets the name of this item
     * 
     * @return The name of the item
     */
    public String getName(){
        return name;
    }
    
    /**
     * Gets the description of this item
     * 
     * @return The description of the item
     */
    public String getDescription(){
        return description;
    }

    /**
     * Uses this item on the specified Pokemon
     * Base implementation checks if the Pokemon exists and is alive
     * Subclasses should override this method to implement specific item effects
     * 
     * @param pokemon The Pokemon to use the item on
     * @throws PoobkemonException INVALID_POKEMON if the Pokemon is null,
     *                           ITEM_NOT_USABLE if the Pokemon is not alive
     */
    public void useItem(Pokemon pokemon) throws PoobkemonException{
        if(pokemon == null) throw new PoobkemonException(PoobkemonException.INVALID_POKEMON);
        if(!pokemon.isAlive()) throw new PoobkemonException(PoobkemonException.ITEM_NOT_USABLE);
        System.out.println("LLEGA A LA CLASE ITEM: " + pokemon.getName());
    }
    
    /**
     * Creates an HTML tooltip string for displaying item information in the UI
     * 
     * @return An HTML-formatted string containing the item's name and description
     */
    public String createPokemonForToolTip(){
        return "<html>" +
                "<b style='font-size:12px; color:blue;'>" + name + "</b><br>" +
                "Type: " + description + "<br>" +
                "</html>";
    }
}