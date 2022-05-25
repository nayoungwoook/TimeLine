package com.coconut.tl;

import java.io.IOException;
import java.io.InputStream;

import org.json.JSONObject;

public class SaveLoader {
	
	public JSONObject saveData = new JSONObject();

	public void readSaveFile() {
		InputStream is = ClassLoader.getSystemResourceAsStream("savefile.txt");
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

		saveData = new JSONObject(_read);
	}

}
