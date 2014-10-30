import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;


// Created By: Zachary Vink && Andrew Peterson
public class Group11 {

	// Stored during read in for counting sort by length. Constant memory in accordance to the rules.
	public static int smallest = 0;
	public static int largest = 0;
	public static double lambda = 0;

	public static void main(String[] args) {

		if (args.length < 3) {
			System.out.println("Please run with three command line arguments: input and output file names "
					+ "followed by the amount of times to run the sort");
			System.exit(0);
		}

		String inputFileName = args[0];
		String outFileName = args[1];
		int trials = Integer.parseInt(args[2]);
		
		
		String [] unsortedArray = readInData(inputFileName);
		String [] toSort = new String[unsortedArray.length];
		
		 //Set start time
		long startTime = System.currentTimeMillis();
		
		for(int i = 0; i < trials; i++) {
			
			//Get unsorted version of the elements (from unsortedArray) for sorting (into toSort)
			for(int j = 0; j < unsortedArray.length; j++) {
				toSort[j] = unsortedArray[j];
			}
			
			
			
			///////////// Runs in a radix hierarchy. From least important to most important feature. ////////////////
			if(lambda < 3.5) {
				sort(toSort);
			} else {
				alphabeticalSortHighLambda(toSort, 0, toSort.length - 1);
			}
			toSort = sumSort(toSort);
			toSort = lengthSort(toSort);
			/////////////////////////////////////////////////////////////////////////////////////////////////////////
			
			
			
		}
		
		// Set end time and print runtime
		long stopTime = System.currentTimeMillis(); 
		System.out.println(stopTime - startTime);
		
		//output the sorted elements into a file
		writeOutResult(toSort,outFileName);
	}
	
	private static void sort(String [] toSort) {
		Arrays.sort(toSort, new StringComparator());
	}

	private static String[] lengthSort(String[] toSort) {
		int[] count = new int[(largest + 1) - smallest];

		for(String current: toSort) {
			count[current.length() - smallest]++;
		}

		int total = -1;
		for(int i = 0; i < count.length; i++) {
			count[i] += total;
			total = count[i];
		}

		String[] toReturn = new String[toSort.length];
		for(int i = toSort.length - 1; i > -1; i--) {
			toReturn[count[toSort[i].length() - smallest]--] = toSort[i];
		}

		return toReturn;
	}

	private static String[] sumSort(String[] toSort) {
		int[] count = new int[largest + 1];

		for(String current: toSort) {
			count[sum(current)]++;
		}

		int total = -1;
		for(int i = 0; i < largest + 1; i++) {
			count[i] += total;
			total = count[i];
		}

		String[] toReturn = new String[toSort.length];
		for(int i = toSort.length - 1; i > -1; i--) {
			toReturn[count[sum(toSort[i])]--] = toSort[i];
		}

		return toReturn;
	}

	private static int sum(String current) {
		int sum = 0;
		char one = '1';
		for(int i = 0; i < current.length(); i++) {
			if(current.charAt(i) == one) {
				sum++;
			}
		}
		return sum;
	}

	private static void alphabeticalSortHighLambda(String[] toSort, int left, int right) {
		if (left < right) {
			int partitionIndex = partition(toSort, left, right);
			alphabeticalSortHighLambda(toSort, left, partitionIndex - 1);
			alphabeticalSortHighLambda(toSort, partitionIndex + 1, right);
		}

	}

	private static int partition(String[] toSort, int left, int right) {
		String pivotValue = toSort[left];
		swap(toSort, left, right);
		int toReturn = left;
		for(int i = left; i < right; i++) {
			if (toSort[i].compareTo(pivotValue) < 0 ) {
				swap(toSort, i, toReturn);
				toReturn++;
			}
		}
		swap(toSort, toReturn, right);
		return toReturn;
	}

	private static void swap(String[] toSort, int val1, int val2) {
		String holder = toSort[val1];
		toSort[val1] = toSort[val2];
		toSort[val2] = holder;
	}

	private static String[] readInData(String inputFileName) {
		ArrayList<String> input = new ArrayList<String>();
		Scanner in;
		try {
			in = new Scanner(new File(inputFileName));
			// the first item in the file is lambda
			lambda = in.nextDouble();
			while (in.hasNext()) {
				String current = in.next();
				if (current.length() < smallest) {
					smallest = current.length();
				}
				if (current.length() > largest) {
					largest = current.length();
				}
				input.add(current);
			}
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		//System.out.println(input);
		return input.toArray(new String[0]); // convert to array of strings
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
	
	private static class StringComparator implements Comparator<String> {

		@Override
		public int compare(String str1, String str2) {
			if (str1.length() != str2.length()) {
				return str1.length() - str2.length();
			}
			
			int sum1 = sumOnes(str1);
			int sum2 = sumOnes(str2);
			if (sum1 != sum2) {
				return (sum1 - sum2);
			}
			
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
