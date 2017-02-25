
import java.util.HashSet;
import java.util.Set;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int squareLength;
       private final int firstComponent = 0;
      private final int lastComponent;
      private final WeightedQuickUnionUF quickUnionFind;
      private final Set<Integer> openedSite = new HashSet<>();

      public Percolation(int n) {
            if (n <= 0) {
                  throw new IllegalArgumentException("Please provide a valid number " + n);
            }

            this.squareLength = n;
            this.lastComponent = n * n + 1;
            // component 0 is connected to top row while component n*n+1 is
            // connected to bottom row.
            int noOfComponents = n * n + 2;
            // create n-by-n grid, with all sites blocked
            this.quickUnionFind = new WeightedQuickUnionUF(noOfComponents);

      }

      // open site (row, col) if it is not open already
      public void open(int row, int col) {
            checkBoundries(row, col);
            int componentNumber = getComponentNumber(row, col);
            int[] neighborComponentNumber = getNeighborComponentNumber(row, col);
            for (int neighbor : neighborComponentNumber) {
                  if (openedSite.contains(neighbor)) {
                        quickUnionFind.union(componentNumber, neighbor);
                  }
            }
            if (row == 1) {
                  quickUnionFind.union(firstComponent, componentNumber);
            } else if (row == squareLength) {
                  quickUnionFind.union(lastComponent, componentNumber);
            }
            openedSite.add(componentNumber);
      }

      // is site (row, col) open?
      public boolean isOpen(int row, int col) {
            checkBoundries(row, col);
            int componentNumber = getComponentNumber(row, col);
            return isOpen(componentNumber);
      }

      private boolean isOpen(int componentNumber) {
            return openedSite.contains(componentNumber);
      }

      public boolean isFull(int row, int col) {
            checkBoundries(row, col);
            return quickUnionFind.connected(firstComponent, getComponentNumber(row, col));
      }

      public int numberOfOpenSites() {
            return openedSite.size();
      }

      public boolean percolates() {
            return quickUnionFind.connected(firstComponent, lastComponent);
      }

      private void checkBoundries(int row, int col) {
            if (row <= 0 || row > squareLength || col <= 0 || col > squareLength) {
                  throw new IndexOutOfBoundsException(String.format("Invalid row/columns [%s,%s]", row, col));
            }
      }

      private int getComponentNumber(int row, int col) {
            int startIndex = row == 1 ? 0 : row - 1;
            return (startIndex * squareLength) + col;
      }

      private int[] getNeighborComponentNumber(int row, int col) {

            if (isAtTheBorder(row, col) && isAtDiagonalEnd(row, col)) {
                  if (row == 1 && col == squareLength) {
                        return new int[] { getComponentNumber(row, col - 1), getComponentNumber(row + 1, col) };
                  } else if (row == 1 && col == 1) {
                        return new int[] { getComponentNumber(row, col + 1), getComponentNumber(row + 1, col) };
                  } else if (row == squareLength && col == 1) {
                        return new int[] { getComponentNumber(row - 1, col), getComponentNumber(row, col + 1) };
                  } else {
                        assert row == squareLength && col == squareLength;
                        return new int[] { getComponentNumber(row, col - 1), getComponentNumber(row - 1, col) };
                  }
            } else if (isAtTheBorder(row, col)) {
                  if (row == 1) {
                        return new int[] { getComponentNumber(row, col + 1), getComponentNumber(row, col - 1),
                                    getComponentNumber(row + 1, col) };
                  } else if (row == squareLength) {
                        return new int[] { getComponentNumber(row, col + 1), getComponentNumber(row, col - 1),
                                    getComponentNumber(row - 1, col) };
                  } else if (col == 1) {
                        return new int[] { getComponentNumber(row - 1, col), getComponentNumber(row + 1, col),
                                    getComponentNumber(row, col + 1) };
                  } else {
                        assert col == squareLength;
                        return new int[] { getComponentNumber(row - 1, col), getComponentNumber(row + 1, col),
                                    getComponentNumber(row, col - 1) };
                  }
            } else {
                  return new int[] { getComponentNumber(row - 1, col), getComponentNumber(row + 1, col),
                              getComponentNumber(row, col - 1), getComponentNumber(row, col + 1) };
            }

      }

      private boolean isAtDiagonalEnd(int row, int col) {
            return (row == 1 && col == 1) || (row == squareLength && col == squareLength)
                        || (row == 1 && col == squareLength) || (row == squareLength && col == 1);
      }

      private boolean isAtTheBorder(int row, int col) {
            return (row == 1 || row == squareLength) || (col == 1 || col == squareLength);
      }

      public static void main(String[] args) {
            Percolation p = new Percolation(3);

            System.out.println("Percolates ? " + p.isFull(3, 3));
   }

}