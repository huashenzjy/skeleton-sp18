/**
 * @Author: huashen
 * @CreateTime: 2024-12-04  15:12
 * @Description: TODO
 * @Version: 1.0
 **/
// 节点类
public class IntNode<T> {
    private IntNode prev;
    private IntNode next;
    private T item;

    public IntNode(T item, IntNode next, IntNode prev) {
        this.item = item;
        this.next = next;
        this.prev = prev;
    }

    public T getItem() {
        return item;
    }

    public void setItem(T item) {
        this.item = item;
    }

    public IntNode getNext() {
        return next;
    }

    public void setNext(IntNode next) {
        this.next = next;
    }

    public IntNode getPrev() {
        return prev;
    }

    public void setPrev(IntNode prev) {
        this.prev = prev;
    }
}

