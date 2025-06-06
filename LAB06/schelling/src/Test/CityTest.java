package Test;

import domain.*;

import java.awt.Color;
import java.io.File;
import java.io.*;

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
public class CityTest {
    City city;
    File testFile;

    /**
     * Sets up the test fixture.
     * <p>
     * Called before every test case method.
     */
    @BeforeEach
    public void setUp() {
        city = new City();
        testFile = new File("testCity.dat");
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(testFile))) {
            out.writeObject(city);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test

    public void shouldWalkerBeHappy() {
        Person eva = new Person(city, 15, 15);
        Walker messner = new Walker(city, 18, 16);

        city.ticTac();
        city.ticTac();
        city.ticTac();

        char expectedState = 'h';
        char actualState = messner.getState();
        assertEquals(expectedState, actualState);
    }

    @Test
    public void shouldWalkerBeDissatisfied() {
        Person adan = new Person(city, 10, 10);
        Walker kukuczka = new Walker(city, 20, 10);

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
        assertEquals(expectedState, actualState);
    }

    @Test
    public void shouldWalkerBeIndifferent() {
        Person adan = new Walker(city, 10, 10);
        char expectedState = 'i';
        char actualState = adan.getState();
        assertEquals(expectedState, actualState);
    }

    @Test
    public void shouldBeRedLightAfter4TicTacs() {
        Light alert = new Light(city, 0, 0);

        city.ticTac();
        city.ticTac();
        city.ticTac();
        city.ticTac();

        char expectedState = 'r';
        char actualState = alert.getLightState();

        assertEquals(expectedState, actualState);
    }

    @Test
    public void shouldBothLightsHaveTheSameStateAfter3TicTacs() {
        Light alert = new Light(city, 0, 0);
        Light alarm = new Light(city, 0, 24);

        city.ticTac();
        city.ticTac();
        city.ticTac();

        char actualState = alert.getLightState();
        char actualState1 = alarm.getLightState();

        assertEquals(actualState, actualState1);
    }

    @Test
    public void shouldCreateKatalan() {
        Katalan prueba = new Katalan(city, 20, 20);
        assertFalse(city.isEmpty(20, 20));
        assertTrue(prueba.isDissatisfied());
    }

    @Test
    public void shouldKatalanBeHappyIfThereExistsItemAround() {
        Katalan pizza = new Katalan(city, 18, 13);
        Person eva = new Person(city, 15, 15);

        city.ticTac();
        city.ticTac();
        city.ticTac();

        char expectedState = 'h';
        char actualState = pizza.getState();
        assertEquals(expectedState, actualState);
    }

    @Test
    public void shouldKatalanStopMovingIfTheresAnItemClose() {
        Katalan lopez = new Katalan(city, 18, 13);
        Person eva = new Person(city, 15, 15);

        city.ticTac();
        city.ticTac();
        city.ticTac();
        city.ticTac();

        char expectedRow = 16;
        char expectedColumn = 15;
        int actualRow = lopez.getRow();
        assertEquals(expectedRow, actualRow);

    }

    @Test
    public void shouldCreateIdentifier() {
        Identifier prueba = new Identifier(city, 0, 24);
        assertFalse(city.isEmpty(0, 24));
    }

    @Test
    public void shouldBeGreenColorIfTheresItemsClose() {
        Identifier prueba = new Identifier(city, 0, 24);
        Person prueba1 = new Person(city, 1, 24);

        city.ticTac();

        char actualColor = prueba.getIdentifierState();
        char expectedColor = 'g';

        assertEquals(expectedColor, actualColor);

    }

    @Test
    public void shouldBeRedColorIfTheres0ItemsClose() {
        Identifier prueba = new Identifier(city, 0, 24);

        city.ticTac();

        char actualColor = prueba.getIdentifierState();
        char expectedColor = 'r';

        assertEquals(expectedColor, actualColor);
    }

    //-----------------------MENU-------------------
    @Test
    public void shouldExport00CityToFile() {
        File file = new File("test.txt");
        city.export00(file);
        assertTrue(file.exists());
    }

    @Test
    public void shouldImport00CityFromFile() {
        File file = new File("test.txt");
        city.import00(file);
        assertTrue(file.exists());
        file.delete();
    }

    @Test
    public void shouldOpen00CityFromFile() {
        City loadedCity = city.open00(testFile);
        assertEquals(city.getSize(), loadedCity.getSize());
        testFile.delete();
    }

    @Test
    public void shouldSave00CityToFile() {
        File file = new File("test1.txt");
        city.save00(file);
        assertTrue(file.exists());
        file.delete();
    }

    @Test
    public void shouldNotOpen01CityFromFile() {
        File file = new File("");
        try {
            city.open01(file);
        } catch (CityException e) {
            assertEquals("No se encontró el archivo: " + file.getAbsolutePath(), e.getMessage());
        }
    }

    @Test
    public void shouldNotSave01CityToFile() {
        File file = new File("");
        try {
            city.save01(file);
        } catch (CityException e) {
            assertEquals("El archivo especificado no existe", e.getMessage());
        }
    }

    @Test
    public void shouldNotImport01CityFromFile() {
        File file = new File("");
        try {
            city.import00(file);
        } catch (CityException e) {
            assertEquals("Error al importar el archivo " + file.getName(), e.getMessage());
        }
    }

    @Test
    public void shouldNotExport01CityToFile() {
        File file = new File("");
        try {
            city.export00(file);
        } catch (CityException e) {
            assertEquals("No se pudo exportar el archivo: " + file.getName(), e.getMessage());
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
        testFile.delete();
    }
}
