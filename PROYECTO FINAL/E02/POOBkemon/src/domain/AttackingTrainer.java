package src.domain;
import java.awt.*;
import java.util.*;
public class AttackingTrainer extends MachineTrainer {
    public AttackingTrainer(String name,Color color) {
        super(name,color);
    }

    @Override
    public Movement decide(Pokemon target) { //FALTA
        return null;
    }
}
