// Dalton Gusaas && Aaron Lemmon

import java.util.Comparator;


public class StringComparator implements Comparator<String>{


		
		public int compare(String str1, String str2) {
			if (str1.length() != str2.length()) {
				return str1.length() - str2.length();
			}
			
			// only get here if the lengths are equal
			int sum1 = sumOnes(str1);
			int sum2 = sumOnes(str2);
			if (sum1 != sum2) {
				return (sum1 - sum2);
			}
			
			// only get here if the sum and the length are equal
			return str1.compareTo(str2);
		}
		
		private int sumOnes(String str) {
			int count = 0;
			int chunks = (str.length() + 30)/31; // Basically divides by 32 and rounds up
			for (int i = 0; i < chunks; i++) {
				int endIndex = Math.min(i*31 + 31, str.length());
				String subString = str.substring(i*31, endIndex);
				count += Integer.bitCount(parseUnsignedInt(subString));
			}
			return count;
		}
		
		public static int parseUnsignedInt(String s) {
			int result = 0;
			int length = s.length();
			int i = 0;
			while (i < length) {
				int digit = s.charAt(i) - '0';
				result <<= 1;
				result += digit;		
				i++;
			}
			return result;
		}

}
		
		
//		private int sumOnes(String str) {
//			int count = 0;
//			for (int i = 0; i < str.length(); ++i) {
//				count += str.charAt(i) - '0';
//			}
//			return count;
//		}

//}
