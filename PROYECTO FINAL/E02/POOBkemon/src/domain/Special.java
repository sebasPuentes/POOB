package src.domain;

public class SpecialMovement extends Movement {
    public SpecialMovement(String newName, String newDescription, int newPP, int newPower, int newPrecision, PokemonType newType, int newPriority) {
        super(newName, newDescription, newPP, newPower, newPrecision, newType, newPriority);
    }

    public int doAttackTo(Pokemon attacker, Pokemon target) throws POOBkemonException{
        if (!canUse()) throw new POOBkemonException(POOBkemonException.INVALID_MOVEMENT);
        double levelFactor = (2.0 * attacker.getLevel()) / 5.0 + 2.0;
        double attackDefenseRatio = (double) attacker.getSpecialAttack() / target.getSpecialDefense();
        double damage = ((levelFactor * power * attackDefenseRatio) / 50.0) + 2.0;
        damage *= getMultiplicator(target.getPrincipalType());
        damage *= 0.85 + (Math.random() * 0.15);
        int damageInt = (int) damage;
        target.losePS(damageInt);
        losePP();
        return (int) damage;
    }

    @Override
    public Movement copy() {
        return new SpecialMovement(name, description, pp, power, precision, type, priority);
    }
}
