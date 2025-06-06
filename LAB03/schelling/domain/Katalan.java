package domain;


/**
 * Katalan .
 * 
 * @author (Juan Sebastian Puentes y Julian Camilo Lopez) 
 * @version (a version number or a date)
 */
public class Katalan extends Person
{
    /**
     * Constructor for objects of class Katalan
     */
    public Katalan(City city,int row, int column){
        super(city,row,column);
        color = color.pink;
        state = DISSATISFIED;
    }
    
    /**
     * 
     */
    @Override
    /**
     * If items around then state is HAPPY, otherwise DISSATISFIED and it moves.
     */
    public void decide(){
        if(!isItemsAround()){
            moveKatalan();
            state = DISSATISFIED;
        }else{
            state = HAPPY;
        }
    }
    
    /**
     * Checks if there exists items around the item. If exists then return true.
     */
    public boolean isItemsAround(){
        for(int dr=-1; dr<2;dr++){
            for (int dc=-1; dc<2;dc++){
                if( dr == 0 && dc == 0){
                    continue;
                }
                int checkRow = row + dr;
                int checkColumn = column + dc;
                if(city.inLocations(checkRow,checkColumn) && city.getItem(checkRow,checkColumn) != null){
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Move the item diagonally to the right.
     */
     public void moveKatalan(){
        if (row > 0 && column < 24 ) { 
            int newRow = row - 1;
            int newColumn = column + 1;
            if (city.isEmpty(newRow, column)) { 
                city.setItem(row, column, null); 
                row = newRow; 
                column = newColumn;
                city.setItem(row, column, this);
            }
        }
    }
    
}
