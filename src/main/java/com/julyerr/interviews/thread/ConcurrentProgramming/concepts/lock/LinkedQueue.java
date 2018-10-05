package com.julyerr.interviews.thread.ConcurrentProgramming.concepts.lock;

import java.util.concurrent.atomic.AtomicReference;

public class LinkedQueue<E> {
    private static class Node<E> {
        final E item;
        final AtomicReference<Node<E>> next;

        public Node(E item, AtomicReference<Node<E>> next) {
            this.item = item;
            this.next = next;
        }
    }

    private final Node<E> dummy = new Node<>(null, null);
    private final AtomicReference<Node<E>> head =
            new AtomicReference<>(dummy);
    private final AtomicReference<Node<E>> tail =
            new AtomicReference<>(dummy);

    public boolean put(E item) {
        Node<E> newNode = new Node<>(item, null);
        while (true) {
            Node<E> curTail = tail.get();
            Node<E> tailNext = curTail.next.get();
            if (curTail == tail.get()) {
//                队列处于中间状态，推进尾节点
                if (tailNext != null) {
                    tail.compareAndSet(curTail, tailNext);
                } else {
//                    处于稳定状态，尝试插入新节点
                    if (curTail.next.compareAndSet(null, newNode)) {
                        tail.compareAndSet(curTail, newNode);
                    }
                    return true;
                }
            }
        }
    }
}
