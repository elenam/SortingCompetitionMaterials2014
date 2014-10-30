import java.util.ArrayList;


public class BinString implements Comparable<BinString>{

	private final String s;
	private ArrayList<Integer> indices = new ArrayList<Integer>();
	private final int size;
	private int sum =0;
	
	public BinString(String s){
		this.s = s;
		size = s.length();
		for(int i =0;i<s.length();i++){
			if(s.charAt(i)=='1'){
				sum++;
				indices.add(i);
			}
		}
	}

	@Override
	public int compareTo(BinString o) {
		if(this.size!=o.size){
			return this.size-o.size;
		}
		if(this.sum!=o.sum){
			return this.sum-o.sum;
		}
		for(int i =0;i<indices.size();i++){
			if(this.indices.get(i)!=o.indices.get(i)){
				return o.indices.get(i)-this.indices.get(i);
			}
		}
		return 0;
	}
	
	@Override
	public String toString(){
		return s;
	}
}
