package src.domain;
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
    public Movement decide(Pokemon target){ //FALTA
        return null;
    }
}
