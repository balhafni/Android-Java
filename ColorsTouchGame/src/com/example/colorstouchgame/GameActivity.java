package com.example.colorstouchgame;

import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.View.OnTouchListener;
import android.widget.Toast;

public class GameActivity extends Activity implements 
GestureDetector.OnGestureListener{
	Paint myPaintRed, myPaintGreen, myPaintBlue, myPaintYellow, myRandomPaint;
	MyView view;
	float x, y;
	int width, height, colorInt;
	boolean startThread = true;
	boolean painting = false;
	boolean matched = false, yesmatch = false;
	GestureDetector gesture;
	Random randnr;
	int randnum, mynum;
	Handler handler;
	int level = 1;
	static int countTries;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		level = getIntent().getExtras().getInt("chosenLevel");
		view = new MyView(this);
		setContentView(view);
		x = y = 0;
		mynum = 0;
		randnr = new Random();
		randnum = randnr.nextInt(2);
		gesture = new GestureDetector(this, gestureListener);
		countTries=0;
	}

	SimpleOnGestureListener gestureListener = new SimpleOnGestureListener() {
		@Override
		public boolean onDoubleTap(MotionEvent event) {
			Log.v("gesture", "double tap");
			return true;
		}

		@Override
		public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {
			Log.v("gesture", "onFling: " + event1.toString() + event2.toString());
			return true;
		}

	};

	@Override
	protected void onPause() {
		super.onPause();
		view.pause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		view.resume();
	}

	
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		Log.v("touchh", "inner");
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			x = event.getX();
			y = event.getY();
			System.out.println(x + " " + y);
			Log.v("down", "inner");

			colorInt = checkIfColorsArePressed(x, y);
			painting = true;
			startThread = true;
			break;
		}
		return true;
	}

	public int checkIfColorsArePressed(float x, float y) {
		view.thisY = (int) (0.1 * height);
		mynum = 0;// this number tells me if i want to draw the dot
		int num = -1;
		if (y >= (int) (0.1 * height) && y <= (int) (0.5 * height)) {
			if (x >= (int) (0.075 * width) && x <= (int) (0.325 * width)) {
				num = 0;

				System.out.println("RED T");
				Log.v("pressedred", "REDDD");
			} else if (x >= (int) (0.375 * width) && x <= (int) (0.625 * width)) {
				num = 1;

				System.out.println("Green");
			} else if (x >= (int) (0.675 * width) && x <= (int) (0.925 * width)) {
				num = 2;

				System.out.println("Blue");
			}
		}

		return num;
	}

	public class MyView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

		SurfaceHolder holder;
		Boolean change = false;
		Thread t = null;
		// int thisY = (int)(0.1*height);
		int thisY;

		public MyView(Context context) {
			super(context);
			this.getHolder().addCallback(this);
			holder = getHolder();
			// thisY = (int)(0.1*height);

			myPaintRed = new Paint();
			myPaintGreen = new Paint();
			myPaintBlue = new Paint();
			myPaintYellow = new Paint();
			myRandomPaint = new Paint();
			myPaintRed.setColor(Color.RED);
			myPaintGreen.setColor(Color.GREEN);
			myPaintBlue.setColor(Color.BLUE);
			myPaintYellow.setColor(Color.YELLOW);
			myRandomPaint.setColor(generateColor());
		}
		

		public int generateColor() {
			int col = -1;
			Random rand = new Random();
			int num = rand.nextInt(3);
			switch (num) {
			case 0:
				col = Color.RED;
				break;
			case 1:
				col = Color.GREEN;
				break;
			case 2:
				col = Color.BLUE;
				break;
			}
			return col;
		}

		public void pause() {
			// stop drawing
			change = false;
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			t = null;
		}

		public void resume() {
			change = true;
			t = new Thread(this);
			Log.v("touch", "start");
			t.start();
		}

		public void draw1(Canvas canvas) {
			mynum = 1;
			canvas.drawColor(Color.YELLOW);

			DisplayMetrics disMet = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(disMet);
			width = disMet.widthPixels;
			height = disMet.heightPixels;

			Rect placeRed = new Rect((int) (0.075 * width), (int) (0.1 * height), (int) (0.325 * width),
					(int) (0.3 * height));
			canvas.drawRect(placeRed, myPaintRed);

			Rect placeGreen = new Rect((int) (0.375 * width), (int) (0.1 * height), (int) (0.625 * width),
					(int) (0.3 * height));
			canvas.drawRect(placeGreen, myPaintGreen);

			Rect placeBlue = new Rect((int) (0.675 * width), (int) (0.1 * height), (int) (0.925 * width),
					(int) (0.3 * height));
			canvas.drawRect(placeBlue, myPaintBlue);

			Rect bottomColor = new Rect(0, (int) (0.7 * height), width, (int) (0.95 * height));
			canvas.drawRect(bottomColor, myRandomPaint);// Should be changing
														// color after 2 sec
			Paint grey = new Paint();
			grey.setColor(Color.MAGENTA);

			Rect randomline = new Rect(0, (int) (0.55 * height), (width), (int) (0.65 * height));
			canvas.drawRect(randomline, grey);

			if (matched && yesmatch) {
				Paint paint = new Paint();
				paint.setColor(Color.rgb(300, 150, 200));
				paint.setStrokeWidth(15);
				int startx = (int) (width * 0.2);
				int starty = (int) (height * 0.3);
				int endx = (int) (width * 0.8);
				int endy = (int) (height * 0.6);
				canvas.drawLine(startx, starty, endx, endy, paint);

				startx = (int) (width * 0.8);
				starty = (int) (height * 0.6);
				endx = (int) (width * 0.2);
				endy = (int) (height * 0.3);
				canvas.drawLine(startx, starty, endx, endy, paint);
				yesmatch = false;
			}

		}
		

		public void drawWhenTouched(Canvas canvas) {
			countTries++;
			
			Rect placeRed = new Rect();
			Rect placeGreen;
			Rect placeBlue;
			Rect randomline;
			Rect bottomColor;
			Paint graaay = new Paint();
			graaay.setColor(Color.MAGENTA);

			Rect back = new Rect(0, 0, width, height);
			if (colorInt != -1) {

				canvas.drawRect(back, myPaintYellow);
			}

			switch (colorInt) {
			case 0:

				painting = true;

				placeRed.set((int) (0.075 * width), thisY, (int) (0.325 * width), (int) (thisY + (0.2 * height)));
				canvas.drawRect(placeRed, myPaintRed);

				placeGreen = new Rect((int) (0.375 * width), (int) (0.1 * height), (int) (0.625 * width),
						(int) (0.3 * height));
				canvas.drawRect(placeGreen, myPaintGreen);

				placeBlue = new Rect((int) (0.675 * width), (int) (0.1 * height), (int) (0.925 * width),
						(int) (0.3 * height));
				canvas.drawRect(placeBlue, myPaintBlue);

				bottomColor = new Rect(0, (int) (0.7 * height), width, (int) (0.95 * height));
				canvas.drawRect(bottomColor, myRandomPaint);// Should be
															// changing
															// color

				randomline = new Rect(0, (int) (0.55 * height), (width), (int) (0.65 * height));
				canvas.drawRect(randomline, graaay);

				// holder.unlockCanvasAndPost(canvas);

				break;
			case 1:
				painting = true;

				// canvas.drawColor(Color.YELLOW);

				placeRed.set((int) (0.075 * width), (int) (0.1 * height), (int) (0.325 * width), (int) (0.3 * height));
				canvas.drawRect(placeRed, myPaintRed);

				placeGreen = new Rect((int) (0.375 * width), thisY, (int) (0.625 * width),
						(int) (thisY + (0.2 * height)));

				canvas.drawRect(placeGreen, myPaintGreen);

				placeBlue = new Rect((int) (0.675 * width), (int) (0.1 * height), (int) (0.925 * width),
						(int) (0.3 * height));
				canvas.drawRect(placeBlue, myPaintBlue);

				bottomColor = new Rect(0, (int) (0.7 * height), width, (int) (0.95 * height));
				canvas.drawRect(bottomColor, myRandomPaint);// Should be
															// changing
															// color

				randomline = new Rect(0, (int) (0.55 * height), (width), (int) (0.65 * height));
				canvas.drawRect(randomline, graaay);

				// holder.unlockCanvasAndPost(canvas);
				break;
			case 2:
				painting = true;

				// canvas.drawColor(Color.YELLOW);

				placeRed.set((int) (0.075 * width), (int) (0.1 * height), (int) (0.325 * width), (int) (0.3 * height));
				canvas.drawRect(placeRed, myPaintRed);

				placeGreen = new Rect((int) (0.375 * width), (int) (0.1 * height), (int) (0.625 * width),
						(int) (0.3 * height));
				canvas.drawRect(placeGreen, myPaintGreen);

				placeBlue = new Rect((int) (0.675 * width), thisY, (int) (0.925 * width),
						(int) (thisY + (0.2 * height)));
				canvas.drawRect(placeBlue, myPaintBlue);

				bottomColor = new Rect(0, (int) (0.7 * height), width, (int) (0.95 * height));
				canvas.drawRect(bottomColor, myRandomPaint);// Should be
															// changing
															// color

				randomline = new Rect(0, (int) (0.55 * height), (width), (int) (0.65 * height));
				canvas.drawRect(randomline, graaay);

				// holder.unlockCanvasAndPost(canvas);
				break;
			}
			// painting = false;
			// itsaMatch();
		}

		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			Canvas canvas = holder.lockCanvas();
			if (holder.getSurface().isValid()) {
				// t.start();
				draw1(canvas);
				holder.unlockCanvasAndPost(canvas);
			} else {
				System.out.println("Can't draw! ");
			}
		}

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
			Canvas canvas = holder.lockCanvas();
			if (holder.getSurface().isValid() && (colorInt == 0 || colorInt == 1 || colorInt == 2)) {
				draw1(canvas);
				// drawWhenTouched(canvas);
				holder.unlockCanvasAndPost(canvas);
			} else {
				System.out.println("Can't draw! ");
			}
		}

		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {

		}

		@Override
		public void run() {
			while (startThread) {
				if (painting) {
					while (thisY < height) {
						if (!holder.getSurface().isValid()) {
							continue;
						}
						if (thisY < (int) (0.55 * height) && colorInt != -1) {
							yesmatch = false;
							thisY += 10;

							Canvas canvas = view.holder.lockCanvas();
							randnum = randnr.nextInt(100);
							if (randnum == 1) {
								myRandomPaint.setColor(generateColor());

							}
							drawWhenTouched(canvas);
							view.holder.unlockCanvasAndPost(canvas);

						} else if (thisY >= (int) (0.55 * height)) {
							matched = itsaMatch();

							Canvas canvas = view.holder.lockCanvas();
							randnum = randnr.nextInt(100);
							if (randnum == 1) {
								myRandomPaint.setColor(generateColor());

							}
							if (mynum == 1)
								yesmatch = false;
							
							draw1(canvas);
							if(matched){
								try {
									Thread.sleep(4000);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
							view.holder.unlockCanvasAndPost(canvas);
							
							
						} else {
							continue;
						}

					}
					
				}
				if (!holder.getSurface().isValid()) {
					continue;
				}
				randnum = randnr.nextInt(20);
				if (randnum == 1) {
					myRandomPaint.setColor(generateColor());

				}
				if(countTries == 10){//Doesn't work
					new AlertDialog.Builder(this.getContext())
				    .setTitle("Summary")
				    .setMessage("Your average is.......?")
				    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dialog, int which) { 
				            // continue with delete
				        	finishActivity(0);
				        }
				     })
				    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dialog, int which) { 
				            // do nothing
				        }
				     })
				    .setIcon(android.R.drawable.dialog_frame)
				     .show();
				}
			}
			
		}

		public boolean itsaMatch() {
			boolean match = false;
			int nowCol = -1;
			Paint currPaint = new Paint();

			switch (colorInt) {
			case 0:
				nowCol = Color.RED;
				currPaint.setColor(nowCol);
				break;
			case 1:
				nowCol = Color.GREEN;
				currPaint.setColor(nowCol);
				break;
			case 2:
				nowCol = Color.BLUE;
				currPaint.setColor(nowCol);
				break;
			}
			if (thisY >= (int) (0.55 * height)) {// doesn't go in!!!!!
				if (currPaint.getColor() == myRandomPaint.getColor()) {
					// matched = true;
					match = true;
					yesmatch = true;
				}
			}
			return match;
		}

		@Override
		public boolean performClick() {
			super.performClick();
			return true;
		}

	}

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		Log.v("gesture", "inner");
		switch (e1.getAction()) {
		case MotionEvent.ACTION_DOWN:
			x = e1.getX();
			y = e1.getY();
			System.out.println(x + " " + y);
			Log.v("gesture", "inner");

			colorInt = checkIfColorsArePressed(x, y);
			painting = true;
			startThread = true;
			break;
		}
		return true;
	}

	



}