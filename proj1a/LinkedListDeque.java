/**
 * @Author: huashen
 * @CreateTime: 2024-12-04  14:50
 * @Description: TODO
 * @Version: 1.0
 **/
public class LinkedListDeque<T> {
    // 双向链表
    private T item;
    private IntNode frontSentinel;
    private IntNode backSentinel;
    private int size = 0; // 记录链表中的数量

    public LinkedListDeque()//：创建一个空的链表 deque。
    {
        frontSentinel = new IntNode(null, null, null);
        backSentinel = new IntNode(null, null, frontSentinel);
        frontSentinel.next = backSentinel;
    }

    public T getRecursive(int index) //：与 get 相同，但使用递归。
    {
        if (index < 0 || index >= size) {
            return null;
        }
        return helpRecursive(frontSentinel.next, index);
    }

    private T helpRecursive(IntNode node, int index) {

        // 基本情况
        if (index == 0) {
            return (T) node.item;
        }
        return helpRecursive(node.next, index - 1);
    }

    //Adds an item of type T to the front of the deque.
    public void addFirst(T item) {
        frontSentinel.next = new IntNode<>(item, frontSentinel.next, frontSentinel);
        size += 1;
    }

    //  Adds an item of type T to the back of the deque.
    public void addLast(T item) {
        backSentinel.prev = new IntNode(item, backSentinel, backSentinel.prev);
        size += 1;
    }

    public boolean isEmpty()
    //: Returns true if deque is empty, false otherwise.
    {
        return size == 0;
    }

    public int size()
    // Returns the number of items in the deque.
    // size must take constant time.
    {
        return size;
    }

    public void printDeque()
    //: Prints the items in the deque from first to last, separated by a space.
    {
        IntNode ptr = frontSentinel;
        while (ptr.next != null && ptr.next != backSentinel) {
            System.out.print(ptr.item + " ");
            ptr = ptr.next;
        }
    }

    public T removeFirst()
    // Removes and returns the item at the front of the deque.
    // If no such item exists, returns null.
    {
        // 判断不为空
        if (frontSentinel.next != backSentinel) {
            // 如果只有一个结点的时候， 会返回null，出现异常
            IntNode beRemoved = frontSentinel.next;
            IntNode nextTemp = beRemoved.next;
            // 进行删除
            beRemoved.next = null;
            beRemoved.prev = null;
            // 接上链表
            // 判断是不是列表中 只有一个节点
            if (nextTemp != backSentinel) {
                nextTemp.prev = frontSentinel;
                frontSentinel.next = nextTemp;
                T item1 = (T) nextTemp.item;
                size -= 1;
                return item1;
            }
        }
        size -= 1;
        return null;
    }

    public T removeLast()
    //: Removes and returns the item at the back of the deque.
    // If no such item exists, returns null.
    {
        // 判断不为空
        if (frontSentinel.next != backSentinel) {
            // 保存地址
            IntNode beRemoved = backSentinel.prev;
            IntNode prevTemp = beRemoved.prev;
            // 进行删除
            beRemoved.next = null;
            beRemoved.prev = null;
            // 接上链表
            prevTemp.next = backSentinel;
            backSentinel.prev = prevTemp;
            T item1 = (T) prevTemp.item;
            return item1;
        }
        return null;
    }

    public T get(int index)
    // 使用迭代
    // Gets the item at the given index,
    // where 0 is the front, 1 is the next item, and so forth.
    // If no such item exists, returns null. Must not alter the deque! // 不能改变链表
    {
        if (index < 0 || index >= size) {
            IntNode ptr = frontSentinel.next;
            // 除去哨兵节点
            int searchIndex = 0;
            while (searchIndex != index) {
                ptr = ptr.next;
                searchIndex++;
            }
            return (T) ptr.item;
        }
        return null;
    }

}