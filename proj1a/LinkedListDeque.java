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
        frontSentinel.setNext(backSentinel);
    }

    public T getRecursive(int index) //：与 get 相同，但使用递归。
    {
        if (index < 0 || index >= size) {
            return null;
        }
        return helpRecursive(frontSentinel.getNext(), index);
    }

    private T helpRecursive(IntNode node, int index) {

        // 基本情况
        if (index == 0) {
            return (T) node.getItem();
        }
        return helpRecursive(node.getNext(), index - 1);
    }

    //Adds an item of type T to the front of the deque.
    public void addFirst(T item) {
        // 现在不是消除， 只是在原有的基础上进行插入
        IntNode newNode = new IntNode(item, frontSentinel.getNext(),frontSentinel);
       // 进行插入
        frontSentinel.getNext().setPrev(newNode);
        frontSentinel.setNext(newNode);

        size += 1;
    }

    //  Adds an item of type T to the back of the deque.
    public void addLast(T item) {
        // 现在不是消除， 只是在原有的基础上进行插入
        IntNode newNode = new IntNode(item,backSentinel, backSentinel.getPrev());
        // 进行插入

        backSentinel.getPrev().setNext(newNode);
        backSentinel.setPrev(newNode);

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
        IntNode ptr = frontSentinel.getNext();  // 从哨兵的下一个开始
        while (ptr != null && ptr != backSentinel) {
            System.out.print(ptr.getItem() + " ");
            ptr = ptr.getNext();
        }
    }

    // Removes and returns the item at the front of the deque.
    // If no such item exists, returns null.
    public T removeFirst() {
        // 判断不为空,证明存在至少一个节点，删除后返回这个节点
        if (isEmpty()) {
            return null;
        }
        // 如果只有一个结点的时候， 会返回null，出现异常
        IntNode beRemoved = frontSentinel.getNext();
        T item = (T) beRemoved.getItem();

        IntNode nextTemp = null;
            nextTemp = beRemoved.getNext();
        // 好像应该先更新指针
        frontSentinel.setNext(nextTemp);
        // 检验制空
        if (nextTemp != null) {
            nextTemp.setPrev(frontSentinel);
        } else {
            backSentinel.setPrev(frontSentinel);
        }
        // 进行删除
        beRemoved.setNext(null);
        beRemoved.setPrev(null);
        // 接上链表
        // 判断是不是列表中 只有一个节点
//        if (nextTemp != backSentinel) {
//            nextTemp.prev = frontSentinel;
//            frontSentinel.next = nextTemp;
//            T item1 = (T) nextTemp.item;
//            size -= 1;
//            return item1;
        size -= 1;
        return item;
    }

    //: Removes and returns the item at the back of the deque.
// If no such item exists, returns null.
    public T removeLast() {
        // 判断不为空,证明存在至少一个节点，删除后返回这个节点
        if (isEmpty()) {
            return null;
        }
        // 如果只有一个结点的时候， 会返回null，出现异常
        IntNode beRemoved = backSentinel.getPrev();
        T item = (T) beRemoved.getItem();

        IntNode prevTemp = beRemoved.getPrev();// 可能等于bs

        // 好像应该先更新指针

        backSentinel.setPrev(prevTemp);

        if (prevTemp != null) {
            prevTemp.setNext(backSentinel);
        } else {
            frontSentinel.setNext( backSentinel);
        }
        // 进行删除
        beRemoved.setNext(null);
        beRemoved.setPrev(null);
        // 接上链表
        size -= 1;
        return item;
    }

    public T get(int index)
// 使用迭代
// Gets the item at the given index,
// where 0 is the front, 1 is the next item, and so forth.
// If no such item exists, returns null. Must not alter the deque! // 不能改变链表
    {
        if (index < 0 || index >= size) {
            return null;
        }
        IntNode ptr = frontSentinel.getNext();
        // 除去哨兵节点
        int searchIndex = 0;
        while (searchIndex != index) {
            ptr = ptr.getNext();
            searchIndex++;
        }
        return (T) ptr.getItem();
    }

}