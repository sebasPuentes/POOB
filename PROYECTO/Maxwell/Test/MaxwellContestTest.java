package Test;
import Maxwell.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class MaxwellContestTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class MaxwellContestTest
{
    private MaxwellContest contest = new MaxwellContest();
    /**
     * Default constructor for test class MaxwellContestTest
     */
    public MaxwellContestTest()
    {
        
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @BeforeEach
    public void setUp()
    {
    }
    
    @Test
    public void shouldSolveCaseOne(){
        int[][] particles = {{2,1,4,1},{-3,1,2,0}};
        String stringSolution = contest.solve(7,4,1,1,1,particles);
        assertEquals(stringSolution,"24,000000");
    }
    
    @Test
    public void shouldSolveCaseTwo(){
        int[][] particles = {{3,1,2,2},{-2,3,-2,-2},{3,2,1,-2},{-2,2,2,2}};
        String stringSolution = contest.solve(4,4,1,2,2,particles);
        assertEquals(stringSolution,"impossible");
    }
    
    @Test
    public void shouldSimulateCaseOne(){
        int[][] particles = {{-3,1,2,0},{2,1,4,1}};
        int[][] particles2 = {{-30,10,2,0},{20,10,4,1}};
        String simulateOne = contest.solve(7,4,1,1,1,particles);
        boolean isSolution = simulateOne.equals("24,000000");
        if (isSolution){
            contest.simulate(200,300,1,1,1,particles2);
        }
        
    }
    
    @Test
    public void shouldNotSimulateCaseOne(){
        int[][] particles = {{2,1,4,1},{-3,1,2,0}};
        int[][] particles2 = {{-30,10,2,0},{20,10,4,1}};
        String simulateOne = contest.solve(7,4,1,1,1,particles);
        boolean isSolution = simulateOne.equals("impossible");
        if (isSolution){
            contest.simulate(200,300,1,1,1,particles2);
        }
    }
    
    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @AfterEach
    public void tearDown()
    {
        
    }
    
    
}
