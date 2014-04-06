package com.kidgeniusdesigns.classcompanion;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract.Events;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

public class SpecificClassActivity extends ListActivity {
	public static ArrayAdapter<String> mAdapter;
	public static ArrayList<String> specifics;
	String classname;
	RadioButton tth, mwf;
	Boolean onTth = false, onMwf = false;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.specifics);
		Bundle extras = getIntent().getExtras();
		TextView title = (TextView) findViewById(R.id.classlist1);
		classname = extras.getString("classname");
		title.setText(classname);
		tth = (RadioButton) findViewById(R.id.tth);
		mwf = (RadioButton) findViewById(R.id.mwf);
		specifics = FileManager.readClassFiles(classname);
		specifics.add("assignment1");
		specifics.add("assignment2");
		specifics.add("add more below");

		if (tth.isChecked())
			onTth = true;
		if (mwf.isChecked())
			onMwf = true;
		mAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, specifics);
		setListAdapter(mAdapter);
		ListView listView = getListView();
		listView.setLongClickable(true);
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> parent, View v,
					int position, long id) {
				Calendar cal = Calendar.getInstance();

				// all version of android
				Intent i = new Intent();

				// mimeType will popup the chooser any for any implementing
				// application (e.g. the built in calendar or applications such
				// as "Business calendar"
				i.setType("vnd.android.cursor.item/event");

				// the time the event should start in millis
				i.putExtra("beginTime", cal.getTimeInMillis());
				i.putExtra("endTime", cal.getTimeInMillis()
						+ DateUtils.HOUR_IN_MILLIS);
				i.putExtra(Events.TITLE, mAdapter.getItem(position));
				i.putExtra("allDay", false);
				if (onTth)
					i.putExtra(Events.RRULE,
							"FREQ=WEEKLY;COUNT=10;WKST=SU;BYDAY=TU,TH");

				if (onMwf)
					i.putExtra(Events.RRULE,
							"FREQ=WEEKLY;COUNT=10;WKST=SU;BYDAY=MO,WE,FR");

				// the action
				i.setAction(Intent.ACTION_EDIT);
				startActivity(i);
				return true;
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.specifics, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void addNewItem(View V) {

		Intent intentAddNewItem = new Intent(this, AddNewItemActivity.class);

		intentAddNewItem.putExtra("classname", classname);
		startActivity(intentAddNewItem);
	}

	public void saveToCal(View view) {
		Calendar cal = Calendar.getInstance();

		// all version of android
		Intent i = new Intent();

		// mimeType will popup the chooser any for any implementing application
		// (e.g. the built in calendar or applications such as
		// "Business calendar"
		i.setType("vnd.android.cursor.item/event");

		// the time the event should start in millis
		i.putExtra("beginTime", cal.getTimeInMillis());
		i.putExtra("endTime", cal.getTimeInMillis() + DateUtils.HOUR_IN_MILLIS);
		i.putExtra(Events.TITLE, classname);
		i.putExtra("allDay", false);
		if (onTth)
			i.putExtra(Events.RRULE, "FREQ=WEEKLY;COUNT=10;WKST=SU;BYDAY=TU,TH");

		if (onMwf)
			i.putExtra(Events.RRULE,
					"FREQ=WEEKLY;COUNT=10;WKST=SU;BYDAY=MO,WE,FR");

		// the action
		i.setAction(Intent.ACTION_EDIT);
		startActivity(i);

	}

}
