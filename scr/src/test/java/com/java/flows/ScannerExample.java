package com.scr.responsiblegold.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;

import org.junit.Assert;

public class ScannerExample {

	public static void headerVerify(String response, String expcsvheaders) {

//		String expHeaders[] = expcsvheaders.split(",");

		String content = response;
		Scanner scanner = new Scanner(content);

		scanner.useDelimiter(",");

		while (scanner.hasNext()) {

			String alpha = scanner.nextLine();
			List<String> result = Arrays.asList(alpha.split("\\s*,\\s*"));
			for (String col : result) {
				System.out.println(col.replace("\"", StringUtils.EMPTY));
				if (!expcsvheaders.contains(col.trim().replace("\"", StringUtils.EMPTY))) {
					Assert.assertTrue("Header is not Present :" + col, false);
				}

			}
			break;
		}

		scanner.close();
	}
}
