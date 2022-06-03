package com.coconut.tl;

import java.io.IOException;
import java.io.InputStream;

import org.json.JSONObject;

public class SaveLoader {

	public JSONObject saveData = new JSONObject();

	public void saveGameData() {
		if (Main.select == null)
			System.out.println("stage select state is null!");

		JSONObject _res = new JSONObject();
		_res.put("CLEAR", new JSONObject());

		for (int i = 0; i < 5; i++)
			_res.getJSONObject("CLEAR").put(i + "", Main.select.stageCleared[i]);
		
	}

	public void readSaveFile() {
//		System.out.println(ClassLoader.getSystemResourceAsStream("savefile.txt"));
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
