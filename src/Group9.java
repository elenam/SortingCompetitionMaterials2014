import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

//Isaac Smolund and Henry Megarry
public class Group9 {

	public static void main(String[] args) throws IOException {
		//these are to take in the input file export file and the number of times to run the loop
		String input_file = args[0];
		String output_file = args[1];
		int number_of_loops = Integer.parseInt(args[2]);

		BufferedReader john = new BufferedReader(new FileReader(input_file));
		//original container
		ArrayList<String> strs = new ArrayList<String>();
		//variables
		int max_number_of_ones = 0;
		int max_length = 0;

		//adding the strings to the original container
		float lambda = Float.parseFloat(john.readLine());
		String line = john.readLine();

		while (line != null) {
			if (line.length() > max_length)
				max_length = line.length();
			if(countOnes(line) > max_number_of_ones)
				max_number_of_ones = countOnes(line);
			strs.add(line);
			line = john.readLine();
		}
		john.close();

		//start timer
		long startTime = System.currentTimeMillis();
		String[] sorted = new String[strs.size()];

		for (int i = 0; i < number_of_loops; i++) {

			//copy the original container into sorting container
			for(int m = 0; m < strs.size(); m++){
				sorted[m] = strs.get(m);
			}

			//sort alphabetically
			if (lambda < 3 )
				ourModifiedQuickSort(sorted, 0, sorted.length - 1);
			else 
				ourQuickSort(sorted, 0, sorted.length -1);

			//sort by number of ones
			ourOnesCountingSort(sorted, max_number_of_ones);

			//sort by length
			ourLengthCountingSort(sorted, max_length);



		}
		long endTime = System.currentTimeMillis();
		long time = endTime - startTime;
		//output
		System.out.println(time);
		writeOutResult(sorted, output_file);

	}

	/*
	 * 
	 * this quick sort checks to see if the values are equal, making it more efficient for smaller lambdas
	 * 
	 */
	private static void ourModifiedQuickSort(String[] sorted, int low, int high) {
		if(high <= low) return;
		int lt = low;
		int gt = high;
		String v = sorted[low];
		int i = low;
		while(i <= gt){
			int cmp = sorted[i].compareTo(v);
			if(cmp < 0) exch(sorted, lt++, i++);
			else if (cmp > 0) exch(sorted, i, gt--);
			else i++;
		}
		ourModifiedQuickSort(sorted, low, lt -1);
		ourModifiedQuickSort(sorted, gt+1, high);
	}



	/*
	 * plain old quick sort
	 */
	private static void ourQuickSort(String[] sorted, int low, int high) {
		if(low < high){
			int q = partition(sorted, low, high);
			ourQuickSort(sorted, low, q - 1);
			ourQuickSort(sorted, q + 1, high);
		}
	}



	private static int partition(String[] sorted, int low, int high){
		String x = sorted[high];
		int i = low -1;
		for(int j = low; j < high; j++){
			if(sorted[j].compareTo(x) <= 0){
				i = i + 1;
				String thing = sorted[i];
				sorted[i] = sorted[j];
				sorted[j] = thing;
			}	
		}
		String thing = sorted[i+1];
		sorted[i + 1] = sorted[high];
		sorted[high] = thing;
		return i + 1;
	}



	/*
	 * our implementation of counting sort for number of ones
	 */
	private static void ourOnesCountingSort(String[] sorted, int max) {
		int[] count = new int[max + 1];
		String[] newSorted = new String[sorted.length]; 
		for(int i = 0; i < sorted.length; i++){
			count[countOnes(sorted[i])] += 1;
		}
		for(int i = 1; i < count.length; i++){
			count[i] += count[i-1];
		}
		for(int i = sorted.length - 1; i > -1; i--){
			int ones = countOnes(sorted[i]);
			newSorted[--count[ones]] = sorted[i];
		}
		for(int i = 0;i < sorted.length; i++){
			sorted[i] = newSorted[i];
		}
	}

	/*
	 * our implementation of counting sort for length
	 */

	private static void ourLengthCountingSort(String[] sorted, int max) {
		int[] count = new int[max + 1];
		String[] newSorted = new String[sorted.length]; 
		for(int i = 0; i < sorted.length; i++){
			count[sorted[i].length()] += 1;
		}
		for(int i = 1; i < count.length; i++){
			count[i] += count[i-1];
		}
		for(int i = sorted.length -1; i > -1; i--){
			int ones = sorted[i].length();
			newSorted[--count[ones]] = sorted[i];
		}
		for(int i = 0;i < sorted.length; i++){
			sorted[i] = newSorted[i];
		}
	}


	/*
	 * HELPER METHODS	
	 */	


	/*
	 * this is a method to exchange the two values in an array 
	 */
	private static void exch(Object[] a, int i, int j) {
		Object swap = a[i];
		a[i] = a[j];
		a[j] = swap;
	}

	/*
	 * this method counts the number of ones in a string
	 */
	private static int countOnes(String string) {
		int ones = 0;
		for (int i = 0; i < string.length(); i++) {
			ones += string.charAt(i) - '0';
		}
		return ones;
	}


	/*
	 * writes to the output file:
	 * 
	 * */
	private static void writeOutResult(String[] sorted, String outputFilename) {
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

}
