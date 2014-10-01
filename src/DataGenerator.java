
public class DataGenerator {
	/**
	 * Competition data is generated as follows: 
	 * Data consists of strings of 0s and 1s, where
	 * - the length of the string is determined by Poisson distribution: 
	 *   http://en.wikipedia.org/wiki/Poisson_distribution#Generating_Poisson-distributed_random_variables
	 * 
	 * @param args
	 */
	public static void main(String[] args) {		
		int length = generateLength();
		System.out.println("length = " + length);
	}
	
	private static int generateLength() {
		double lambda = 30.0;
		double L = Math.exp(-lambda);
		int k = 1;
		double p = 1;
		
		do {
			double u = Math.random();
			p = p * u;
			k = k + 1;			
		} while (p > L);
		
		return k;
	}
	
	

}
