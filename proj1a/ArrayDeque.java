/**
 * @Author: huashen
 * @CreateTime: 2024-12-04  14:46
 * @Description: TODO 循环数组的特性
 * TODO 循环数组的特点是当数组的末尾被填满后，新的元素会从数组的开头继续添加。
 * TODO 为了实现这一点，我们需要对数组的下标进行取模运算，以确保下标始终在数组的有效范围内。
 * @Version: 1.0
 **/
public class ArrayDeque<T> {
    // 双端队列  Deque , 我明白了， 在数组双端队列中， 指针就是 下标；
    // 循环数组实际上，就是循环下标；

// TODO Add 和 Remove 必须花费恒定的时间，但在调整大小操作期间除外 ,get 和 size 必须花费恒定的时间。
    //My solution has the following handy invariants.
    //我的解决方案具有以下方便的不变量。
    //
    //The position of the next item to be inserted (using addLast) is always size.
    //要插入的下一个项的位置（使用 addLast）始终为 size。
    //The number of items in the AList is always size.
    //AList 中的项数始终为 size。
    //The position of the last item in the list is always size - 1.
    //列表中最后一项的位置始终为 size - 1。

    private T[] items;
    private int size; // 真实项数
    private int nextFirst; // nextFirst 指向的是下一个插入到队列头部的位置，
    // 指的是下标
    private int nextLast;//指向的是下一个插入到队列尾部的位置
    private int natural; // 假设起始点 为下标四
    private final int expansionFactor = 2;
    private double smallFactor = 0.5;

    // 数组的起始大小应为 8。


//    public ArrayDeque() {
//        // 创建一个8 位置的数组
//        items = (T[]) new Object[8]; // T 是类型参数
//        // 加上初始化，nF是在4的位置
//        // 假设起始点 为下标四
//        size = 0;
//        natural = 4;
//        nextFirst = 4;
//        nextLast = 5;
//    }

    @SuppressWarnings("unchecked")
    public ArrayDeque() {
        items = (T[]) new Object[8]; // 使用泛型数组创建技巧
        size = 0;
        nextFirst = 4;
        nextLast = 5;
    }

    //Adds an item of type T to the front of the deque.
    public void addFirst(T t) {
        if (size == items.length) {
            resize();
        }

        items[nextFirst] = t;
        // 只用下标便是指针
        // nextLast - 1 示意 为上一个位置
        // 进行取模运算 ，目的是将答案·框在 { 0 , item.length}之间
        // 如此就实现了 循环数组
      /*
        如果 a 小于 b，则 a % b 等于 a。
        如果 a 等于 b，则 a % b 等于 0。
        如果 a 大于 b，则 a % b 等于 a 减去 b 的最大整数倍后剩余的部分。
*/
        // 需要判断 nextFirst - 1，这个下标是否为正数
        nextFirst = (nextFirst - 1 + items.length) % items.length;
        // 如果是正数， 加上length也是本身，没有影响，负数则是循环到了 数组末尾
        size += 1;
    }

    //      Adds an item of type T to the back of the deque.
    public void addLast(T t) {
        if (size == items.length) {
            resize(); // 在添加前调用， 保证有足够的空间，新数据可以放进去
        }
        items[nextLast] = t;
        // 只用下标便是指针
        // nextLast + 1 示意 为下一个位置
        // 进行取模运算 ，目的是将答案·框在 { 0 , item.length}之间
        // 如此就实现了 循环数组
      /*
        如果 a 小于 b，则 a % b 等于 a。
        如果 a 等于 b，则 a % b 等于 0。
        如果 a 大于 b，则 a % b 等于 a 减去 b 的最大整数倍后剩余的部分。
*/
        nextLast = ((nextLast + 1) % items.length);
        size += 1;
    }


    //: Returns true if deque is empty, false otherwise.
    public boolean isEmpty() {
        return size == 0;
    }

    //: Returns the number of items in the deque.
    public int size() {
        return size;
    }

    //: Prints the items in the deque from first to last, separated by a space.
    public void printDeque() {
        for (T item : items) {
            System.out.print(item + " ");
        }
    }

    // Removes and returns the item at the front of the deque.
    // If no such item exists, returns null.
    public T removeFirst() {   // 删除前端
        if (size != 0) {
            int front = (nextFirst + 1) % items.length;
            T item = items[front];
            items[front] = null;
// 直接就等于原来前端的位置
            nextFirst = front;
            size -= 1;
            // 检查数组是否需要缩容
            if (size > 0 && (double) size / items.length <= 0.25) {
                resize();
            }
            return item;
        }
        return null;
    }

    //: Removes and returns   the item at the back of the deque.
    // If no such item exists, returns null.
    // 意思就是删除队列的后端  并 返回
    public T removeLast() {
        if (size != 0) {
            T t;
            // 保证下标不会变成负数
            int last = (nextLast - 1 + items.length) % items.length;
            ;
            t = items[last];
            // 删除
            items[last] = null;
            // 指针前移
            nextLast = last;
            size -= 1;
            // 检查数组是否需要缩容
            if (size > 0 && (double) size / items.length <= 0.25) {
                resize();
            }
            return t;
        }
        return null;
    }

    // Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
    // If no such item exists, returns null. Must not alter the deque!
   /*
front + index:
front 是队列前端在数组中的位置。
index 是从队列前端开始的顺序索引。
front + index 计算出从 front 开始的第 index 个位置在数组中的相对位置。
% items.length:
% items.length 是取模运算，确保 actualIndex 始终在数组的有效范围内（即 0 到 items.length - 1）。
如果 front + index 超过了数组的长度，取模运算会将其“循环”回数组的开头
   */
    public T get(int index) {
        // 列出失败条件
        if (!(index < 0 || index >= items.length)) {
            // 求出队列的前端
            int front = (nextFirst + 1) % items.length;
// 其实就是使得无论 nF指针指在哪个位置， 通过这样的方法 ，循环求出前端
// 实际上指向的是当前队列的第一个元素的位置。我们可以通过 (nextFirst + 1) % items.length
//来确保这个索引在数组的有效范围内
            int actualIndex = (front + index) % items.length;
            return items[actualIndex];
        }
        return null;
    }

    //    private void resize() {
///* 对于长度为16或更大的数组，使用率（即数组中实际存储的元素数量与数组长度的比例）应该至少为25%。
//对于较小的数组，使用率可以任意低。
//*/
//
//        // int newCapacity = size == items.length ? items.length * expansionFactor
//        // 扩容
//        if (size == items.length) {
//            int newCapacity = items.length * expansionFactor;
//            T[] newArray = (T[]) new Object[newCapacity];
//            System.arraycopy(items, 0, newArray, 0, items.length);
//            items = newArray;
//        }
//        // 缩容
//        // 将size 转为double 类型
//        if (size > 0 && (double) size / items.length <= 0.25) {
//            int newCapacity = (int) (items.length * smallFactor);
//            T[] newArray = (T[]) new Object[newCapacity];
//            System.arraycopy(items, 0, newArray, 0, items.length);
//            items = newArray;
//        }
//    }

    private void resize() {
        // 扩容
        if (size == items.length) {
            int newCapacity = items.length * 2;
            T[] newItems = (T[]) new Object[newCapacity];

            int startIndex = newCapacity / 4; // 开始的下标
            for (int i = 0; i < size; i++) {
                // 取模运算，下标不会出界
                newItems[startIndex + i] = items[(nextFirst + 1 + i) % items.length];
            }
            //System.arraycopy 中，复制的元素数量是 items.length，即旧数组的总长度，
            // 这可能会导致复制多余的 null 元素或超出数组边界。
            //需要注意的是 System.arraycopy 是连续复制的，
            //而这里的复制涉及到环形数组的处理，因此需要分两次复制来处理环形数组的情况。
            // System.arraycopy(items, ((nextFirst + 1 ) % items.length), newItems, startIndex , size);
            items = newItems;
            nextFirst = startIndex - 1;
            nextLast = startIndex + size;
        }
        // 缩容
        // 将size 转为double 类型
        if (size > 0 && (double) size / items.length <= 0.25) {
            int newCapacity = (int) (items.length * smallFactor);
            T[] newItems = (T[]) new Object[newCapacity];

            int startIndex = newCapacity / 4; // 开始的下标
            for (int i = 0; i < size; i++) {
                // 取模运算，下标不会出界
                newItems[startIndex + i] = items[(nextFirst + 1 + i) % items.length];
            }
            //System.arraycopy 中，复制的元素数量是 items.length，即旧数组的总长度，
            // 这可能会导致复制多余的 null 元素或超出数组边界。
            //需要注意的是 System.arraycopy 是连续复制的，
            //而这里的复制涉及到环形数组的处理，因此需要分两次复制来处理环形数组的情况。
            // System.arraycopy(items, ((nextFirst + 1 ) % items.length), newItems, startIndex , size);
            items = newItems;
            nextFirst = startIndex - 1;
            nextLast = startIndex + size;
        }
    }
}