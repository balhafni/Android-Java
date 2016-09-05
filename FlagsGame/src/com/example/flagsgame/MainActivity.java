package com.example.flagsgame;

import java.util.ArrayList;
import org.w3c.dom.Text;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView.FindListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.FrameLayout.LayoutParams;

public class MainActivity extends Activity {
	String[] regionsArray = { "Africa", "Asia", "Europe", "North America", "South America" };
	ArrayList<Integer> selectedRegions = new ArrayList<Integer>();
	ImageView me;
	TextView txt1, txt2, txt3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		me = (ImageView) findViewById(R.id.me);
		txt1 = (TextView) findViewById(R.id.hw);
		txt2 = (TextView) findViewById(R.id.myName);
		txt3 = (TextView) findViewById(R.id.ID);
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int width = dm.widthPixels;
		int height = dm.heightPixels;

		LayoutParams pp = new LayoutParams((int) (width), (int) (height));
		pp.setMargins((int) (width * .15), (int) (height * .010), 0, 0);
		txt1.setLayoutParams(pp);

		pp = new LayoutParams((int) (width), (int) (height));
		pp.setMargins((int) (width * .28), (int) (height * .10), 0, 0);
		txt2.setLayoutParams(pp);

		pp = new LayoutParams((int) (width), (int) (height));
		pp.setMargins((int) (width * .35), (int) (height * .20), 0, 0);
		txt3.setLayoutParams(pp);

		pp = new LayoutParams((int) (width * 0.3), (int) (height * 0.3));
		pp.setMargins((int) (width * .3), (int) (height * .25), 0, 0);
		me.setLayoutParams(pp);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.regions, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.Regions) {
			creatingAlertDialog();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void creatingAlertDialog() {
		AlertDialog dialog = new AlertDialog.Builder(this).setTitle("Select a Region")
				.setMultiChoiceItems(regionsArray, null, new DialogInterface.OnMultiChoiceClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which, boolean isChecked) {
						if (isChecked) {
							// If the user checked the item, add it to the
							// selected items
							selectedRegions.add(which);

						} else if (selectedRegions.contains(which)) {
							// Else, if the item is already in the array, remove
							// it
							selectedRegions.remove(Integer.valueOf(which));
						}
					}
				}).setPositiveButton("Start", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						if (selectedRegions.isEmpty()) {

							Toast.makeText(getApplicationContext(), "Please Select a Region", Toast.LENGTH_LONG).show();
							creatingAlertDialog();

						} else {
							Intent intent = new Intent(MainActivity.this, FlagsGame.class);
							intent.putIntegerArrayListExtra("regions", selectedRegions);
							startActivity(intent);
							selectedRegions.clear();

						}
					}
				}).create();
		dialog.show();
	}

}
