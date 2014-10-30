import java.util.ArrayList;

public class BinaryString implements Comparable<BinaryString> {

	ArrayList<Long> content;
	//because longs don't need to be cast for the processor 
	//(ints have to be cast to 64 bit numbers on the lab boxes)
	boolean isCounted = false;
	long numOnes;
	
	public BinaryString(ArrayList<Long> l) {
		content = l;
	}
	
	public int compareTo(BinaryString b){
		if (b.content.size() < this.content.size()){
			return 1;
		} 
		if (b.content.size() > this.content.size()){
			return -1;
		}
		// if b.length = this.length
		if (b.getNum() < this.getNum()){
			return 1;
		}
		if (b.getNum() > this.getNum()){
			return -1;
		}
		// if b.num = this.num
		return this.alphabetize(b);
	}
	
	private int alphabetize(BinaryString b) {
		for (int i = 0; i < this.content.size(); i++){
			if(this.content.get(i) > b.content.get(i)){
				return 1;
			}
			if (this.content.get(i) < b.content.get(i)){
				return -1;
			}
		}
		return 0;
	}
	
	private long getNum() {
		if (isCounted) {
			return numOnes;
		}
		for (int i = 0; i < content.size(); i++) {
			if (this.content.get(i) == 1){
				this.numOnes++;
			}
			this.isCounted = true;
		}
		return numOnes;
	}
}
