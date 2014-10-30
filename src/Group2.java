import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;


//Authors: Anna-Maria Sen, Pazao Vue
public class Group2 {
	public static void main(String[] args) {
		if (args.length < 3) {
			System.out.println("Please run with three command line arguments: input and output file names and the number of loops");
			System.exit(0);
		}
		
		String inputFileName = args[0];
		String outFileName = args[1];
		int loopCount = Integer.parseInt(args[2]);
		String [] toSort = readInData(inputFileName);
		int max = toSort[0].length();
		int min = toSort[0].length();
		String[] array = null;
		
		//find largest length and sum
		for(int i = 1; i < toSort.length; i++) {
			if(toSort[i].length() < min) {
				min = toSort[i].length();
			} else if(toSort[i].length() > max) {
				max = toSort[i].length();
			}
		}
		
		long time = 0;
		long startTime = System.currentTimeMillis();
		
		for(int i = 0; i < loopCount; i++) {
			array = toSort.clone();
			sort(array, min, max);
		}
		time = System.currentTimeMillis() - startTime;
		try {
			PrintWriter out = new PrintWriter(outFileName);
			for (int i = 0; i < array.length; i++) {
				out.println(array[i]);
			}
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		System.out.println(time);
	}
	
	public static void sort(String[] array, int min, int max) {
		quicksort(array, 0, array.length - 1);
		countingSortSums(array, max);
		countingSortLength(array, min, max);
	}
	public static void quicksort(String[] array, int startIndex, int endIndex) {
		if (startIndex + 1 < endIndex) {
			if(array[startIndex].compareTo(array[endIndex]) > 0) {
				String temp = array[startIndex];
				array[startIndex] = array[endIndex];
				array[endIndex] = temp;
			}
			int pivot1 = startIndex;
			int pivot2 = endIndex;
			
			int lastSmall = startIndex;
			int lastMed = startIndex;
			int firstLarge = endIndex;
			
			while(lastMed + 1 < firstLarge) {
				if(array[lastMed + 1].compareTo(array[pivot1]) < 0) {
					String temp = array[lastMed + 1];
					array[lastMed + 1] = array[lastSmall + 1];
					array[lastSmall + 1] = temp;
					lastSmall++;
					lastMed++;
				} else if(array[lastMed + 1].compareTo(array[pivot2]) < 0) {
					lastMed++;
				} else {
					String temp = array[lastMed + 1];
					array[lastMed + 1] = array[firstLarge - 1];
					array[firstLarge - 1] = temp;
					firstLarge--;
				}
			}
			String temp = array[pivot1];
			array[pivot1] = array[lastSmall];
			array[lastSmall] = temp;
			
			temp = array[pivot2];
			array[pivot2] = array[firstLarge];
			array[firstLarge] = temp;
			
			quicksort(array, startIndex, lastSmall - 1);
			quicksort(array, lastSmall + 1, firstLarge - 1);
			quicksort(array, firstLarge + 1, endIndex);
		} else if(startIndex + 1 == endIndex) {
			if(array[startIndex].compareTo(array[endIndex]) > 0) {
				String temp = array[startIndex];
				array[startIndex] = array[endIndex];
				array[endIndex] = temp;
			}
		}
	}
	
	public static void countingSortSums(String[] array, int max) {
		String[] arrayCopy = array.clone();
		int[] totals = new int[max + 1];
		int[] count = new int[max + 1];
		
		for(int i = 0; i < array.length; i++) {
			totals[sumOnes(array[i])]++;
		}
		count[0] = totals[0] - 1;
		for(int i = 1; i < totals.length; i++) {
			count[i] = count[i - 1] + totals[i];
		}
		for(int i = arrayCopy.length - 1; i > 0; i--) {
			int sum = sumOnes(arrayCopy[i]);
			array[count[sum]] = arrayCopy[i];
			count[sum]--;
		}
	}
	
	public static void countingSortLength(String[] array,int min, int max) {
		String[] arrayCopy = array.clone();
		int[] totals = new int[max - min + 1];
		int[] count = new int[max - min + 1];
		
		
		for(int i = 0; i < array.length; i++) {
			totals[array[i].length() - min]++;
		}
		count[0] = totals[0] - 1;
		for(int i = 1; i < totals.length; i++) {
			count[i] = count[i - 1] + totals[i];
		}
		for(int i = arrayCopy.length - 1; i > 0; i--) {
			int index = arrayCopy[i].length() - min;
			array[count[index]] = arrayCopy[i];
			count[index]--;
		}
	}
	
	private static String[] readInData(String inputFileName) {
		ArrayList<String> input = new ArrayList<String>();
		Scanner in;
		try {
			in = new Scanner(new File(inputFileName));
			// the first item in the file is lambda
			double lambda = in.nextDouble();
			//System.out.println(lambda);
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
	
	private static int sumOnes(String str) {
		int count = 0;
		for (int i = 0; i < str.length(); ++i) {
			count += str.charAt(i) - '0';
		}
		return count;
	}
}
