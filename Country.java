package com.inautix.file.csv;

import java.util.HashMap;
import java.util.Map;

public class Country {
	Map<String, String> data = new HashMap<String, String>();
	
	public void add(String key, String value) {
		data.put(key, value);
	}
	
	 @Override
     public String toString() {
         return "CSV [data=" + data + "]";
	}
}
