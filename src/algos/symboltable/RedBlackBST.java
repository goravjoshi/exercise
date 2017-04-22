package algos.symboltable;

public class RedBlackBST<Key extends Comparable<Key>, Value> implements SymbolTable<Key, Value> {
    
    private static int BLACK_COLOR = 0;
    private static int RED_COLOR = 1;
    
    private Node rootNode;
    
    
    @Override
    public void put(Key key, Value val) {
        rootNode = insert(rootNode, key, val);
        rootNode.color = BLACK_COLOR;        
    }

    private Node insert(Node tree, Key key, Value val) {
        if(tree == null) {
            return new Node(key, val, 1, RED_COLOR);
        }
        int cmp = key.compareTo(tree.key);
        if(cmp < 0) tree.leftChild = insert(tree.leftChild, key, val);
        if(cmp > 0) tree.rightChild = insert(tree.rightChild, key, val);
        else tree.value = val;
        
        
        if(!isRed(tree.leftChild) && isRed(tree.rightChild)) tree = rotateLeft(tree);
        if(isRed(tree.leftChild) && isRed(tree.leftChild.leftChild)) tree = rotateRight(tree);
        if(isRed(tree.leftChild) && isRed(tree.leftChild)) flipColor(tree);
        
        tree.size = size(tree.leftChild)+size(tree.rightChild) +1;
        return tree;
    }
    
    private boolean isRed(Node n) {
        return n != null && n.color == RED_COLOR ? true : false;
    }

    private void flipColor(Node tree) {
        tree.leftChild.color = BLACK_COLOR;
        tree.rightChild.color = BLACK_COLOR;
        tree.color = RED_COLOR;
    }

    private Node rotateRight(Node h) {
        Node x = h.leftChild;
        h.leftChild = x.rightChild;
        x.rightChild = h;
        x.color = h.color;
        h.color = RED_COLOR;
        x.size = h.size;
        h.size = size(h.leftChild)+size(h.rightChild)+1;
        return x;
    }
    
    private Node rotateLeft(Node h) {
        Node x = h.rightChild;
        h.rightChild = x.leftChild;
        x.leftChild = h;
        x.size = h.size;
        x.color = h.color;
        h.size = size(h.leftChild)+size(h.rightChild)+1;
        return x;
    }

    private int size(Node node) {
        return node != null?node.size:0;
    }

    @Override
    public Value get(Key key) {
        return recursiveGet(rootNode, key).value;
    }

    private Node recursiveGet(Node tree, Key key) {
        if(tree == null) {
            return null;
        } else {
            int cmp = key.compareTo(tree.key);
            if(cmp == 0) {
                return tree;
            } else if(cmp < 0){
                return recursiveGet(tree.leftChild, key);
            } else {
                return recursiveGet(tree.rightChild, key);   
            }
        }
    }

    @Override
    public void delete(Key key) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean contains(Key key) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isEmpty() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public int size() {
        // TODO Auto-generated method stub
        return 0;
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
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void preOrderTraversal() {
        // TODO Auto-generated method stub
        
    }
    
    private class Node {        
        private Key key;
        private Value value;
        private Node leftChild;
        private Node rightChild;
        private int size = 0;
        private int color;
        
        public Node(Key key, Value val, int size, int color) {
            this.key = key;
            this.value = val;
            this.size = size;
            this.color = color;
        }
    }

}
