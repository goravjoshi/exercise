package algos.symboltable;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class BSTBasedSymbolTable<Key extends Comparable<Key>, Value> implements SymbolTable<Key, Value> {

    private Node rootNode;

    public BSTBasedSymbolTable() {
    }

    @Override
    public void put(Key key, Value val) {
        if (val == null)
            delete(key);
        Node n = new Node(key, val);
        n.size = 1;
        if (rootNode == null) {
            rootNode = n;
        } else {
            Node existing = search(key, rootNode);
            if (existing == null)
                insert(n, rootNode, null);
            else
                existing.v = val;
        }
    }

    private void insert(Node toInsert, Node aNode, Node parent) {
        if (aNode == null) {
            if (parent.k.compareTo(toInsert.k) > 0) {
                parent.leftChild = toInsert;
            } else {
                parent.rightChild = toInsert;
            }
        } else {
            if (aNode.k.compareTo(toInsert.k) > 0) {
                aNode.size++;
                insert(toInsert, aNode.leftChild, aNode);
            } else {
                aNode.size++;
                insert(toInsert, aNode.rightChild, aNode);
            }
        }
    }

    private Node search(Key key, Node node) {
        if (node == null)
            return null;
        if (node.k.compareTo(key) == 0) {
            return node;
        } else if (node.k.compareTo(key) > 0) {
            return search(key, node.leftChild);
        } else {
            return search(key, node.rightChild);
        }
    }

    @Override
    public Value get(Key key) {
        return search(key, rootNode).v;
    }

    @Override
    public void delete(Key key) {
        rootNode = deleteRecursively(key, rootNode);
    }

    private Node deleteRecursively(Key key, Node aNode) {
        int cmp = aNode.k.compareTo(key);
        Node n = null;
        if (cmp == 0) {
            Node sub = recursiveMin(aNode.rightChild);
            deleteMinRecursively(aNode.rightChild);

            sub.leftChild = aNode.leftChild;
            if (sub != aNode.rightChild)
                sub.rightChild = aNode.rightChild;
            sub.size = aNode.size - 1;
            aNode.leftChild = null;
            aNode.rightChild = null;
            return sub;
        } else if (cmp > 0) {
            n = deleteRecursively(key, aNode.leftChild);
            aNode.leftChild = n;
        } else {
            n = deleteRecursively(key, aNode.rightChild);
            aNode.rightChild = n;
        }
        aNode.size = size(aNode.leftChild) + size(aNode.rightChild) + 1;
        return aNode;
    }

    @Override
    public boolean contains(Key key) {
        return search(key, rootNode) != null;
    }

    @Override
    public boolean isEmpty() {
        return rootNode == null;
    }

    @Override
    public int size() {
        return rootNode.size;
    }

    @Override
    public Key min() {
        return recursiveMin(rootNode).k;
    }

    private Node recursiveMin(Node aNode) {
        if (aNode.leftChild == null) {
            return aNode;
        } else {
            return recursiveMin(aNode.leftChild);
        }
    }

    @Override
    public Key max() {
        return recursiveMax(rootNode);
    }

    private Key recursiveMax(Node aNode) {
        if (aNode.rightChild == null) {
            return aNode.k;
        } else {
            return recursiveMax(aNode.rightChild);
        }
    }

    @Override
    public Key floor(Key key) {
        return recursiveFloor(key, rootNode);
    }

    private Key recursiveFloor(Key input, Node aNode) {
        Key result;
        if (aNode == null)
            return null;
        int cmp = aNode.k.compareTo(input);
        if (cmp == 0) {
            return aNode.k;
        } else if (cmp > 0) {
            return recursiveFloor(input, aNode.leftChild);
        } else {
            result = recursiveFloor(input, aNode.rightChild);
        }

        if (result == null) {
            return aNode.k;
        } else {
            return result;
        }
    }

    @Override
    public Key ceiling(Key key) {
        return recursiveCeiling(rootNode, key);
    }

    private Key recursiveCeiling(Node aNode, Key key) {
        if (aNode == null)
            return null;
        int cmp = aNode.k.compareTo(key);
        if (cmp == 0) {
            return key;
        } else if (cmp < 0) {
            return recursiveCeiling(aNode.rightChild, key);
        } else {
            Key r = recursiveCeiling(aNode.leftChild, key);
            if (r == null) {
                return aNode.k;
            } else {
                return r;
            }
        }
    }

    @Override
    public int rank(Key key) {
        return findRank(rootNode, key);
    }

    private int findRank(Node aNode, Key key) {
        if (aNode == null)
            return 0;
        int cmp = aNode.k.compareTo(key);
        if (cmp > 0) {
            return findRank(aNode.leftChild, key);
        } else if (cmp < 0) {
            return 1 + size(aNode.leftChild) + findRank(aNode.rightChild, key);
        } else {
            return size(aNode.leftChild);
        }
    }

    @Override
    public Key select(int rank) {
        return findNodeForRank(rootNode, rank).k;
    }

    private Node findNodeForRank(Node aNode, int rank) {
        if (aNode == null)
            return null;
        int n = size(aNode.leftChild);
        if (n > rank) {
            return findNodeForRank(aNode.leftChild, rank);
        } else if (n < rank) {
            return findNodeForRank(aNode.rightChild, rank - n - 1);
        } else {
            return aNode;
        }
    }

    int size(Node node) {
        if (node == null)
            return 0;
        return node.size;
    }


    @Override
    public void deleteMin() {
        deleteMinRecursively(rootNode);
    }

    private Node deleteMinRecursively(Node aNode) {
        if (aNode.leftChild == null)
            return aNode.rightChild;
        else {
            aNode.leftChild = deleteMinRecursively(aNode.leftChild);
            aNode.size = size(aNode.leftChild) + size(aNode.rightChild) + 1;
            return aNode;
        }
    }

    private Node deleteMaxRecursively(Node aNode) {
        if (aNode.rightChild == null)
            return aNode.leftChild;
        else {
            aNode.rightChild = deleteMinRecursively(aNode.rightChild);
            aNode.size = size(aNode.leftChild) + size(aNode.rightChild) + 1;
            return aNode;
        }
    }

    @Override
    public void deleteMax() {
        deleteMaxRecursively(rootNode);
    }

    @Override
    public int size(Key lo, Key hi) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Iterable<Key> keys(Key lo, Key hi) {
        return () -> new RangeIterator(lo, hi);
    }

    @Override
    public Iterable<Key> keys() {
        return new Iterable<Key>() {
            @Override
            public Iterator<Key> iterator() {
                return new SymbolTableIterator();
            }
        };
    }


    private class Node {
        Key k;
        Value v;
        Node leftChild;
        Node rightChild;
        int size;

        public Node(Key key, Value val) {
            this.k = key;
            this.v = val;
        }

        @Override
        public String toString() {
            return k.toString();
        }
    }

    private class SymbolTableIterator implements Iterator<Key> {

        private Stack<Key> stack = new Stack<>();

        public SymbolTableIterator() {
            visit(rootNode);
        }

        private void visit(Node node) {
            if (node == null)
                return;
            else {
                visit(node.leftChild);
                stack.push(node.k);
                visit(node.rightChild);
            }
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public Key next() {
            return stack.pop();
        }

    }

    private class RangeIterator implements Iterator<Key> {

        private Queue<Key> q = new ArrayDeque<>();
        private Key from;
        private Key to;

        public RangeIterator(Key from, Key to) {
            this.from = from;
            this.to = to;
            populateQueue(rootNode);
        }

        private void populateQueue(Node aNode) {
            if (aNode == null)
                return;
            int cmp = from.compareTo(aNode.k);
            int cmpTo = to.compareTo(aNode.k);
            if (cmp <= 0) {
                populateQueue(aNode.leftChild);
            }
            if (cmp <= 0 && cmpTo >= 0)
                q.add(aNode.k);

            if (cmpTo >= 0) {
                populateQueue(aNode.rightChild);
            }
        }

        @Override
        public boolean hasNext() {
            return !q.isEmpty();
        }

        @Override
        public Key next() {
            return q.remove();
        }

    }

    @Override
    public void preOrderTraversal() {
        preOrder(rootNode);
    }

    private void preOrder(Node aNode) {
        if(aNode == null) return;
        System.out.print(aNode.k+" ");
        preOrder(aNode.leftChild);
        preOrder(aNode.rightChild);
    }

    public void inOrderTraversal() {
        inOrder(rootNode);
    }

    private void inOrder(Node aNode) {
        if(aNode == null) {
            return;
        } else {
            inOrder(aNode.leftChild);
            System.out.print(aNode.k+" ");
            inOrder(aNode.rightChild);
        }
    }

    public void postOrderTraversal() {
        postOrder(rootNode);
    }
    
    private void postOrder(Node aNode) {
        if(aNode == null) {
            return;
        } else {
            inOrder(aNode.rightChild);
            System.out.print(aNode.k+" ");
            inOrder(aNode.leftChild);            
        }
    }

    public void levelTraversal() {
        Queue<Key> q = new ArrayDeque<>();
        levelTraversal(q,Collections.singletonList(rootNode));
        q.forEach(k -> System.out.print(k+" "));
    }
    
    private void levelTraversal(Queue<Key> q, List<Node> aNode) {
        if(aNode.size() == 0) {
            return;
        } else {
            List<Node> childs = new ArrayList<>();
            aNode.stream().filter(n -> n!=null).forEach(k -> {
                q.add(k.k);                
                childs.add(k.leftChild);
                childs.add(k.rightChild);
            });
            levelTraversal(q,childs);
        }
    }


}
