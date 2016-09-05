package com.example.hw_part2;

import java.text.DecimalFormat;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class MainActivity extends Activity {

	TextView myTitle;
	TextView myTotal;
	TextView myTax;
	TextView SelectPercentage;
	TextView WithFiveP;
	TextView WithTenP;
	TextView MyCostum;
	Button Calculate;
	SeekBar Seek;
	EditText Total;
	EditText Tax;
	double TotalAmount;
	double TotalTaxes;
	double fiveP;
	double tenP;
	double min = 11;
	double max = 50;
	EditText Seeking;
	int totalCustom = 0;
	TextView fivep;
	TextView tenp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		fivep = (TextView) findViewById(R.id.FivePercent);
		tenp = (TextView) findViewById(R.id.TenPercent);
		myTitle = (TextView) findViewById(R.id.title);
		myTotal = (TextView) findViewById(R.id.EnterTotal);
		myTax = (TextView) findViewById(R.id.EnterTax);
		SelectPercentage = (TextView) findViewById(R.id.SelectPercentage);
		WithFiveP = (TextView) findViewById(R.id.With5);
		WithTenP = (TextView) findViewById(R.id.With10);
		MyCostum = (TextView) findViewById(R.id.Custom);
		Calculate = (Button) findViewById(R.id.Calculate);
		Seek = (SeekBar) findViewById(R.id.seekBar1);
		Total = (EditText) findViewById(R.id.Total);
		Tax = (EditText) findViewById(R.id.Tax);
		Seeking = (EditText) findViewById(R.id.SB);
		Calculate.setClickable(true);
		Calculate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				DecimalFormat d = new DecimalFormat("#.##");
				if (myTotal.getText().equals(null) || myTax.getText().equals(null)) {
					myTotal.setText(String.valueOf(0.0));
					myTax.setText(String.valueOf(0.0));
					fivep.setText(String.valueOf(0.0));
					tenp.setText(String.valueOf(0.0));
					MyCostum.setText(String.valueOf(0.0));
					

				} else
				TotalAmount = Double.parseDouble(Total.getText().toString());
				TotalTaxes = Double.parseDouble(Tax.getText().toString());
				MyCostum.setText(String.valueOf(d.format(((totalCustom / 100) * (TotalTaxes + TotalAmount)))));
				fivep.setText((String.valueOf(d.format((0.05 * (TotalTaxes + TotalAmount))))));
				tenp.setText((String.valueOf(d.format((0.10 * (TotalTaxes + TotalAmount))))));
			}
		});

		Seek.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				totalCustom = progress;
				if (progress <= min) {
					Seeking.setText(String.valueOf(min));

				} else if (progress > max) {
					Seeking.setText(String.valueOf(max));

				} else
					Seeking.setText(String.valueOf(progress + 1));

			}
		});
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
