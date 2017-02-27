import edu.princeton.cs.algs4.StdIn;

public class Permutation {

    public static void main(String[] args) {
        assert args.length == 1;
        int noOfPermutation = Integer.parseInt(args[0]);

   RandomizedQueue<String> rq = new RandomizedQueue<>();
   while (!StdIn.isEmpty()) {
   String str = StdIn.readString();
   rq.enqueue(str);
   }

   System.out.println(rq.toString());

   for (int i = 0; i < noOfPermutation; i++) {
   System.out.println(rq.dequeue());

   }

}

}
