package algos.symboltable;

public interface SymbolTable<Key extends Comparable<Key>, Value> {
    
    /**
     *  put key-value pair into the table (remove key from table if value is null)
     */
    void put(Key key, Value val);
    
    /**
     * value paired with key (null if key is absent)
     * @param key
     * @return
     */
    Value get(Key key);
    
    /**
     * remove key (and its value) from table
     * @param key
     */
    void delete(Key key);
    
    /**
     * is there a value paired with key?
     * @param key
     * @return
     */
    boolean contains(Key key);
    
    /**
     *  is the table empty?
     * @return
     */
    boolean isEmpty();
    
    /**
     *  number of key-value pairs
     * @return
     */
    int size();
    
    /**
     *  smallest key
     * @return
     */
    Key min();
    
    /**
     *  largest key
     * @return
     */
    Key max();
    
    /**
     *  largest key less than or equal to key
     * @param key
     * @return
     */
    Key floor(Key key);
    
    /**
     *  smallest key greater than or equal to key
     * @param key
     * @return
     */
    Key ceiling(Key key);
    
    /**
     *  number of keys less than key
     * @param key
     * @return
     */
    int rank(Key key);
    
    /**
     *  key of rank k
     * @param k
     * @return
     */
    Key select(int k);
    
    /**
     *  delete smallest key
     */
    void deleteMin();
    
    /**
     *  delete largest key
     */
    void deleteMax();
    
    /**
     *  number of keys in [lo..hi]
     * @param lo
     * @param hi
     * @return
     */
    int size(Key lo, Key hi);
    
    /**
     * keys in [lo..hi], in sorted order
     * @param lo
     * @param hi
     * @return
     */
    Iterable<Key> keys(Key lo, Key hi);
    
    /**
     *  all keys in the table, in sorted order
     * @return
     */
    Iterable<Key> keys();

    void preOrderTraversal();
}
