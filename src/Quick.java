// Dalton Gusaas && Aaron Lemmon

public class Quick implements Sorter {

	@Override
	public void sort(EnhancedString[] array) {
		quickSort(array, 0, array.length - 1);
	}

	// endIndex is inclusive
	private static void quickSort(EnhancedString[] array, int lowIndex, int highIndex) {
		if (lowIndex < highIndex) {
			int pivot = partition(array, lowIndex, highIndex);
			quickSort(array, lowIndex, pivot - 1);
			quickSort(array, pivot + 1, highIndex);
		}
	}

	private static int partition(EnhancedString[] array, int lowIndex, int highIndex) {
		EnhancedString key = array[highIndex]; // Takes pivot from end of section.
		int i = lowIndex - 1;
		for (int j = lowIndex; j < highIndex; j++) {
			if (array[j].compareTo(key) <= 0) {
				i++;
				EnhancedString temp = array[i];
				array[i] = array[j];
				array[j] = temp;
			}
		}
		array[highIndex] = array[i + 1];
		array[i + 1] = key;
		return i + 1;
	}
}
