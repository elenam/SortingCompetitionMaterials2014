// Dalton Gusaas && Aaron Lemmon

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Group4 {

	private static String inputFile;
	private static String outputFile;
	private static int numberOfLoops;
	private static double lambda;
	private static int n = 0;
	private static ArrayList<String> rawInput = new ArrayList<String>();
	private static EnhancedString[] data;
	private static Sorter algorithm;

	public static void main(String[] args) throws IOException, InterruptedException {

		// Store command-line args
		inputFile = args[0];
		outputFile = args[1];
		numberOfLoops = Integer.parseInt(args[2]);

		// Get the input from the file
		Path inFile = Paths.get(inputFile);
		try (BufferedReader reader = Files.newBufferedReader(inFile, StandardCharsets.UTF_8)) {
			lambda = Double.parseDouble(reader.readLine());
			String line = null;
			while ((line = reader.readLine()) != null) {
				rawInput.add(line);
				n++;
			}
		}

		if (lambda >= 3) {
			algorithm = new Quick();
		} else if (lambda <= 1) {
			algorithm = new Quick3();
		} else if (n <= 40000) {
			algorithm = new Quick();
		} else {
			algorithm = new Quick3();
		}

		data = new EnhancedString[n];
		Thread.sleep(10);

		long startTime = System.currentTimeMillis();

		// Runs the algorithm numberOfLoops times.
		for (int loop = 0; loop < numberOfLoops; loop++) {
			// Copies in the strings.
			for (int i = rawInput.size() - 1; i >= 0; i--) {
				data[i] = new EnhancedString(rawInput.get(i));
			}

			// The chosen algorithm sorts the data.
			algorithm.sort(data);
		}
		long endTime = System.currentTimeMillis();
		System.out.println(endTime - startTime);

		Path outFile = Paths.get(outputFile);
		try (BufferedWriter writer = Files.newBufferedWriter(outFile, StandardCharsets.UTF_8)) {
			for (EnhancedString line : data) {
				writer.write(line.binaryString);
				writer.newLine();
			}
		}
	}
}
