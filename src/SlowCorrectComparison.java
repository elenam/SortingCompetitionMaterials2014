import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;



public class SlowCorrectComparison {

	public static void main(String[] args) {
		// reading from a file 
		// write a comparator, call Arrays.sort
		// output into a file
		
		if (args.length < 2) {
			System.out.println("Please run with two command line arguments: input and output file names");
			System.exit(0);
		}
		
		String inputFileName = args[0];
		String outFileName = args[1];
		
		String [] toSort = readInData(inputFileName);
	}

	private static String[] readInData(String inputFileName) {
		ArrayList<String> input = new ArrayList<String>();
		Scanner in;
		try {
			in = new Scanner(new File(inputFileName));
			while (in.hasNext()) {
				input.add(in.next());
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(input);

		return input.toArray(new String[0]); // convert to array of strings
	}

}
