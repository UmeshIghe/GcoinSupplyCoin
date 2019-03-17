package com.mastercard.driver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ScannerExample {
	public static void main(String[] args) throws IOException {
	
  //Here content should be response , for trial i have given file path this has to be removed
		String content = new String(Files.readAllBytes(Paths.get("C:\\\\Users\\\\e070376\\\\eclipse-workspace\\\\TechnicalWriter\\\\src\\\\main\\\\resources\\\\chromeDriver\\\\response.csv")));
		// Get scanner instance
		Scanner scanner = new Scanner(content);

		// Set the delimiter used in file
		scanner.useDelimiter(",");

		// Get all tokens and store them in some data structure
		// I am just printing them
		while (scanner.hasNext()) {
//			System.out.print(scanner.nextLine());
			String alpha = scanner.nextLine();
			List<String> result = Arrays.asList(alpha.split("\\s*,\\s*"));
      //REsult holds all the header
			for (String col : result) {
				System.out.println(col);
			}
			break;
		}

		// Do not forget to close the scanner
		scanner.close();
	}
}
