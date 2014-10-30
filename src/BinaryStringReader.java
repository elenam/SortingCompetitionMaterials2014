
import java.io.IOException;
import java.io.Reader;
import java.io.BufferedReader;
import java.util.ArrayList;

public class BinaryStringReader extends BufferedReader {
    
   

public BinaryStringReader(Reader in) {
		super(in);
	}

	//this method returns an arraylist with the values zero (0) or one (1). 
	
	//This method is hyperspecific - it only works for streams with binary strings, delimited by
	//newlines (linefeed, 0xA / 10 in decimal) 
	//OR       (linefeed + carriage return, 0xA and 0xD / 10 and 13 in decimal)
   
	public  ArrayList<Long> readLineLongs() throws IOException{
		ArrayList<Long> longs = new ArrayList<Long>();
		Boolean endOfline = false; //the stream is not empty
		
		while (!endOfline) {
			int charCode = this.read();
			switch (charCode) {
			case 48: longs.add((long) 0);break;
			case 49: longs.add((long) 1); break;
			case 10: endOfline = true; break;//we stop on newlines
			case 13: this.read(); break;//we skip carriage return
			case -1: endOfline = true; break; //we stop at the end of the stream
			default: break;
			}	
		}
		return longs;
	}
	




}
