
public class DataGenerator {
	/**
	 * Competition data is generated as follows: 
	 * Data consists of strings of 0s and 1s, where
	 * - the length of the string is determined by multiplying two variables 
	 *   generated using Poisson distribution: 
	 *   http://en.wikipedia.org/wiki/Poisson_distribution#Generating_Poisson-distributed_random_variables
	 * - in each string each character is 0 or 1 with equal probability.
	 * @param args:
	 * 	arg[0] is the number of elements to sort
	 *  arg[1] is the parameter for the Poisson distribution
	 *  Both arguments are optional. If there is only one argument, it is interpreted the number of elements. 
	 *  If one or both arguments are not provided, defaults are used. 
	 *  
	 *  The program outputs the generated strings to standard output.
	 *  
	 *  Author: elenam 
	 */
	
	public static void main(String[] args) {
		double defaultLambda = 4.0;
		int defaultN = 50000;
		double lambda = defaultLambda;
		int n = defaultN;		
		
		if (args.length >= 1) {
			n = Integer.parseInt(args[0]);
		}
		
		if (args.length >= 2){
			lambda = Double.parseDouble(args[1]);
		}
				
		System.out.println("n = " + n + " lambda = " + lambda);

		for (int i = 0; i < n; ++i) {
			System.out.println(generateString(lambda));
		}
			
	}
	
	private static int generatePoisson(double lambda) {
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
	
	private static char generateCharacter() {
		int i = (int) Math.round(Math.random());
		return (char) ('0' + i);
	}
	
	private static String generateString(double lambda) {
		int p1 = generatePoisson(lambda);
		int p2 = generatePoisson(lambda);
		int length = p1 * p2;
		StringBuffer str = new StringBuffer(length);
		for (int i = 0; i < length; ++i) {
			str.append(generateCharacter()); 
		}
		return str.toString();
	}

}
