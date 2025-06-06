package domain;
import java.awt.*;
import java.util.*;
public class AttackingTrainer extends MachineTrainer {
    public AttackingTrainer(String name,Color color) {
        super(name,color);
    }

    public Movement decide(Pokemon target){
        ArrayList<AttributeMovement> movementsPokemon = inventory.getPokemons().get(activePokemon.getName()).getMovementsGiveAttack();
        System.out.println("ENTRO EN ATACANTE");
        Movement bestAttackMovement = null;
        double possibleAttackMovement = 0;
        for (int i = 0; i < movementsPokemon.size(); i++){
            System.out.println("ENTRO EN ATACANTE1");
            double attackMovement = movementsPokemon.get(i).getMultiplicator(target.getPrincipalType());
            if (possibleAttackMovement < attackMovement && movementsPokemon.get(i).getPP() > 0){
                System.out.println("ENTRO EN ATACANTE2");
                possibleAttackMovement = attackMovement;
                bestAttackMovement = movementsPokemon.get(i);
            }
        }
        if (bestAttackMovement != null){
            activePokemon.affectPokemonStatus();
            return bestAttackMovement;
        }
        System.out.println("MOVIMIENTO ALEATORIO");
        return activePokemon.aleatoryMovement(target);
    }
}
