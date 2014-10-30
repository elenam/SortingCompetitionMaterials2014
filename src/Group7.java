import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;

//Brian Mitchell && Kristin Rachor

public class Group7 {
	public static Random rand = new Random();
	private static double lambda;

	public static void main(String[] args) throws InterruptedException {	
		if (args.length < 3) {
			System.out.println("Please run with three command line arguments: input and output file names and number of times to sort");
			System.exit(0);
		}

		//Pre-processing
		long timeBefore;
		long timeAfter;
		String inputFileName = args[0];
		String outFileName = args[1];
		int sortNum = Integer.parseInt(args[2]);
		String [] toSort = readInData(inputFileName);
		String [] output = new String[toSort.length];

		Thread.sleep(100);

		//Sorting
		if (lambda <= 1.4) {
			timeBefore = System.currentTimeMillis();
			for (int i = 0; i < sortNum; i++) {
				if (i == sortNum - 1) {
					output = toSort.clone();
					sortqs3(output);
				} else {
					String [] sortMe = toSort.clone();
					sortqs3(sortMe);
				}
			}
			timeAfter = System.currentTimeMillis();
		} else if(lambda < 1.75) {
			timeBefore = System.currentTimeMillis();
			for (int i = 0; i < sortNum; i++) {
				if (i == sortNum - 1) {
					output = toSort.clone();
					Arrays.sort(output, new StringComparator());
				} else {
					String [] sortMe = toSort.clone();
					Arrays.sort(sortMe, new StringComparator());
				}
			}
			timeAfter = System.currentTimeMillis();
		} else {
			timeBefore = System.currentTimeMillis();
			for (int i = 0; i < sortNum; i++) {
				if (i == sortNum - 1) {
					output = toSort.clone();
					sort(output);
				} else {
					String [] sortMe = toSort.clone();
					sort(sortMe);
				}
			}
			timeAfter = System.currentTimeMillis();
		}

		//debugging print:
		//		for (String str: toSort) {
		//			System.out.println(str);
		//		}
		writeOutResult(output,outFileName);

		//Converts to seconds if over 1000 milliseconds
		long total = timeAfter - timeBefore;
		System.out.println(total);
	}

	private static String[] readInData(String inputFileName) {
		ArrayList<String> input = new ArrayList<String>();
		Scanner in;
		try {
			in = new Scanner(new File(inputFileName));
			// the first item in the file is lambda
			lambda = in.nextDouble();
			while (in.hasNext()) {
				input.add(in.next());
			}
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
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

	//This is mainly the books implementation of QuickSort using Elena's compare methods from her StringComparator and a random pivot
	//Forked from Brian & Tom's quicksort lab

	public static void sort(String[] arr) {
		runQuickSort(arr, 0, arr.length - 1) ;
	}

	//recursive quicksorting method
	private static void runQuickSort(String[] arr, int leftIndex, int rightIndex) {
		if (leftIndex < rightIndex) {
			int middleIndex = partition(arr, leftIndex, rightIndex);
			runQuickSort(arr, leftIndex, middleIndex-1);
			runQuickSort(arr, middleIndex + 1, rightIndex);
		}
	}

	private static int partition(String[] arr, int leftIndex, int rightIndex) {
		//random part of random quicksort
		swap(arr, rand.nextInt(rightIndex - leftIndex + 1) + leftIndex, rightIndex);
		//we store the pivot at the end of the array
		String rightIndexValue = arr[rightIndex];
		int i = leftIndex - 1;
		for (int j = leftIndex; j < rightIndex; j++) {
			if (compare(arr[j], rightIndexValue) <= 0) {
				i++;
				swap(arr, i, j);
			}
		}
		swap(arr, i + 1, rightIndex);
		return i + 1;
	}

	//swap two strings
	private static void swap(String[] arr, int left, int right) {
		String temp = arr[right];
		arr[right] = arr[left];
		arr[left] = temp;
	}

	//This 3-way quicksort was adapted from "arunma"s, which can be found: https://github.com/arunma/DataStructuresAlgorithms/blob/master/src/basics/sorting/quick/QuickSort3Way.java
	private static void sortqs3 (String[] input){
		//input=shuffle(input);
		sortqs3 (input, 0, input.length-1);
	}

	private static void sortqs3(String[] input, int lowIndex, int highIndex) {
		if (highIndex<=lowIndex) return;

		int lt=lowIndex;
		int gt=highIndex;
		int i=lowIndex+1;

		int pivotIndex=lowIndex;
		String pivotValue=input[pivotIndex];

		while (i<=gt){
			if (less(input[i],pivotValue)){
				exchange(input, i++, lt++);
			}
			else if (less (pivotValue, input[i])){
				exchange(input, i, gt--);
			}
			else{
				i++;
			}
		}

		sortqs3 (input, lowIndex, lt-1);
		sortqs3 (input, gt+1, highIndex);		
	}

	private static boolean less(String a, String b) {
		return compare(a, b) < 0;
	}

	private static void exchange(String array[], int from, int to) {
		String temp = array[from];
		array[from] = array[to];
		array[to] = temp;
	}

	//Elena's String compare methods
	public static int compare(String str1, String str2) {
		if (str1.length() != str2.length()) {
			return str1.length() - str2.length();
		}
		// only get here if the lengths are equal
		int sum1 = sumOnes(str1);
		int sum2 = sumOnes(str2);
		if (sum1 != sum2) {
			return (sum1 - sum2);
		}
		// only get here if the sum and the length are equal
		return str1.compareTo(str2);
	}
	private static int sumOnes(String str) {
		int count = 0;
		for (int i = 0; i < str.length(); ++i) {
			count += str.charAt(i) - '0';
		}
		return count;
	}
	// the inner class has to be static because it is used in a static method 
	private static class StringComparator implements Comparator<String> {

		public int compare(String str1, String str2) {
			if (str1.length() != str2.length()) {
				return str1.length() - str2.length();
			}

			// only get here if the lengths are equal
			int sum1 = sumOnes(str1);
			int sum2 = sumOnes(str2);
			if (sum1 != sum2) {
				return (sum1 - sum2);
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