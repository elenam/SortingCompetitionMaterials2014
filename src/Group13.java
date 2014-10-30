import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;
import java.util.*;

//Group# 13 Michael Schuweiler && Dillon Stenberg

public class Group13 {

	private static String[] arr2;
	private static String[] arr3;
	private static int number;

	public static void main(String[] args) {

		String[] input = new String[0];
		String inputFileName = args[0];
		String outFileName = args[1];
		int N = Integer.parseInt(args[2]);
		input = readInData(inputFileName);

		long startTime = System.currentTimeMillis();

		for(int i = 0; i < N; i++) {
			sort(input);
		}

		writeOutResult(input, outFileName);
		long endTime = System.currentTimeMillis() - startTime;
		System.out.println(endTime);
	}

	//WHERE the MAGIC happens
	public static void sort(String[] values) {
		arr2 = values;
		number = values.length;
		arr3 = new String[number];

		mergeSortAlphabetically(0, number - 1);
		mergeSortByOnes(0, number - 1);
		mergeSortByLength(0, number - 1);
	}

	public static void mergeSortAlphabetically(int left, int right) {
		if(left < right) {
			int middle = left + (right-left)/2;
			mergeSortAlphabetically(left, middle);
			mergeSortAlphabetically(middle + 1, right);
			mergeAlphabetically(left, middle, right);
		}
	}

	//Calls the mergeSorts to sort by number of ones in the string
	public static void mergeSortByOnes(int left, int right) {
		if(left < right) {
			int middle = left + (right-left)/2;
			mergeSortByOnes(left, middle);
			mergeSortByOnes(middle + 1, right);
			mergeByOnes(left, middle, right);
		}
	}

	//Calls the mergeSorts to sort by length
	public static void mergeSortByLength(int left, int right) {
		if(left < right) {
			int middle = left + (right-left)/2;
			mergeSortByLength(left, middle);
			mergeSortByLength(middle + 1, right);
			mergeByLength(left, middle, right);
		}
	}

	//MergeSort implementation; sort by number of ones in the string
	public static void mergeByOnes(int left, int middle, int right) {
		for(int i = left; i <= right; i++) {
			arr3[i] = arr2[i];
		}

		int i = left;
		int j = middle + 1;
		int k = left;

		while(i <= middle && j <= right) {
			if(sum1(arr3[i]) > sum1(arr3[j])) {
				arr2[k] = arr3[i];
				i++;
			} else {
				arr2[k] = arr3[j];
				j++;
			}
			k++;
		} 

		while(i <= middle) {
			arr2[k] = arr3[i];
			k++;
			i++;
		}
	}

	//MergeSort implementation; Sorts by items starting with 0 && 1
	public static void mergeAlphabetically(int left, int middle, int right) {
		for(int i = left; i <= right; i++) {
			arr3[i] = arr2[i];
		}

		int i = left;
		int j = middle + 1;
		int k = left;

		while(i <= middle && j <= right) {
			if(arr3[i].compareTo(arr3[j]) <= 0) {
				arr2[k] = arr3[i];
				i++;
			} else {
				arr2[k] = arr3[j];
				j++;
			}
			k++;
		} 

		while(i <= middle) {
			arr2[k] = arr3[i];
			k++;
			i++;
		}	
	}

	//Mergesort implementation; sorting by lengths
	public static void mergeByLength(int left, int middle, int right) {
		for(int i = left; i <= right; i++) {
			arr3[i] = arr2[i];
		}

		int i = left;
		int j = middle + 1;
		int k = left;

		while(i <= middle && j <= right) {
			if(arr3[i].length() < arr3[j].length()) {
				arr2[k] = arr3[i];
				i++;
			} else {
				arr2[k] = arr3[j];
				j++;
			}
			k++;
		} 

		while(i <= middle) {
			arr2[k] = arr3[i];
			k++;
			i++;
		}
	}

	//This turns string into charArray and counts number of 1's
	public static int sum1(String str){

		int count = 0;

		for(int i = 0; i < str.length(); i++) {
			count += str.charAt(i) - '0';
		}
		return count;
	}

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
}
