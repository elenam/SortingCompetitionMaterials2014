import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class Group14 {

	/**
	 * @param args
	 * @throws IOException 
	 */
	
	//This is Tim Snyder's submission.
	public static void main(String[] args) throws IOException {
		String inputFile = args[0];
		String outputFile = args[1];
		int loops = Integer.parseInt(args[2]);
		
		File in = new File(inputFile);
		File out = new File(outputFile);
		ArrayList<String> input = new ArrayList<String>();
		Scanner scan = new Scanner(in);
		scan.nextLine();
		while(scan.hasNext()){
			input.add(scan.nextLine());
		}
		scan.close();
		ArrayList<BinString> list = null;
		double start = System.currentTimeMillis();
		for(int i =0;i<loops;i++){
			list = new ArrayList<BinString>();
			for(String s:input){
				list.add(new BinString(s));
			}
			Collections.sort(list);
		}
		double end = System.currentTimeMillis();
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(out));
		for(int i =0;i<list.size();i++){
			writer.write(list.get(i).toString()+"\r\n");
		}
		writer.flush();
		writer.close();
		System.out.println(end-start);
	}

}
