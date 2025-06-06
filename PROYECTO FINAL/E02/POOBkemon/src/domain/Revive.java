package src.domain;

/**
 * Clase que representa un Revive en el juego
 * Ítem que puede revivir a un Pokémon debilitado
 */
public class Revive extends Item {
    private final boolean isMaxRevive;
    
    /**
     * Constructor para crear un Revive estándar (recupera 50% de PS)
     */
    public Revive() {
        super(
            "Revive", 
            "Revive a un Pokémon debilitado y restaura la mitad de sus PS máximos."
        );
        isMaxRevive = false;
    }
    
    /**
     * Constructor para crear un Revive con opción de Max Revive
     * @param isMaxRevive true para Max Revive (recupera 100% de PS), false para Revive normal
     */
    public Revive(boolean isMaxRevive) {
        super(
            isMaxRevive ? "Max Revive" : "Revive",
            isMaxRevive ? "Revive a un Pokémon debilitado y restaura todos sus PS." :
                        "Revive a un Pokémon debilitado y restaura la mitad de sus PS máximos."
        );
        this.isMaxRevive = isMaxRevive;
    }
    
    /**
     * Método para usar el revive en un Pokémon
     * @param target El Pokémon al que se aplicará el revive
     * @return true si se usó correctamente, false si no
     */
    @Override
    public boolean use(Object target) {
        if (!(target instanceof Pokemon)) {
            return false;
        }
        Pokemon pokemon = (Pokemon) target;
        if (!pokemon.isFainted()) {
            return false;
        }
        int maxHp = pokemon.getMaxPs();
        int newHp = isMaxRevive ? maxHp : maxHp / 2;
        pokemon.heal(newHp);
        return true;
    }
    
    /**
     * Verifica si es un Max Revive
     * @return true si es Max Revive, false si es Revive normal
     */
    public boolean isMaxRevive() {
        return isMaxRevive;
    }
}
