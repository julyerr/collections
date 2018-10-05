package com.julyerr.interviews.thread.ConcurrentProgramming.concepts.lock;

import java.util.concurrent.atomic.AtomicReference;

public class ConcurrentStack<E> {
    AtomicReference<Node<E>> top = new AtomicReference<>();

    public void push(E item) {
        Node<E> newHead = new Node(item);
        Node<E> oldHead = null;
        do {
            oldHead = top.get();
            newHead.next = oldHead;
//            自从上次读取以来，元素没有发生改变
        } while (!top.compareAndSet(oldHead, newHead));
    }

    public E pop() {
        Node<E> oldHead;
        Node<E> newHead;
        do {
            oldHead = top.get();
            if (oldHead == null) {
                return null;
            }
            newHead = oldHead.next;
        } while (!top.compareAndSet(oldHead, newHead));
        return oldHead.item;
    }

    private static class Node<E> {
        public final E item;
        public Node<E> next;

        public Node(E item) {
            this.item = item;
        }
    }
}
