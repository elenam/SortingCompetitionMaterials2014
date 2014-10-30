import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


public class Group15 {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {

		FileReader file = new FileReader(args[0]);
		PrintWriter write = new PrintWriter(args[1]);
		int n = Integer.parseInt(args[2]);
		Scanner scn = new Scanner(file);
		double lambda = scn.nextDouble();
		int max = 0;
		ArrayList<String> str= new ArrayList<String>();
		int w =0;
		while(scn.hasNextLine()){
			String line = scn.nextLine();
			if(!line.equals("")){
				if(line.length()>max){
					max = line.length();
				}

				str.add(line);
			}
		}
		String[] arr = new String[str.size()];
		for(int k =0;k<str.size();k++){
			arr[k] = str.get(k);
		}
		double start = System.currentTimeMillis();
		String[] copy = new String[str.size()-1];
		String[] sorted = null;
		for(int i = 0; i<n; i++){
			System.arraycopy(arr,0,copy,0,arr.length-1);
			sorted = sortIt(max,arr,arr.length);
		}
		double end = System.currentTimeMillis();
		System.out.println(end-start);

		for(String p: sorted){

			write.println(p);

		}


	}



	private static void Printarr(String[] copy) {
		for(int i=0; i<copy.length;i++){
			System.out.println(copy[i]);
		}

	}



	private static String[] sortIt(int max, String[] copy,int items) {
		dataArr[][] sort =new dataArr[max][max];
		String[] t = new String[items];
		//HashMap<String,dataPair> map = new HashMap<String, dataPair>();
		for(int i =0;i<copy.length;i++){
			dataPair pair;
		//	if(map.containsKey(copy[i])){
			//	pair = map.get(copy[i]);
			//}
			//else{	
			pair = new dataPair(copy[i]);
			//map.put(copy[i], pair);
			//}
			if(sort[pair.size-1][pair.ones] ==null){
				sort[pair.size-1][pair.ones] = new dataArr();
			}
			sort[pair.size-1][pair.ones].add(pair);
		}
		int k=0;
		for(int i=0;i<max;i++){
			for(int j=0;j<max;j++){
				if(sort[i][j]!= null){
					for(dataPair p: sort[i][j].arr){
						t[k]=p.str;
						k++;
					}
				}
			}
		}
		return t;
	}




	static boolean isSorted(String file) throws FileNotFoundException{
		FileReader f = new FileReader(file);
		FileReader sorted = new FileReader("sorted");
		Scanner correct = new Scanner(sorted);
		Scanner scn = new Scanner(f);
		int i =0;
		String line;
		String cLine;
		while(scn.hasNextLine()){
			line = scn.nextLine();
			cLine = correct.nextLine();
			if(cLine != line){
				return false;
			}
		}
		return true;
	}

}
class dataPair implements Comparable{
	int size;
	BigInteger value;
	String str;
	int ones =0;
	dataPair(String str){
		this.str = str;
		value = new BigInteger(str, 2);
		ones = countOnes(str);
		size = str.length();
	}
	private int countOnes(String str) {
		int count = 0;
		for (int i = 0; i < str.length(); ++i) {
			count += str.charAt(i) - '0';
		}
		return count;
	}
	@Override
	public int compareTo(Object other) {
		dataPair otherval = (dataPair) other;
		return this.value.compareTo(otherval.value);
	}
}
class dataArr{

	ArrayList<dataPair> arr;
	dataArr(){
		arr = new ArrayList<dataPair>();
	}
	public void add(dataPair pair) {
		if(arr.size()==0){arr.add(pair);}
		else{
			for(int i =0; i<arr.size();i++){
				int comp = arr.get(i).compareTo(pair);
				if(comp==1||comp==0){
					arr.add(i,pair);
					break;
				}
			}
		}

	}

}

