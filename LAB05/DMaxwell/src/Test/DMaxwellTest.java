package Test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*; 

import domain.*;

public class DMaxwellTest {
    @Test
    public void testContainerDefaultValues() {
        DMaxwell dMaxwell = new DMaxwell();
        int[][] container = dMaxwell.container();

        assertNotNull(container);
        assertEquals(4, container.length); 
        assertEquals(10, container[0].length); 
        assertEquals(10, container[1].length); 
        assertEquals(6, container[2].length); 
        assertEquals(11, container[3].length);
    }

    @Test
    public void testConsultDefaultValues() {
        DMaxwell dMaxwell = new DMaxwell();
        int[] consult = dMaxwell.consult();

        assertNotNull(consult);
        assertEquals(5, consult.length); 
        for (int value : consult) {
            assertEquals(0, value); 
        }
    }

    @Test
    public void testVerifyHole() {
        DMaxwell dMaxwell = new DMaxwell();
        int[] holes = dMaxwell.container()[2];
        assertFalse(dMaxwell.verifyHole(holes[0]));
        assertTrue(dMaxwell.verifyHole(0)); 
    }
 
    @Test
    public void testMoveDown() {
        DMaxwell dMaxwell = new DMaxwell();
        int[] initialReds = dMaxwell.container()[1].clone();
        
        dMaxwell.move('d');
        
        int[] movedReds = dMaxwell.container()[1];
        assertNotEquals(initialReds[0], movedReds[0], "First red particle should have moved");
    }

    @Test
    void testMoveUp() {
        DMaxwell dMaxwell = new DMaxwell();
        int[] initialBlues = dMaxwell.container()[0].clone();
        int[] initialReds = dMaxwell.container()[1].clone();
        
        // Realiza el movimiento hacia arriba
        dMaxwell.move('u');
        
        int[] movedBlues = dMaxwell.container()[0];
        int[] movedReds = dMaxwell.container()[1];
        
        // Verifica que las posiciones de las partículas azules y rojas hayan cambiado correctamente
        assertNotEquals(initialBlues[0], movedBlues[0]);
        assertNotEquals(initialReds[0], movedReds[0]);
    }

    @Test
    void testMoveRight() {
        DMaxwell dMaxwell = new DMaxwell();
        int[] initialBlues = dMaxwell.container()[0].clone();
        int[] initialReds = dMaxwell.container()[1].clone();
        
        // Realiza el movimiento hacia la derecha
        dMaxwell.move('r');
        
        int[] movedBlues = dMaxwell.container()[0];
        int[] movedReds = dMaxwell.container()[1];
        
        // Verifica que las posiciones de las partículas azules y rojas hayan cambiado correctamente
        assertNotEquals(initialBlues[0], movedBlues[0]);
        assertNotEquals(initialReds[0], movedReds[0]);
    }

    @Test
    void testMoveLeft() {
        DMaxwell dMaxwell = new DMaxwell();
        int[] initialBlues = dMaxwell.container()[0].clone();
        int[] initialReds = dMaxwell.container()[1].clone();
        
        // Realiza el movimiento hacia la izquierda
        dMaxwell.move('l');
        
        int[] movedBlues = dMaxwell.container()[0];
        int[] movedReds = dMaxwell.container()[1];
        
        // Verifica que las posiciones de las partículas azules y rojas hayan cambiado correctamente
        assertNotEquals(initialBlues[0], movedBlues[0]);
        assertNotEquals(initialReds[0], movedReds[0]);
    }
}
