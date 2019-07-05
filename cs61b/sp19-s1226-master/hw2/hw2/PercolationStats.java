package hw2;
import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    private double[] data;
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        data =  new double[T];
        for (int i = 0; i < T; i++) {
            Percolation percolation = pf.make(N);
            while (!percolation.percolates()) {
                int row = StdRandom.uniform(0, N);
                int col = StdRandom.uniform(0, N);
                percolation.open(row, col);
            }
            data[i] = (double) percolation.numberOfOpenSites() / (N * N);
        }
    }
    public double mean() {
        return StdStats.mean(data);
    }
    public double stddev() {
        return StdStats.stddev(data);
    }
    public double confidenceLow() {
        return mean() - (1.96 * stddev()) / (Math.sqrt(data.length));
    }
    public double confidenceHigh() {
        return mean() + (1.96 * stddev()) / (Math.sqrt(data.length));
    }
}
