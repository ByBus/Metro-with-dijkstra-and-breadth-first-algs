package metro;

import java.util.ArrayList;
import java.util.List;

class Queue<T> {
    private final List<T> queue= new ArrayList<>();
    private int lastPosition = -1;

    public void push(T item) {
        queue.add(0, item);
        lastPosition++;
    }

    public T pop(){
        if (lastPosition < 0) {
            return null;
        }
        T item = queue.get(lastPosition);
        queue.set(lastPosition--, null);
        return item;
    }

    void print(){
        System.out.println(queue);
    }

    public T peek() {
        if (lastPosition < 0) {
            return null;
        }
        return queue.get(lastPosition);
    }
}
