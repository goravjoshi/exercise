package algos.symboltable;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class TestClient {

    public static void main(String[] args) {
        SymbolTable<String, Integer> st = new BSTBasedSymbolTable<String, Integer>();
//        for (int i = 0; !StdIn.isEmpty(); i++) {
//            String key = StdIn.readString();
//            st.put(key, i);
//        }
        int index = 0;
        for(char s:"SEARCHEXAMPLE".toCharArray()) {
            st.put(String.valueOf(s), index++);
            
        }
        for (String s : st.keys())
            StdOut.println(s + " " + st.get(s));
        
        System.out.println("Minimum is "+st.min());
        System.out.println("Maximum is "+st.max());
        
        System.out.println("Floor of Y is "+st.floor("Y"));
        System.out.println("Floor of B is "+st.floor("B"));
        
        System.out.println("Ceiling of W is "+st.ceiling("W"));
        
        System.out.println("Select result of select(3) "+st.select(3));
        System.out.println("Rank of select(3) is 3 "+st.rank(st.select(3)));
    }
}
