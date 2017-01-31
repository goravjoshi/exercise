
public class PercolationStats {
	
	private int[] trialsResult;

	public PercolationStats(int n, int trials) {
		// perform trials independent experiments on an n-by-n grid
		
		trialsResult = new int[trials];
		for (int i = 0; i < trialsResult.length; i++) {
			Percolation p = new Percolation(n);
			
		}
		

	}

	public double mean() { // sample mean of percolation threshold
		return 0.0;
	}

	public double stddev() { // sample standard deviation of percolation
								// threshold
		return 0.0;
	}

	public double confidenceLo() { // low endpoint of 95% confidence interval
		return 0.0;
	}

	public double confidenceHi() { // high endpoint of 95% confidence interval
		return 0.0;
	}

	public static void main(String[] args) { // test client (described below)

	}
}
