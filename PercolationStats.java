import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
public class PercolationStats {
    private int size;
    private int total;
    private int runCount;
    private double[] results;
// perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
         if (n <= 0 || trials <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
         size = n;
         int i = trials;
         total = 0;
         runCount = 0;
         results = new double [trials];
         while (i > 0) {
             i--;
            int c = monteCarloSimulation(n);
            results[i] = (double) c/n/n;
            total += c;
            runCount++;
        }

    }
// sample mean of percolation threshold
    public double mean() {
        return (double) total/runCount/size/size;

    }
// sample standard deviation of percolation threshold
    public double stddev() {
        if (1 == runCount) {
            return Double.NaN;
        }
        return StdStats.stddev(results);
    }  
// low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - ((1.96 * stddev()) / Math.sqrt(runCount));

    }  
// high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + ((1.96 * stddev()) / Math.sqrt(runCount));

    }
// test client (described below)
    public static void main(String[] args)
    {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(n, t);

        // Print out the results
        double m = ps.mean();
        double s = ps.stddev();
        double low = (m - (1.96*s)/Math.sqrt(t));
        double high = (m + (1.96*s)/Math.sqrt(t));

        StdOut.println("mean                    = "+ m);
        StdOut.println("stddev                  = "+ s);
        StdOut.println("95% confidence interval = "+ low +", "+ high);
    }
     // run the Monte Carlo simulation on an N-by-N grid
    private int monteCarloSimulation(int N) {
        int c = 0;
        Percolation p = new Percolation(N);
        while (!p.percolates()) {
            int i = 1+StdRandom.uniform(N);
            int j = 1+StdRandom.uniform(N);
            if (!p.isOpen(i, j)) {
                c++;
                p.open(i, j);
            }
        }
        return c;
    }
}