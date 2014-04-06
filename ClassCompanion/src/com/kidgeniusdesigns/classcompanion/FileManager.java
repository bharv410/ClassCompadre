package com.kidgeniusdesigns.classcompanion;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.kidgeniusdesigns.classcompanion.fragments.HomeFragment;

public class FileManager {
	public static String[] savedFiles;
	public static ArrayList<String> savedClassNames;
	public static ArrayList<String> items, specifics;
	public static File dir;
	public static Context mainContext;

	public FileManager() {

	}

	public static void saveEntry(String fileData, String type) {
		try {
			String fileName = fileData;

			fileName = fileName + type + ".txt";
			HomeFragment.mAdapter.add(fileData);

			FileOutputStream fOut = mainContext.openFileOutput(fileName,
					Context.MODE_PRIVATE);
			OutputStreamWriter osw = new OutputStreamWriter(fOut);
			// Write the string to the file
			osw.write(fileData);

			osw.flush();
			osw.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

	}

	public static void readFilesFromDirectory() {
		try {
			items = new ArrayList<String>();

			items.add("sliding menu");
			items.add("hold2text/ swipe delete");
			dir = MainActivity.getDir();
			savedFiles = dir.list();
			mainContext = MainActivity.getAppContext();
			savedClassNames = new ArrayList<String>();

			for (int i = 0; i < FileManager.savedFiles.length; i++) {

				File currentFile = new File(dir, savedFiles[i]);
				if (currentFile.getName().contains("mn1")) {
					items.add(readFromFile(currentFile));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<String> readClassFiles(String type) {
		specifics = new ArrayList<String>();
		try {

			dir = MainActivity.getDir();
			savedFiles = dir.list();
			mainContext = MainActivity.getAppContext();
			savedClassNames = new ArrayList<String>();

			for (int i = 0; i < FileManager.savedFiles.length; i++) {

				File currentFile = new File(dir, savedFiles[i]);
				if (currentFile.getName().contains(type)) {
					specifics.add(readFromFile(currentFile));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return specifics;
	}

	private static String readFromFile(File fileName) {

		try {
			FileInputStream fIn = mainContext.openFileInput(fileName.getName());
			InputStreamReader isr = new InputStreamReader(fIn);

			/*
			 * Prepare a char-Array that will hold the chars we read back
			 * in.LIMIT IS 75 CHARACTERS
			 */
			char[] inputBuffer = new char[75];

			// Fill the Buffer with data from the file
			isr.read(inputBuffer);

			// Transform the chars to a String
			String readString = new String(inputBuffer);
			return readString;

		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return "";
	}

	public static void deleteFile(String fileName) {

		for (int i = 0; i < FileManager.savedFiles.length; i++) {

			File currentFile = new File(dir, savedFiles[i]);
			if (currentFile.getName().contains(fileName)) {
				Boolean deleted = currentFile.delete();
				Log.i("deleted?", deleted.toString());
			}
		}

		Toast.makeText(mainContext, "permanently deleted", Toast.LENGTH_SHORT);

	}

}
