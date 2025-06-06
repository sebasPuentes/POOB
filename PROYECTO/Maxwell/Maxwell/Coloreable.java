package Maxwell;
import shapes.*;
import java.util.Random;

public class Coloreable extends Particles {
    private static final String[] COLORS = {"green", "yellow", "orange", "purple", "cyan", "magenta", "pink", "lime", "teal", "brown"};
    private Random random;
    
    /**
     * Constructor for objects of class Coloreable.
     */
    public Coloreable(String color, int xPosition, int yPosition, int vx, int vy, boolean isRed) {
        this.color = color;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.vx = vx;
        this.vy = vy;
        this.random = new Random();
        type = COLOREABLE;
        if(isRed){
            particle = new Circle("red",xPosition,yPosition);
        }
        
        if(!isRed){
            particle = new Circle(color,xPosition,yPosition);
        }
    }

    /**
     * Changes Coloreable color.
     */
    private void changeColor() {
        int index = random.nextInt(COLORS.length);
        String newColor = COLORS[index];
        particle.erase(); 
        particle = new Circle(newColor, xPosition, yPosition);
        particle.draw();
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
                setVx(Math.abs(vx));
                changeColor();
            } else if (getX() >= maxXLeftBoard) {
                setVx(-Math.abs(vx));
                changeColor();
            }
        } else {
            if (getX() <= minXRightBoard) {
                setVx(Math.abs(vx));
                changeColor();
            } else if (getX() >= maxXRightBoard) {
                setVx(-Math.abs(vx));
                changeColor();
            }
        }
        if (getY() <= minY) {
            setVy(Math.abs(vy));
            changeColor();
        } else if (getY() >= maxY) {
            setVy(-Math.abs(vy));
            changeColor();
        }
    }
}