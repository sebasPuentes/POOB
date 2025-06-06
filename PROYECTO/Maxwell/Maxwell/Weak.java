package Maxwell;
import shapes.*;
/**
 * Write a description of class Weak here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Weak extends Demon
{
    private int canCross = 1;
    private int crossedParticles;
    /**
     * Constructor for objects of class Weak
     */
    public Weak(int xPos,int yPos){
        super(xPos,yPos);
        demon.changeColor("green");
        crossedParticles = 0;
        type = WEAK;
    }
    
    /**
     * Verifys for right particles the access for left chamber.
     * @param p Particles.
     * @return boolean.
     */
    @Override
    public boolean isInRight(Particles p){
        if(canCross()){
            if(Math.abs(p.getX()- getX()) <= size &&
               Math.abs(p.getY() - getY()) <= size){
               crossedParticles ++;
               makeInvisibleDemon();
               return true;
            }
        }
        return false;
    }

    /**
     * Verifys for left particles the access for right chamber.
     * @param p Particles.
     * @return boolean.
     */
    @Override
    public boolean isInLeft(Particles p){
        if(canCross()){
            if(Math.abs(p.getX()- getX()) <= size &&
               Math.abs(p.getY() - getY()) <= size){
               crossedParticles ++;
               makeInvisibleDemon();
               return true;
            }
        }
        return false;
    }
    
    /**
     * Verifys if more particles can cross Weak Demon.
     */
    public boolean canCross(){
        return crossedParticles < canCross;
    }
}
