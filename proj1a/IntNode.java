/**
 * @Author: huashen
 * @CreateTime: 2024-12-04  15:12
 * @Description: TODO
 * @Version: 1.0
 **/
// 节点类
public class IntNode<T> {
    public IntNode prev;
    public IntNode next;
    public T item;

    public IntNode(T item, IntNode next, IntNode prev) {
        this.item = item;
        this.next = next;
        this.prev = prev;
    }
}

