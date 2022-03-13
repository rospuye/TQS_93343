package tqs;

import java.util.NoSuchElementException;

public class TqsStack {
    private int arr[];
    private int top;
    private int capacity;

    TqsStack(int size)
    {
        arr = new int[size];
        capacity = size;
        top = -1;
    }

    public void push(int x) {
        if (this.isFull()) {
            throw new IllegalStateException();
        }
        arr[++top] = x;
    }

    public int pop() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        return arr[top--];
    }

    public int peek() {
        if (!isEmpty()) {
            return arr[top];
        } else {
            throw new NoSuchElementException();
        }
    }

    public int size() {
        return top + 1;
    }

    public boolean isEmpty() {
        return top == -1; // or return size() == 0;
    }

    public boolean isFull() {
        return top == capacity - 1; // or return size() == capacity;
    }
}
