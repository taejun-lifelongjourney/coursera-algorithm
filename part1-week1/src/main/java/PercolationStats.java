import java.util.Random;

public class PercolationStats {

	private double mean = 0;
	private double stddev = 0;
	private double confidenceLow = 1;
	private double confidenceHigh = 0;

	// perform T independent computational experiments on an N-by-N grid
	public PercolationStats(int n, int t){

		if(n <= 0 || t <= 0)
			throw new IllegalArgumentException("");

		double siteOpenCount = 0;
		double siteOpenRate[] = new double[t];
		double siteOpenRateSum = 0;
		Random random = new Random();
		for(int i=0;i<t;i++){
			Percolation percolation =new Percolation(n);
			siteOpenCount=0;
			do{
				int r  = random.nextInt(n)+1;
				int c  = random.nextInt(n)+1;

				if(!percolation.isOpen(r,c)){
					siteOpenCount++;
					percolation.open(r,c);
				}
 			}while(!percolation.percolates());

			siteOpenRate[i]=siteOpenCount/(n*n);
			siteOpenRateSum+=siteOpenRate[i];
		}
		//get sample mean
		mean = siteOpenRateSum / t;

		//get standard deviation
		double deviationSquareSum=0.0;
		for(int i=0;i<t;i++)
			deviationSquareSum+=Math.pow(siteOpenRate[i]- mean,2);
		stddev = Math.sqrt(deviationSquareSum / (t - 1));

		confidenceLow = mean - 1.96*stddev/Math.sqrt(t);
		confidenceHigh = mean + 1.96*stddev/Math.sqrt(t);
	}

	// sample mean of percolation threshold
	public double mean(){
		return mean;
	}

	// sample standard deviation of percolation threshold
	public double stddev(){
		return stddev;

	}

	// returns lower bound of the 95% confidence interval
	public double confidenceLo(){
		return confidenceLow;
	}

	// returns upper bound of the 95% confidence interval
	public double confidenceHi(){
		return confidenceHigh;
	}

	private String getFormattedResult(){
		StringBuilder builder = new StringBuilder();
		builder.append("mean\t=").append(mean).append("\n")
				.append("stddev\t=").append(stddev).append("\n")
				.append("95% confidence interval = ").append(confidenceLow).append(",").append(confidenceHigh);
		return builder.toString();
	}

	public static void main(String[] args){

		// test client, described below
		if(args.length<2)
			throw new IllegalArgumentException("Two number arguments must be given.");
		int n = Integer.parseInt(args[0]);
		int t = Integer.parseInt(args[1]);

		if(n<=0)
			throw new IllegalArgumentException("The first argument must be a number greater than zero.");
		if(t<=0)
			throw new IllegalArgumentException("The second argument must be a number greater than zero.");

		PercolationStats ps = new PercolationStats(n, t);
		System.out.println(ps.getFormattedResult());
	}
}
