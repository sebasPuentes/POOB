package Maxwell;


/**
 * Write a description of class Movil here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Movil extends Hole
{   
    private int vx = 1;
    private int vy = -1;
    /**
     * Constructor for objects of class Movil
     */
    public Movil(int xPosition, int yPosition, int height, int width,int maxParticles)
    {
      super(xPosition, yPosition,height,width, maxParticles); 
      hole.changeColor("blue");
      type = MOVIL;
    }

    /**
     * Checks Hole Collision.
     * @param boolean isInLeft.
     * @param int minX.
     * @param int maxXLeftBoard.
     * @param int minXRightBoard.
     * @param int maxXRightBoard.
     * @param int minY.
     * @param int maxY.
     */
    @Override
    public void checkHoleCollision(boolean isInLeft,int minX, int maxXLeftBoard, int minXRightBoard, int maxXRightBoard, int minY, int maxY) {
        if(isInLeft){
                if (getX() <= minX) {
                    stop();
                }else if (getX() >= maxXLeftBoard ) {
                    stop();
                } 
        }else{
            if (getX() <= minXRightBoard) {
                stop();
            }else if (getX() >= maxXRightBoard) {
                stop();
            } 
        }
        if (getY() <= minY) {
            stop();
        }else if (getY() >= maxY) {
            stop();
        }
    }
    
    /**
     * Moves movil demon.
     */
    @Override
    public void move(){
        hole.makeInvisible();
        hole.moveHorizontal(vx);
        hole.moveVertical(vy);
        xPosition+=vx;
        yPosition+=vy;
        hole.makeVisible();
    }
    
    /**
     * Stops demons move.
     */
    public void stop(){
        vx = 0;
        vy = 0;
        xPosition += vx;
        xPosition += vy;
    }
}

