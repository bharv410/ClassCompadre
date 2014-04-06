package com.kidgeniusdesigns.classcompanion;

import java.util.ArrayList;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.kidgeniusdesigns.classcompanion.fragments.DatePickerFragmentCal;
import com.kidgeniusdesigns.classcompanion.fragments.TimePickerFragment;

public class AddNewItemActivity extends Activity {

	EditText assignmentName, assignmentDescrip;
	String classtitle, classType;
	ArrayList<String> specificClassFiles;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_add_assignments);
		assignmentName = (EditText) findViewById(R.id.assignmentName);
		Bundle extras = getIntent().getExtras();

		// add to main screen or to specific class?
		if (extras != null) {
			classtitle = extras.getString("classname");
			classType = classtitle;
		} else {
			classtitle = "main";
			classType = "mn1";
		}
		specificClassFiles = FileManager.readClassFiles(classType);

		// Automatically open keyboard
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(assignmentName, InputMethodManager.SHOW_IMPLICIT);
	}

	public void addItem(View V) {
		// get the Entered message
		String item = assignmentName.getText().toString();

		FileManager.saveEntry(item, classType);

		// Debugging toast
		Toast.makeText(AddNewItemActivity.this, "Add new item: " + item,
				Toast.LENGTH_LONG).show();
		Intent toMain = new Intent(this, MainActivity.class);
		startActivity(toMain);

	}

	public void showDatePickerDialog(View v) {
		DialogFragment newFragment = new DatePickerFragmentCal();
		newFragment.show(getFragmentManager(), "datePicker");
	}

	public void showTimePickerDialog(View v) {
		DialogFragment newFragment = new TimePickerFragment();
		newFragment.show(getFragmentManager(), "timePicker");
		// set text
		// EditText dateChecker = (EditText)findViewById(R.id.editText1);
		// dateChecker.setText(""+day+ "-" + month+"-"+year);
	}

}
