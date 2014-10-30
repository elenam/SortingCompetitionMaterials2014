// Dalton Gusaas && Aaron Lemmon

public class EnhancedString implements Comparable<EnhancedString> {

	public String binaryString;
	public int length;
	public int sumOfOnes;

	public EnhancedString(String bs) {
		binaryString = bs;
		length = bs.length();
		for (int i = 0; i < bs.length(); i++) {
			sumOfOnes += bs.charAt(i) - '0';
		}
	}

	@Override
	public int compareTo(EnhancedString other) {
		int lengthDifference = this.length - other.length;
		if (lengthDifference != 0) {
			return lengthDifference;
		}
		int sumOfOnesDifference = this.sumOfOnes - other.sumOfOnes;
		if (sumOfOnesDifference != 0) {
			return sumOfOnesDifference;
		}
		return this.binaryString.compareTo(other.binaryString);
	}
}
