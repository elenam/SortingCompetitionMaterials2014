import java.io.FileNotFoundException;
import java.io.PrintWriter;


public class DataGenerator {
	/**
	 * Competition data is generated as follows: 
	 * Data consists of strings of 0s and 1s, where
	 * - the length of the string is determined by multiplying two variables 
	 *   generated using Poisson distribution: 
	 *   http://en.wikipedia.org/wiki/Poisson_distribution#Generating_Poisson-distributed_random_variables
	 * - in each string each character is 0 or 1 with equal probability.
	 * @param args:
	 *  arg[0] is the file name for the generated data 
	 * 	arg[1] is the number of elements to sort
	 *  arg[2] is the parameter for the Poisson distribution
	 *  Both arguments are optional. If there is only one argument, it is interpreted as the file name;
	 *  if there are only two, the second one is interpreted as the number of elements. 
	 *  If some arguments are not provided, defaults are used. If no file name is provided or if the
	 *  file name is given as "nofile", the output goes to standard output.
	 *  
	 *  Author: elenam 
	 */
	
	public static void main(String[] args) {
		double defaultLambda = 3.5;
		int defaultN = 50000;
		double lambda = defaultLambda;
		int n = defaultN;	
		String filename = "nofile";
		
		if (args.length >= 1) {
			filename = args[0];
		}
		
		if (args.length >= 2) {
			n = Integer.parseInt(args[1]);
		}
		
		if (args.length >= 3){
			lambda = Double.parseDouble(args[2]);
		}
				
		System.out.println("file = " + filename + " n = " + n + " lambda = " + lambda);

		// the output goes to the standard output (console)
		if (filename.equals("nofile")) {
			generateAndWriteOutputStandardOut(n, lambda);
		} else { //output goes to a file 
			try {
				generateAndWriteOutput(new PrintWriter(filename), n, lambda);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}			
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
	
	private static void generateAndWriteOutputStandardOut(int n, double lambda) {
		System.out.println(lambda);
		for (int i = 0; i < n; ++i) {
			System.out.println(generateString(lambda));
		}
	}
	
	private static void generateAndWriteOutput(PrintWriter out, int n,
			double lambda) {
		out.println(lambda);
		for (int i = 0; i < n; ++i) {
			out.println(generateString(lambda));
		}
		out.close();
	}

}
