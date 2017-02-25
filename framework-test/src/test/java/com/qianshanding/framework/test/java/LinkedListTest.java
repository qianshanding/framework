package com.qianshanding.framework.test.java;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by zhengyu on 2016/12/5.
 */
public class LinkedListTest {
    public static void main(String[] srgs) {
        LinkedList<Integer> linkedList = new LinkedList<>();

        /************************** 基本操作 ************************/
        linkedList.addFirst(0);    // 添加元素到列表开头
        linkedList.add(1);         // 在列表结尾添加元素
        linkedList.add(2, 2);       // 在指定位置添加元素
        linkedList.addLast(3);     // 添加元素到列表结尾

        System.out.println("LinkedList: " + linkedList);

        System.out.println("getFirst(): " + linkedList.getFirst());       // 返回此列表的第一个元素
        System.out.println("getLast(): " + linkedList.getLast());         // 返回此列表的最后一个元素
        System.out.println("removeFirst(): " + linkedList.removeFirst()); // 移除并返回此列表的第一个元素
        System.out.println("removeLast(): " + linkedList.removeLast());   // 移除并返回此列表的最后一个元素
        System.out.println("After remove:" + linkedList);
        System.out.println("contains(1) is :" + linkedList.contains(1));  // 判断此列表包含指定元素，如果是，则返回true
        System.out.println("size is : " + linkedList.size());             // 返回此列表的元素个数

        /************************** 位置访问操作 ************************/
        System.out.println("-----------------------------------------");
        linkedList.set(1, 3);                                             // 将此列表中指定位置的元素替换为指定的元素
        System.out.println("After set(1, 3):" + linkedList);
        System.out.println("get(1): " + linkedList.get(1));               // 返回此列表中指定位置处的元素

        /************************** Search操作  ************************/
        System.out.println("-----------------------------------------");
        linkedList.add(3);
        System.out.println("indexOf(3): " + linkedList.indexOf(3));        // 返回此列表中首次出现的指定元素的索引
        System.out.println("lastIndexOf(3): " + linkedList.lastIndexOf(3));// 返回此列表中最后出现的指定元素的索引

        /************************** Queue操作   ************************/
        System.out.println("-----------------------------------------");
        System.out.println("peek(): " + linkedList.peek());                // 获取但不移除此列表的头
        System.out.println("element(): " + linkedList.element());          // 获取但不移除此列表的头
        linkedList.poll();                                                 // 获取并移除此列表的头
        System.out.println("After poll():" + linkedList);
        linkedList.remove();
        System.out.println("After remove():" + linkedList);                // 获取并移除此列表的头
        linkedList.offer(4);
        System.out.println("After offer(4):" + linkedList);                // 将指定元素添加到此列表的末尾

        /************************** Deque操作   ************************/
        System.out.println("-----------------------------------------");
        linkedList.offerFirst(2);                                          // 在此列表的开头插入指定的元素
        System.out.println("After offerFirst(2):" + linkedList);
        linkedList.offerLast(5);                                           // 在此列表末尾插入指定的元素
        System.out.println("After offerLast(5):" + linkedList);
        System.out.println("peekFirst(): " + linkedList.peekFirst());      // 获取但不移除此列表的第一个元素
        System.out.println("peekLast(): " + linkedList.peekLast());        // 获取但不移除此列表的第一个元素
        linkedList.pollFirst();                                            // 获取并移除此列表的第一个元素
        System.out.println("After pollFirst():" + linkedList);
        linkedList.pollLast();                                             // 获取并移除此列表的最后一个元素
        System.out.println("After pollLast():" + linkedList);
        linkedList.push(2);                                                // 将元素推入此列表所表示的堆栈（插入到列表的头）
        System.out.println("After push(2):" + linkedList);
        linkedList.pop();                                                  // 从此列表所表示的堆栈处弹出一个元素（获取并移除列表第一个元素）
        System.out.println("After pop():" + linkedList);
        linkedList.add(3);
        linkedList.removeFirstOccurrence(3);                               // 从此列表中移除第一次出现的指定元素（从头部到尾部遍历列表）
        System.out.println("After removeFirstOccurrence(3):" + linkedList);
        linkedList.removeLastOccurrence(3);                                // 从此列表中移除最后一次出现的指定元素（从头部到尾部遍历列表）
        System.out.println("After removeFirstOccurrence(3):" + linkedList);

        /************************** 遍历操作   ************************/
        System.out.println("-----------------------------------------");
        linkedList.clear();
        for (int i = 0; i < 100000; i++) {
            linkedList.add(i);
        }
        // 迭代器遍历
        long start = System.currentTimeMillis();
        Iterator<Integer> iterator = linkedList.iterator();
        while (iterator.hasNext()) {
            iterator.next();
        }
        long end = System.currentTimeMillis();
        System.out.println("Iterator：" + (end - start) + " ms");

        // 顺序遍历(随机遍历)
        start = System.currentTimeMillis();
        for (int i = 0; i < linkedList.size(); i++) {
            linkedList.get(i);
        }
        end = System.currentTimeMillis();
        System.out.println("for：" + (end - start) + " ms");

        // 另一种for循环遍历
        start = System.currentTimeMillis();
        for (Integer i : linkedList) ;
        end = System.currentTimeMillis();
        System.out.println("for2：" + (end - start) + " ms");

        //  通过pollFirst()或pollLast()来遍历LinkedList
        LinkedList<Integer> temp1 = new LinkedList<>();
        temp1.addAll(linkedList);
        start = System.currentTimeMillis();
        while (temp1.size() != 0) {
            temp1.pollFirst();
        }
        end = System.currentTimeMillis();
        System.out.println("pollFirst()或pollLast()：" + (end - start) + " ms");

        // 通过removeFirst()或removeLast()来遍历LinkedList
        LinkedList<Integer> temp2 = new LinkedList<>();
        temp2.addAll(linkedList);
        start = System.currentTimeMillis();
        while (temp2.size() != 0) {
            temp2.removeFirst();
        }
        end = System.currentTimeMillis();
        System.out.println("removeFirst()或removeLast()：" + (end - start) + " ms");
    }
}
