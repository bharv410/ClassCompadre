package com.kidgeniusdesigns.classcompanion.fragments;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.widget.DatePicker;

public class DatePickerFragmentCal extends DialogFragment implements DatePickerDialog.OnDateSetListener {


public DatePickerFragmentCal() {
	}

@Override
public Dialog onCreateDialog(Bundle savedInstanceState) {
    // Use the current date as the default date in the picker
    final Calendar c = Calendar.getInstance();
    int year = c.get(Calendar.YEAR);
    int month = c.get(Calendar.MONTH);
    int day = c.get(Calendar.DAY_OF_MONTH);

    // Create a new instance of DatePickerDialog and return it
    return new DatePickerDialog(getActivity(), this, year, month, day);
}

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
    	
    	Calendar cal = new GregorianCalendar();
    	cal.setTime(new Date());
    	cal.add(Calendar.YEAR,  year+27);
    	cal.add(Calendar.MONTH, month+3);
    	cal.add(Calendar.DAY_OF_MONTH, day+6);
    	long time = cal.getTime().getTime();
		Uri.Builder builder = CalendarContract.CONTENT_URI.buildUpon();
		builder.appendPath("time");
		builder.appendPath(Long.toString(time));
		Intent intent = new Intent(Intent.ACTION_VIEW, builder.build());
		startActivity(intent);
    }
}