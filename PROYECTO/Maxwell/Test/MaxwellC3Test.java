package Test;

import Maxwell.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class MaxwellC3Test.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class MaxwellC3Test
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
    public void shouldAddMovilHole(){
        container.addHole("Movil",-280,180,2);
        assertTrue(container.ok());
    }
    
    @Test
    public void shouldNotAddMovilHole(){
        container.addHole("Movil",-350,100,1);
        assertFalse(container.ok());
    }
    
    @Test
    public void shouldAddWeakDemon(){
        container.addDemon("Weak",0);
        assertTrue(container.ok());
    }
    
    @Test
    public void shouldNotAddWeakDemon(){
        container.addDemon("Weak",-10);
        assertFalse(container.ok());
    }
    
    @Test
    public void shouldAddBlueDemon(){
        container.addDemon("Blue",180);
        assertTrue(container.ok());
    }
    
    @Test
    public void shouldNotAddBlueDemon(){
        container.addDemon("Blue",220);
        assertFalse(container.ok());
    }
    
    @Test
    public void shouldAddRotatorParticle(){
        container.addParticle("Rotator","orange",false,-50,110,10,5);
        assertTrue(container.ok());
    }
    
    @Test
    public void shouldAddEphemeralParticle(){
        container.addParticle("Ephemeral","green",false,-50,160,10,10);
        assertTrue(container.ok());
    }
    
    @Test
    public void shouldAddFlyingParticle(){
        container.addParticle("Flying","pink",false,-50,190,10,10);
        assertTrue(container.ok());
    }
    
    @Test
    public void shouldAddColoreableParticle(){
        container.addParticle("Coloreable","pink",false,50,60,10,10);
        assertTrue(container.ok());
    }
    
    @Test
    public void shouldMovilHoleMove(){
        Movil movil = new Movil(-180,100,20,20,2);
        
        movil.move();
        
        assertEquals(-179,movil.getX());
        assertEquals(99,movil.getY());
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
