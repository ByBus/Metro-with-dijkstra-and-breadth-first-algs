package metro;

import java.util.Objects;

public class CustomLinkedList<T> {
    public String name = "";
    private int length = 0;
    Node<T> head = null;

    public CustomLinkedList(String name) {
        this.name = name;
    }

    public CustomLinkedList() { }

    public void append(T data) {
        Node<T> newNode = new Node<>(data);
        if (head == null) {
            head = newNode;
        } else {
            Node<T> current = head;
            while (current.nextNode != null) {
                current = current.nextNode;
            }
            current.nextNode = newNode;
            newNode.previousNode = current;
        }
        length++;
    }

    public void addHead(T data) {
        Node<T> newNode = new Node<>(data);
        head.previousNode = newNode;
        newNode.nextNode = head;
        head = newNode;
        length++;
    }

    public void insert(T data, int index) {
        Node<T> newNode = new Node<>(data);
        int currentIndex = 0;
        Node<T> current = head;
        while (currentIndex != index) {
            current = current.nextNode;
            currentIndex++;
        }
        Node<T> previous = current.previousNode;
        Node<T> next = current;
        newNode.nextNode = next;
        newNode.previousNode = previous;
        next.previousNode = newNode;
        if (previous != null) {
            previous.nextNode = newNode;
        }
        if (index == 0) {
            head = newNode;
        }
        length++;
    }

    public void remove (T data) {
        Node<T> nodeToDelete = new Node<>(data);
        if (head.equals(nodeToDelete)) {
            Node<T> next = head.nextNode;
            next.previousNode = null;
            head = next;
            length--;
            return;
        }
        Node<T> current = head;
        while (!current.equals(nodeToDelete) && current.nextNode != null) {
            current = current.nextNode;
        }
        if (current.nextNode == null && current.equals(nodeToDelete)) {
            Node<T> previous = current.previousNode;
            previous.nextNode = null;
            length--;
            return;
        }
        if (current.equals(nodeToDelete)) {
            Node<T> next = current.nextNode;
            Node<T> previous = current.previousNode;
            previous.nextNode = next;
            assert next != null;
            next.previousNode = previous;
            length--;
        }
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        Node<T> current = head;
        while (current.nextNode != null) {
            if (current.previousNode == null) {
                current = current.nextNode;
                continue;
            }
            String prev = current.previousNode.data.toString();
            String next = current.nextNode.data.toString();
            String str = prev + " - " + current.data + " - " + next + "\n";
            buffer.append(str);
            current = current.nextNode;
        }
        return buffer.toString();
    }

    public int getLength() {
        return length;
    }
}

class Node<T> {
    T data;
    Node<T> nextNode;
    Node<T> previousNode;

    public Node(T data) {
        this.data = data;
        this.nextNode = null;
        this.previousNode = null;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Node)) {
            return false;
        }

        Node<T> other = (Node<T>) obj;
        return Objects.equals(this.data, other.data);
    }
}
