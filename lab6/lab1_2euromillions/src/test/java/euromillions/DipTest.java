/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package euromillions;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import sets.SetOfNaturals;


/**
 * @author ico0
 */
public class DipTest {

    private Dip instance;


    public DipTest() {
    }

    @BeforeEach
    public void setUp() {
        instance = new Dip(new int[]{10, 20, 30, 40, 50}, new int[]{1, 2});
    }

    @AfterEach
    public void tearDown() {
        instance = null;
    }


    /* ------ (a) ------ */
    @Test
    public void testConstructorFromBadArrays() {
        // todo: instantiate a dip passing valid or invalid arrays

        // Case 1: invalid number of elements (numbers)
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Dip(new int[]{10, 20, 30, 40}, new int[]{1, 2});
        });
        String expectedMessage = "wrong number of elements in numbers/stars";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.equals(expectedMessage));

        // Case 2: invalid number of elements (stars)
        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> {
            new Dip(new int[]{10, 20, 30, 40, 50}, new int[]{1});
        });
        String expectedMessage2 = "wrong number of elements in numbers/stars";
        String actualMessage2 = exception2.getMessage();
        assertTrue(actualMessage2.equals(expectedMessage2));

        // Case 3: invalid number range (numbers)
        Exception exception3 = assertThrows(IllegalArgumentException.class, () -> {
            new Dip(new int[]{10, 20, -30, 40, 50}, new int[]{1, 2});
        });
        String expectedMessage3 = "wrong number ranges for either numbers or stars (or both)";
        String actualMessage3 = exception3.getMessage();
        assertTrue(actualMessage3.equals(expectedMessage3));

        // Case 4: invalid number range (stars)
        Exception exception4 = assertThrows(IllegalArgumentException.class, () -> {
            new Dip(new int[]{10, 20, 30, 40, 50}, new int[]{-1, 2});
        });
        String expectedMessage4 = "wrong number ranges for either numbers or stars (or both)";
        String actualMessage4 = exception4.getMessage();
        assertTrue(actualMessage4.equals(expectedMessage4));

    }

    @Test
    public void testFormat() {
        // note: correct the implementation of the format(), not the test...
        String result = instance.format();
        assertEquals("N[ 10 20 30 40 50] S[  1  2]", result, "format as string: formatted string not as expected. ");
    }

    /* ------ (b) ------ */
    @Test
    public void rangeTest() {
        SetOfNaturals stars = instance.getStarsColl();
        for (int star : stars) {
            assertTrue(star>0 && star<13);
        }
    }

}
