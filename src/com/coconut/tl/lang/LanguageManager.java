package com.coconut.tl.lang;

import java.io.IOException;
import java.io.InputStream;

import org.json.JSONObject;

public class LanguageManager {

	public JSONObject langData = new JSONObject();

	public void readLangFile(String path) {
		InputStream is = ClassLoader.getSystemResourceAsStream(path);
		String _read = "";
		int index = 0;

		try {
			
			while ((index = is.read()) != -1) {
				_read += (char) index;
			}

			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		langData = new JSONObject(_read);
	}
 
}
