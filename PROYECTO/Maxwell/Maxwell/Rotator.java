package Maxwell;
import shapes.*;
public class Rotator extends Particles {
    /**
     * Constructor for objects of class Rotator.
     */
    public Rotator(String color, int xPosition, int yPosition, int vx, int vy, boolean isRed) {
        this.color = color;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.vx = vx;
        this.vy = vy;
        type = ROTATOR;
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
    public void checkCollision(boolean isInLeftSide, int minXLeftBoard, int maxXLeftBoard,
                               int minXRightBoard, int maxXRightBoard, int minY, int maxY) {
        if (isInLeftSide) {
            if (getX() <= minXLeftBoard) {
                int temp = vx;
                vx = Math.abs(vy);
                vy = temp;
            } else if (getX() >= maxXLeftBoard) {
                int temp = vx;
                vx = -Math.abs(vy); 
                vy = temp;
            }
        } else {
            if (getX() <= minXRightBoard) {
                int temp = vx;
                vx = Math.abs(vy);
                vy = temp;
            } else if (getX() >= maxXRightBoard) {
                int temp = vx;
                vx = -Math.abs(vy); 
                vy = temp;
            }
        }

        if (getY() <= minY) {
            int temp = vy;
            vy = Math.abs(vx);
            vx = temp;
        } else if (getY() >= maxY) {
            int temp = vy;
            vy = -Math.abs(vx); 
            vx = temp;
        }
    }
}

