//Xavier Walcome & Brandon Stock
//GROUP 8 FUN TIME~~~~~~~~~~~~~~~~
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Group8 {
	static int minLength = 999999;
	static int maxLength = 0;
	static double lambda = 0;
	
	public static void main(String[] args) throws IOException, InterruptedException {
		String inputName = args[0];
		String outputName = args[1];
		int loop = Integer.parseInt(args[2]);
		
		String[] testArr = readData(inputName);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
		int count = 0;
		long t1 = System.currentTimeMillis();
		String[] test = null;
		while (count < loop) {
			test = null;
			test = someSortOfAlgorithm(testArr);
			count++;
		}
		long t2 = System.currentTimeMillis();
		writeFile(outputName, test);
		System.out.println(t2-t1);
		
	}
	
	//Does all three types of sorts on an array of strings
	public static String[] someSortOfAlgorithm(String[] arrMatey) {
		String[] stepOne = sumSortaSumSort(arrMatey);
		String[] stepTwo = lengthSorty(stepOne);
		alphaSort(stepTwo,0);
		return stepTwo;
	}	
	
	public static void alphaSort(String[] stArr, int start) {
		int end = start;
		while(end < stArr.length - 1 && stArr[end].length() == stArr[end + 1].length() && countOnes(stArr[end]) == countOnes(stArr[end + 1])){
			end++;
		}
		if(end < stArr.length && end - start > 0) {
			quicksort(stArr,start,end);
		}
		start = end + 1;
		if(end < stArr.length) alphaSort(stArr, start);
	}
	
	//quicksort used in the alphabetic sort
	public static void quicksort(String[] tArr, int start, int end) {
		if(start < end){
			int kiwi = partie(tArr, start, end);
			quicksort(tArr, start, kiwi - 1);
			quicksort(tArr, kiwi + 1, end);
		}
	}
	
	public static int partie(String[] tArrt, int start, int end){
		int i = start - 1;
		String endy = tArrt[end];
		for(int j = start; j < end; j++){
			if(tArrt[j].compareTo(endy) <= 0){
				i++;
				swapsies(tArrt, i, j);
			}
		}
		swapsies(tArrt, i + 1, end);
		return i + 1;
	}
	
	public static void swapsies(String[] str, int i, int j) {
		String one = str[i];
		str[i] = str[j];
		str[j] = one;
	}
	
	//Counting-sorts an array of strings by their length
	public static String[] lengthSorty(String[] arr) {
		String[] sorted = new String[arr.length];
		int[] length = new int[maxLength - minLength + 1];
		for(String str: arr) {
			length[str.length() - minLength]++;
		}
		for(int i = 1; i < length.length; i++) {
			length[i] += length[i - 1];
		}
		for(int i = arr.length - 1; i >= 0; i--) {
			sorted[length[arr[i].length()- minLength] - 1] = arr[i];
			length[arr[i].length() - minLength]--;
		}
		return sorted;
	}
	
//-----------------------------Sum of Ones-----------------------------//
	//Counting-sorts an array of strings by the sum of ones in each string.
	public static String[] sumSortaSumSort(String[] arr) {
		int[] count = new int[maxLength];
		String[] newArr = new String[arr.length];
		for(String str: arr) {
			count[countOnes(str)]++;
		}
		for(int i = 1; i < count.length; i++) {
			count[i] += count[i - 1];
		}
		for(int i = arr.length - 1; i >= 0; i--) {
			newArr[count[countOnes(arr[i])] - 1] = arr[i];
			count[countOnes(arr[i])]--;
		}
		return newArr;
	}
	
	//Counts the amount of ones in a string.
	public static int countOnes(String str) {
		int ones = 0;
		for(int i = 0; i < str.length(); i++) {
			if(str.charAt(i) == '1') ones++;
		}
		return ones;
	}
//---------------------------------------------------------------------//
	
	
	//Reads a file whose lines are all strings of 0's and 1's, except for the first string which has a double in it, and stores these in an array
	public static String[] readData(String fileName) {
		ArrayList<String> input = new ArrayList<String>();
		Scanner scan;
		try {
			scan = new Scanner(new File(fileName));
			lambda = scan.nextDouble();
			while(scan.hasNext()) {
				String nextie = scan.next();
				input.add(nextie);
				if(maxLength < nextie.length()) maxLength = nextie.length();
				if(minLength > nextie.length()) minLength = nextie.length();
			}
			scan.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return input.toArray(new String[0]);
	}
	
	//Writes a file using a file name and an array of strings, putting one string on each line.
	public static void writeFile(String fileName, String[] streengz) throws IOException{
		BufferedWriter outputWriter = null;
		outputWriter = new BufferedWriter(new FileWriter(fileName));
		for(int i = 0; i < streengz.length; i++) {
			outputWriter.write(streengz[i]);
			outputWriter.newLine();
		}
		outputWriter.flush();
		outputWriter.close();
	}
}
