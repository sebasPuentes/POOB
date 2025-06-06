package src.domain;

public class Special extends Movement {
    public Special(String newName, String newDescription, int newPP, int newPower, int newPrecision, PokemonType newType, int newPriority) {
        super(newName, newDescription, newPP, newPower, newPrecision, newType, newPriority);
    }

    public int doAttackTo(Pokemon attacker, Pokemon target) throws POOBkemonException{
        if (!canUse()) throw new POOBkemonException(POOBkemonException.INVALID_MOVEMENT);
        if (Math.random() * 100 > precision) {
            losePP();
            System.out.println("Fallo");
        }
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
        return new Special(name, description, pp, power, precision, type, priority);
    }
}
