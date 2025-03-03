public class LinkedListDequeTest1 {

    public static void main(String[] args) {
        // 创建一个新的 LinkedListDeque 实例
        LinkedListDeque<Integer> deque = new LinkedListDeque<>();

        // 测试添加元素到队列前面
        System.out.println("AddFirst test:");
        deque.addFirst(10);
        deque.addFirst(20);
        deque.addFirst(30);
        deque.printDeque();  // 应该输出: 30 20 10
        System.out.println();

        // 测试添加元素到队列后面
        System.out.println("AddLast test:");
        deque.addLast(40);
        deque.addLast(50);
        deque.printDeque();  // 应该输出: 30 20 10 40 50
        System.out.println();

        // 测试获取特定索引位置的元素
        System.out.println("Get test:");
        System.out.println(deque.get(0));  // 应该输出: 30
        System.out.println(deque.get(3));  // 应该输出: 40
        System.out.println(deque.get(5));  // 应该输出: 50
        System.out.println(deque.get(6));  // 应该输出: null (不存在)

        // 测试递归获取特定索引位置的元素
        System.out.println("GetRecursive test:");
        System.out.println(deque.getRecursive(1));  // 应该输出: 20
        System.out.println(deque.getRecursive(4));  // 应该输出: 50
        System.out.println(deque.getRecursive(6));  // 应该输出: null (不存在)

        // 测试删除队列前面的元素
        System.out.println("RemoveFirst test:");
        System.out.println(deque.removeFirst());  // 应该输出: 30
        deque.printDeque();  // 应该输出: 20 10 40 50
        System.out.println();

        // 测试删除队列后面的元素
        System.out.println("RemoveLast test:");
        System.out.println(deque.removeLast());  // 应该输出: 50
        deque.printDeque();  // 应该输出: 20 10 40
        System.out.println();

        // 测试队列是否为空
        System.out.println("isEmpty test:");
        System.out.println(deque.isEmpty());  // 应该输出: false
        System.out.println("Size test:");
        System.out.println(deque.size());  // 应该输出: 3

        // 删除剩余元素
        deque.removeFirst();  // 删除 20
        deque.removeLast();   // 删除 40
        deque.removeFirst();  // 删除 10

        // 测试队列是否为空
        System.out.println("After removing all elements:");
        System.out.println(deque.isEmpty());  // 应该输出: true
        System.out.println("Size test:");
        System.out.println(deque.size());  // 应该输出: 0
    }
}
