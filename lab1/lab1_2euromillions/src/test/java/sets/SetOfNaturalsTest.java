/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sets;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

/**
 * @author ico0
 */

/* SET OF IMPORTANT TESTS:
    (1) test if inserting a duplicate throws a IllegalArgumentException exception
    (2) test if an exception is thrown when we try to insert anything other than a positive integer (0 is invalid)
    (3) test if adding an element increases the set size by 1 and if the element is contained in the set
    (4) test if adding an array of n elements increases the set size by n and if the elements are contained in the set
    (5) test if two sets with common elements result in a positive result for the intersection method
    (6) test if a set containing a specific element results in a positive result for the contains method
*/

public class SetOfNaturalsTest {
    private SetOfNaturals setA;
    private SetOfNaturals setB;
    private SetOfNaturals setC;
    private SetOfNaturals setD;

    @BeforeEach
    public void setUp() {
        setA = new SetOfNaturals();
        setB = SetOfNaturals.fromArray(new int[]{10, 20, 30, 40, 50, 60});

        setC = new SetOfNaturals();
        for (int i = 5; i < 50; i++) {
            setC.add(i * 10);
        }
        setD = SetOfNaturals.fromArray(new int[]{30, 40, 50, 60, 10, 20});
    }

    @AfterEach
    public void tearDown() {
        setA = setB = setC = setD = null;
    }

    /* ------ (3) ------ */
    // i changed it a bit (to fit better with my description of the test, which i
    // think is more complete)
    @Test
    public void testAddElement() {

        int beforeSize = setA.size();
        setA.add(99);
        int afterSize = setA.size();
        assertTrue(setA.contains(99), "add: added element not found in set.");
        assertEquals(afterSize, beforeSize+1);

        int beforeSize2 = setB.size();
        setB.add(11);
        int afterSize2 = setB.size();
        assertTrue(setB.contains(11), "add: added element not found in set.");
        assertEquals(afterSize2, beforeSize2+1, "add: elements count not as expected.");
    }

    /* ------ (2) ------ */
    // also changed this one to include more both exception cases in the bad array scenario
    @Test
    public void testAddBadArray() {
        int[] elems = new int[]{10, 20, -30}; // negative number are invalid
        int[] elems2 = new int[]{0, 20, 30}; // 0 is invalid

        // must fail with exception
        assertThrows(IllegalArgumentException.class, () -> setA.add(elems));
        assertThrows(IllegalArgumentException.class, () -> setA.add(elems2));
    }


    @Test
    public void testIntersectForNoIntersection() {
        assertFalse(setA.intersects(setB), "no intersection but was reported as existing");
    }

    /* ------ (1) ------ */
    @Test
    public void testNoDuplicates() {

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            SetOfNaturals.fromArray(new int[]{10, 20, 30, 40, 50, 50});
        });
        String expectedMessage = "duplicate value: 50";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.equals(expectedMessage));

        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> {
            setB.add(10);
        });
        String expectedMessage2 = "duplicate value: 10";
        String actualMessage2 = exception2.getMessage();
        assertTrue(actualMessage2.equals(expectedMessage2));

    }

    /* ------ (4) ------ */
    @Test
    public void testAddArray() {

        int beforeSize = setA.size();
        int[] arr = new int[]{1,2,3,4};
        setA.add(arr);
        int afterSize = setA.size();

        for (int i : arr) {
            assertTrue(setA.contains(i), "add: added element not found in set.");
        }
        assertEquals(afterSize, beforeSize + arr.length);

    }

    /* ------ (5) ------ */
    @Test
    public void testIntersect() {
        assertTrue(setB.intersects(setD));
    }

    /* ------ (6) ------ */
    @Test
    public void testContains() {
        assertTrue(setB.contains(20));
    }

}
