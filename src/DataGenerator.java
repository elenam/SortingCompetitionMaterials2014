
public class DataGenerator {
	/**
	 * Competition data is generated as follows: 
	 * Data consists of strings of 0s and 1s, where
	 * - the length of the string is determined by Poisson distribution: 
	 *   http://en.wikipedia.org/wiki/Poisson_distribution#Generating_Poisson-distributed_random_variables
	 * - in each string each character is 0 or 1 with equal probability.
	 * @param args
	 */
	
	// to do: maybe change lambda to be a command arg, if supplied, and 
	// some default value otherwise
	// also need to specify the number of elements, perhaps as a command 
	// arg as well
	public static void main(String[] args) {
		double lambda = 4.0;
		int length = generatePoisson(lambda);
		System.out.println("length = " + length);
		System.out.println(generateCharacter());
		
		System.out.println(generateString(lambda));
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
