package algos.symboltable;

import java.util.Iterator;
import java.util.Stack;

public class BSTBasedSymbolTable<Key extends Comparable<Key>,Value> implements SymbolTable<Key, Value> {
    
    private Node rootNode;
    
    public BSTBasedSymbolTable() {}

    @Override
    public void put(Key key, Value val) {
        if(val == null) delete(key);
        Node n = new Node(key,val);
        n.size = 1;
        if(rootNode == null) {
            rootNode = n;
        } else {
            Node existing = search(key, rootNode);
            if(existing == null)
                insert(n,rootNode, null);
            else 
               existing.v = val;
        }
    }

    private void insert(Node toInsert, Node aNode, Node parent) {
        if(aNode == null) {
            if(parent.k.compareTo(toInsert.k) > 0) {
                parent.leftChild = toInsert;
            } else {
                parent.rightChild = toInsert;
            } 
        } else {
            if(aNode.k.compareTo(toInsert.k) > 0) {
                aNode.size++;
                insert(toInsert, aNode.leftChild, aNode);
            } else {
                aNode.size++;
                insert(toInsert, aNode.rightChild, aNode);
            }
        }
    }

    private Node search(Key key, Node node) {
        if(node == null) return null;
        if(node.k.compareTo(key) == 0) {
            return node;
        } else if(node.k.compareTo(key) > 0) {
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
        // TODO Auto-generated method stub
        
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
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Key max() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Key floor(Key key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Key ceiling(Key key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int rank(Key key) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Key select(int k) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void deleteMin() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void deleteMax() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public int size(Key lo, Key hi) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Iterable<Key> keys(Key lo, Key hi) {
        // TODO Auto-generated method stub
        return null;
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
    }
    
    private class SymbolTableIterator implements Iterator<Key> {
        
        private Stack<Key> stack = new Stack<>();
        
        public SymbolTableIterator() {
            visit(rootNode);            
        }

        private void visit(Node node) {            
            if(node == null) return;
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

}
