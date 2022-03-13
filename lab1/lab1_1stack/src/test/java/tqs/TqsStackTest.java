package tqs;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.NoSuchElementException;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

/**
 * Unit test for stack.
 */
public class TqsStackTest 
{

    private TqsStack stack;

    @BeforeEach
    public void initEach(){
        this.stack = new TqsStack(10);
    }
    /* ------ (a) ------ */
    @Test
    public void emptyOnConstruction()
    {   
        // this.stack = new TqsStack(10);
        assertTrue(this.stack.isEmpty());
    }
    /* ------ (b) ------ */
    @Test
    public void sizeZeroOnConstruction() {
        assertEquals(0, this.stack.size());
    }
    /* ------ (c) ------ */
    @Test
    public void NPushesNotEmpty() {
        // n pushes
        for (int i=0; i<10; i++) {
            this.stack.push(i);
        }
        // assert
        assertAll("this.stack",
            () -> assertFalse(this.stack.isEmpty()),
            () -> assertEquals(10, this.stack.size())
        );
    }
    /* ------ (d) ------ */
    @Test
    public void PushThenPop() {
        this.stack.push(1);
        int out = this.stack.pop();
        assertEquals(1, out);
    }
    /* ------ (e) ------ */
    @Test
    public void PushAndPeek() {
        this.stack.push(1);
        int there = this.stack.peek();
        assertEquals(1, there);
    }
    /* ------ (f) ------ */
    @Test
    public void PushAllThenPopAllIsEmpty() {
        for (int i=0; i<10; i++) {
            this.stack.push(i);
        }
        for (int i=0; i<10; i++) {
            this.stack.pop();
        }
        assertAll("this.stack",
            () -> assertTrue(this.stack.isEmpty()),
            () -> assertEquals(0, this.stack.size())
        );
    }
    /* ------ (g) ------ */
    @Test
    public void NoSuchElementExceptionPop() {
        assertThrows(NoSuchElementException.class, () -> this.stack.pop());
    }
    /* ------ (h) ------ */
    @Test
    public void NoSuchElementExceptionPeek() {
        assertThrows(NoSuchElementException.class, () -> this.stack.peek());
    }
    /* ------ (i) ------ */
    @Test
    public void IllegalStateException() {
        // filling up the this.stack
        for (int i=0; i<10; i++) {
            this.stack.push(i);
        }
        // assert
        assertThrows(IllegalStateException.class, () -> this.stack.push(10));
    }

}
