package src.domain;
import java.util.ArrayList;
import java.util.List;
import java.awt.*;
/**
 * Entrenador defensivo que prioriza estrategias de protección y mejora de estadísticas defensivas
 */
public class DefensiveTrainer extends MachineTrainer { ;
    public DefensiveTrainer(String name,Color color) {
        super(name,color);
    }

    /*
     * Su enfoque va principalmente a la defensa.
     * Utiliza movimientos que potencian las estadísticas de
     * defensa y/o defensa especial, que brindan protección contra ataques
     * rivales o que bajan las estadísticas de ataque y/o ataque especial del jugador rival.
     */
    @Override
    public Movement decide(Pokemon target){
        ArrayList<AttributeMovement> movementsPokemon = inventory.getPokemons().get(activePokemon.getName()).getMovementsGiveDefense();
        System.out.println("ENTRO EN DEFENSIVO");
        Movement bestMovementDefensive = null;
        int status = 0;
        for (int i = 0; i < movementsPokemon.size(); i++){
            System.out.println("ENTRO EN DEFENSIVO1");
            if (movementsPokemon.get(i).getStateTo().get("Defense") > status && movementsPokemon.get(i).getPP() > 0){
                System.out.println("ENTRO EN DEFENSIVO2");
                status = movementsPokemon.get(i).getStateTo().get("Defense");
                bestMovementDefensive = movementsPokemon.get(i);
            }
        }
        if (bestMovementDefensive != null){
            return bestMovementDefensive;
        }
        else{
            System.out.println("FALLO");
            try {
                doOtherThen(target);
            } catch (POOBkemonException e) {
                System.out.println("Error: " + e.getMessage());
            }
            return pokemonMovementDecide(target);
        }
    }
}
