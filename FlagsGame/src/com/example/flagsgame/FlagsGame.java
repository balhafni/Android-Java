package com.example.flagsgame;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.example.flagsgame.R.id;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Im;
import android.provider.UserDictionary.Words;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;

public class FlagsGame extends Activity implements OnClickListener {
	Bundle extras;

	ArrayList<Integer> regions;
	String[] africaPath, europePath, asiaPath, sAmericaPath, nAmericaPath;
	ArrayList<String[]> allPaths = new ArrayList<String[]>();
	TextView counter, title, checking;
	String correctName, wrongName1, wrongName2;
	ImageView flagImage;
	Button btn1, btn2, btn3;
	static int count = 0;
	int rand;
	String[] temp;
	Drawable theFlag;
	static int[] incorrectCounter = new int[10];
	static ArrayList<Integer> wrongCounter = new ArrayList<Integer>();
	Animator shake;
	Animation rotate;
	Button[] arrayBtns = new Button[3];

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.flags);
		// getting the list of regions from an intent

		extras = getIntent().getExtras();
		regions = extras.getIntegerArrayList("regions");
		// initializing elements
		counter = (TextView) findViewById(R.id.counter);
		title = (TextView) findViewById(R.id.quiz);
		checking = (TextView) findViewById(R.id.hw);
		flagImage = (ImageView) findViewById(R.id.Flag);
		btn1 = (Button) findViewById(R.id.reset);
		btn2 = (Button) findViewById(R.id.button2);
		btn3 = (Button) findViewById(R.id.button3);
		arrayBtns[0] = btn1;
		arrayBtns[1] = btn2;
		arrayBtns[2] = btn3;
		shake = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.shakeit);
		shake.addListener(new AnimatorListener() {

			@Override
			public void onAnimationStart(Animator animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animator animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animator animation) {
				for (int i = 0; i < arrayBtns.length; i++) {
					arrayBtns[i].setEnabled(true);
				}

			}

			@Override
			public void onAnimationCancel(Animator animation) {
				// TODO Auto-generated method stub

			}
		});
		rotate = AnimationUtils.loadAnimation(this, R.animator.rotate);
		rotate.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				finish();
				startActivity(getIntent());

			}
		});

		counter.setText((count + 1) + " of 10");
		title.setText("Flag Quiz");
		checking.setText("");

		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int width = dm.widthPixels;
		int height = dm.heightPixels;

		LayoutParams pp = new LayoutParams((int) (width * 0.30), (int) (width * 0.32));
		pp.setMargins((int) (width * .70), (int) (height * .10), 0, 0);
		counter.setLayoutParams(pp);

		pp = new LayoutParams((int) (width * 0.30), (int) (height * 0.2));
		pp.setMargins((int) (width * .40), (int) (height * 0.2), 0, 0);
		title.setLayoutParams(pp);

		pp = new LayoutParams((int) (width * 0.30), (int) (height * 0.2));
		pp.setMargins((int) (width * .40), (int) (height * 0.25), 0, 0);
		checking.setLayoutParams(pp);

		pp = new LayoutParams((int) (width * 0.5), (int) (width * 0.32));
		pp.setMargins((int) (width * .25), (int) (height * 0.30), 0, 0);
		flagImage.setLayoutParams(pp);

		try {
			addImages();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		flagImage.setImageDrawable(theFlag);
		shake.setTarget(flagImage);

		pp = new LayoutParams((int) (width * 0.25), (int) (height * 0.1));
		pp.setMargins((int) (width * 0.05), (int) (height * 0.80), 0, 0);
		btn1.setLayoutParams(pp);

		pp = new LayoutParams((int) (width * 0.25), (int) (height * 0.1));
		pp.setMargins((int) (width * 0.35), (int) (height * 0.80), 0, 0);
		btn2.setLayoutParams(pp);

		pp = new LayoutParams((int) (width * 0.25), (int) (height * 0.1));
		pp.setMargins((int) (width * .65), (int) (height * 0.80), 0, 0);
		btn3.setLayoutParams(pp);

		// putting random flag names in the buttons randomly
		Random rand = new Random();
		int pickBtn = rand.nextInt(3);
		if (count < 10) {
			if (pickBtn == 0) {
				btn1.setText(correctName);
				btn1.setOnClickListener(this);
				btn2.setText(wrongName1);
				btn2.setOnClickListener(this);
				btn3.setText(wrongName2);
				btn3.setOnClickListener(this);
			} else if (pickBtn == 1) {
				btn1.setText(wrongName1);
				btn1.setOnClickListener(this);
				btn2.setText(correctName);
				btn2.setOnClickListener(this);
				btn3.setText(wrongName2);
				btn3.setOnClickListener(this);
			} else if (pickBtn == 2) {
				btn1.setText(wrongName1);
				btn1.setOnClickListener(this);
				btn2.setText(wrongName2);
				btn2.setOnClickListener(this);
				btn3.setText(correctName);
				btn3.setOnClickListener(this);
			}

		} else if (count >= 10) {
			Intent intent = new Intent(FlagsGame.this, EndOfGame.class);
			for (int i = 0; i < incorrectCounter.length; i++) {
				wrongCounter.add(incorrectCounter[i]);
			}
			intent.putIntegerArrayListExtra("incorrectCounter", wrongCounter);
			startActivityForResult(intent, 0);
			// reinitializing the counter array to 0
			incorrectCounter = new int[10];
			wrongCounter.clear();
			count = 0;

		}
	}

	public void addImages() throws IOException {
		AssetManager assets = getAssets();
		InputStream imageStream;

		for (int i = 0; i < regions.size(); i++) {
			if (Integer.valueOf(regions.get(i)) == 0) {
				africaPath = assets.list("Africa");
				allPaths.add(africaPath);
			}
			if (Integer.valueOf(regions.get(i)) == 1) {
				asiaPath = assets.list("Asia");
				allPaths.add(asiaPath);
			}
			if (Integer.valueOf(regions.get(i)) == 2) {
				europePath = assets.list("Europe");
				allPaths.add(europePath);
			}
			if (Integer.valueOf(regions.get(i)) == 3) {
				nAmericaPath = assets.list("North_America");
				allPaths.add(nAmericaPath);
			}
			if (Integer.valueOf(regions.get(i)) == 4) {
				sAmericaPath = assets.list("South_America");
				allPaths.add(sAmericaPath);
			}
		}
		// generating a random image
		Random random = new Random();
		rand = random.nextInt(allPaths.size());
		temp = allPaths.get(rand);// picking a random array of paths

		if (Arrays.equals(temp, africaPath)) {
			rand = random.nextInt(africaPath.length);
			imageStream = getAssets().open("Africa/" + africaPath[rand]);
			theFlag = Drawable.createFromStream(imageStream, null);
			correctName = africaPath[rand].substring(africaPath[rand].indexOf('-') + 1).replace(".png", "");
			// picking 2 random names which are no correct
			rand = random.nextInt(africaPath.length);
			wrongName1 = africaPath[rand].substring(africaPath[rand].indexOf('-') + 1).replace(".png", "");
			rand = random.nextInt(africaPath.length);
			wrongName2 = africaPath[rand].substring(africaPath[rand].indexOf('-') + 1).replace(".png", "");

			if (wrongName1.equals(wrongName2) || wrongName1.equals(correctName) || wrongName2.equals(correctName)) {
				rand = random.nextInt(africaPath.length);
				wrongName1 = africaPath[rand].substring(africaPath[rand].indexOf('-') + 1).replace(".png", "");
				rand = random.nextInt(africaPath.length);
				wrongName2 = africaPath[rand].substring(africaPath[rand].indexOf('-') + 1).replace(".png", "");
			}
		} else if (Arrays.equals(temp, asiaPath)) {
			rand = random.nextInt(asiaPath.length);
			imageStream = getAssets().open("Asia/" + asiaPath[rand]);
			theFlag = Drawable.createFromStream(imageStream, null);
			correctName = asiaPath[rand].substring(asiaPath[rand].indexOf('-') + 1).replace(".png", "");
			// picking 2 random names which are no correct
			rand = random.nextInt(asiaPath.length);
			wrongName1 = asiaPath[rand].substring(asiaPath[rand].indexOf('-') + 1).replace(".png", "");
			rand = random.nextInt(asiaPath.length);
			wrongName2 = asiaPath[rand].substring(asiaPath[rand].indexOf('-') + 1).replace(".png", "");

			if (wrongName1.equals(wrongName2) || wrongName1.equals(correctName) || wrongName2.equals(correctName)) {
				rand = random.nextInt(asiaPath.length);
				wrongName1 = asiaPath[rand].substring(asiaPath[rand].indexOf('-') + 1).replace(".png", "");
				rand = random.nextInt(asiaPath.length);
				wrongName2 = asiaPath[rand].substring(asiaPath[rand].indexOf('-') + 1).replace(".png", "");
			}
		} else if (Arrays.equals(temp, europePath)) {
			rand = random.nextInt(europePath.length);
			imageStream = getAssets().open("Europe/" + europePath[rand]);
			theFlag = Drawable.createFromStream(imageStream, null);
			correctName = europePath[rand].substring(europePath[rand].indexOf('-') + 1).replace(".png", "");
			// picking 2 random names which are no correct
			rand = random.nextInt(europePath.length);
			wrongName1 = europePath[rand].substring(europePath[rand].indexOf('-') + 1).replace(".png", "");
			rand = random.nextInt(europePath.length);
			wrongName2 = europePath[rand].substring(europePath[rand].indexOf('-') + 1).replace(".png", "");

			if (wrongName1.equals(wrongName2) || wrongName1.equals(correctName) || wrongName2.equals(correctName)) {
				rand = random.nextInt(europePath.length);
				wrongName1 = europePath[rand].substring(europePath[rand].indexOf('-') + 1).replace(".png", "");
				rand = random.nextInt(europePath.length);
				wrongName2 = europePath[rand].substring(europePath[rand].indexOf('-') + 1).replace(".png", "");
			}
		} else if (Arrays.equals(temp, nAmericaPath)) {
			rand = random.nextInt(nAmericaPath.length);
			imageStream = getAssets().open("North_America/" + nAmericaPath[rand]);
			theFlag = Drawable.createFromStream(imageStream, null);
			correctName = nAmericaPath[rand].substring(nAmericaPath[rand].indexOf('-') + 1).replace(".png", "");
			// picking 2 random names which are no correct
			rand = random.nextInt(nAmericaPath.length);
			wrongName1 = nAmericaPath[rand].substring(nAmericaPath[rand].indexOf('-') + 1).replace(".png", "");
			rand = random.nextInt(nAmericaPath.length);
			wrongName2 = nAmericaPath[rand].substring(nAmericaPath[rand].indexOf('-') + 1).replace(".png", "");

			if (wrongName1.equals(wrongName2) || wrongName1.equals(correctName) || wrongName2.equals(correctName)) {
				rand = random.nextInt(nAmericaPath.length);
				wrongName1 = nAmericaPath[rand].substring(nAmericaPath[rand].indexOf('-') + 1).replace(".png", "");
				rand = random.nextInt(nAmericaPath.length);
				wrongName2 = nAmericaPath[rand].substring(nAmericaPath[rand].indexOf('-') + 1).replace(".png", "");
			}
		} else if (Arrays.equals(temp, sAmericaPath)) {
			rand = random.nextInt(sAmericaPath.length);
			imageStream = getAssets().open("South_America/" + sAmericaPath[rand]);
			theFlag = Drawable.createFromStream(imageStream, null);
			correctName = sAmericaPath[rand].substring(sAmericaPath[rand].indexOf('-') + 1).replace(".png", "");
			// picking 2 random names which are no correct
			rand = random.nextInt(sAmericaPath.length);
			wrongName1 = sAmericaPath[rand].substring(sAmericaPath[rand].indexOf('-') + 1).replace(".png", "");
			rand = random.nextInt(sAmericaPath.length);
			wrongName2 = sAmericaPath[rand].substring(sAmericaPath[rand].indexOf('-') + 1).replace(".png", "");

			if (wrongName1.equals(wrongName2) || wrongName1.equals(correctName) || wrongName2.equals(correctName)) {
				rand = random.nextInt(sAmericaPath.length);
				wrongName1 = sAmericaPath[rand].substring(sAmericaPath[rand].indexOf('-') + 1).replace(".png", "");
				rand = random.nextInt(sAmericaPath.length);
				wrongName2 = sAmericaPath[rand].substring(sAmericaPath[rand].indexOf('-') + 1).replace(".png", "");
			}
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Button btn;
		if (v instanceof Button) {
			btn = (Button) v;
			for (int i = 0; i < arrayBtns.length; i++) {
				if (!arrayBtns[i].equals(btn)) {
					arrayBtns[i].setEnabled(false);
				}
			}
			if (btn.getText() == correctName) {
				checking.setText("Correct!");
				checking.setTextColor(Color.GREEN);
				flagImage.startAnimation(rotate);
				count++;
			} else if (btn.getText() != correctName) {
				checking.setText("Incorrect!");
				shake.start();
				checking.setTextColor(Color.RED);
				btn.setEnabled(false);
				btn.setAlpha(0.5f);
				// wrongCounter++;
				// incorrectCounter.set(count, new
				// Integer(Integer.valueOf(incorrectCounter.get(count)) + 1));
				incorrectCounter[count]++;
			}

		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// super.onActivityResult(requestCode, resultCode, data);
		finish();
	}

}
