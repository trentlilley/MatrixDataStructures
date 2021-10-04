package com.study.tlilley;

import java.util.Iterator;

/**
 * A generic singly linked list that does not accept duplicate entries
 * Used to implement the adjacency list
 * Node<E> head: pointer to the head of the linked list
 * int size: number of elements in the linked list
 * @author Trent Lilley
 * @sources https://github.com/williamfiset/DEPRECATED-data-structures/blob/master/com/williamfiset/datastructures/linkedlist/DoublyLinkedList.java
 */
public class SinglyLinkedList<E> implements Iterable<E> {
    private Node<E> head;
    private int size = 0;

    // inner node class
    private class Node<E> {
        private E val;
        Node<E> next;

        // constructor
        public Node (E val) {
            this.val = val;
        }

        @Override
        public String toString() {
            return String.valueOf(val);
        }
    }

    // constructor
    public SinglyLinkedList() {
        head = null;
    }

    // list is empty
    public boolean isEmpty() {
        return size == 0;
    }

    // list length
    public int size() {
        return size;
    }

    // get node value by index
    public E getValue(int index) {
        if (index < 0 || index > size - 1) throw new IndexOutOfBoundsException("Index out of bounds");

        Node<E> tmp = head;
        int count = 0;
        while (count != index) {
            tmp = tmp.next;
            count++;
        }
        return tmp.val;
    }

    // get node by index
    public Node<E> getNode(int index) {
        if (index < 0 || index > size - 1) throw new IndexOutOfBoundsException("Index out of bounds");

        Node<E> tmp = head;
        int count = 0;
        while (count != index) {
            tmp = tmp.next;
            count++;
        }
        return tmp;
    }

    // add to front
    public void addFirst(E val) {

        // prevents insertion of duplicates
        if (contains(val)) return;

        Node<E> current = new Node<E>(val);
        current.next = head;
        head = current;
        size++;
    }

    // add to last (add method for Exercise 1)
    public boolean addLast(E val) {

        // use addFirst if list is empty
        if (size == 0) {
            addFirst(val);
            return true;
        }

        // prevents insertion of duplicates
        if (contains(val)) {
            return false;
        }

        Node<E> current = new Node<E>(val);
        Node<E> lastNode = getNode(size - 1);
        lastNode.next = current;
        current.next = null;
        size++;
        return true;
    }

    // add after index
    public void insert(int index, E val) {

        // prevents insertion of duplicates
        if (contains(val)) return;

        if (size == 0) {
            addFirst(val);
            return;
        }

        Node<E> tmp = head;
        int count = 0;
        while (count != index) {
            tmp = tmp.next;
            count++;
        }
        Node<E> node = new Node<E>(val);
        node.next = tmp.next;
        tmp.next = node;
        size++;
    }

    // remove at index
    public void remove(int index) {
        if (index < 0 || index > size - 1) throw new IndexOutOfBoundsException("Index out of bounds");

        if (size > 1 && index > 0) {
            Node<E> remove = getNode(index);
            Node<E> prev = getNode(index - 1);
            prev.next = remove.next;
            remove.next = null;
            remove = null;
        }
        else if (size > 1 && index == 0) {
            Node<E> remove = getNode(index);
            head = remove.next;
            remove.next = null;
            remove = null;
        }
        else {
            head = null;
        }
        size--;
    }

    // return the top value of the singly linked list
    public E pop() {
        if (isEmpty()) throw new RuntimeException("List is empty");
        Node<E> popped = head;
        head = head.next;
        size--;
        return popped.val;
    }

    public E peek() {
        return head.val;
    }

    // check if the item is already in the linked list
    public boolean contains(E item) {
        Node<E> current = head;
        while (current != null) {
            if (current.val.equals(item)) return true;
            current = current.next;
        }
        return false;
    }

    // return string representation of the linked list
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");

        int count = 0;
        Node<E> current = head;
        while (count < size) {
            sb.append(current.val + ", ");
            current = current.next;
            count++;
        }
        if (sb.length() > 3) sb.delete(sb.length() - 2, sb.length());
        sb.append("]");
        return sb.toString();
    }

    // implement Iterable method
    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private Node<E> current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public E next() {
                E val = current.val;
                current = current.next;
                return val;
            }
        };
    }
}
