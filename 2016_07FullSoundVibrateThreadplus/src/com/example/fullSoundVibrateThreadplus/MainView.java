package com.example.fullSoundVibrateThreadplus;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MainView extends SurfaceView implements SurfaceHolder.Callback {

	int screenWidth;
	int screenHeight;
	float timeLeft = 0;
	float totalElapsedTime = 10;
	SquareThread sqThread;
	boolean threadIsRunning = false;
	Paint backgroundPaint;
	Paint textPaint; // Paint used to draw text
	Paint cannonPaint; // Paint used to draw the cannon
	Paint ballPaint;
	Point barrelEnd;
	Point ball;
	private int cannonballRadius; // cannonball radius
	private int cannonballSpeed; // cannonball speed
	private int cannonballVelocityX; // cannonball's x velocity
	private int cannonballVelocityY; // cannonball's y velocity
	int cannonLength; // cannon barrel length
	private int lineWidth; // width of the target and blocker
	int cannonBaseRadius; // cannon base radius
	boolean ballOnScreen = false;
	public MainView(Context context) {
		super(context);
		getHolder().addCallback(this);//SUPER IMPORTANT
	}

	public MainView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	// called by surfaceChanged when the size of the SurfaceView changes,
	// such as when it's first added to the View hierarchy
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		Log.v("surface", "surface size changed" + w + "," + h + "," + oldw
				+ "," + oldh);
		screenWidth = w; // store the width
		screenHeight = h; // store the height
		backgroundPaint = new Paint();
		backgroundPaint.setColor(Color.GRAY);
		textPaint = new Paint(); // Paint for drawing text
		textPaint.setTextSize(w / 20); // text size 1/20 of screen width
		textPaint.setColor(Color.WHITE);
		ballPaint = new Paint(); // Paint for drawing a cannonball
		ballPaint.setColor(Color.RED);
		cannonPaint = new Paint(); // Paint for drawing the cannon
		lineWidth = w / 24; // target and blocker 1/24 screen width
		cannonPaint.setStrokeWidth(lineWidth * 1.5f); // set line thickness
		cannonLength = w / 8; // cannon length 1/8 screen width
		barrelEnd = new Point(cannonLength,h/2);// endpoint of the cannon's barrel initially points horizontally
		cannonBaseRadius = h / 18; // cannon base radius 1/18 screen height
		cannonballRadius = w / 36; // cannonball radius 1/36 screen width
		cannonballSpeed = w * 3 / 2; // cannonball speed multiplier
		ball = new Point(); // create the cannonball as a point
		timeLeft = 30.0f;
	}

	public void newGame() {

		sqThread = new SquareThread(getHolder());
		sqThread.setRunning(true);
		sqThread.start();

	}

	public class SquareThread extends Thread {
		private SurfaceHolder surfaceHolder;

		public SquareThread(SurfaceHolder holder) {
			surfaceHolder = holder;
			setName("Square_Mover");
		}

		@Override
		public void run() {
			Canvas canvas = null; // used for drawing
			long previousFrameTime = System.currentTimeMillis();
			while (threadIsRunning) {
				try {
					canvas = surfaceHolder.lockCanvas();
					// lock the surfaceHolder for drawing
					synchronized (surfaceHolder) {
						long currentTime = System.currentTimeMillis();
						float elapsedTimeMS = currentTime - previousFrameTime;
						 totalElapsedTime += elapsedTimeMS / 1000.00;
						 updatePositions(elapsedTimeMS); // update game state
						drawSquares(canvas); // draw
						previousFrameTime = currentTime; // update previous time
					} // end synchronized block
				} // end try
				finally {
					if (canvas != null)
						surfaceHolder.unlockCanvasAndPost(canvas);
				} // end finally
			} // end while
		} // end method run

		void setRunning(boolean val) {
			threadIsRunning = val;
		}
		void updatePositions(float elapsedTimeMS)
		{
			float interval = elapsedTimeMS / 1000.0f; // convert to seconds
			timeLeft -= interval; // subtract from time left
			//update the ball trajectory

			if (ballOnScreen) // if there is currently a shot fired
			{
				// update cannonball position
				ball.x += interval * cannonballVelocityX;
				ball.y += interval * cannonballVelocityY;
						
					// check for collisions with left and right walls
				if (ball.x + cannonballRadius > screenWidth
						|| ball.x - cannonballRadius < 0)
					ballOnScreen = false; // remove cannonball from screen
					// check for collisions with top and bottom walls
				else if (ball.y + cannonballRadius > screenHeight
						|| ball.y - cannonballRadius < 0)
					ballOnScreen = false; // make the cannonball disappear
			}
		}
		void drawSquares(Canvas canvas) {
			// clear the background
			canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(),
					backgroundPaint);
			// display time remaining using the format provided by the r.string at location 30,50 with the color in textpaint
			 if (timeLeft>10)
				 textPaint.setColor(Color.WHITE);
			 else if (timeLeft > 5)
				 textPaint.setColor(Color.YELLOW);
			 else if (timeLeft > 0)
				 textPaint.setColor(Color.RED);
			 else
				 timeLeft = 20;
			 canvas.drawText(getResources().getString(R.string.time_format,timeLeft),30, 50, textPaint);
			 
			 if (ballOnScreen)//if is on the screen draw it. Draw it first so that it appear that it is actually
				 //coming out of the barrel
					canvas.drawCircle(ball.x, ball.y, cannonballRadius,
							ballPaint);
			// draw the cannon barrel
				canvas.drawLine(0, screenHeight / 2, barrelEnd.x, barrelEnd.y,
						cannonPaint);
				// draw the cannon base
				canvas.drawCircle(0, (int) screenHeight / 2, (int) cannonBaseRadius,
						cannonPaint);
				
		}
	}
	public void shoot(MotionEvent event)
	{
		if (ballOnScreen) // if a cannonball is already on the screen, allow only one shot
			return; // do nothing
		double angle = moveScope(event); // get the cannon barrel's angle
		// move the cannonball to be inside the cannon
		ball.x = cannonballRadius; // align x-coordinate with cannon
		ball.y = screenHeight / 2; // centers ball vertically
		// get the x component of the total velocity
		cannonballVelocityX = (int) (cannonballSpeed * Math.sin(angle));
		// get the y component of the total velocity
		cannonballVelocityY = (int) (-cannonballSpeed * Math.cos(angle));
		ballOnScreen = true; // the cannonball is on the screen, allow only one shot
		
		// play cannon fired sound
		//soundPool.play(soundMap.get(CANNON_SOUND_ID), 1, 1, 1, 0, 1f);
	}
	public double moveScope(MotionEvent event)
	{
		Point touchPoint = new Point((int) event.getX(), (int) event.getY());
		// compute the touch's distance from center of the screen
		// on the y-axis
		double centerMinusY = (screenHeight / 2 - touchPoint.y);
		double angle = 0; // initialize angle to 0
		// calculate the angle the barrel makes with the horizontal
		if (centerMinusY != 0) // prevent division by 0
			angle = Math.atan((double) touchPoint.x / centerMinusY);
		// if the touch is on the lower half of the screen
		if (touchPoint.y > screenHeight / 2)
			angle += Math.PI; // adjust the angle
			// calculate the endpoint of the cannon barrel
		barrelEnd.x = (int) (cannonLength * Math.sin(angle));
		barrelEnd.y = (int) (-cannonLength * Math.cos(angle) + screenHeight / 2);
		return angle;//used for the shoot position
	}
	
	//addcallback needed or this wont get called
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		Log.v("surface", "surface created");
		newGame();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		Log.v("surface", "surface changed");

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.v("surface", "surface destroyed");

	}

}
