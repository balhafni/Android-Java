package com.example.taxescalculator;

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

public class MainActivity extends Activity {
	EditText total, tax, fivePercent, tenPercent, customPercent, seekValue;
	SeekBar bar;
	Button btn;
	int MIN = 11;
	int MAX = 50;
	double totalPayment, taxes;
	double customTip;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		total = (EditText) findViewById(R.id.mytotal);
		tax = (EditText) findViewById(R.id.mytax);
		fivePercent = (EditText) findViewById(R.id.myfive);
		tenPercent = (EditText) findViewById(R.id.myten);
		customPercent = (EditText) findViewById(R.id.mycustom);
		seekValue = (EditText) findViewById(R.id.seekvalue);
		bar = (SeekBar) findViewById(R.id.Bar);
		btn = (Button) findViewById(R.id.button1);

		seekValue.setText("11");
		bar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

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
				if (progress < MIN) {
					seekValue.setText(String.valueOf(MIN));
				} else if (progress >= MAX) {
					seekValue.setText(String.valueOf(MAX));
				} else {
					seekValue.setText(String.valueOf(progress 
							+ 1));
				}
			}
		});

		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				DecimalFormat d = new DecimalFormat("#.##");
				if (!total.getText().toString().matches("") && !tax.getText().toString().matches("")) {
					totalPayment = Double.parseDouble(total.getText().toString())
							+ Double.parseDouble(tax.getText().toString());
					customTip = Double.parseDouble(seekValue.getText().toString()) / 100.0;
					fivePercent.setText(String.valueOf(d.format(totalPayment * 0.05)));
					tenPercent.setText(String.valueOf(d.format(totalPayment * 0.10)));
					customPercent.setText(String.valueOf(d.format(totalPayment * customTip)));

				} else {
					totalPayment = 0;
					customTip = Double.parseDouble(seekValue.getText().toString()) / 100.0;
					fivePercent.setText(String.valueOf(totalPayment * 0.05));
					tenPercent.setText(String.valueOf(totalPayment * 0.10));
					customPercent.setText(String.valueOf(totalPayment * customTip));

				}
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
