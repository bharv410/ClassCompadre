package com.kidgeniusdesigns.classcompanion;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class ChoosePictureActivity extends Activity {
	private static final int SELECT_PICTURE = 1;
	private String selectedImagePath;
	private ImageView img;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_picture);
		img = (ImageView) findViewById(R.id.loadImage);
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		startActivityForResult(intent, SELECT_PICTURE);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			if (requestCode == SELECT_PICTURE) {
				Uri selectedImageUri = data.getData();
				selectedImagePath = getPath(selectedImageUri);
				try {
					FileInputStream fileis = new FileInputStream(
							selectedImagePath);
					BufferedInputStream bufferedstream = new BufferedInputStream(
							fileis);
					byte[] bMapArray = new byte[bufferedstream.available()];
					bufferedstream.read(bMapArray);
					Bitmap bMap = BitmapFactory.decodeByteArray(bMapArray, 0,
							bMapArray.length);
					// this is the image that you are choosen
					img.setImageBitmap(bMap);

					if (fileis != null) {
						fileis.close();
					}
					if (bufferedstream != null) {
						bufferedstream.close();
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public String getPath(Uri uri) {
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}

	public void load(View view) {
		Context context = getApplicationContext();
		CharSequence text = "Photo Saved!";
		int duration = Toast.LENGTH_SHORT;

		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
	}
}