package service;

import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {

    private static Map<Integer, Node> nodeHashMap = new HashMap<>();
    private Node head;
    private Node tail;
    private int size = 0;



    @Override
    public void add(Task task) {

        nodeHashMap.remove(task.getId());

        linkLast(task);


    }

    @Override
    public void remove(int id) {

        Node node = nodeHashMap.remove(id);

        if (node == null) {

            return;
        }

        removeNode(node);
    }


    @Override
    public List<Task> getHistory() {

        return new ArrayList<>();
    }


    private void removeNode(Node node) {

        if (node.prev == null) {
            // удаляемый узел - голова списка

            node.prev = head;

        } else if (node.next == null) {
            // удаляемый узел - хвост списка

            node.next = tail;

        } else {
            // удаляемый узел - середина списка

            node.prev.next = node.next;
            node.next.prev = node.prev;
        }

    }


    private void linkLast(Task task) {

        final Node oldTail = tail;
        final Node newNode = new Node(null, task, oldTail);
        tail = newNode;
        if (oldTail == null)
            head = newNode;
        else
            oldTail.prev = newNode;
        size++;


        nodeHashMap.put(task.getId(), newNode);

    }

    private static class Node {

        private Node prev;
        private Task data;
        private Node next;


        private Node(Node prev, Task data, Node next) {


            this.data = data;
            this.next = null;
            this.prev = null;



        }
    }
}