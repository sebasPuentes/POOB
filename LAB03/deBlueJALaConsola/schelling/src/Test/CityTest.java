package Test;

import domain.Walker;
import domain.City;
import domain.Person;
import domain.Light;
import domain.Katalan;
import domain.Identifier;
import java.awt.Color;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class CityTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class CityTest
{
    City city;
    /**
     * Default constructor for test class CityTest
     */
    public CityTest()
    {
        city = new City();
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
    
    public void shouldWalkerBeHappy(){
        Person eva = new Person(city,15,15);
        Walker messner = new Walker(city,18,16);
        
        city.ticTac();
        city.ticTac();
        city.ticTac();
        
        char expectedState = 'h';
        char actualState = messner.getState();
        assertEquals(expectedState,actualState);
    }
    
    @Test
    public void shouldWalkerBeDissatisfied(){
        Person adan = new Person(city,10,10);
        Walker kukuczka = new Walker(city,20,10);
        
        city.ticTac();
        city.ticTac();
        city.ticTac();
        city.ticTac();
        city.ticTac();
        city.ticTac();
        city.ticTac();
        city.ticTac();
        city.ticTac();
        city.ticTac();
        
        char expectedState = 'd';
        char actualState = kukuczka.getState();
        assertEquals(expectedState,actualState);
    }
    
    @Test
    public void shouldWalkerBeIndifferent(){
        Person adan = new Walker(city,10,10);
        char expectedState = 'i';
        char actualState = adan.getState();
        assertEquals(expectedState,actualState);
    }
    
    @Test
    public void shouldBeRedLightAfter4TicTacs(){
        Light alert = new Light(city,0,0);
        
        city.ticTac();
        city.ticTac();
        city.ticTac();
        city.ticTac();
        
        char expectedState = 'r';
        char actualState = alert.getLightState();
        
        assertEquals(expectedState,actualState);  
    }
    
    @Test
    public void shouldBothLightsHaveTheSameStateAfter3TicTacs(){
        Light alert = new Light(city,0,0);
        Light alarm = new Light(city,0,24);
        
        city.ticTac();
        city.ticTac();
        city.ticTac();
        
        char actualState = alert.getLightState();
        char actualState1 = alarm.getLightState();
        
        assertEquals(actualState,actualState1);  
    }
    
    @Test
    public void shouldCreateKatalan(){
        Katalan prueba = new Katalan(city,20,20);
        assertFalse(city.isEmpty(20,20));
        assertTrue(prueba.isDissatisfied());
    }
    
    @Test
    public void shouldKatalanBeHappyIfThereExistsItemAround(){
        Katalan pizza = new Katalan(city,18,13);
        Person eva = new Person(city,15,15);
        
        city.ticTac();
        city.ticTac();
        city.ticTac();
        
        char expectedState = 'h';
        char actualState = pizza.getState();
        assertEquals(expectedState,actualState);
    }
    
    @Test
    public void shouldKatalanStopMovingIfTheresAnItemClose(){
        Katalan lopez = new Katalan(city,18,13);
        Person eva = new Person(city,15,15);
        
        city.ticTac();
        city.ticTac();
        city.ticTac();
        city.ticTac();
        
        char expectedRow = 16;
        char expectedColumn = 15;
        int actualRow = lopez.getRow();
        assertEquals(expectedRow,actualRow);
       
    }
    
    @Test
    public void shouldCreateIdentifier(){
        Identifier prueba = new Identifier(city,0,24);
        assertFalse(city.isEmpty(0,24));
    }
    
    @Test
    public void shouldBeGreenColorIfTheresItemsClose(){
        Identifier prueba = new Identifier(city,0,24);
        Person prueba1 = new Person(city,1,24);
        
        city.ticTac();
        
        char actualColor = prueba.getIdentifierState();
        char expectedColor = 'g';
        
        assertEquals(expectedColor,actualColor);
        
    }
    
    @Test
    public void shouldBeRedColorIfTheres0ItemsClose(){
        Identifier prueba = new Identifier(city,0,24);
        
        city.ticTac();
        
        char actualColor = prueba.getIdentifierState();
        char expectedColor = 'r';
        
        assertEquals(expectedColor,actualColor);
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
