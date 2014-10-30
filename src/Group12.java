// Henry Fellows, Nic Ricci

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Group12 {

	static double lambda = 0.0;
	
	public static void main(String[] args) throws FileNotFoundException {		
		//having the correct number of args.

		if (args.length < 3) {
			System.out.println("Please run with three command line arguments: input and output file names, and the number of loops.");
			System.exit(0);
		}
		//making the current args into vars.
		String inFileName = args[0];
		String outFileName = args[1];
		String sLoops = args[2];
		int loops = Integer.valueOf(sLoops);
		BinaryString[] origin = readInData(inFileName);
		
		// copy/sort loop
		long time1 =  System.currentTimeMillis();
		for (int i = 0; i < loops; i++) {
			BinaryString[] cpy = makeCopy(origin);
			MergeSorter.sort(cpy);
			
			if (i == (loops - 1)){
				long time2 = System.currentTimeMillis();
				System.out.println(time2 - time1);
				writeOutResult(cpy, outFileName);
			}
		}		
	}
	
	private static BinaryString[] makeCopy(BinaryString[] origin) {
		BinaryString[] cpy = new BinaryString[origin.length];
		for (int i = 0; i < origin.length; i++) {
			//we only copy the content of the origin - we never 
			//get the other fields (because they contain information.)
			cpy[i] = new BinaryString(origin[i].content);
		}
		return cpy;
	}

	private static BinaryString[] readInData(String FileName) {
		//Read in the data;
		BinaryStringReader reader = null;
		ArrayList<BinaryString> BST = new ArrayList<BinaryString>();
		
		try {
		    File file = new File(FileName);
		    reader = new BinaryStringReader(new FileReader(file));
		    reader.readLine(); //lambda was never removed from the file, but now we throw it out.
		    while (reader.ready()) {
		    	BinaryString B = new BinaryString(reader.readLineLongs());
		    	BST.add(B);
		    }
		} catch (IOException e) {
		    e.printStackTrace();
		} finally {
		    try {
		        reader.close();
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		}
		
		return BST.toArray(new BinaryString[BST.size()]);
	}
	
	private static void writeOutResult(BinaryString[] sorted, String outputFilename) {
		try {
			PrintWriter out = new PrintWriter(outputFilename);
			for (BinaryString str: sorted) {
				for (int i = 0; i < str.content.size(); i++) {
					out.print(str.content.get(i));
				}
				out.println("");
			}
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
}
