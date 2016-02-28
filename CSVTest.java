package com.inautix.file.csv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVTest {
	private static String DELIMITER = ",";
    private static String csvFile ="C:\\work\\GeoIPCountryWhois.csv";

	public static void main(String[] args) {
		String[] header = null;
		String[] colm = null;
		List<Country> countries = new ArrayList<Country>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(csvFile)));
			String line= "";
			if((line=br.readLine())!=null) {
				header = line.split(DELIMITER);
			}
			for(String str : header) {
				System.out.println(str);
			}
			
			while((line=br.readLine())!=null) {
				String[] cells = line.split(DELIMITER);
				Country country = new Country();
				int i=0;
				for(String cell : cells) {
					country.add(header[i], cell);
				}
				countries.add(country);
			}
			
			System.out.println(countries);
			System.out.println(countries.size());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
		}
	}

}
