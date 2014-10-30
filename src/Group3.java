// Jacob Opdahl & Jeremy Eberhardt
// Sorting Competition
// See SortingCompetitionWriteup.txt for efficiency analysis and algorithm description.

//======== Changes since the first preliminary round. ========
//
// Note: Not all changes from early on will be relevant anymore
// since later changes involved complete removal of sorting methods
// that might have been modified.
// We took out the old code, but if anyone wants to see it, we can
// provide it. 
//
//-------- Changes made after 1st preliminary round.  --------
// 1. What: Removed duplicate logic from a check to do Counting Sort.
// 1. Why:  The duplicate logic added unnecessary checks to the algorithm.
// 2. What: Fixed where it did not sort correctly in every case. The issue
//          was our counting sorts were not called on the last subarray.
// 2. Why:  Self-explanatory. Our algorithm needs to work.
// 3. What: Find and remove whatever did parallel processing. 
//          (Was the default array merge sort method.)
// 3. Why:  We run algorithms pinned to one processor so it became very slow.
//          We did not actually know it used parallel processing.
// 4. What: Implemented our own merge sort.
// 4. Why:  Replaced the built in array sort with parallel processing.
// 5. What: Removing type casts from merge sort that made it work for anything comparable.
// 5. Why:  SIGNIFICANTLY faster to assume input is strings and not type cast.
// 6. What: Removed checks in counting sorts for exceptions.
// 6. Why:  Only were there for testing, removed to not spend extra time.
// 7. What: Changed 2nd counting sort (the one that sorts on sums as keys and
//          is still being used) to also have a min key.
// 7. Why:  Having a min key could make a smaller count array and help in our worst case of big lambdas.
// 8. What: Tried to augment merge sort by combining it with insertion after a certain point.
// 8. Why:  Read online it can be faster.
// 8. Rejected: We did not keep this as it was worse than regular in all cases.
// 9. What: Implemented Quicksort to be done instead of merge sort depending 
//          on a function with parameters lambda and number of items.
// 9. Why:  Quicksort has better coefficients in best and average case than merge sort.
//          Wanted to take advantage of that when data was not likely to have repetitions.
//
//-------- Changes made after 2nd preliminary round.  --------
// 10. What: Removed 1st sort on length (a counting sort) and third sort on
//           lexicographic (merge sort or quicksort), and combined them into one.
//           We made a compare method for shortlex and implemented with quicksort.
// 10. Why:  Found out about shortlex order (lexicographic within a length) and 
//           thought it might be faster to do both at once. Also, realized we could
//           keep our old counting sort on sums since it is stable.
// 10. Kept: Our theory was right and this seemed to improve times in (almost) all cases.
// 11. What: Changed the way we summed the '1's in a string from char conversion to what Elena used.
// 11. Why:  After testing, it turned out to be faster.
// 12. What: Also implemented a merge sort with shortlex compare method.
// 12. Why:  Quicksort shortlex can get nasty times with lots of repetitions.
// 13. What: Technically, this is part of 12, but added a check to do Quicksort
//           or merge sort based on the value of lambda.
// 13. Why:  I made it have its own number because this took some significant time to
//           test. The check helped to avoid
//           Quicksort's worst case while allowing us to get its better coefficients.
//           It seemed the proper threshold was to do Quicksort for lambda > 2.
// 14. What: Implemented a function to calculate a threshold for lambda based on the length.
// 14. Why:  Wanted more accuracy than previous check used to determine merge sort or Quicksort.
// 15. What: Tried recursing on sides in Quicksort in the same order.
// 15. Why:  Previously recursed on smallest side first, thought it might not matter.
// 15. Rejected: No significant change either way in running times.
// 16. What: Tried implementing Heap Sort Shortlex instead of merge sort.
// 16. Why:  Thought it might be faster since parallel processing isn't helping the recursion.
// 16. Rejected: Performed worse in all tests with Heap Sort.
//
//========              End change log.               ========




import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class Group3 {
	
	private static double lambda = 0; // Assigned during pre-processing.
	private static double threshold = 1.8;
	
	// Second sort fields
	private static int maxSum = -1; // Max and min will be for within a given length.
	private static int minSum = Integer.MAX_VALUE; 
	private static int firstIndex = 0; // Indices are used to show what portion of the array to sort on.
	private static int lastIndex = -1;
	private static int currentLength = -1; //Need to know when length changes to know when to sort again.
	private static int[] sumsOfToSort; // Note, we do not initialize or fill this in until our timer is running!
	
	public static void main (String[] args) {
		
		if (args.length < 2) {
			System.out.println("Please run with two command line arguments: input and output file names");
			System.exit(0);
		}
		
		String inputFileName = args[0];
		String outFileName = args[1];
		int numberOfLoops = Integer.parseInt(args[2]);
		
		String[] toSortOriginal = readInData(inputFileName);
		
		// As part of our pre-processing we determine the threshold based on the size of the original data 
		threshold = threshold - .05 * ((200000-toSortOriginal.length)/10000);
		
		long timeBefore = System.currentTimeMillis(); // Time starts here!!
		
		
		// Must be sorted in this array when complete.
		String[] toSort = new String[toSortOriginal.length];
		
		
		for (int i = 0; i < numberOfLoops; ++i) {
			
			// Copies the original array into a new one called toSort.
			System.arraycopy(toSortOriginal, 0, toSort, 0, toSortOriginal.length);
			
			//THIS IS ELENA'S
			//sort(toSort);
			
			// Implemented a threshold to determine which sort we used based on the length of the given data.
			// See item 14 in the changelog above.
			// This sorts our array lexicographically AND by length.
			if (lambda > threshold) {
				quickSortShortlex(toSort, 0, toSort.length - 1);
			} else {
				mergeSortShortlex(toSort, 0, toSort.length - 1);
			}
			
			
			int currentSum;
			// Stores the sums of 1's in an array as we go through toSort
			// This is primarily used in our counting sort method below.
			if (i == 0) {
				sumsOfToSort = new int[toSort.length];
			}
			
			// Finds where the counting sort needs to be called.
			// Then calls our countingSortSum on the proper portion of toSort.
			for (int j = 0; j < toSort.length; ++j) {
				currentLength = toSort[j].length();
				if (j != 0 && currentLength > toSort[j-1].length()) {
					firstIndex = lastIndex + 1;
					lastIndex = j - 1;
					if (firstIndex != lastIndex) {
						countingSortSum(toSort);
					}
					maxSum = -1;
					minSum = Integer.MAX_VALUE;
				}
				
				// Stores the current sum and puts it into our sumsOfToSort array.
				currentSum = sumOnes(toSort[j]);
				sumsOfToSort[j] = currentSum;
				
				// Updates the value of maxSum and minSum if necessary. 
				if (currentSum > maxSum) {
					maxSum = currentSum;
				} 
				if (currentSum < minSum) {
					minSum = currentSum;
				}
			}
			
			// Updates our firstIndex and lastIndex to call our counting sort on the final chunk of toSort, then calls countingSortSums.
			firstIndex = lastIndex + 1;
			lastIndex = toSort.length - 1;
			countingSortSum(toSort);
			
			
			// Second sort fields
			maxSum = -1;
			firstIndex = 0;
			lastIndex = -1;
			currentLength = -1;
			
		}
		
		// Ends the timer.
		long timeAfter = System.currentTimeMillis(); // Time ends here!!
		
		// Writes out the outuput to a file.
		writeOutResult(toSort,outFileName);
		
		// Prints the total time taken by our algorithm.
		System.out.println(timeAfter - timeBefore);
	}
	
	// Given at beginning of sorting competition, unchanged.
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

		//System.out.println(input);

		return input.toArray(new String[0]); // convert to array of strings
	}
	
	// Given at beginning of sorting competition, unchanged.
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
	
	
	/******** Our sorting and extra methods start here. ********/
	
	
	// The sum of the integers that compose a string is the key
	// for this sort. We kept track of the max and min since we 
	// had to find them anyway to use counting sort.
	public static void countingSortSum(String[] array) {
		
		int[] countArr = new int[maxSum - minSum + 1];
		
		for(int i = firstIndex; i <= lastIndex; ++i) {
			countArr[sumsOfToSort[i] - minSum]++;
		}
		
		countArr[0] += (firstIndex - 1);
		for (int i = 1; i < countArr.length; i++) {
			countArr[i] += countArr[i - 1]; 
		}
		
		String[] copy = new String[array.length];
		
		for (int i = lastIndex; i >= firstIndex; i--) {
			copy[countArr[sumsOfToSort[i] - minSum]--] = array[i];
		}
		
		// The fast and easy way to copy from one array to another (according to Joe Beaver, and backed up by some testing).
		System.arraycopy(copy, firstIndex, array, firstIndex, lastIndex - firstIndex + 1);
		
	} 
	
	
	// Quicksort to sort lexicographically within lengths on a portion of an array.
	// Sorts Inclusively on the indices.
	private static void quickSortShortlex(String[] arr, int left, int right) {
		
		int current = left;

		// Since the data should be largely random already, we just stick with the default of
		// choosing the left index as the pivot.
		int pivotIndex = left;
		String temp = arr[pivotIndex];
		arr[pivotIndex] = arr[right];
		arr[right] = temp;
			
		String pivotValue = arr[right];
			
		// Make comparisons for partitioning
		for(int i = left; i < right; i++) {
			// The actual work happens here!
			if (compare(arr[i], pivotValue) <= 0) {		
				String tempSwap = arr[i];
				arr[i] = arr[current];
				arr[current] = tempSwap;
				++current;
			} 
				
		}
			
		temp = arr[current];
		arr[current] = pivotValue;
		arr[right] = temp;
		
		// The nested ifs make it recurse on the smaller side first.
		if (((current - 1) - left) > (right - (current + 1))) {
			if ((right - current) > 1) {
				quickSortShortlex(arr, (current + 1), right);
			}
			if ((current - left) > 1 ) {
				quickSortShortlex(arr, left, (current - 1));
			}
		} else {
			if ((current - left) > 1 ) {
				quickSortShortlex(arr, left, (current - 1));
				}
			if ((right - current) > 1) {
				quickSortShortlex(arr, (current + 1), right);
			}
		}
	}
	
	
	// Merge sort for shortlex (lexicographic within length) order.
	// Inclusive firstIndex and lastIndex.
	public static void mergeSortShortlex(String[] arr, int firstIndex, int lastIndex) {
		
		int numOfItems = lastIndex - firstIndex + 1;
		String[] leftHalf = new String[numOfItems / 2];
		String[] rightHalf = new String[numOfItems - leftHalf.length];

		for (int i = 0; i < leftHalf.length; i++) {
			leftHalf[i] = arr[firstIndex + i];
		}

		for (int i = 0; i < rightHalf.length; i++) {
			rightHalf[i] = arr[firstIndex + leftHalf.length + i];
		}

		if (leftHalf.length > 1) {
			mergeSortShortlex(leftHalf, 0, leftHalf.length - 1);
		}

		if (rightHalf.length > 1) {
			mergeSortShortlex(rightHalf, 0, rightHalf.length - 1);
		}

		int indexL = 0;
		int indexR = 0;
		int indexMain = firstIndex;

		while(indexL < leftHalf.length && indexR < rightHalf.length){
			if(compare(leftHalf[indexL], rightHalf[indexR]) <= 0){
				arr[indexMain++] = leftHalf[indexL++];
			}
			else {
				arr[indexMain++] = rightHalf[indexR++];
			}
		}

		while (indexL < leftHalf.length) {
			arr[indexMain++] = leftHalf[indexL++];
		}
		
		while (indexR < rightHalf.length) {
			arr[indexMain++] = rightHalf[indexR++];	
		}

	}
	
	// This is the "compareTo" method we use for merge sort and
	// quick sort in order to do length and lexicographic at the same time.
	// The comparison essentially says if they are in shortlex order.
	// Shortlex being lexicographic within a given length.
	private static int compare(String str1, String str2) {
		if (str1.length() != str2.length()) {
			return str1.length() - str2.length();
		}
			
		// only get here if the lengths are equal
		return str1.compareTo(str2);
	}

	
	// An efficient way to sum the ones in a string.
	// Thanks, Elena!
	private static int sumOnes(String str) {
		int count = 0;
		for (int i = 0; i < str.length(); ++i) {
			count += str.charAt(i) - '0';
		}
		return count;
	}
	
	
	/** End here. **/
	
	
	private static class StringComparator implements Comparator<String> {

		@Override
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
