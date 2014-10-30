import java.util.Random;

//Brian Mitchell && Kristin Rachor

//This is mainly the books implementation of QuickSort using Elena's compare methods from her StringComparator and a random pivot
//Pulled from Brian & Tom's quicksort lab 

public class SevenSort {
	public static Random rand = new Random();
	
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
	
}
