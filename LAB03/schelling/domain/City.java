package domain;
import java.util.*;
  
/*No olviden adicionar la documentacion*/
public class City{
    static private int SIZE=25;
    private Item[][] locations;
    
    /**
     * Constructor of city instance.
     */
    public City() {
        locations = new Item[SIZE][SIZE];
        for (int r=0;r<SIZE;r++){
            for (int c=0;c<SIZE;c++){
                locations[r][c]=null;
            }
        }
        someItems();
    }
    
    /**
     * Size of the matrix.
     * @return SIZE.
     */
    public int  getSize(){
        return SIZE;
    }
    
    /**
     * Get the item in a specific position.
     * @return Item.
     */
    public Item getItem(int r,int c){
        return locations[r][c];
    }
    
    /**
     * Sets an Item in a specific position.
     * 
     */
    public void setItem(int r, int c, Item e){
        locations[r][c] = e;
    }
    
    /**
     * Calling constructors for creating instances of walkers, person, ... etc.
     */
    public void someItems(){   
        Person adan = new Person(this,10,10);
        Person eva = new Person(this,15,15);
        Walker messner = new Walker(this,18,16);
        Walker kukuczka = new Walker(this,21,10);
        Light alarm = new Light(this,0,0);
        Light alert = new Light(this,0,24);
        Katalan lopez = new Katalan(this,18,13);
        Katalan puentes = new Katalan(this,20,11);
        Identifier sebastian = new Identifier(this,24,14);
        Identifier julian = new Identifier(this,0,10);
        Schelling p1 =new Schelling(this,14,23);
        Schelling p2 = new Schelling(this,15,23);
        Person zeus =new Person(this,15,22);
        Person ares =new Person(this,14,24);
    }
    
    /**
     * Checks the number of neighborEquals around an item.
     * @param r row.
     * @parm c column.
     * @return num number of neighborEquals.
     */
    public int neighborsEquals(int r, int c){
        int num=0;
        if (inLocations(r,c) && locations[r][c]!=null){
            for(int dr=-1; dr<2;dr++){
                for (int dc=-1; dc<2;dc++){
                    if ((dr!=0 || dc!=0) && inLocations(r+dr,c+dc) && 
                    (locations[r+dr][c+dc]!=null) &&  (locations[r][c].getClass()==locations[r+dr][c+dc].getClass())) num++;
                }
            }
        }
        return num;
    }
   
    /**
     * Checks the number of neighborEquals around an item.
     * @param r row.
     * @parm c column.
     * @return num number of neighborEquals.
     */
    public int neighbors(int r, int c){
        int num=0;
        if (inLocations(r,c) && locations[r][c]!=null){
            for(int dr=-1; dr<2;dr++){
                for (int dc=-1; dc<2;dc++){
                    if ((dr!=0 || dc!=0) && inLocations(r+dr,c+dc) && 
                    (locations[r+dr][c+dc]!=null) &&  (locations[r][c].getClass().equals(locations[r+dr][c+dc].getClass()))) num++;
                }
                }
        }
        return num;
    }
    
    /**
     * Changes the position of an item.
     */
    public void changeItemPosition(int row, int column, int newRow, int newColumn, Item item) {
        if (inLocations(newRow, newColumn) && locations[row][column] == item && locations[newRow][newColumn] == null) {
            locations[row][column] = null;
            locations[newRow][newColumn] = item;
        }
    }
    
    /**
     * Verifys if there is an item in a specific position.
     * @return boolean.
     */
    public boolean isEmpty(int r, int c){
        return (inLocations(r,c) && locations[r][c]==null);
    }    
    
    /**
     * Verifys if an specific position exists inside the board.
     * @return boolean.
     */
    public boolean inLocations(int r, int c){
        return ((0<=r) && (r<SIZE) && (0<=c) && (c<SIZE));
    }
    
    /**
     * Logic for tic tac button where items change and decide.
     */
    public void ticTac(){
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                if (locations[r][c] != null) { 
                    locations[r][c].change();
                    locations[r][c].decide();
                }
            }
        }
    }

}
