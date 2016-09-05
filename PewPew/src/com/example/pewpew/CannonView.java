package com.example.pewpew;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CannonView extends SurfaceView implements SurfaceHolder.Callback {

	private CannonThread cannonThread; // controls the game loop
	private Activity activity; // to display Game Over dialog in GUI thread
	private boolean dialogIsDisplayed = false; // constants for game play
	public static final int TARGET_PIECES = 6; // sections in the target
	public static final int ROWS = 3;
	public static final int COLS = 2;
	public static final int MISS_PENALTY = 2; // seconds deducted on a miss
	public static final int HIT_REWARD = 3; // seconds added on a hit
	// variables for the game loop and tracking statistics
	private boolean gameOver; // is the game over?
	private double timeLeft; // the amount of time left in seconds
	private int shotsFired; // the number of shots the user has fired
	private double totalElapsedTime; // the number of seconds elapsed
	// variables for the blocker and target
	private Line blocker; // start and end points of the blocker
	private int blockerDistance; // blocker distance from left
	private int blockerBeginning; // blocker distance from top
	private int blockerEnd; // blocker bottom edge distance from top
	private int initialBlockerVelocity; // initial blocker speed multiplier
	private float blockerVelocity; // blocker speed multiplier during game
	private Line target; // start and end points of the target
	private int targetDistance, targetDistance2; // target distance from left,
													// targetDistance2 is the
													// distance of the other 3
													// rectangles
	private int targetBeginning; // target distance from top
	private double pieceLength; // length of a target piece
	private int targetEnd; // target bottom's distance from top
	private int initialTargetVelocity; // initial target speed multiplier
	private float targetVelocity; // target speed multiplier during game
	private int lineWidth; // width of the target and blocker
	private boolean[] hitStates; // is each target piece hit?
	private int targetPiecesHit; // number of target pieces hit (out of 7)
	// variables for the cannon and cannonball
	private Point cannonball; // cannonball image's upper-left corner
	private int cannonballVelocityX; // cannonball's x velocity
	private int cannonballVelocityY; // cannonball's y velocity
	private boolean cannonballOnScreen; // is the cannonball on the screen
	private int cannonballRadius; // cannonball radius
	private int cannonballSpeed; // cannonball speed
	private int cannonBaseRadius; // cannon base radius
	private int cannonLength; // cannon barrel length
	private Point barrelEnd; // the endpoint of the cannon's barrel
	private int screenWidth; // width of the screen
	private int screenHeight; // height of the screen
	// constants and variables for managing sounds
	private static final int TARGET_SOUND_ID = 0;
	private static final int CANNON_SOUND_ID = 1;
	private static final int BLOCKER_SOUND_ID = 2;
	private SoundPool soundPool; // plays sound effects
	private Map<Integer, Integer> soundMap; // maps IDs to SoundPool
	// Paint variables used when drawing each item on the screen
	private Paint textPaint; // Paint used to draw text
	private Paint cannonballPaint; // Paint used to draw the cannonball
	private Paint cannonPaint; // Paint used to draw the cannon
	private Paint blockerPaint; // Paint used to draw the blocker
	private Paint targetPaint; // Paint used to draw the target
	private Paint backgroundPaint; // Paint used to clear the drawing area
	private Vibrator vibrate;
	public static boolean startVibration = false;

	public CannonView(Context context) {
		super(context);// call super's constructor

		activity = (Activity) context;
		MainActivity.player.start();
		// register SurfaceHolder.Callback listener
		getHolder().addCallback(this);
		// initialize Lines and points representing game items
		blocker = new Line(); // create the blocker as a Line
		target = new Line(); // create the target as a Line
		cannonball = new Point(); // create the cannonball as a point
		// initialize hitStates as a boolean array
		hitStates = new boolean[TARGET_PIECES];
		// initialize SoundPool to play the app's three sound effects
		soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
		// create Map of sounds and pre-load sounds (load returns a sound_ID)
		soundMap = new HashMap<Integer, Integer>(); // create new HashMap
		soundMap.put(TARGET_SOUND_ID, soundPool.load(context, R.raw.target_hit, 1));
		soundMap.put(CANNON_SOUND_ID, soundPool.load(context, R.raw.cannon_fire, 1));
		soundMap.put(BLOCKER_SOUND_ID, soundPool.load(context, R.raw.block_hit, 1));

		// construct Paints for drawing text, cannonball, cannon,
		// blocker and target; these are configured in method onSizeChanged
		textPaint = new Paint(); // Paint for drawing text
		cannonPaint = new Paint(); // Paint for drawing the cannon
		cannonballPaint = new Paint(); // Paint for drawing a cannonball
		blockerPaint = new Paint(); // Paint for drawing the blocker
		targetPaint = new Paint(); // Paint for drawing the target
		backgroundPaint = new Paint(); // Paint for drawing the target
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (!dialogIsDisplayed) {
			cannonThread = new CannonThread(holder);
			cannonThread.setRunning(true);
			cannonThread.start(); // start the game loop thread
		} // end if
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// ensure that thread terminates properly
		boolean retry = true;
		cannonThread.setRunning(false);
		while (retry) {
			try {
				cannonThread.join();
				retry = false;
			} // end try
			catch (InterruptedException e) {

			} // end catch
		} // end while

	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);

		screenWidth = w; // store the width
		screenHeight = h; // store the height
		cannonBaseRadius = h / 18; // cannon base radius 1/18 screen height
		cannonLength = w / 8; // cannon length 1/8 screen width

		cannonballRadius = w / 36; // cannonball radius 1/36 screen width
		cannonballSpeed = w * 3 / 2; // cannonball speed multiplier

		lineWidth = w / 24; // target and blocker 1/24 screen width

		// configure instance variables related to the blocker
		blockerDistance = w * 5 / 8; // blocker 5/8 screen width from left
		blockerBeginning = h / 8; // distance from top 1/8 screen height
		blockerEnd = h * 3 / 8; // distance from top 3/8 screen height
		initialBlockerVelocity = h / 2; // initial blocker speed multiplier
		blocker.start = new Point(blockerDistance, blockerBeginning);
		blocker.end = new Point(blockerDistance, blockerEnd);

		// configure instance variables related to the target
		targetDistance = w * 7 / 8; // target 7/8 screen width from left
		targetDistance2 = (int) (targetDistance - 200);
		targetBeginning = h / 8; // distance from top 1/8 screen height
		targetEnd = h * 7 / 8; // distance from top 7/8 screen height
		pieceLength = (targetEnd - targetBeginning) / (TARGET_PIECES);
		initialTargetVelocity = -h / 4; // initial target speed multiplier
		target.start = new Point(targetDistance, targetBeginning);
		target.end = new Point(targetDistance, targetBeginning); // endpoint
																	// of
																	// the
		// cannon's barrel
		// initially points
		// horizontally
		barrelEnd = new Point(cannonLength, h / 2); // configure Paint objects
													// for drawing game elements
		textPaint.setTextSize(w / 20); // text size 1/20 of screen width
		textPaint.setAntiAlias(true); // smoothes the text
		cannonPaint.setStrokeWidth(lineWidth * 1.5f); // set line thickness
		blockerPaint.setStrokeWidth(lineWidth); // set line thickness
		targetPaint.setStrokeWidth(lineWidth + 100); // set line thickness
		backgroundPaint.setColor(Color.WHITE); // set background color
		newGame(); // set up and start a new game
	}

	// stops the game
	public void stopGame() {
		if (cannonThread != null)
			cannonThread.setRunning(false);
		MainActivity.player.stop();
	} // end method stopGame
		// releases resources; called by
		// CannonGame's onDestroy method

	public void releaseResources() {
		soundPool.release(); // release all resources used by the SoundPool
		soundPool = null;
		MainActivity.player.release();
		MainActivity.player = null;
	} // end method releaseResources

	public void newGame() {

		// set every element of hitStates to false--restores target pieces
		for (int i = 0; i < TARGET_PIECES; ++i) {
			hitStates[i] = false;
		}
		targetPiecesHit = 0; // no target pieces have been hit
		blockerVelocity = initialBlockerVelocity; // set initial velocity
		targetVelocity = initialTargetVelocity; // set initial velocity
		timeLeft = 0; // start the countdown at 10 seconds
		cannonballOnScreen = false; // the cannonball is not on the screen
		shotsFired = 0; // set the initial number of shots fired
		totalElapsedTime = 0.0; // set the time elapsed to zero
		blocker.start.set(blockerDistance, blockerBeginning);
		blocker.end.set(blockerDistance, blockerEnd);
		target.start.set(targetDistance, targetBeginning);
		// target.end.set(targetDistance, targetEnd);
		target.end = new Point(targetDistance, targetBeginning + 600);
		if (gameOver) {
			gameOver = false; // the game is not over
			MainActivity.player.stop();
			
			cannonThread = new CannonThread(getHolder());
			cannonThread.start();
		} // end if
	}

	private void updatePositions(double elapsedTimeMS) {
		double interval = elapsedTimeMS / 1000.0; // convert to seconds
		if (cannonballOnScreen) { // if there is currently a shot fired
			// update cannonball position
			cannonball.x += interval * cannonballVelocityX;
			cannonball.y += interval * cannonballVelocityY;
			// check for collision with blocker, but not too far
			if (cannonball.x + cannonballRadius > blockerDistance && cannonball.x - cannonballRadius < blockerDistance
					&& cannonball.y + cannonballRadius > blocker.start.y
					&& cannonball.y - cannonballRadius < blocker.end.y) {
				cannonballVelocityX *= -1; // reverse cannonball's direction
				// timeLeft -= MISS_PENALTY; // penalize the user
				// play sound:ID, left Vol, right Vol, priority, loop, rate
				soundPool.play(soundMap.get(BLOCKER_SOUND_ID), 1, 1, 1, 0, 1f);
			} // end if
				// check for collisions with left and right walls
			else if (cannonball.x + cannonballRadius > screenWidth || cannonball.x - cannonballRadius < 0) {
				cannonballOnScreen = false; // remove cannonball from screen
			} // check for collisions with top and bottom walls
			else if (cannonball.y + cannonballRadius > screenHeight || cannonball.y - cannonballRadius < 0) {
				cannonballOnScreen = false; // make the cannonball disappear
			} else if (cannonball.x + cannonballRadius > targetDistance2
					&& cannonball.x - cannonballRadius < targetDistance2
					&& cannonball.y + cannonballRadius > target.start.y
					&& cannonball.y - cannonballRadius < target.end.y) {
				int pos = (int) ((cannonball.y - target.start.y) / 200) + 3;

				// check if the piece hasn't been hit yet
				if ((pos >= 0 && pos < TARGET_PIECES) && !hitStates[pos]) {
					hitStates[pos] = true; // section was hit
					cannonballOnScreen = false; // remove cannonball
					timeLeft += HIT_REWARD; // add reward to remaining time

					// play target hit sound
					soundPool.play(soundMap.get(TARGET_SOUND_ID), 1, 1, 1, 0, 1f);

					// if all pieces have been hit
					if (++targetPiecesHit == TARGET_PIECES) {
						cannonThread.setRunning(false);
						showGameOverDialog(R.string.win); // show winning dialog
						gameOver = true; // the game is over
					} // end if
				} // end if
			} // end elseif
				// check for cannonball collision with target
			else if (cannonball.x + cannonballRadius > targetDistance
					&& cannonball.x - cannonballRadius < targetDistance
					&& cannonball.y + cannonballRadius > target.start.y
					&& cannonball.y - cannonballRadius < target.end.y) {

				int section = (int) ((cannonball.y - target.start.y) / 200);

				// check if the piece hasn't been hit yet
				if (section >= 0 && section < TARGET_PIECES && !hitStates[section]) {
					hitStates[section] = true; // section was hit
					cannonballOnScreen = false; // remove cannonball
					// timeLeft += HIT_REWARD; // add reward to remaining
					// time
					// play target hit sound
					soundPool.play(soundMap.get(TARGET_SOUND_ID), 1, 1, 1, 0, 1f);
					
					// if all pieces have been hit
					if (++targetPiecesHit == TARGET_PIECES) {
						cannonThread.setRunning(false);
						showGameOverDialog(R.string.win); // show winning
															// dialog
						gameOver = true; // the game is over
					} // end if
				} // end if
			} // end elseif
		} // end if

		// update the blocker's position
		double blockerUpdate = interval * blockerVelocity;
		blocker.start.y += blockerUpdate;
		blocker.end.y += blockerUpdate;
		// update the target's position
		double targetUpdate = interval * targetVelocity;
		target.start.y += targetUpdate;
		target.end.y += targetUpdate;
		// if the blocker hit the top or bottom, reverse direction
		if (blocker.start.y < 0 || blocker.end.y > screenHeight)
			blockerVelocity *= -1;
		// if the target hit the top or bottom, reverse direction
		if (target.start.y < 0 || target.end.y > screenHeight)
			targetVelocity *= -1;

		timeLeft += interval; // subtract from time left

		// if the timer reached zero
		// if (timeLeft <= 0.0) {
		// timeLeft = 0.0;
		// gameOver = true; // the game is over
		// cannonThread.setRunning(false);
		// showGameOverDialog(R.string.lose); // show the losing dialog
		// } // end if

	}

	// fires a cannonball
	public void fireCannonball(MotionEvent event) {
		if (cannonballOnScreen) // if a cannonball is already on the screen
			return; // do nothing

		double angle = alignCannon(event); // get the cannon barrel's angle
		// move the cannonball to be inside the cannon
		cannonball.x = cannonballRadius; // align x-coordinate with cannon
		cannonball.y = screenHeight / 2; // centers ball vertically
		// get the x component of the total velocity
		cannonballVelocityX = (int) (cannonballSpeed * Math.sin(angle));
		// get the y component of the total velocity
		cannonballVelocityY = (int) (-cannonballSpeed * Math.cos(angle));
		cannonballOnScreen = true; // the cannonball is on the screen
		++shotsFired; // increment shotsFired // play cannon fired sound
		vibrate = (Vibrator) activity.getSystemService(Context.VIBRATOR_SERVICE);
		vibrate.vibrate(200);
		soundPool.play(soundMap.get(CANNON_SOUND_ID), 1, 1, 1, 0, 1f);
		System.out.println("P_Length: " + pieceLength);
	} // end method fireCannonball

	// aligns the cannon in response to a user touch
	public double alignCannon(MotionEvent event) {
		// get the location of the touch in this view
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
		return angle; // return the computed angle
	} // end method alignCannon

	// Draw Game elements on canvas
	public void drawGameElements(Canvas canvas) {
		// clear the background
		canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), backgroundPaint);
		// display time remaining
		canvas.drawText(getResources().getString(R.string.time_remaining_format, timeLeft), 30, 50, textPaint);
		if (cannonballOnScreen)
			canvas.drawCircle(cannonball.x, cannonball.y, cannonballRadius, cannonballPaint);
		// draw the cannon barrel
		canvas.drawLine(0, screenHeight / 2, barrelEnd.x, barrelEnd.y, cannonPaint);
		// draw the cannon base
		canvas.drawCircle(0, (int) screenHeight / 2, (int) cannonBaseRadius, cannonPaint);
		// draw the blocker
		canvas.drawLine(blocker.start.x, blocker.start.y, blocker.end.x, blocker.end.y, blockerPaint);
		Point currentPoint = new Point(); // start of current target section
		// initialize curPoint to the starting point of the target
		currentPoint.x = target.start.x;
		currentPoint.y = target.start.y;
		// draw the target
		for (int i = 1; i <= TARGET_PIECES; ++i) {
			// if this target piece is not hit, draw it
			if (!hitStates[i - 1]) {
				// if (i % 2 == 0)
				// targetPaint.setColor(Color.YELLOW);
				// else
				// targetPaint.setColor(Color.BLUE);
				targetPaint.setColor(colorLine()); //making the colors way more awesome!!!
				canvas.drawLine(currentPoint.x, currentPoint.y, currentPoint.x, (int) (currentPoint.y + 200),
						targetPaint);

			} // end if
				// move curPoint to the start of the next piece
			currentPoint.y += 200;
			if (i == 3) {
				currentPoint.x -= 200;
				currentPoint.y -= 600;
			}

		}
	} // end method drawGameElements

	public int colorLine() {
		Random r = new Random();
		int color = r.nextInt(7);
		switch (color) {
		case 0:
			return Color.RED;
		case 1:
			return Color.YELLOW;
		case 2:
			return Color.BLUE;
		case 3:
			return Color.CYAN;
		case 4:
			return Color.GREEN;
		case 5:
			return Color.LTGRAY;
		}
		return -1;
	}

	// display an AlertDialog when the game ends
	private void showGameOverDialog(int messageId) {
		// create a dialog displaying the given String
		final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
		dialogBuilder.setTitle(getResources().getString(messageId));
		dialogBuilder.setCancelable(false);
		// display number of shots fired and total time elapsed
		dialogBuilder.setMessage(getResources().getString(R.string.results_format, shotsFired, totalElapsedTime));
		dialogBuilder.setPositiveButton(R.string.reset_game, new DialogInterface.OnClickListener() {
			// called when "Reset Game" Button is pressed
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialogIsDisplayed = false;
				newGame(); // set up and start a new game
				try {
					MainActivity.player.prepare();
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				MainActivity.player.start();
			} // end method onClick
		} // end anonymous inner class
		); // end call to setPositiveButton
		activity.runOnUiThread(new Runnable() {
			public void run() {
				dialogIsDisplayed = true;
				dialogBuilder.show(); // display the dialog
			} // end method run
		} // end Runnable
		); // end call to runOnUiThread
	} // end method showGameOverDialog

	private class CannonThread extends Thread {
		private SurfaceHolder surfaceHolder; // for manipulating canvas
		private boolean threadIsRunning = true; // running by default
		// initializes the surface holder

		public CannonThread(SurfaceHolder holder) {
			surfaceHolder = holder;
			setName("CannonThread");
		} // end constructor
			// changes running state

		public void setRunning(boolean running) {
			threadIsRunning = running;
		} // end method setRunning

		// controls the game loop
		@Override
		public void run() {
			Canvas canvas = null; // used for drawing
			long previousFrameTime = System.currentTimeMillis();
			while (threadIsRunning) {
				try {
					canvas = surfaceHolder.lockCanvas(null);
					// lock the surfaceHolder for drawing
					synchronized (surfaceHolder) {
						long currentTime = System.currentTimeMillis();
						double elapsedTimeMS = currentTime - previousFrameTime;
						totalElapsedTime += elapsedTimeMS / 1000.00;
						updatePositions(elapsedTimeMS); // update game state
						drawGameElements(canvas); // draw
						previousFrameTime = currentTime; // update previous time
					} // end synchronized block
				} // end try
				finally {
					if (canvas != null)
						surfaceHolder.unlockCanvasAndPost(canvas);
				} // end finally
			} // end while
		} // end method run
	} // end nested class CannonThread
}