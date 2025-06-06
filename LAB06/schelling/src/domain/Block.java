package domain;

import java.awt.*;
import java.io.Serializable;

public class Block implements Item, Serializable {

    private int row,column;
    private City city;
    private Color color;

    public Block(City city, int row, int column){
        this.city=city;
        this.row=row;
        this.column=column;
        this.city.setItem(row,column,this);
        color = Color.BLACK;
    }

    @Override
    public int shape() {
        return SQUARE;
    }

    public void decide(){
    }

    public Color getColor(){
        return color;
    }
}
