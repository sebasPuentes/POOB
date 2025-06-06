package src.domain;
import java.util.*;
public class AttributeEffect extends Effect {
    protected HashMap<String, Integer> stateTo = new HashMap<>();

    public AttributeEffect(String newName, String newDescription, int newTimes) {
        super(newName, newDescription, newTimes);
    }
    public AttributeEffect(String newName, String newDescription, int newTimes, HashMap<String, Integer> tributes) {
        super(newName, newDescription, newTimes);
        stateTo = tributes;
    }
    public HashMap<String, Integer> getStateTo() {
        return stateTo;
    }
    public void affectPokemon(Pokemon affectPokemon) throws POOBkemonException {
        for (Map.Entry<String, Integer> entry : stateTo.entrySet()) {
            String stat = entry.getKey();
            int amount = entry.getValue();
            affectPokemon.increaseStat(stat, amount);
        }
        decrementDuration();
    }
}
