package tqs;

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
        System.out.println("Inserting " + x);
        arr[++top] = x;
    }

    public int pop() {
        System.out.println("Removing " + peek());
        return arr[top--];
    }

    public int peek() {
        if (!isEmpty()) {
            return arr[top];
        } else {
            System.exit(-1);
        }
        return -1;
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
