package Test;

import Maxwell.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class MaxwellContainerC1Test.
 *
 * @author  (Julian Lopez , Sebastian Puentes)
 * @version (14/03/2025)
 */
public class MaxwellContainerTest
{
    private MaxwellContainer container;
    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @BeforeEach
    public void setUp()
    { 
        container = new MaxwellContainer(200,300);
    }
    
    @Test
    public void shouldAddParticle(){
        container.addParticle("red",true,-150,150,15,15);
        assertTrue(container.ok());
    }
    
    @Test
    public void shouldNotDeleteParticle(){
        container.addParticle("red",true,150,150,25,25);
        container.delParticle("blue");
        assertFalse(container.ok());
    }
    
    @Test
    public void shouldDeleteParticle(){
        container.addParticle("red",true,-150,150,15,15);
        container.delParticle("red");
        assertTrue(container.ok());
    }
    
    @Test
    public void shouldAddHole(){
        container.addHole(-150,150,1);
        assertTrue(container.ok());
    }
    
    @Test
    public void shouldNotAddHole(){
        container.addHole(-400,15,1);
        assertFalse(container.ok());
    }
    
    @Test
    public void shouldAddDemon(){
        container.addDemon(15);
        assertTrue(container.ok());
    }
    
    @Test
    public void shouldNotAddParticle(){
        container.addParticle("red",true,-400,150,25,25);
        assertFalse(container.ok());
    }
    
    @Test
    public void shouldDeleteDemon(){
        container.addDemon(15);
        container.delDemon(15);
        assertTrue(container.ok());
    }
    
    @Test 
    public void shouldNotDeleteDemon(){
        container.addDemon(15);
        container.delDemon(35);
        assertFalse(container.ok()); 
    }
    
    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @AfterEach
    public void tearDown(){
    }

}