import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

/**
 * The class implements inefficient, but correct, sorting
 * according to the comparison defined in the comparator.
 * 
 * @author elenam
 *
 */

public class SlowCorrectSorting {

	public static void main(String[] args) {		
		if (args.length < 2) {
			System.out.println("Please run with two command line arguments: input and output file names");
			System.exit(0);
		}
		
		String inputFileName = args[0];
		String outFileName = args[1];
		
		String [] toSort = readInData(inputFileName);
		
		sort(toSort);
		
		//debugging print:
		for (String str: toSort) {
			System.out.println(str);
		}
		
		writeOutResult(toSort,outFileName);
	}
	

	private static String[] readInData(String inputFileName) {
		ArrayList<String> input = new ArrayList<String>();
		Scanner in;
		try {
			in = new Scanner(new File(inputFileName));
			// the first item in the file is lambda
			double lambda = in.nextDouble();
			System.out.println(lambda);
			while (in.hasNext()) {
				input.add(in.next());
			}
			in.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		//System.out.println(input);

		return input.toArray(new String[0]); // convert to array of strings
	}
	
	private static void sort(String [] toSort) {
		Arrays.sort(toSort, new StringComparator());
	}
	
	private static void writeOutResult(String [] sorted, String outputFilename) {
		try {
			PrintWriter out = new PrintWriter(outputFilename);
			for (String str: sorted) {
				out.println(str);
			}
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * The comparator provides a comparison method for strings
	 * The strings will be sorted by the following:
	 * the sum of 1s in the string (in increasing order),
	 * within each sum,  by length (in increasing order),
	 * within each group as determined above, alphabetically.
	 * @author elenam
	 *
	 */
	// the inner class has to be static because it is used in a static method 
	private static class StringComparator implements Comparator<String> {

		@Override
		public int compare(String str1, String str2) {
			int sum1 = sumOnes(str1);
			int sum2 = sumOnes(str2);
			if (sum1 != sum2) {
				return (sum1 - sum2);
			}
			// only get here if the sums are equal
			if (str1.length() != str2.length()) {
				return str1.length() - str2.length();
			}
			// only get here if the sum and the length are equal
			return str1.compareTo(str2);
		}
		
		private int sumOnes(String str) {
			int count = 0;
			for (int i = 0; i < str.length(); ++i) {
				count += str.charAt(i) - '0';
			}
			return count;
		}
		
	}

}
