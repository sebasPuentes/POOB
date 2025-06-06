package src.domain;
import java.util.Random;
public class Physical extends Movement{
    public Physical(String newName, String newDescription, int newPP, int newPower, int newPrecision, PokemonType newType, int newPriority){
        super(newName,newDescription,newPP,newPower,newPrecision,newType, newPriority);
    }

    public int doAttackTo(Pokemon attacker, Pokemon target) throws POOBkemonException{
        Random random = new Random();
        if (!canUse()) throw new POOBkemonException(POOBkemonException.INVALID_MOVEMENT);
        if (random.nextInt(100) > precision) {
            losePP();
            System.out.println("Fallo");
        }

        double levelFactor = (2.0 * attacker.getLevel()) / 5.0 + 2.0;
        double attackDefenseRatio = (double) attacker.getAttack() / target.getDefense();
        double damage = ((levelFactor * power * attackDefenseRatio) / 50.0) + 2.0;
        damage *= getMultiplicator(target.getPrincipalType());
        damage *= 0.85 + (Math.random() * 0.15);
        int damageInt = (int) damage;
        target.losePS(damageInt);
        losePP();
        return (int) damage;
    }

    @Override
    public Movement copy(){
        return new Physical(name, description, pp, power, precision, type, priority);
    }
}
