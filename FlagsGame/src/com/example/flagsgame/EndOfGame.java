package com.example.flagsgame;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.MarginLayoutParamsCompat;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Button;
import android.widget.FrameLayout.LayoutParams;

public class EndOfGame extends Activity {
	TextView title, stats;
	ArrayList<Integer> counter;
	Bundle bundle;
	Button btn;
	Intent reset;
	StringBuilder b;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.end_of_game);
		title = (TextView) findViewById(R.id.myName);
		stats = (TextView) findViewById(R.id.ID);
		btn = (Button) findViewById(R.id.reset);
		bundle = getIntent().getExtras();
		counter = bundle.getIntegerArrayList("incorrectCounter");

		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int width = dm.widthPixels;
		int height = dm.heightPixels;

		LayoutParams pp = new LayoutParams((int) (width * 0.30), (int) (height * 0.2));
		pp.setMargins((int) (width * .10), (int) (height * 0.2), 0, 0);
		title.setLayoutParams(pp);

		pp = new LayoutParams((int) (width), (int) (height));
		pp.setMargins((int) (width * .10), (int) (height * 0.25), 0, 0);
		stats.setLayoutParams(pp);

		pp = new LayoutParams((int) (width * 0.25), (int) (height * 0.1));
		pp.setMargins((int) (width * .35), (int) (height * 0.70), 0, 0);
		btn.setLayoutParams(pp);
		btn.setText("Reset");
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		b = new StringBuilder();
		for (int i = 0; i < counter.size(); i++) {
			b.append("Question " + (i + 1) + " Was answered " + Integer.valueOf(counter.get(i))
					+ " times incorrectly\n");
		}
		stats.setText(b.toString());
		stats.setTextColor(Color.RED);
	}
}
