package com.kidgeniusdesigns.classcompanion.fragments;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.kidgeniusdesigns.classcompanion.FileManager;
import com.kidgeniusdesigns.classcompanion.R;

public class HomeFragment extends ListFragment {
	public static ArrayAdapter<String> mAdapter;

	public HomeFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_home, container,
				false);
		mAdapter = new ArrayAdapter<String>(inflater.getContext(),
				android.R.layout.simple_list_item_1, FileManager.items);
		setListAdapter(mAdapter);
		return rootView;
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(getArguments());
		ListView listView = getListView();

		SwipeDismissListViewTouchListener touchListener =
                new SwipeDismissListViewTouchListener(
                        listView,
                        new SwipeDismissListViewTouchListener.DismissCallbacks() {
                            @Override
                            public boolean canDismiss(int position) {
                                return true;
                            }

                            @Override
                            public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                	String fileData =mAdapter.getItem(position).toString();
                                    mAdapter.remove(fileData);
                                    String fileName= fileData.substring(0, 9);
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
						"FWD: " + mAdapter.getItem(position));
				smsIntent.putExtra("address", "");
				smsIntent.setType("vnd.android-dir/mms-sms");

				startActivity(smsIntent);
				return true;
			}
		});
	}
}
