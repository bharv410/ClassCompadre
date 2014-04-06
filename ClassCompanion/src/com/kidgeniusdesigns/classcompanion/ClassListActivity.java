package com.kidgeniusdesigns.classcompanion;

import android.app.DialogFragment;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.kidgeniusdesigns.classcompanion.fragments.SwipeDismissListViewTouchListener;
import com.kidgeniusdesigns.classcompanion.fragments.TimePickerFragment;

public class ClassListActivity extends ListActivity {
	public ArrayAdapter<String> mAdapter;
	EditText className;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.classlist);
		FileManager.savedClassNames = FileManager.readClassFiles("clzz");
		FileManager.savedClassNames.add("Class1");
		FileManager.savedClassNames.add("Class2");
		FileManager.savedClassNames.add("Add more below");
		FileManager.savedClassNames.add("Click me");
		className = (EditText) findViewById(R.id.className);

		mAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1,
				FileManager.savedClassNames);

		setListAdapter(mAdapter);

		ListView listView = getListView();
		// Create a ListView-specific Swipe listener.
		SwipeDismissListViewTouchListener touchListener = new SwipeDismissListViewTouchListener(
				listView,
				new SwipeDismissListViewTouchListener.DismissCallbacks() {
					@Override
					public boolean canDismiss(int position) {
						return true;
					}

					@Override
					public void onDismiss(ListView listView,
							int[] reverseSortedPositions) {
						for (int position : reverseSortedPositions) {
							String fileName = mAdapter.getItem(position);
							mAdapter.remove(fileName);
							FileManager.deleteFile(fileName);

						}
						mAdapter.notifyDataSetChanged();
					}
				});
		listView.setOnTouchListener(touchListener);
		// Setting this scroll listener is required to ensure that during
		// ListView scrolling,
		// we don't look for swipes.
		listView.setOnScrollListener(touchListener.makeScrollListener());

		listView.setLongClickable(true);
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> parent, View v,
					int position, long id) {
				Intent smsIntent = new Intent(Intent.ACTION_VIEW);
				smsIntent.putExtra("sms_body",
						"ToDo Item: " + mAdapter.getItem(position));
				smsIntent.putExtra("address", "");
				smsIntent.setType("vnd.android-dir/mms-sms");

				startActivity(smsIntent);
				return true;
			}
		});

	}

	public void showTimePickerDialog(View v) {
		DialogFragment newFragment = new TimePickerFragment();
		newFragment.show(getFragmentManager(), "timePicker");
		// set text
		EditText dateChecker = (EditText) findViewById(R.id.editText1);
		// dateChecker.setText(""+day+ "-" + month+"-"+year);
	}

	// Method to handle the Click Event on GetMessage Button
	public void addClass(View V) {
		FileManager.saveEntry(className.getText().toString(), "clzz");
		mAdapter.add(className.getText().toString());
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);

		String title = getListView().getItemAtPosition(position).toString();
		Intent viewAssignments = new Intent(this, SpecificClassActivity.class);
		viewAssignments.putExtra("classname", title);
		startActivity(viewAssignments);
	}

}
