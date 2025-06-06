package Maxwell;
import shapes.*;
/**
 * Write a description of class Flying here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Flying extends Particles
{
    /**
     * Constructor for objects of class Flying
     */
    public Flying(String color, int xPosition, int yPosition,int vx, int vy,boolean isRed){
        this.color = color;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.vx = vx;
        this.vy = vy;
        type = FLYING;
        if(isRed){
            particle = new Circle("red",xPosition,yPosition);
        }
        
        if(!isRed){
            particle = new Circle(color,xPosition,yPosition);
        }
    }

    /**
     * Checks particles collision.
     * @param isInLeftSide true if is in left side of the board.
     * @param minXLeftBoard min x.
     * @param maxXLeftBoard max x of leftboard.
     * @param minXRightBoard min x of rightboard.
     * @param maxXRightBoard max x of rightbaord.
     * @param minY minimun y.
     * @param maxY maximum y.
     */
    public void checkCollision( boolean isInLeftSide,int minXLeftBoard, int maxXLeftBoard,int minXRightBoard,int maxXRightBoard,int minY,int maxY){
        if(isInLeftSide){
                if (getX() <= minXLeftBoard) {
                    setVx(Math.abs(vx));
                }else if (getX() >= maxXLeftBoard) {
                    setVx(-Math.abs(vx));
                } 
        }else{
                if (getX() <= minXRightBoard) {
                    setVx(Math.abs(vx));
                } else if (getX() >= maxXRightBoard) {
                    setVx(-Math.abs(vx));
                } 
        }
        
        if (getY() <= minY) {
            setVy(Math.abs(vy));
        }else if (getY() >= maxY) {
            setVy(-Math.abs(vy));
        }
    }
    
    /**
     * Overrides the isInTheSamePosition method to make Flying particles
     * never be considered in the same position as a gap.
     * 
     * @param hole The hole to check
     * @return false always, so that the particle flies over the gaps
     */
    @Override
    public boolean isInTheSamePosition(Hole hole) {
        return false;
    }
}
