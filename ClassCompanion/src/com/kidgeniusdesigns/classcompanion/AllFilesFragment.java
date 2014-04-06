package com.kidgeniusdesigns.classcompanion;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AllFilesFragment extends ListFragment {
	EditText fileDataEditText;
	EditText fileNameEditText;
	Button button;
	TextView textView;
	BufferedReader br = null;
	String fileStr, theFileName, returnPath, returnString;
	File path;
	ArrayList<String> all;
	public ArrayAdapter<String> mAdapter;
	LayoutInflater inflater;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.inflater=inflater;
		View rootView = inflater.inflate(R.layout.activity_file, container,
				false);

		
		//merge main files and specific class files
		all=FileManager.items;
		ArrayList<String> others =FileManager.readClassFiles("");
		Iterator<String> it = others.iterator();
		while(it.hasNext())
			all.add((it.next().toString()));
		
		 mAdapter = new ArrayAdapter<String>(inflater.getContext(),
	                android.R.layout.simple_list_item_1,
	                android.R.id.text1,
	                all);
	        
	        
	        setListAdapter(mAdapter);
	        return rootView;

	}

	public void writeToFile(View view) {
		fileStr = fileDataEditText.getText().toString();
		fileDataEditText.setEnabled(false);

		FileOutputStream outputStream;

		try {
			outputStream = inflater.getContext().openFileOutput(theFileName, Context.MODE_PRIVATE);
			outputStream.write(fileStr.getBytes());
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void readTheFile(View view) {

		try {
			// Reading the file back...
			FileInputStream inputStream = inflater.getContext().openFileInput(theFileName);
			InputStreamReader isreader = new InputStreamReader(inputStream);

			char[] inputBuffer = new char[fileStr.length()];

			isreader.read(inputBuffer);

			// Transform the char Array to a String
			String readString = new String(inputBuffer);

		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public static Boolean pathExists(String path, Context ctx) {
		Boolean result = false;
		String[] pathSeqments = path.split("/");
		String pathStr = "";
		for (int i = 0; i < pathSeqments.length; i++) {
			pathStr += pathSeqments[i];
			if (!new File(ctx.getFilesDir() + "/" + pathStr).exists()) {
				result = false;
				break;
			}
			pathStr += "/";
			result = true;
		}
		return result;
	}
}