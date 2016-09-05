package com.example.memorygame;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class RelativeLayout extends Activity implements OnClickListener {

	TextView txt;
	Spinner spinner;
	// ModifiedImageView v1, v2, v3, v4, v5, v6, v7, v8, v9, v10, v11, v12, v13,
	// v14, v15, v16, v17, v18, v19, v20;
	int count, countAlpha;
	ArrayList<ImageView> images = new ArrayList<ImageView>();
	String temp1, temp2;
	ImageView img1, img2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		temp1 = "";
		temp2 = "";
		super.onCreate(savedInstanceState);
		setContentView(R.layout.relative_activity);
		txt = (TextView) findViewById(R.id.textView1);
		spinner = (Spinner) findViewById(R.id.spinner1);
		spinner.setPrompt("Choose Number of Images");
		initializeImageViews();
		game();
		spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				switch (position) {
				case 0:
					for (int i = 0; i < images.size(); i++) {
						images.get(i).setVisibility(View.INVISIBLE);
					}

					break;
				case 1:

					for (int i = 0; i < images.size(); i++) {
						if (i <= 7) {
							images.get(i).setVisibility(View.VISIBLE);
							count++;
						}

					}
					endOfGame();

					spinner.setEnabled(false);
					game();
					break;
				case 2:

					for (int i = 0; i < images.size(); i++) {
						if (i <= 15) {
							images.get(i).setVisibility(View.VISIBLE);
							count++;
						}

					}
					game();
					spinner.setEnabled(false);
					break;
				case 3:

					for (int i = 0; i < images.size(); i++) {
						images.get(i).setVisibility(View.VISIBLE);
						count++;
					}
					game();
					spinner.setEnabled(false);
					break;
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

	}

	public void initializeImageViews() {
		// v1 = (ModifiedImageView) findViewById(R.id.imageView1);
		images.add((ImageView) findViewById(R.id.imageView01));
		images.add((ImageView) findViewById(R.id.imageView02));
		images.add((ImageView) findViewById(R.id.imageView03));
		images.add((ImageView) findViewById(R.id.imageView04));
		images.add((ImageView) findViewById(R.id.imageView5));
		images.add((ImageView) findViewById(R.id.imageView6));
		images.add((ImageView) findViewById(R.id.imageView7));
		images.add((ImageView) findViewById(R.id.imageView8));
		images.add((ImageView) findViewById(R.id.imageView9));
		images.add((ImageView) findViewById(R.id.imageView010));
		images.add((ImageView) findViewById(R.id.imageView011));
		images.add((ImageView) findViewById(R.id.imageView012));
		images.add((ImageView) findViewById(R.id.imageView013));
		images.add((ImageView) findViewById(R.id.imageView014));
		images.add((ImageView) findViewById(R.id.imageView015));
		images.add((ImageView) findViewById(R.id.imageView016));
		images.add((ImageView) findViewById(R.id.imageView017));
		images.add((ImageView) findViewById(R.id.imageView018));
		images.add((ImageView) findViewById(R.id.imageView019));
		images.add((ImageView) findViewById(R.id.imageView020));
		images.add((ImageView) findViewById(R.id.imageView021));
		images.add((ImageView) findViewById(R.id.imageView022));
		images.add((ImageView) findViewById(R.id.imageView023));
		images.add((ImageView) findViewById(R.id.imageView024));
	

	}

	public void game() {
		for (int i = 0; i < images.size(); i++) {
			images.get(i).setColorFilter(Color.RED);
		}
		for (int i = 0; i < images.size(); i++) {
			images.get(i).setClickable(true);
		}
		for (int i = 0; i < images.size(); i++) {
			images.get(i).setOnClickListener(this);

		}

	}

	@Override
	public void onClick(View v) {

		ImageView imageView;
		if (v instanceof ImageView) {
			imageView = ((ImageView) v);

			imageView.setColorFilter(0);
			if (temp1 == "") {
				temp1 = (String) imageView.getTag();
				img1 = imageView;
			} else {
				temp2 = (String) imageView.getTag();
				img2 = imageView;

				if (temp1.equals(temp2)) {
					img1.setAlpha(.5f);
					img2.setAlpha(.5f);
				
					boolean found2 = false;
					int prevIndex = -1;
					for (int i = 0; i < images.size(); i++) {
						if (images.get(i).getTag().equals(temp1) || images.get(i).getTag().equals(temp2)) {
							// images.remove(i);
							if (found2) {
								countAlpha += 2;
								images.remove(i);
								images.remove(prevIndex);

							} else {
								prevIndex = i;
								found2 = true;
							}
						}
					}
				} else if (!temp1.equals(temp2)) {
					CountDownTimer time = new CountDownTimer(1200, 1000) {

						@Override
						public void onTick(long millisUntilFinished) {
							for (int i = 0; i < images.size(); i++) {
								images.get(i).setClickable(false);
							}

						}

						@Override
						public void onFinish() {

							for (int i = 0; i < images.size(); i++) {

								images.get(i).setColorFilter(Color.RED);
								images.get(i).setClickable(true);
							}

						}
					}.start();
				}
				temp1 = temp2 = "";
			}
			endOfGame();

		}

	}

	public void endOfGame() {
		if (count == countAlpha) {
			Toast.makeText(getApplicationContext(), "You found all matches! Let's play again!", Toast.LENGTH_LONG)
					.show();

			Intent intent = new Intent();
			setResult(0, intent);
			finish();

		} else {
			System.out.println("count: " + count);
		}

	}
}
