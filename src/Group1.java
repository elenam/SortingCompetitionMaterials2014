import java.io.File;
import java.util.Random;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;


// Done by Justin YaDeau and Brennan Gensch


public class Group1 {
	public static Random rand = new Random();
	public static void main(String[] args) throws InterruptedException{		
		if (args.length < 2) {
			System.out.println("Please run with two command line arguments: input and output file names");
			System.exit(0);
		}

		String inputFileName = args[0];
		String outFileName = args[1];

		ArrayList<String> input = new ArrayList<String>();
		Scanner in;
		int minLength = 10000000;
		int maxLength = 0;
		int minSum = 10000000;
		int maxSum = 0;
		try {
			in = new Scanner(new File(inputFileName));
			// the first item in the file is lambda
			double lambda = in.nextDouble();
			String next;
			while (in.hasNext()) {
				next = in.next();
				if (next.length() > maxLength) {
					maxLength = next.length();
				} else if (next.length() < minLength) {
					minLength = next.length();
				}
				if (sumString(next) > maxSum) {
					maxSum = sumString(next);
				} else if (sumString(next) < minSum) {
					minSum = sumString(next);
				}
				input.add(next);
			}
			in.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String [] toSort = new String[0];
		long start = System.currentTimeMillis();
		for (int i = 0; i < Integer.parseInt(args[2]); i++) {
			toSort = input.toArray(new String[0]);
			quickSortInsertion(toSort);
			toSort = countingSortSum(toSort, minSum, maxSum, minLength, maxLength, start, outFileName);
		}
		long end = System.currentTimeMillis();
				
		System.out.println(end - start);
		writeOutResult(toSort,outFileName);
	}


	public static void quickSortInsertion(String[] array) {
	    recursiveQuickSortInsertion(array, 0, array.length - 1);
	}
	
	public static void recursiveQuickSortInsertion(String[] array, int left, int right) {
		
	    int pivot = partitionInsertion(array, left, right);
	    if(right - left > 6){
		
		    if (left < pivot - 1) {
		    	recursiveQuickSortInsertion(array, left, pivot - 1);
		    }
		
		    if (right > pivot) {
		    	recursiveQuickSortInsertion(array, pivot, right);
		    }
	    }
	}
	
	public static int partitionInsertion(String[] array, int left, int right) { 
	    String pivot = array[rand.nextInt(right - left) + left];
	    String tmp = null;
		if(right - left > 6){
		    while (left <= right) {
		        //searching number which is greater than pivot, bottom up
		        while (array[left].compareTo(pivot) < 0) {
		            left++;
		        }
		        //searching number which is less than pivot, top down
		        while (array[right].compareTo(pivot) > 0) {
		            right--;
		        }
		
		        // swap the values
		        if (left <= right) {
		           	tmp = array[left];
		           	array[left] = array[right];
		            array[right] = tmp;
		            //increment left index and decrement right index
		            left++;
		            right--;
		        }
		     }
		     return left;
		}
		else {
			insertionSort(array, left, right);
			return left;
		}
	 }
	
	public static void insertionSort(String[] insertionArr, int left, int right){
		String key = null;
		int i;
		for(int j = left + 1; j <= right; j++){
			key = insertionArr[j];
			i = j - 1;
			while (i >= left && insertionArr[i].compareTo(key) > 0){
				insertionArr[i+1] = insertionArr[i];
				i = i - 1;
			}
			insertionArr[i+1] = key;
		}
	}

	private static String[] countingSortSum(String[] toSort, int min, int max, int minLength, int maxLength, long start, String outFileName) {

		int[] count = new int[max - min + 1]; 
		String[] temp = new String[toSort.length];
		int[] value = new int[toSort.length];
		int index;
		
		for (int i = 0; i < toSort.length; i++) {
			value[i] = sumString(toSort[i]);
			count[value[i] - min]++;
		}

		for (int i = 1; i < count.length; i++) {
			count[i] += count[i - 1];
		}
		for (int i = toSort.length - 1; i >= 0; i--) {
			index = --count[value[i] - min];
			temp[index] = toSort[i];
		}
		
		return countingSortLength(temp, minLength, maxLength , start, outFileName);		

	}
	
	private static int sumString(String next) {
		int count = 0;
		int sub = 48 * next.length();
		for (int i = 0; i < next.length(); ++i) {
			count += next.charAt(i);
		}
		return count - sub;
	}

	private static String[] countingSortLength(String[] toSort, int min, int max, long start, String outFileName) {

		int[] count = new int[max - min + 1]; 
		String[] temp = new String[toSort.length];
		int index;
		for (int i = 0; i < toSort.length; i++) {
			count[toSort[i].length() - min]++;
		}

		for (int i = 1; i < count.length; i++) {
			count[i] += count[i - 1];
		}
		for (int i = toSort.length - 1; i >= 0; i--) {
			index = --count[toSort[i].length() - min];
			temp[index] = toSort[i];
		}
		
		
		return temp;

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
