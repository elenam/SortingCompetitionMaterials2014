// Snuffy Linder, Josh Chapman 

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.cert.CollectionCertStoreParameters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Group6 {
	public static void main(String[] args) throws IOException {
		//////////Pre-Processing/////////////////////
		if (args.length < 2) {
			System.out.println("Please run with two command line arguments: input and output file names");
			System.exit(0);
		}
		String inputFileName = args[0];
		String outputFileName = args[1];
		String stringOfPasses = args[2];
		int numberOfPasses = Integer.parseInt(stringOfPasses);
		Path inputPath = Paths.get(inputFileName);
		Path outputPath = Paths.get(outputFileName);


		List<String> allLines = Files.readAllLines(inputPath, Charset.forName("US-ASCII"));
		allLines.remove(0);
		int totalLines = allLines.size();
		int maxOnes = 0;
		int minLength = allLines.get(0).length();
		int maxLength = minLength;
		for (int i = 0; i < totalLines; i++) {
			if (allLines.get(i).length() < minLength) {
				minLength = allLines.get(i).length();
			}
		}
		for (int i = 0; i < totalLines; i++) {
			if (allLines.get(i).length() > maxLength) {
				maxLength = allLines.get(i).length();
			}
		}
		for (int i = 0; i < totalLines; i++) {
			if (sumOnes(allLines.get(i)) > maxOnes) {
				maxOnes = sumOnes(allLines.get(i));
			}
		}
		
		
		ArrayList<ArrayList<String>> ArrayOfBuckets = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> ArrayOfBucketsBySum = new ArrayList<ArrayList<String>>();

		for (int i = minLength; i <= maxLength; i++){
			ArrayOfBuckets.add(new ArrayList<String>());
		}
		for (int i = 0; i <= maxOnes; i++) {
			ArrayOfBucketsBySum.add(new ArrayList<String>());
		}
		ArrayList<String> finalOutputLines = new ArrayList<String>();
		ArrayList<String> outputLines = new ArrayList<String>();


		//System.out.println(numberOfPasses);
		int count = 0;
		//////////////////////////////////
		///////////////////////////////////////////////////////////////////////////////////////////////
		long startTime = System.currentTimeMillis();
		

		while (count < numberOfPasses) {
			ArrayOfBuckets = new ArrayList<ArrayList<String>>();
			ArrayOfBucketsBySum = new ArrayList<ArrayList<String>>();
			outputLines = new ArrayList<String>();
			finalOutputLines = new ArrayList<String>();
			
			for (int i = minLength; i <= maxLength; i++){
				ArrayOfBuckets.add(new ArrayList<String>());
			}
			for (int i = 0; i <= maxOnes; i++) {
				ArrayOfBucketsBySum.add(new ArrayList<String>());
			}
			



			///////////Throw things into buckets by sum/////////
			for (int i = 0; i < totalLines; i++){
				String theCurrentLine = allLines.get(i);
				int currentLineSum = sumOnes(theCurrentLine);
				ArrayOfBucketsBySum.get(currentLineSum).add(theCurrentLine);
			}
			///////////Combines Buckets   sorting alphabetically w/ sort//////////
			for (int i = 0; i <= maxOnes; i++) {
				Collections.sort(ArrayOfBucketsBySum.get(i));
				outputLines.addAll(ArrayOfBucketsBySum.get(i));
			}
			////////////////////Throws into buckets by length//////////////////
			for (int i = 0; i < totalLines; i++) {
				String theCurrentLine = outputLines.get(i);
				int theCurrentLineLength = theCurrentLine.length();
				ArrayOfBuckets.get(theCurrentLineLength - minLength).add(theCurrentLine);
			}
			/////////////////////////combines buckets//////////////////////
			for (int i = 0; i <= (maxLength- minLength); i++) {
				finalOutputLines.addAll(ArrayOfBuckets.get(i));
			}
			
			
			
			count++;
		}

		//////////////////////////////////////////////////////////////////////////////////////
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println(totalTime);
		///////////////////////////////////////////////
		/////////////Output//////////////////////////////

		Files.write(outputPath, finalOutputLines, Charset.forName("US-ASCII"));
	}

	public static int sumOnes(String theString) {
		int count = 0;
		for (int i = 0; i < theString.length(); ++i) {
			count += theString.charAt(i) - '0';
		}
		return count;
	}

//	private static String[] numbers;
//	private static String[] helper;
//
//	private static int number;
//
//	public static void mSort(ArrayList<String> valuesAL) {
//		String[] values = valuesAL.toArray(new String[valuesAL.size()]);
//
//		numbers = values;
//		number = values.length;
//		helper = new String[number];
//		mergesort(0, number - 1);
//		valuesAL = new ArrayList<String>(Arrays.asList(values));
////		for (int i = 0; i < values.length; i++){
////			valuesAL.set(i, values[i]);
////		}
//	}
//
//	private static void mergesort(int low, int high) {
//		// check if low is smaller then high, if not then the array is sorted
//		if (low < high) {
//			// Get the index of the element which is in the middle
//			int middle = low + (high - low) / 2;
//			// Sort the left side of the array
//			mergesort(low, middle);
//			// Sort the right side of the array
//			mergesort(middle + 1, high);
//			// Combine them both
//			merge(low, middle, high);
//		}
//	}
//
//	private static void merge(int low, int middle, int high) {
//
//		// Copy both parts into the helper array
//		for (int i = low; i <= high; i++) {
//			helper[i] = numbers[i];
//		}
//
//		int i = low;
//		int j = middle + 1;
//		int k = low;
//		// Copy the smallest values from either the left or the right side back
//		// to the original array
//		while (i <= middle && j <= high) {
//			if (helper[i].compareTo(helper[j]) <= 0) {
//				numbers[k] = helper[i];
//				i++;
//			} else {
//				numbers[k] = helper[j];
//				j++;
//			}
//			k++;
//		}
//		// Copy the rest of the left side of the array into the target array
//		while (i <= middle) {
//			numbers[k] = helper[i];
//			k++;
//			i++;
//		}
//
//	
//	}
	
}
	