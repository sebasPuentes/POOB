package Maxwell;
import shapes.*;
/**
 * Write a description of class Ephemeral here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Ephemeral extends Particles
{

    /**
     * Constructor for objects of class Ephemeral
     */
    public Ephemeral(String color, int xPosition, int yPosition,int vx, int vy,boolean isRed){
        this.color = color;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.vx = vx;
        this.vy = vy;
        type = EPHEMERAL;
        if(isRed){
            particle = new Circle("red",xPosition,yPosition);
        }
        
        if(!isRed){
            particle = new Circle(color,xPosition,yPosition);
        }
    }

    /**
     * Checks particle collision.
     * @param isInLeftSide true if is in left side of the board.
     * @param minXLeftBoard min x.
     * @param maxXLeftBoard max x of left board.
     * @param minXRightBoard min x of right board.
     * @param maxXRightBoard max x of right board.
     * @param minY minimum y.
     * @param maxY maximum y.
     */
    public void checkCollision(boolean isInLeftSide, int minXLeftBoard, int maxXLeftBoard,  int minXRightBoard, int maxXRightBoard, int minY, int maxY) {
        if (vx == 0 && vy == 0) {
            makeInvisibleParticle();
            return;
        }

        if (isInLeftSide) {
            if (getX() <= minXLeftBoard) {
                setVx(Math.abs(vx));
                if(vx>0){
                    vx--;
                }
                else if(vx<0){
                    vx++;
                }
            } else if (getX() >= maxXLeftBoard) {
                setVx(-Math.abs(vx));
                if(vx>0){
                    vx--;
                }
                else if(vx<0){
                    vx++;
                }
            }
        } else {
            if (getX() <= minXRightBoard) {
                setVx(Math.abs(vx));
                if(vx>0){
                    vx--;
                }
                else if(vx<0){
                    vx++;
                }
            } else if (getX() >= maxXRightBoard) {
                setVx(-Math.abs(vx));
                if(vx>0){
                    vx--;
                }
                else if(vx<0){
                    vx++;
                }
            }
        }

        if (getY() <= minY) {
            setVy(Math.abs(vy));
            if(vy>0){
                vy--;
                }
            else if(vy<0){
                vy++;
                }
        } else if (getY() >= maxY) {
            setVy(-Math.abs(vy));
            if(vy>0){
                    vy--;
                }
            else if(vy<0){
                vy++;
                }
        }
        
        if (vx == 0 && vy == 0) {
            makeInvisibleParticle();
        }
    }
}

