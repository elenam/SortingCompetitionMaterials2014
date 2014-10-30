
public class MergeSorter{

	final static int threshold = 7;
	//we have no idea if this is correct. The times, with the same settings, varied up to 30% between run, on five different boxes.
	//Some basic statistical tools suggest that this is correct, but I'm fairly certain that they aren't valid for the distribution we actually have.
	
	public static void sort(BinaryString[] arr)
	{
		BinaryString[] tmp = new BinaryString[arr.length];
		mergeSort(arr, tmp,  0,  arr.length - 1);
	}

	public static void mergeSort(BinaryString[] arr, BinaryString[] tmp, int left, int right){
		if( left < right )
		{
			if ((right - left) <= threshold){
				//System.out.println("Insert");
				//long in = System.currentTimeMillis();
				insertionSort(arr, left, right);
				//long out = System.currentTimeMillis();
				//System.out.println("Time in insertionSort: " + (out - in));
			}else{
				int center = left + ((right - left) / 2);
				//choosing our center, based on 
				//http://googleresearch.blogspot.ca/2006/06/extra-extra-read-all-about-it-nearly.html
				/// int center = (left + right)/2 fails when the sum is bigger than the int limit.
				//using this version ensures that our center is correct.
				mergeSort(arr, tmp, left, center);
				mergeSort(arr, tmp, center + 1, right);
				merge(arr, tmp, left, center + 1, right);
			}
		}
	}


	 private static void merge(BinaryString[] arr, BinaryString[] tmp, int left, int right, int rightEnd){
	        int leftEnd = right - 1;
	        int k = left;
	        int num = rightEnd - left + 1;

	        while(left <= leftEnd && right <= rightEnd)
	            if(arr[left].compareTo(arr[right]) <= 0)
	                tmp[k++] = arr[left++];
	            else
	                tmp[k++] = arr[right++];

	        while(left <= leftEnd)    
	            tmp[k++] = arr[left++];

	        while(right <= rightEnd) 
	            tmp[k++] = arr[right++];

	        for(int i = 0; i < num; i++, rightEnd--)
	            arr[rightEnd] = tmp[rightEnd];
	    }
	

	 public static void insertionSort(BinaryString[] arr, int left, int right){
		 for (int i = left; i <= right; i++) {
			 BinaryString k = arr[i];
			 int j = i;
			 while((j > left) && (arr[j - 1].compareTo(k) == 1)){
				 arr[j] = arr[j - 1];
				 j--;
			 }
			 arr[j] = k;
		 }
	 }

}
