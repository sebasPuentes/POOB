package domain;
import java.awt.Color;


public abstract class Agent{
    
    public final static char HAPPY='h', INDIFFERENT='i', DISSATISFIED='d';
    protected char state;
    private int steps;

    /**Create a new Agent
     * 
     */
    public Agent(){
        state=DISSATISFIED;
        steps=0;
    }
    
    /**The Agent makes one step
     * 
     */
    protected void step(){
        steps++;
    }    
    
    /**
     * Returns state of the agent.
     */
    public final char getState(){
        return state;
    }
    
    /**Returns the step
    @return 
     */   
    public final int getSteps(){
        return steps;
    }    

    /**It's an agent
     */
    public final boolean isAgent(){
        return true;
    }  
    
    /**Returns if happy
    @return true, if HAPPY; false, otherwise
     */
    public final boolean isHappy(){
        return (state == Agent.HAPPY) ;
    }
    
    /**Returns if indifferent
    @return true, if INDIFFERENT; false, otherwise
     */
    public final boolean isIndifferent(){
        return (state == Agent.INDIFFERENT) ;
    }
    
    /**Returns if dissatisfied
    @return true, if DISSATISFIED; false, otherwise
     */
    public final boolean isDissatisfied(){
        return (state == Agent.DISSATISFIED) ;
    }

}
