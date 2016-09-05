package com.example.firstapp;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	TextView name;
	TextView id;
	ImageView myImage;
	Button btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		name = (TextView) findViewById(R.id.nametxt);
		id = (TextView) findViewById(R.id.Idtxt);
		myImage = (ImageView) findViewById(R.id.myfaceimg);
		btn = (Button) findViewById(R.id.btncnt);

		name.setText("Name: Bashar Alhafni");
		id.setText("ID: 0959614");
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.me);
		
		myImage.setImageBitmap(bitmap);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
}
