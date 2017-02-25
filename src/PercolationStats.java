import java.util.Arrays;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

       private int sites;
       private int[] trialsResult;

       public PercolationStats(int n, int trials) {
              // perform trials independent experiments on an n-by-n grid

              this.sites = n * n;
              trialsResult = new int[trials];
              for (int i = 0; i < trialsResult.length; i++) {
                     Percolation p = new Percolation(n);
                     int openSites = 0;
                     while (!p.percolates()) {
                            int row = StdRandom.uniform(1, n + 1);
                            int col = StdRandom.uniform(1, n + 1);
                            if (!p.isOpen(row, col)) {
                                   p.open(row, col);
                                   openSites++;
                            }
                     }
                     trialsResult[i] = openSites;
              }

       }       

       public double mean() { // sample mean of percolation threshold
              return StdStats.mean(Arrays.stream(trialsResult).asDoubleStream().map(r -> r / sites).toArray());
       }

       public double stddev() { // sample standard deviation of percolation
                                                        // threshold
              double m = mean();
              return StdStats.stddev(Arrays.stream(trialsResult).asDoubleStream().map(r -> (r / sites - m)).toArray());
       }

       public double confidenceLo() { // low endpoint of 95% confidence interval
              return mean() - (1.96 * stddev()) / Math.sqrt(trialsResult.length);
       }

       public double confidenceHi() { // high endpoint of 95% confidence interval
              return mean() + (1.96 * stddev()) / Math.sqrt(trialsResult.length);
       }

       public static void main(String[] args) { // test client (described below)
              if (args.length != 2) {
                     System.out.println("Usage: " + PercolationStats.class.getName() + " <grid-size> <repeat-by>");
                     return;
              } else {
                     PercolationStats ps = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
                     System.out.println("mean = " + ps.mean());
                     System.out.println("stddev = " + ps.stddev());
                     System.out.println("95% confidence interval = [" + ps.confidenceLo() + "," + ps.confidenceHi() + "]");
              }

       }
}
