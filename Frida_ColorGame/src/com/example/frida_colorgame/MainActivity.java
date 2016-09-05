package com.example.frida_colorgame;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends Activity{
	ImageView mypic;
	TextView hwtxt, idtxt;
	ViewGroup container;
	Scene scene1, scene2;
	Transition customTrans;
	TransitionDrawable trans;
	int width, height;
	MyView v;
	RadioButton rBtn1, rBtn2;
	RadioGroup group;
	Handler handler;
	int chosenLevel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		chosenLevel = 1;
		
		mypic = (ImageView) findViewById(R.id.mypic);
		
		
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		width = dm.widthPixels;
		height = dm.heightPixels;
			
		
		rBtn1 = (RadioButton) findViewById(R.id.radio0);
		rBtn2 = (RadioButton) findViewById(R.id.radio1);
		group = (RadioGroup) findViewById(R.id.radioGroup1);
		
		container = (ViewGroup)findViewById(R.id.container);
		scene1 = Scene.getSceneForLayout(container, R.layout.view1, this);
		scene2 = Scene.getSceneForLayout(container, R.layout.view2, this);
		customTrans = TransitionInflater.from(this).inflateTransition(R.transition.reg_trans);
		scene1.enter();
		TransitionManager.go(scene1, customTrans);
		
		
		
		handler = new Handler();
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				TransitionManager.go(scene2, customTrans);

			}
		}, 2000);
	}
	
	public void setModeEasy(View v){
		chosenLevel = 1;//Easy
	}
	public void setModeHard(View v){
		chosenLevel = 2;//Hard
	}

	public class MyView extends SurfaceView{

		public MyView(Context context) {
			super(context);
		}
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}


	
	public boolean onClick(View v) {
		
		Intent intent = new Intent(MainActivity.this, GameActivity.class);
		intent.putExtra("chosenLevel", chosenLevel);
		startActivityForResult(intent, 0);
		return false;
	}
}
