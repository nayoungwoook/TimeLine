package com.coconut.tl;

import java.io.File;
import java.io.FileOutputStream;
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

	public void writeSaveFile() {
		String str = "test";
		byte[] bytes = str.getBytes();
		File file = new File("res/savefile.txt");
		File aFile = new File(file.getAbsolutePath());
		System.out.println(aFile.getPath());
		try {

			FileOutputStream outputStream = new FileOutputStream(aFile);
			outputStream.write(bytes);
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
