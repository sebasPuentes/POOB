package Maxwell;
/**
 * Write a description of class Main here.
 * 
 * @author (Julian Lopez , Sebastian Puentes) 
 * @version (1,0)
 */
public class Main {
    public static void main(String[] args) {
        MaxwellContainer container = new MaxwellContainer(300, 380, 0, 2, 1, new int[][]{
            {-150, 150, 15, 15}, 
            {-180, 10, 15, 15}, 
            {100, 50, 15, 15},
            {50, 120, 15, 15},
  
        });
        container.delDemon(0);
        container.delDemon(180);
        container.delDemon(45);
        container.addDemon("Blue",180);
        container.addDemon("Blue",0);
        container.makeVisible();
        container.start(1000);
        container.isGoal();
        container.consultHoles();
        container.consultParticles();
        
    }
}
 
