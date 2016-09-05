package com.example.colorgame;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.transition.Scene;
import android.transition.Transition;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class Game extends Activity {

	Scene oneScene, anotherScene;
	ViewGroup rootScene;
	Transition custom, mFadeTransition;
	Button btn;
	SurfaceView recs;
	SurfaceHolder holder;
	Canvas canvas;
	Paint paint;
	MyRec rec;
	int top = 400;
	int bottom = 400;
	int width, height;
	GestureDetector gesture;
	DisplayMetrics dm = new DisplayMetrics();
	int currX, currY;
	Thread t;
	boolean drawOn = false;
	int change = 0;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		width = dm.widthPixels;
		height = dm.heightPixels;
		rec = new MyRec(this);
		setContentView(rec);

	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		boolean handled = super.dispatchTouchEvent(ev);
		handled = gesture.onTouchEvent(ev);
		return handled;
	}

	public class MyRec extends SurfaceView
			implements SurfaceHolder.Callback, GestureDetector.OnGestureListener, OnTouchListener,Runnable {

		private static final String TAG = "Svetlin SurfaceView";

		public MyRec(Context context) {
			super(context);
			holder = getHolder();
			holder.addCallback(this);
		}

		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			tryDrawing(holder);
			newGame(holder);
		}

		@Override
		public void surfaceChanged(SurfaceHolder holder, int frmt, int w, int h) {
			tryDrawing(holder);

		
		
		}

		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
		}

		private void tryDrawing(SurfaceHolder holder) {
			Log.i(TAG, "Trying to draw...");

			Canvas canvas = holder.lockCanvas();
			if (canvas == null) {
				Log.e(TAG, "Cannot draw onto the canvas as it's null");
			} else {
				drawMyStuff(canvas);
				holder.unlockCanvasAndPost(canvas);
			}
		}

		private void drawMyStuff(Canvas canvas) {
			Rect red = new Rect((int) (width * 0.15),change + (int) (height * 0.1), (int) (width * 0.30),
					change+(int) (height * 0.21));
			Log.i(TAG, "Drawing...");
			Paint paintt = new Paint();
			paintt.setColor(Color.RED);
			canvas.drawRect(red, paintt);
			paintt.setColor(Color.GREEN);
			Rect green = new Rect((int) (width * 0.45), (int) (height * 0.1), (int) (width * 0.60),
					(int) (height * 0.21));
			canvas.drawRect(green, paintt);
			Rect blue = new Rect((int) (width * 0.75), (int) (height * 0.1), (int) (width * 0.90),
					(int) (height * 0.21));
			paintt.setColor(Color.BLUE);
			canvas.drawRect(blue, paintt);
			// // drawing the finish line
			Rect finish = new Rect(0, (int) (height * 0.7), (int) (width), (int) (height * 0.73));
			paintt.setColor(Color.GRAY);
			canvas.drawRect(finish, paintt);
			// drawing the bottom rectangle
			Rect bottom = new Rect(0, (int) (height * 0.78), (int) (width), (int) (height));
			paintt.setColor(Color.BLUE);
			canvas.drawRect(bottom, paintt);
		}

		@Override
		public boolean onDown(MotionEvent e) {

			return false;
		}

		@Override
		public void onShowPress(MotionEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void onLongPress(MotionEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			int x = (int) e1.getX();
			int y = (int) e1.getY();
			System.out.println("x is: " + x);
			System.out.println("y is: " + y);
			System.out.println("bla : "+ width * 0.15);
			System.out.println("blabla : "+ width * 0.45);
			if ( x >= (int)(width * 0.15) && x <= (int)(width*0.30)) {
				Toast.makeText(getApplicationContext(), "I'm red!", Toast.LENGTH_LONG).show();
			}else if( x >= (int)(width * 0.45) && x <= (int)(width*0.60)){
				Toast.makeText(getApplicationContext(), "I'm Green!", Toast.LENGTH_LONG).show();
			}else{
				Toast.makeText(getApplicationContext(), "I'm Blue!", Toast.LENGTH_LONG).show();
			}
			return true;
		}
		
		public void newGame(SurfaceHolder holder){
			t = new Thread();
			t.start();
			drawOn = true;
		}

		@Override
		public boolean onTouch(View v, MotionEvent event) {

			return true;
		}

		@Override
		public void run() {
			while(true){
				if(drawOn){
					if(!holder.getSurface().isValid()){
						continue;
					}
					change += 10;
//					if (change > screenHeight)
//						change = 0;
					Canvas canvas = holder.lockCanvas();
					Log.v("size", canvas.getHeight() + "");
					drawMyStuff(canvas);
					holder.unlockCanvasAndPost(canvas);
					
				}
			}
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
