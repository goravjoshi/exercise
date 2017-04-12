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
        return recursiveMin(rootNode);        
    }

    private Key recursiveMin(Node aNode) {
        if(aNode.leftChild == null) {
            return aNode.k;
        } else {
            return recursiveMin(aNode.leftChild);
        }
    }

    @Override
    public Key max() {
        return recursiveMax(rootNode);
    }

    private Key recursiveMax(Node aNode) {
        if(aNode.rightChild == null) {
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
        if(aNode == null) return null;
        int cmp = aNode.k.compareTo(input);
        if(cmp == 0) {
            return aNode.k;
        } else if(cmp > 0){
            return recursiveFloor(input, aNode.leftChild);            
        } else {
            result = recursiveFloor(input, aNode.rightChild);
        }
        
        if(result == null) {
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
        if(aNode == null) return null;
        int cmp = aNode.k.compareTo(key);
        if(cmp == 0) {
            return key;
        } else if(cmp < 0) {
            return recursiveCeiling(aNode.rightChild, key);
        } else {
            Key r = recursiveCeiling(aNode.leftChild, key);
            if(r == null) {
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
        if(aNode == null) return 0;
        int cmp = aNode.k.compareTo(key);
        if(cmp > 0) {
            return findRank(aNode.leftChild, key);
        } else if(cmp < 0) {
            return 1 + size(aNode.leftChild)+ findRank(aNode.rightChild, key);
        } else {
            return size(aNode.leftChild);
        }
    }

    @Override
    public Key select(int rank) {
        return findNodeForRank(rootNode, rank).k;        
    }

    private Node findNodeForRank(Node aNode, int rank) {    
        if(aNode == null) return null;
        int n = size(aNode.leftChild);
        if(n > rank) {
            return findNodeForRank(aNode.leftChild, rank);
        } else if(n < rank) {
            return findNodeForRank(aNode.rightChild, rank-n-1);
        } else {
            return aNode;
        }
    }
    
    int size(Node node) {
        if(node == null) return 0; 
        return node.size;
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
