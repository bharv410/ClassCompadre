package com.kidgeniusdesigns.classcompanion;

import info.androidhive.slidingmenu.adapter.NavDrawerListAdapter;
import info.androidhive.slidingmenu.model.NavDrawerItem;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.kidgeniusdesigns.classcompanion.fragments.DatePickerFragmentCal;
import com.kidgeniusdesigns.classcompanion.fragments.HomeFragment;

public class MainActivity extends Activity {
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	public static int year, month, day,hour1, min1;
	static Context context;
	public static File dir;
	public static String[] navMenuTitles,allFiles;
	private ArrayList<NavDrawerItem> navDrawerItems;
	private static NavDrawerListAdapter adapter;
	public static Calendar cal;
	public ImageView myImage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setUpNavDrawer(savedInstanceState);
		dir = getFilesDir();
		allFiles = fileList();
		int i = 1;
		for (String file : allFiles) {
			Log.i("file" + i, file);
			i++;
		}
		context = getApplicationContext();
		FileManager.readFilesFromDirectory();
	}

	public static File getDir() {
		return dir;
	}

	public static Context getAppContext() {

		return context;
	}

	public void addNewItem(View V) {

		Intent intentAddNewItem = new Intent(this, AddNewItemActivity.class);
		startActivityForResult(intentAddNewItem, 2);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (null != data) {
			String item = data.getStringExtra("ITEM");
			FileManager.saveEntry(item, "mn1");
		}
	}

	public void goToDate(View view) {

		cal = new GregorianCalendar();
		DialogFragment newFragment = new DatePickerFragmentCal();
		newFragment.show(getFragmentManager(), "datePicker");

	}

	public void addEvent(View view) {

		// all version of android
		Intent i = new Intent();

		// mimeType will popup the chooser any for any implementing application
		// (e.g. the built in calendar or applications such as
		// "Business calendar"
		i.setType("vnd.android.cursor.item/event");

		// the time the event should start in millis. This example uses now as
		// the start time and ends in 1 hour
		i.putExtra("beginTime", new Date().getTime());
		i.putExtra("endTime", new Date().getTime() + DateUtils.HOUR_IN_MILLIS);

		// the action
		i.setAction(Intent.ACTION_EDIT);
		startActivity(i);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	private void setUpNavDrawer(Bundle savedInstanceState) {
		mTitle = getTitle();
		mDrawerTitle = "Menu";
		// load slide menu items
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
		navDrawerItems = new ArrayList<NavDrawerItem>();
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[0]));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[1]));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[2]));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], true, "22"));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[4]));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], true, "50+"));
		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());
		adapter = new NavDrawerListAdapter(getApplicationContext(),
				navDrawerItems);
		mDrawerList.setAdapter(adapter);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, // nav menu toggle icon
				R.string.app_name, // nav drawer open - description for
									// accessibility
				R.string.app_name // nav drawer close - description for
									// accessibility
		) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			// on first time display view for first nav item
			displayView(0);
		}
	}

	/**
	 * Slide menu item click listener
	 * */
	private class SlideMenuClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// display view for selected nav drawer item
			displayView(position);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action bar actions click
		switch (item.getItemId()) {
		case R.id.action_settings:
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	private void displayView(int position) {
		// update the main content by replacing fragments
		Fragment fragment = null;
		switch (position) {
		case 0:
			fragment = new HomeFragment();
			break;
		case 1:
			Intent intent = new Intent(this, ClassListActivity.class);
			startActivity(intent);
			break;
		case 2:
			Intent intent2 = new Intent(this, AddNewItemActivity.class);
			startActivity(intent2);
			break;
		case 3:
			fragment = new AllFilesFragment();
			break;
		case 4:
			Intent intent3 = new Intent(this, ChoosePictureActivity.class);
			startActivity(intent3);
			break;
		case 5:
			fragment = new AllFilesFragment();
			break;

		default:
			break;
		}

		if (fragment != null) {
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.frame_container, fragment).commit();

			// update selected item and title, then close the drawer
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			setTitle(navMenuTitles[position]);
			mDrawerLayout.closeDrawer(mDrawerList);
		} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

}
