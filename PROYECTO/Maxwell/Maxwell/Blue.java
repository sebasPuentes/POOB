package Maxwell;
import shapes.*;
/**
 * Write a description of class Blue here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Blue extends Demon
{
    /**
     * Constructor for objects of class Blue
     */
    public Blue(int xPos,int yPos){
        super(xPos,yPos);
        demon.changeColor("blue");
        type = BLUE;
    }
    

    /**
     * Verifys for right particles the access for left chamber.
     * @param p Particles.
     * @return boolean.
     */
    @Override
    public boolean isInRight(Particles p){
        return  (Math.abs(p.getX() - getX()) <= size + 10 && 
                Math.abs(p.getY() - getY()) <= size  
                && p.getColor().equals("blue"));
    }

    /**
     * Verifys for left particles the access for right chamber.
     * @param p Particles.
     * @return boolean.
     */
    @Override
    public boolean isInLeft(Particles p){
        return (Math.abs(p.getX()- getX()) <= size &&
                Math.abs(p.getY() - getY()) <= size &&  
                p.getColor().equals("blue"));
    }
    
}
