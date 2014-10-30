import java.util.Comparator;

public class EverythingComparator implements Comparator<EnhancedString> {

	@Override
	public int compare(EnhancedString o1, EnhancedString o2) {
		int lengthDifference = o1.length - o2.length;
		if (lengthDifference != 0) {
			return lengthDifference;
		}
		int sumOfOnesDifference = o1.sumOfOnes - o2.sumOfOnes;
		if (sumOfOnesDifference != 0) {
			return sumOfOnesDifference;
		}
		return o1.binaryString.compareTo(o2.binaryString);
	}
}