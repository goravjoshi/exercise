import static java.lang.String.format;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final int squareLength;
    private final WeightedQuickUnionUF quickUnionFind;

    public Percolation(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Please provide a valid number " + n);
        }

        this.squareLength = n;
        int noOfComponents = n * n + 2;
        this.quickUnionFind = new WeightedQuickUnionUF(noOfComponents);

        connectPseudoTopAndBottomSites(quickUnionFind, noOfComponents);
        // create n-by-n grid, with all sites blocked
    }

    private void connectPseudoTopAndBottomSites(WeightedQuickUnionUF quickUnionFind, int size) {
        for (int i = 1; i <= squareLength; i++) {
            quickUnionFind.union(0, i);
            quickUnionFind.union(size - 1, (squareLength * (squareLength - 1) + i));
        }
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        checkBoundries(row, col);
        int[] neighborComponentNumber = getNeighborComponentNumber(row, col);
        int componentNumber = getComponentNumber(row, col);
        for (int i = 0; i < neighborComponentNumber.length; i++) {
            quickUnionFind.union(componentNumber, neighborComponentNumber[i]);
        }
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        checkBoundries(row, col);
        int[] neighborComponentNumber = getNeighborComponentNumber(row, col);
        int componentNumber = getComponentNumber(row, col);
        for (int i = 0; i < neighborComponentNumber.length; i++) {
            boolean connected = quickUnionFind.connected(componentNumber, neighborComponentNumber[i]);
            if (!connected) {
                return true;
            }
        }
        return false;
    }

    public boolean isFull(int row, int col) {
        return !isOpen(row, col);
    }

    public int numberOfOpenSites() {
        int num = quickUnionFind.count() - 2;
        return num > 0 ? num : 0;
    }

    public boolean percolates() {
        return quickUnionFind.connected(0, squareLength * squareLength + 1);
    }

    private void checkBoundries(int row, int col) {
        if (!((row > 0 && row <= squareLength) || (col > 0 || col <= squareLength))) {
            throw new IndexOutOfBoundsException(format("Invalid row/columns [%s,%s]", row, col));
        }
    }

    private int getComponentNumber(int row, int col) {
        int startIndex = row == 1 ? 0 : row - 1;
        return (startIndex * squareLength) + col;
    }

    private int[] getNeighborComponentNumber(int row, int col) {
        if (isAtTheBorder(row, col) && isAtDiagonalEnd(row, col)) {
            if (row == 1 && col == squareLength) {
                return new int[] { getComponentNumber(row, col - 1),
                        getComponentNumber(row + 1, col) };
            } else if (row == 1 && col == 1) {
                return new int[] { getComponentNumber(row, col + 1),
                        getComponentNumber(row + 1, col) };
            } else if (row == squareLength && col == 1) {
                return new int[] { getComponentNumber(row - 1, col),
                        getComponentNumber(row, col + 1) };
            } else {
                assert row == squareLength && col == squareLength;
                return new int[] { getComponentNumber(row, col - 1),
                        getComponentNumber(row - 1, col) };
            }
        } else if (isAtTheBorder(row, col)) {
            if (row == 1) {
                return new int[] { getComponentNumber(row, col + 1),
                        getComponentNumber(row, col - 1),
                        getComponentNumber(row + 1, col)
                };
            } else if (row == squareLength) {
                return new int[] { getComponentNumber(row, col + 1),
                        getComponentNumber(row, col - 1),
                        getComponentNumber(row - 1, col)
                };
            } else if (col == 1) {
                return new int[] { getComponentNumber(row - 1, col),
                        getComponentNumber(row + 1, col),
                        getComponentNumber(row, col + 1)
                };
            } else {
                assert col == squareLength;
                return new int[] { getComponentNumber(row - 1, col),
                        getComponentNumber(row + 1, col),
                        getComponentNumber(row, col - 1)
                };
            }
        } else {
            return new int[] { getComponentNumber(row - 1, col),
                    getComponentNumber(row + 1, col),
                    getComponentNumber(row, col - 1),
                    getComponentNumber(row, col + 1)
            };
        }

    }

    private boolean isAtDiagonalEnd(int row, int col) {
        return (row == 1 && col == 1) || (row == squareLength && col == squareLength) || (row == 1 && col == squareLength)
                || (row == squareLength && col == 1);
    }

    private boolean isAtTheBorder(int row, int col) {
        return (row == 1 || row == squareLength) || (col == 1 || col == squareLength);
    }

    public static void main(String[] args) {
        // test client (optional)
    }


}