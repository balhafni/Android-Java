package com.example.colorgamemodified;

import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends Activity {
	public static int width, height;
	RadioGroup group;
	ViewGroup container;
	Scene scene1;
	Scene scene2;
	Transition cust;
	Intent intent;
	Handler handler;
	RadioButton radioBtn1, radioBtn2;
	String level = "easy";
	AlertDialog dialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		width = dm.widthPixels;
		height = dm.heightPixels;
		
//		dialog
		
//		AlertDialog.Builder builder = new AlertDialog.Builder(this);

//		builder.setMessage("Done!").setTitle("yaaay");
//		dialog = builder.create();
		
		container = (ViewGroup) findViewById(R.id.container);
		radioBtn1 = (RadioButton) findViewById(R.id.radio0);
		radioBtn2 = (RadioButton) findViewById(R.id.radio1);
		group = (RadioGroup) findViewById(R.id.radioGroup1);

		scene1 = Scene.getSceneForLayout(container, R.layout.view1, this);
		scene2 = Scene.getSceneForLayout(container, R.layout.view2, this);

		cust = TransitionInflater.from(this).inflateTransition(R.transition.reg_trans);
		intent = new Intent(MainActivity.this, Game.class);

		scene1.enter();

		TransitionManager.go(scene1, cust);

		handler = new Handler();
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				TransitionManager.go(scene2, cust);

			}
		}, 1000);

	}

	public void setEasy(View v) {
		level = "easy";
	}

	public void setHard(View v) {
		level = "hard";
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

	public void onClick(View v) {
		intent.putExtra("level", level);
		System.out.println("level yaaaaaaaaay "+ level);
		startActivityForResult(intent, 0);

	}
}
	
