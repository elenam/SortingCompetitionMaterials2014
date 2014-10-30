import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

//Molly Grove and Josh Sorenson

public class Group5 {
	public static int count = 0;
	public static int longest = 0;
	public static int[] frequencies = new int[longest+2];
	public static int shortest = 9999;
	
	public static void main(String[] args) {
		if (args.length < 2) {
			System.out
					.println("Please run with three command line arguments: input file name, output file name, and number of cycles");
			System.exit(0);
		}
		
		String inputFileName = args[0];
		String outFileName = args[1];
		int cycles  = Integer.parseInt(args[2]);
		
		String[] toSort = readInData(inputFileName); //Data to Sort
		
		long start = System.currentTimeMillis(); //StartClock
		
		String[] sorted = copySortLoop(toSort, cycles); //Start Sorting Algorithm
		
		long end = System.currentTimeMillis(); //EndTimer
		long time = end - start;
		
		writeOutResult(sorted, outFileName);
		System.out.println(time);
	}
	
	public static String[] copySortLoop(String[] input, int cycles){
		String[] data = new String[count];
		for (int i = 0; i < cycles; i++){
			System.arraycopy(input, 0, data, 0, count);
			lengthCountingSort(data,longest); //sorts in order of length
			sortInSections(data);
		}
		return data;
	}
	
	
	
	
	//*******************************************	
	
	//This is a variation of user arunma's implementation 3-way partitioning quicksort that was pushed to gitHub
	//on Jun 15, 2013.
	//URL: https://github.com/arunma/DataStructuresAlgorithms/blob/master/src/basics/sorting/quick/QuickSort3Way.java
	
	//More info about this modification to quickSort can be found here in this presentation
	//URL: http://www.cs.princeton.edu/courses/archive/fall12/cos226/lectures/23Quicksort-2x2.pdf
	public static void fooSorting(String[] input, int lowIndex, int highIndex, Comparator<String> c) {
		if (highIndex<=lowIndex) return;
		int lt= lowIndex;
		int gt= highIndex;
		int i= lowIndex+1;
		int pivotIndex= lowIndex;
		String pivotValue= input[pivotIndex];			
		while (i<=gt){			//Partition on elements left
			if (c.compare(input[i], pivotValue) < 0){
				swap(i++, lt++, input);
			} else if (c.compare(pivotValue, input[i]) < 0){
				swap(i, gt--, input);
			} else{
				i++;
			}		
		}
		//recursive calls on partitions less than pivot and greater than pivot
		fooSorting(input, lowIndex, lt-1, c);
		fooSorting(input, gt+1, highIndex, c);	
	}
	
	
	//*******************************************
	
	public static void swap(int pos1, int pos2, String[] ourArray){
        String temp = ourArray[pos1]; 
        String temp2 = ourArray[pos2];
        ourArray[pos1]=temp2; // Swap the two indices
        ourArray[pos2]=temp; //
	}
	
	public static int[] sumCountingSort(String[] inArray, int maxValue, int fromValue, int toValue){
		String[] outArray = new String[count+1];
		int[] storage = new int[maxValue+2];
		for (int i = 0; i < maxValue+2; i++){
			storage[i] = 0;
		}
		
		for (int j = fromValue; j < toValue; j++){
			storage[sumOfOnes(inArray[j])]++;
		}
		
		for (int i = 1; i < maxValue+2; i++){
			storage[i] = storage[i] + storage[i-1];
		}
		int[] output = Arrays.copyOf(storage, storage.length);
		for (int j = toValue-1; j >= fromValue; j--){
			outArray[storage[sumOfOnes(inArray[j])] + fromValue] = inArray[j];
			storage[sumOfOnes(inArray[j])]--;
		}
		for (int i = fromValue; i < toValue; i++){
			inArray[i] = outArray[i + 1];
		}
		return output;
	}
	
	public static void lengthCountingSort(String[] inArray, int longestValue){
		String[] outArray = new String[count+1];
		int[] storage = new int[longestValue+2];
		for (int i = 0; i < longestValue+2; i++){
			storage[i] = 0;
		}
		for (int j = 0; j < inArray.length; j++){
			storage[inArray[j].length()]++;
		}
		for (int i = 1; i < longestValue+2; i++){
			storage[i] = storage[i] + storage[i-1];
		}
		frequencies = Arrays.copyOf(storage, storage.length);
		for (int j = inArray.length-1; j >=0; j--){
			outArray[storage[inArray[j].length()]] = inArray[j];
			storage[inArray[j].length()]--;
		}
		for (int i = 0; i < inArray.length; i++){
			inArray[i] = outArray[i + 1];
		}
	}
	
	public static void sortInSections(String[] inArray){
		for (int i = 0; i < frequencies.length-1; i++){ // Use the auxiliary array, frequencies, from lengthCountingSort
			if (frequencies[i] != frequencies[i+1]){	// if there are elements of length i
				sortInSumSections(inArray, i);			// sort by sum of ones and alphabetically
			}
		}
	}

	public static void sortInSumSections(String[] inArray, int i){
		int[] sumFrequencies = sumCountingSort(inArray, inArray[frequencies[i]].length(), frequencies[i], frequencies[i+1]); // sort by sum of ones, store auxiliary array as sumFrequencies
		for (int j = 0; j < sumFrequencies.length-1; j++){	// for each section of equal length and sum of ones
			if (sumFrequencies[j] != sumFrequencies[j+1]){	// if there are elements of length i with sum of ones equal to j
				fooSorting(inArray, frequencies[i] + sumFrequencies[j], frequencies[i] + sumFrequencies[j+1]-1, new StringComparator()); // sort alphabetically using modified quicksort
			}
		}
	}
	
	private static String[] readInData(String inputFileName) {
		ArrayList<String> input = new ArrayList<String>();
		Scanner in;
		try {
			in = new Scanner(new File(inputFileName));
			// the first item in the file is lambda
			double lambda = in.nextDouble();
//			System.out.println(lambda);
			while (in.hasNext()) {
				String temp = in.next();
				input.add(temp);
				if (temp.length() > longest){
					longest = temp.length();
				}
				if (temp.length() < shortest){
					shortest = temp.length();
				}
				count++;
			}
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return input.toArray(new String[0]); // convert to array of strings
	}
	
	private static void writeOutResult(String[] sorted, String outputFilename) {
		try {
			PrintWriter out = new PrintWriter(outputFilename);
			for (String str : sorted) {
				out.println(str);
			}
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private static int sumOfOnes(String str) {
		int count = 0;
		for (int i = 0; i < str.length(); ++i) {
			count += str.charAt(i) - '0';
		}
		return count;
	}
	
	private static class StringComparator implements Comparator<String> {
		@Override
		public int compare(String str1, String str2) {
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