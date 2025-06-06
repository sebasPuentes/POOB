import java.awt.*;
import java.util.*;
/**
 * Pit.
 * 
 * @author (Juan Sebastian Puentes Julio and Julian Camilo Lopez Barrero) 
 * @version 1.0(5/02/2025)
 */
public class Pit{
    
    private Rectangle rectangle1;
    private Rectangle rectangle2;
    private Rectangle rectangleSeeds;
    private int posX;
    private int posY;
    private ArrayList<Rectangle> Seeds = new ArrayList<>();
    private int limitSeeds = 4;
    private int sizePit = 16;
    /**
     * Constructor
     */
    //Mini-Cycle 1
    public Pit(boolean big){
        rectangle1 = new Rectangle();
        rectangle1.changeColor("black");
        rectangle1.setPositionX(80);
        rectangle1.setPositionY(100);
        rectangle2 = new Rectangle();
        rectangle2.changeColor("blue");
        rectangle2.setPositionX(110);
        rectangle2.setPositionY(130);
        posX = 110;
        posY = 130;
        if(!big){
            pitAux(130,130);
        }else{
        pitAux(260,260);
        limitSeeds = 10;
        sizePit = limitSeeds * limitSeeds;
        }
    }
    
    /**
     * Auxiliar Pit method to change de size of the rectangles
     */
    private void pitAux(int sizeX, int sizeY){
        rectangle1.changeSize(sizeX,sizeY);
        rectangle2.changeSize(sizeX-60,sizeY-60);
    }
    
    /**
     * Method to know the size of Seeds array
     */
    public int seeds(){
        Seeds.size();
        return Seeds.size();
    }
    
    /**
     * Method to put seeds into the rectangles
     */
    public void putSeeds(int seeds){
        for(int i = 0 ; i< seeds; i++){
        Rectangle aux = new Rectangle();
        aux.changeColor("yellow");
        if(Seeds.size() == sizePit){
        return;
        }
        if(Seeds.size() == 0){
            aux.setPositionX(posX);
            aux.setPositionY(posY);
            Seeds.add(aux);
        }else{
            putSeedsAux(aux);  
        }   
        aux.changeSize(8,8);
        aux.makeVisible();
        }
    }
    
    /**
     * Auxiliar method to put size to verify if when we can put 
     * seeds in a range greater than the width.
     */
    private void putSeedsAux(Rectangle aux){
        if(Seeds.size() % limitSeeds == 0){
            aux.setPositionX(posX);
            aux.setPositionY(Seeds.get(Seeds.size()-1).getPositionY() + 20);
            Seeds.add(aux);
        }else{
             aux.setPositionX(Seeds.get(Seeds.size()-1).getPositionX() + 20);
             aux.setPositionY(Seeds.get(Seeds.size()-1).getPositionY());
             Seeds.add(aux);
        }
    }
    
    /**
     * Auxiliar Function for removing seeds in descending order
     */
    private void defRemove() {
        while (Seeds.size()-1 >= 0 && Seeds.get(Seeds.size()-1) == null ) {
            Seeds.remove(Seeds.size()-1);
        }
    }
    
    /**
     * Method for remove seeds
     */
    public void removeSeeds(int seeds) {
        int count = 0;
        while (count < seeds && Seeds.size()-1-count >= 0) {
            Rectangle seed = Seeds.get(Seeds.size()-1-count);
                if (seed != null) {
                    seed.makeInvisible();
                    Seeds.set(Seeds.size()-1-count, null);
                }
            count++;
        }
        defRemove();
    }
    
    //Mini-Cycle 2
    /**
     * Make Visible the rectangles
     */
    public void makeVisible(){
        rectangle1.makeVisible();
        rectangle2.makeVisible();
        for(Rectangle k : Seeds ){
            k.makeVisible();
        }
    }   
    
    /**
     * Make Invisible the rectangles
     */
    public void makeInvisible(){
        rectangle1.makeInvisible();
        rectangle2.makeInvisible();
        for(Rectangle k : Seeds ){
            k.makeInvisible();
        }   
    }
    
    //Mini-Cycle 3
    /**
     * Change the colors at the same time of the rectangles
     */
    public void changeColors(String background,String seeds){
        rectangle1.changeColor(background);
        rectangle2.changeColor(seeds);
    }
    
    /**
     * Set an specific position
     */
    private void movePos(Rectangle r, int posX,int posY){
        r.setPositionX(r.getPositionX() + posX);
        r.setPositionY(r.getPositionY() + posY);
    }
    
    /**
     * Move to an especific position
     */
    public void moveTo(int x, int y){
        makeInvisible();
        int X = x - posX;
        int Y = y - posY;
        movePos(rectangle1,X,Y);
        movePos(rectangle2,X,Y);
        for(Rectangle seed : Seeds) {
            if(seed != null) {
                seed.setPositionX(seed.getPositionX() + X);
                seed.setPositionY(seed.getPositionY() + Y);
            }
        }
        posX = x;
        posY = y;
        makeVisible();
    }
}