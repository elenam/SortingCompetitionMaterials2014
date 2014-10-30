// Dalton Gusaas && Aaron Lemmon

public class Quick3 implements Sorter {

	public void sort(EnhancedString[] array) {
		quick3Sort(array, 0, array.length - 1);
	}

	// highIndex is inclusive
	private static void quick3Sort(EnhancedString[] array, int lowIndex, int highIndex) {
		if (highIndex > lowIndex) {
			int lesserIndex = lowIndex;
			int greaterIndex = highIndex;
			EnhancedString key = array[lowIndex];
			int i = lowIndex;
			while (i <= greaterIndex) {
				int comparison = array[i].compareTo(key);
				if (comparison < 0) {
					EnhancedString temp = array[lesserIndex];
					array[lesserIndex++] = array[i];
					array[i++] = temp;
				} else if (comparison > 0) {
					EnhancedString temp = array[i];
					array[i] = array[greaterIndex];
					array[greaterIndex--] = temp;
				} else {
					i++;
				}
			}

			quick3Sort(array, lowIndex, lesserIndex - 1);
			quick3Sort(array, greaterIndex + 1, highIndex);
		}
	}
}
