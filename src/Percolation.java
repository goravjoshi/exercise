import static java.lang.String.format;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

	enum SitePosition {
		TOP, MIDDLE, BOTTOM
	};

	private final int squareLength;
	private final WeightedQuickUnionUF quickUnionFind;
	private final Set<Integer> topOpenSites = new HashSet<>();
	private final Set<Integer> middleOpenSites = new HashSet<>();
	private final Set<Integer> bottomOpenSites = new HashSet<>();

	public Percolation(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("Please provide a valid number " + n);
		}

		this.squareLength = n;
		int noOfComponents = n * n;
		// create n-by-n grid, with all sites blocked
		this.quickUnionFind = new WeightedQuickUnionUF(noOfComponents);

	}

	// open site (row, col) if it is not open already
	public void open(int row, int col) {
		checkBoundries(row, col);
		int componentNumber = getComponentNumber(row, col);
		if (row == 1) {
			topOpenSites.add(componentNumber);
		} else if (row == squareLength) {
			bottomOpenSites.add(componentNumber);
		} else {
			middleOpenSites.add(componentNumber);
		}
	}

	// is site (row, col) open?
	public boolean isOpen(int row, int col) {
		checkBoundries(row, col);
		int componentNumber = getComponentNumber(row, col);
		return isOpen(componentNumber);
	}
	
	private boolean isOpen(int componentNumber) {
		return topOpenSites.contains(componentNumber) || middleOpenSites.contains(componentNumber)
				|| bottomOpenSites.contains(componentNumber);
	}

	public boolean isFull(int row, int col) {
		return !isOpen(row, col);
	}

	public int numberOfOpenSites() {
		return topOpenSites.size() + middleOpenSites.size()
				+ bottomOpenSites.size();
	}

	public boolean percolates() {
		connect(topOpenSites);
		connect(middleOpenSites);
		connect(bottomOpenSites);

		return false;
	}

	private void connect(Set<Integer> openSites) {
		for (Integer compNumber : openSites) {
			int[] neighborComponentNumber = getNeighborComponentNumber(compNumber);
			for (int i : neighborComponentNumber) {
				if(isOpen(i)) {
					quickUnionFind.union(compNumber, i);
				}
			}
		}
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

	private int[] getNeighborComponentNumber(int componentNumber) {
		int row, col;
		if(componentNumber % squareLength == 0) {
			row = componentNumber / squareLength;						
		} else {
			row = componentNumber/squareLength + 1;			
		}
		col = componentNumber - (row - 1)*squareLength;
		
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
		// test client (optional)
	}

}