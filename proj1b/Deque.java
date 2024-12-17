/**
 * Deque interface.
 */
public interface Deque<T> {
    public void addFirst(T item);

    // Adds an item of type T to the front of the deque.
    public boolean isEmpty();

    public int size();

    public void printDeque();

    public T removeFirst();

    public T removeLast();

    public T get(int index);
}
