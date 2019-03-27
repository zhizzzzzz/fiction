package com.zhi.fiction.util;

public class DoubleLinkedList<T> {
    Node head = null;
    int  size = 0;

}

class Node {
    Object value;
    Node   pre  = null;
    Node   next = null;

    Node(Object value) {
        this.value = value;
        this.pre = null;
        this.next = null;
    }
}