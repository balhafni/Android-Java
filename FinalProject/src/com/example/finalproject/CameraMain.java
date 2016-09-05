package com.example.finalproject;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

public class CameraMain extends Activity {
	Button takePic, manualBtn;
	public static ImageView img;
	Camera camera;
	CameraPicLayout lay;
	int whichCamera;
	boolean hasFrontCam;
	boolean picTakenAlready;
	FrameLayout preview;
	SoundPool soundPool;
	Map<Integer, Integer> soundMap;
	private static final int TAKE_PIC_ID = 1;
	public static boolean finished = false;
	Vibrator vibrate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.camera_layout);
		takePic = (Button) findViewById(R.id.takePic);
		img = (ImageView) findViewById(R.id.takenPicView);
		img.setAlpha(0f);
		takePic.setOnClickListener(takePicClick);
		picTakenAlready = false;
		preview = (FrameLayout) findViewById(R.id.frameLayout1);
		soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
		vibrate = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
		// create Map of sounds and pre-load sounds (load returns a sound_ID)
		soundMap = new HashMap<Integer, Integer>(); // create new HashMap
		soundMap.put(TAKE_PIC_ID, soundPool.load(this, R.raw.blocker_hit, 1));
		startCamera();

	}

	private int findFrontFacingCamera() {
		int cameraId = -1;
		// Search for the front facing camera
		int numberOfCameras = Camera.getNumberOfCameras();
		for (int i = 0; i < numberOfCameras; i++) {
			CameraInfo info = new CameraInfo();
			Camera.getCameraInfo(i, info);
			if (i == CameraInfo.CAMERA_FACING_FRONT) {
				Log.v("tagg", "Camera found");
				cameraId = i;
				break;
			}
		}
		return cameraId;
	}

	@Override
	public void finish() {
		finished = true;
		super.finish();
	}

	public void startCamera() {

		try {
			whichCamera = findFrontFacingCamera();
			camera = Camera.open(whichCamera);
		} catch (Exception e) {
			Toast.makeText(CameraMain.this, "Could not open camera", Toast.LENGTH_SHORT).show();
			return;
		}
		Parameters parameters = camera.getParameters();// get the parameters of
														// the came
		parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);// set
																					// the
																					// focus
																					// type
		// camera.setParameters(parameters);// save them to the camera
		Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();// getWindowManager().getDefaultDisplay();

		if (display.getRotation() == 0)
			lay = new CameraPicLayout(CameraMain.this, camera, 90);
		else if (display.getRotation() == 1)
			lay = new CameraPicLayout(CameraMain.this, camera, 0);
		preview.addView(lay);
	}

	OnClickListener takePicClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			vibrate.vibrate(200);
			// play sound: ID, left Vol, right Vol, priority, loop, rate
			soundPool.play(soundMap.get(TAKE_PIC_ID), 1, 1, 1, 0, 1f);
			camera.takePicture(null, null, tookIt);
			picTakenAlready = true;
		}
	};
	// for the capture image that comes from camera
	private PictureCallback tookIt = new PictureCallback() {

		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			camera.stopPreview();
			Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
			if (bitmap == null) {
				Toast.makeText(getApplicationContext(), "image not taken", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(getApplicationContext(), "image taken", Toast.LENGTH_SHORT).show();
				img.setScaleX(1 / 10);
				img.setScaleY(1 / 20);
				img.setImageBitmap(rotateImage(bitmap));
			}
			preview.setVisibility(View.INVISIBLE);
			finish();
			savePic();
			// camera.startPreview(); //if we want to start it again after
			// taking pic
			// so we do this if the person wants to take another pic
		}
	};

	public void savePic() {

	}

	public Bitmap rotateImage(Bitmap bitmap) {
		if (bitmap.getWidth() <= bitmap.getHeight()) {
			return bitmap;
		} else {
			Matrix mat = new Matrix();
			mat.postRotate(270);
			Bitmap bMapRotate = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), mat, true);
			return bMapRotate;
		}
	}

	public void setImage(Bitmap bitmap) {

		Matrix mat = new Matrix();
		mat.postRotate(90);
		Bitmap bMapRotate = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), mat, true);
		// bMapRotate.createScaledBitmap(bMapRotate, 50, 50, true);
		img.setImageBitmap(bMapRotate);
	}

	public static ImageView getImage() {
		return img;
	}
}