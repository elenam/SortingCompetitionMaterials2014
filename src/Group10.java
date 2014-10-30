//Thomas Harren & David Donatucci

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class Group10 {

	//from preprocessing
	public static int n;
	public static int maxLength;

	//for use in sorting
	public static String[] masterArr;

	public static String[] tempA;
	public static String[] tempB;
	public static int zeroIndexA;
	public static int zeroIndexB;
	public static int oneIndexA;
	public static int oneIndexB;
	public static int threshold;

	public static int sortIndex = 1;
	public static int masterIndex = 0;
	public static int sorted = 0;


	public static void main(String[] args) {	
		//handle inputs
		if (args.length < 3) {
			System.out.println("Please run with three command line arguments: input file name, output file name, number of times to sort");
			System.exit(0);
		}

		String inputFileName = args[0];
		String outFileName = args[1];
		int sortNTimes = Integer.parseInt(args[2]);

		String[] initialArr = readInData(inputFileName);
		/*		System.out.println("Input from: " + inputFileName);
		System.out.println("Output to: " + inputFileName);
		System.out.println("Run 10-Sort " + sortNTimes +" times.");
		System.out.println("");*/

		//Preprocessing Stage
		findMaxLength(initialArr);
		n = initialArr.length;
		//threshold = maxLength;

		//Sorting Stage: Copy, Run, and Time
		double startTime = System.currentTimeMillis();
		for (int i = 0; i < sortNTimes; i++) {
			sort(initialArr);
			masterIndex=0;
		}
		double endTime = System.currentTimeMillis();
		System.out.println(endTime - startTime);


		//Results Stage
		writeOutResult(masterArr, outFileName);
		//      System.out.println("Original: "+ Arrays.toString(masterArr));
		//      System.out.println(Arrays.toString(elenaCorrect));	
		//		System.out.println(Arrays.toString(masterArr));	


		///For Testing:
		///String[] copy = Arrays.copyOf(initialArr, n);
		///double startTimeElena = System.currentTimeMillis();
		///String[] elenaCorrect = SlowCorrectSorting.slowSort(copy);
		///double endTimeElena = System.currentTimeMillis();
		///System.out.println("Elena: "+ (endTimeElena - startTimeElena)*sortNTimes);
		///System.out.println("Correct: " + Arrays.equals(elenaCorrect, masterArr));
		//System.out.println("SumTime: " + totalSumTime);
	}


	private static void sort(String[] initialArr) {
		//Copy storage into "masterArr", sort into 2 "buckets" based on length
		masterArr = new String[n];
		int frontIndex = 0;
		int backIndex = n-1;
		threshold = 25;
		for (int i = 0; i < initialArr.length; i++) {
			if(initialArr[i].length() <= threshold) {
				masterArr[frontIndex++] = initialArr[i];
			} else {
				masterArr[backIndex--] = initialArr[i];
			}
		}
		//short strings go to radix, long strings to quick
		pseudoRadix(frontIndex);
		quickSortRecursive(masterArr, frontIndex, n - 1);
	}
	
	
	
	
	
	
	

	public static void pseudoRadix(int length){
		// Create two working arrays and fill the first array:
		// Numbers will be stored sorted if you read the 2 separate ranges as one:[0, zeroIndex) followed by [n-1, oneIndex).
		tempA = new String[length];
		tempB = new String[length];

		zeroIndexA = 0;
		oneIndexA = length - 1;
		for (int i = 0; i < length; i++) {
			//ASSUMPTION: Each binary string has at least length of one.
			//Here, we are looking at the last char in each string. Radix count would be 0, so skipping that reference.
			if(masterArr[i].charAt(masterArr[i].length() - 1) == '0') {
				tempA[zeroIndexA++] = masterArr[i];
			}else{
				tempA[oneIndexA--] = masterArr[i];
			}
			sortIndex=1;
		}

		//Sort items until the longest is sorted.
		while(sortIndex <= threshold) {
			if(sortIndex % 2 == 0) {
				zeroIndexA = 0;
				oneIndexA = length - 1;
				sortTempBtoA(length);
			} else {
				zeroIndexB = 0;
				oneIndexB = length - 1;
				sortTempAtoB(length);
			}

			//After sorting by size and abc, we sort by sum of ones using counting sort
			if(sorted > 1) {
				countingSort();
			}

			sorted = 0;
			sortIndex++;
		}
	}

	private static void sortTempAtoB(int length) {
		//Sort all items in order by first sorting items at top of the array, then sorting the reverse sorted ones at the bottom.
		for (int i = 0; i < zeroIndexA; i++) {
			//If a string is too short, then it is sorted so add it to masterArr, else sort it!
			if (tempA[i].length()-1 - sortIndex < 0){
				masterArr[masterIndex++] = tempA[i];
				sorted++;
			}else{
				//Here, we are looking at the char radixRunCount from the end of each string.
				if(tempA[i].charAt(tempA[i].length() - 1 -sortIndex) == '0') {
					tempB[zeroIndexB++] = tempA[i];
				}else{
					tempB[oneIndexB--] = tempA[i];
				}
			}
		}
		for (int i = length-1; i > oneIndexA; i--) {
			//If a string is to short, then it is sorted so add it to masterArr, else sort it!
			if (tempA[i].length()-1 - sortIndex < 0){
				masterArr[masterIndex++] = tempA[i];
				sorted++;
			}else{
				//Here, we are looking at the char radixRunCount from the end of each string.
				if(tempA[i].charAt(tempA[i].length() - 1 -sortIndex) == '0') {
					tempB[zeroIndexB++] = tempA[i];
				}else{
					tempB[oneIndexB--] = tempA[i];
				}
			}
		}
	}

	private static void sortTempBtoA(int length) {
		//Sort all items in order by first sorting items at top of the array, then sorting the reverse sorted ones at the bottom.
		for (int i = 0; i < zeroIndexB; i++) {
			//If a string is to short, then it is sorted so add it to masterArr, else sort it!
			if (tempB[i].length()-1 - sortIndex < 0){
				masterArr[masterIndex++] = tempB[i];
				sorted++;
			}else{
				//Here, we are looking at the char radixRunCount from the end of each string.
				if(tempB[i].charAt(tempB[i].length() - 1 -sortIndex) == '0') {
					tempA[zeroIndexA++] = tempB[i];
				}else{
					tempA[oneIndexA--] = tempB[i];
				}
			}
		}
		for (int i = length-1; i > oneIndexB; i--) {
			//If a string is to short, then it is sorted so add it to masterArr, else sort it!
			if (tempB[i].length()-1 - sortIndex < 0){
				masterArr[masterIndex++] = tempB[i];
				sorted++;
			}else{
				//Here, we are looking at the char radixRunCount from the end of each string.
				if(tempB[i].charAt(tempB[i].length() - 1 -sortIndex) == '0') {
					tempA[zeroIndexA++] = tempB[i];
				}else{
					tempA[oneIndexA--] = tempB[i];
				}
			}
		}
	}



	private static void countingSort(){
		String[] sortedCopyOfMaster = new String[sorted];
		int[] countingSortArr = new int[sorted];
		int[] countingArr = new int[sortIndex + 1];

		for(int i = masterIndex - sorted; i < masterIndex; i++) {
			int count = sumOnes(masterArr[i]);
			countingSortArr[sorted - masterIndex + i] = count;
			countingArr[count]++;
		}
		for(int i = 1; i <= sortIndex; i++){
			countingArr[i] += countingArr[i-1];
		}
		for(int i = sorted -1; i >= 0; i--){
			countingArr[countingSortArr[i]]--;
			int index = countingArr[countingSortArr[i]];
			sortedCopyOfMaster[index] = masterArr[masterIndex - sorted + i];
		}
		for (int i = 0; i < sorted; i++) {
			masterArr[masterIndex -sorted + i] = sortedCopyOfMaster[i];
		}
	}

	
	
	
	
	
	
	

	public static void quickSort(String[] arr) {
		quickSortRecursive(arr, 0, arr.length - 1) ;
	}

	private static void quickSortRecursive(String[] arr, int left, int right) {
		if (right > left) {
			int current = left;


			/* Next step: choose pivot and swap it with the last value in the subarray */
			//int pivot = findPivot(arr, left, right);

			swap(arr, findPivot(arr, left, right), right);
			int pivot = right;

			// make comparisons for partitioning
			for(int i = left; i < right; i++) {
				if (compareByRules(arr[pivot],arr[i]) > 0) {
					swap(arr, current, i);
					current++;
				}
			}
			swap(arr, current, right);

			/* Last Step: Make the recursive calls! For maximum efficiency, recurse on the smaller partition first */
			quickSortRecursive(arr,left,current-1);
			quickSortRecursive(arr, current + 1 ,right);
		}
	}

	private static void swap(String[] arr, int left, int right) {
		String temp = arr[right];
		arr[right] = arr[left];
		arr[left] = temp;
	}

	private static int findPivot(String[] arr, int left, int right) {
		int fir = left;
		int mid = right/2;
		int las = right;

		String first = arr[left];
		String last = arr[right];
		String middle = arr[right/2];

		if (compareByRules(first,middle) >= 0) {
			if (compareByRules(middle,last) >= 0) {
				return mid;
			} else if (compareByRules(first,last) >= 0) {
				return las;
			} else {
				return fir;
			}
		} else {
			if (compareByRules(last,middle) >= 0) {
				return mid;
			} else if (compareByRules(first,last) >= 0) {
				return fir;
			} else {
				return las;
			}
		}

	}

	private static int compareByRules(String str1, String str2 ) {
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

	private static void findMaxLength(String[] initialArr) {
		for(int i = 0; i < initialArr.length; i++) {
			if(initialArr[i].length() > maxLength) {
				maxLength = initialArr[i].length();
			}
		}
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


}
