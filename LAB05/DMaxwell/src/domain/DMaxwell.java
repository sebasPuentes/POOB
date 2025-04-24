package domain;
import java.util.*;
public class DMaxwell {
    
    private final int posDemonDefault = 225;
    private final int[] paredDefault = {20,61,102,143,184,225,266,307,348,389,430};
    private final int[] blueDefault = {43,52,139,254,291,343,67,201,228,310};
    private final int[] redDefault = {48,55,126,336,79,112,193,277,326,360};
    private final int[] defaultHoles = {116,129,175,288,356,364};
    private  int[] blues;
    private  int[] red;
    private  int[] holes;
    private int[] wall;
    private int demon ;
    


    public DMaxwell(){
        blues = blueDefault.clone();
        red = redDefault.clone();
        holes = defaultHoles.clone();
        wall = paredDefault.clone();
        demon = posDemonDefault;
    }

    public int[] consult(){
        int[] particles = new int[5];
        return particles;
    }

    public int[][] container(){
        return new int[][] { blues, red, holes, wall};
    }


}