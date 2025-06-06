package domain;
import java.awt.Color;

/*No olviden adicionar la documentacion*/
public interface Item{
  public static final int ROUND = 1;
  public static final int SQUARE = 2;

  public abstract void decide();
   
  public default void change(){
    };
  
  public default int shape(){
      return ROUND;
  }
  
  public default Color getColor(){
      return Color.black;
  };
  
  public default boolean isActive(){
      return true;
  }
  
  public default boolean isAgent(){
      return false;
  }

     
}
