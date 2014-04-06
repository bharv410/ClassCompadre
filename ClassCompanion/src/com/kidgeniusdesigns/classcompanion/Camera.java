package com.kidgeniusdesigns.classcompanion;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.Toast;

public class Camera extends Activity {
	private static final int CAMERA_REQUEST = 1888;
	private String selectedImagePath;
	WebView webview;
	String fileName = "capturedImage.jpg";
	private static Uri mCapturedImageURI;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_picture);

		
		
		//NOT BEING USED CURRENTLY
		
		
	}

	public void capture(View view) {

		Intent cameraIntent = new Intent(
				android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(cameraIntent, CAMERA_REQUEST);
	}

	@SuppressWarnings("resource")
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		try {
			FileOutputStream fo = new FileOutputStream("placeholder");
			if (resultCode == RESULT_OK) {
				if (requestCode == CAMERA_REQUEST) {
					Bitmap photo = (Bitmap) data.getExtras().get("data");
					ByteArrayOutputStream bytes = new ByteArrayOutputStream();
					photo.compress(Bitmap.CompressFormat.JPEG, 40, bytes);
					Random randomGenerator = new Random();
					randomGenerator.nextInt();
					String newimagename = randomGenerator.toString() + ".jpg";
					File f = new File(Environment.getExternalStorageDirectory()
							+ File.separator + newimagename);

					f.createNewFile();

					fo = new FileOutputStream(f.getAbsoluteFile());
					fo.write(bytes.toByteArray());
					fo.close();

					selectedImagePath = f.getAbsolutePath();

					ImageView myImage = (ImageView) findViewById(R.id.loadImage);
					Bitmap bmImg = BitmapFactory.decodeFile(selectedImagePath);
					myImage.setImageBitmap(bmImg);
					

					// this is the url that where you are saved the image

				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void load(View view){
		Context context = getApplicationContext();
		CharSequence text = "Photo Saved";
		int duration = Toast.LENGTH_SHORT;

		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
	}
}
