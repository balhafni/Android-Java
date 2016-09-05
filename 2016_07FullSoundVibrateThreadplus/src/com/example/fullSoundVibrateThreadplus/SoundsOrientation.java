package com.example.fullSoundVibrateThreadplus;

import java.io.IOException;
import java.util.HashMap;









import android.app.Activity;
import android.content.Intent;
import android.content.Loader;
import android.content.Loader.OnLoadCompleteListener;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class SoundsOrientation extends Activity {
	TextView text;
	Button mainSoundbtn,stopBtn,sound2btn,sound3btn,sound4btn,sound5btn;
	MediaPlayer mp;
	boolean isMainPlaying = false;
	SoundPool sound_pool;
	SparseIntArray soundMap;
	int width,height;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sound_layout);
		//get references
		mainSoundbtn = (Button)findViewById(R.id.mainSound);
		stopBtn = (Button)findViewById(R.id.stopBTN);
		sound2btn = (Button)findViewById(R.id.sound2Btn);
		sound3btn = (Button)findViewById(R.id.sound3Btn);
		sound4btn = (Button)findViewById(R.id.sound4Btn);
		sound5btn = (Button)findViewById(R.id.sound5Btn);
		
		text = (TextView)findViewById(R.id.textView1);
		//set the location of each button
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		width = dm.widthPixels;
		height = dm.heightPixels;
		int dens = dm.densityDpi;
		Log.e("metrics:",width+","+height);
		getWindowManager().getDefaultDisplay().getRealMetrics(dm);//api >16
		
		width = dm.widthPixels;
		height = dm.heightPixels;
		dens = dm.densityDpi;
		Log.e("real metrics:",width+","+height);
		
		//use the layout that corresponds to the layout that the view is using
		//set the width and height values, in pixels
		/*does not work for linear layouts, since they orientation is either vertical or horizontal
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        or
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int)(width*.4),(int)(height*.4));//percentages
        params.leftMargin = 50; //in  pixels
        view.setLayoutParams(params);
        */
		/* does not work in relative layout since it uses the relation between views
		 * 
		 
		RelativeLayout.LayoutParams mainparams = new RelativeLayout.LayoutParams((int)(width*.2),(int)(height*.2));
		//mainparams.addRule(RelativeLayout.BELOW, someOtherView.getId())
		mainparams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		//set the x and y location, in pixels.
		mainparams.setMargins((int)(width*.2), (int)(height*.4),0,0);
		//set the layout to the view 
		mainSoundbtn.setLayoutParams(mainparams);
		*/
		//setting the framelayout:
		if (height > width)
			setPortraitLayoutPlease();
		else 
			setLandscapePLZ();
		
		/* one sound
		 * doing it this way it calls prepare() automatically 
		 * audio CANNOT BE CHANGED
		 */
		 //mp = MediaPlayer.create(MainActivity.this, R.raw.background_music);
		/*setting it manually
		 * 
		 */
		 //reset it to be reused
		 mp = new MediaPlayer();
		 mp.reset();
		 //set the source ( the song)
		 //Uniform Resource Identifier is used to "identify a resource"
		 Uri theUri = Uri.parse("android.resource://"+
				 	  SoundsOrientation.this.getPackageName()+
				 	  "/"+R.raw.background_music);
		 try {
			mp.setDataSource(SoundsOrientation.this ,theUri);
			//set it to be ready for playback, this one blocks until is done(not recommended for streams or large files)
			//mp.prepare();
			mp.prepareAsync();//doesnt block, mainly for streams
			mp.setOnPreparedListener(okStart);//start it when is done,
			mp.setOnCompletionListener(mainDone);
			//calling play when is not done can cause the app to crash
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//set the pool if there is multiple sounds
		// initialize SoundPool to play the app's three sound effects
		// for api 21 and up
		 AudioAttributes audioatts = new AudioAttributes.Builder().
		 							setUsage(AudioAttributes.USAGE_GAME).
		 							setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build(); 
				 
		 sound_pool =new SoundPool.Builder().
				 	 setMaxStreams(6).setAudioAttributes( audioatts).build(); //SoundPool(1, AudioManager.STREAM_MUSIC, 0);
		 
		 //sound_pool = new SoundPool(4,AudioManager.STREAM_MUSIC,0);
	      // create HashMap of sounds and pre-load sounds
		  	 
	     // soundMap = new SparseIntArray();// new HashMap<Integer, Integer>(); // //memory efficient create new HashMap
	      HashMap<Integer,Integer> soundMap_H= new HashMap<Integer, Integer>();
	      soundMap = new SparseIntArray();
	      soundMap.put(1, sound_pool.load(SoundsOrientation.this, R.raw.sound1, 1));
	      soundMap.put(2,sound_pool.load(SoundsOrientation.this, R.raw.sound2, 1));
	      soundMap.put(3,sound_pool.load(SoundsOrientation.this, R.raw.sound3, 1));
	      soundMap.put(4, sound_pool.load(SoundsOrientation.this, R.raw.clap, 1));
	      sound_pool.setOnLoadCompleteListener(poolDone);
		//set the buttons
		 mainSoundbtn.setOnClickListener(playMe);
		 stopBtn.setOnClickListener(playMe);
		 sound2btn.setOnClickListener(playMe);
		 sound3btn.setOnClickListener(playMe);
		 sound4btn.setOnClickListener(playMe);
		 sound5btn.setOnClickListener(playMe);
	}
	
	OnClickListener playMe = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if (v.getId() == R.id.mainSound)
			{
				if (isMainPlaying)
				{
					mp.pause();
					mainSoundbtn.setText("play sound");
					isMainPlaying = false;
				}
				else
				{
					mp.start();//this resumes and starts from the beginning as well
					mainSoundbtn.setText("pause sound");
					isMainPlaying = true;
					
				}
			}
			else if (v.getId() == R.id.stopBTN)
			{
				if (mp.isPlaying())
				{
					//if it is playing , stop and release resources
					//sound_pool.stop(1);
					
					//do NOT call stop(), stop will make the mp go into stopped state and prepare needs to be set
					//mp.stop()
					//do not call reset, since it makes the mp go into idle state,unless you want to change the song
					//by calling setDataSource()
					//mp.reset();
					mp.pause();
					mp.seekTo(0);
					mainSoundbtn.setText("play sound");
					isMainPlaying = false;
				}
			}
			else if (v.getId() == R.id.sound2Btn)
			{
				 
			 //id, left n right vol, priority,loop? , -1 loop forever , speed(pitch)
				sound_pool.play(1,  1, 1, 1 , 0, 1f);
				
			}
			else if (v.getId() == R.id.sound3Btn)
			{
				sound_pool.play(2,  1, 1, 1 , 0, 1f);
			}
			else if (v.getId() == R.id.sound4Btn)
			{
				sound_pool.play(3,  1, 1, 1 , 0, 1f);
			}
			else if (v.getId() == R.id.sound5Btn)
			{
				sound_pool.play(4,  1, 1, 1 , 0, 1f);
			}
			
		}
	};
	void setPortraitLayoutPlease()
	{
		
		FrameLayout.LayoutParams mainparam = new FrameLayout.LayoutParams((int)(width*.24),(int)(height*.15));
		mainparam.setMargins((int)(width*.38), (int)(height*.1),0,0);
		mainSoundbtn.setLayoutParams(mainparam);
		// stop btn
		mainparam = new FrameLayout.LayoutParams((int)(width*.24),(int)(height*.15));
		mainparam.setMargins((int)(width*.72), (int)(height*.1),0,0);
		stopBtn.setLayoutParams(mainparam);
		//second btn
		mainparam = new FrameLayout.LayoutParams((int)(width*.24),(int)(height*.15));
		mainparam.setMargins((int)(width*.04), (int)(height*.3),0,0);
		sound2btn.setLayoutParams(mainparam);
		//3rd button
		mainparam = new FrameLayout.LayoutParams((int)(width*.24),(int)(height*.15));
		mainparam.setMargins((int)(width*.38), (int)(height*.3),0,0);
		sound3btn.setLayoutParams(mainparam);
		//fourth button
		mainparam = new FrameLayout.LayoutParams((int)(width*.24),(int)(height*.15));
		mainparam.setMargins((int)(width*.72), (int)(height*.3),0,0);
		sound4btn.setLayoutParams(mainparam);
		//fifth button
	    mainparam = new FrameLayout.LayoutParams((int)(width*.24),(int)(height*.15));
		mainparam.setMargins((int)(width*.38), (int)(height*.5),0,0);
		sound5btn.setLayoutParams(mainparam);
		
		//text
		mainparam = new FrameLayout.LayoutParams((int)(width*.8),(int)(height*.15));
		mainparam.setMargins((int)(width*.1), (int)(height*.0),0,0);
		text.setLayoutParams(mainparam);
		
		
	}
	void setLandscapePLZ()
	{
		FrameLayout.LayoutParams mainparam = new FrameLayout.LayoutParams((int)(height*.1),(int)(height*.1));
		mainparam.setMargins((int)(width*.3), (int)(height*.1),0,0);
		mainSoundbtn.setLayoutParams(mainparam);
		// stop btn
		mainparam = new FrameLayout.LayoutParams((int)(width*.2),(int)(height*.1));
		mainparam.setMargins((int)(width*.3), (int)(height*.1),0,0);
		stopBtn.setLayoutParams(mainparam);
		//second btn
		mainparam = new FrameLayout.LayoutParams((int)(width*.2),(int)(height*.1));
		mainparam.setMargins((int)(width*.1), (int)(height*.3),0,0);
		sound2btn.setLayoutParams(mainparam);
		//3rd button
		mainparam = new FrameLayout.LayoutParams((int)(width*.2),(int)(height*.1));
		mainparam.setMargins((int)(width*.3), (int)(height*.3),0,0);
		sound3btn.setLayoutParams(mainparam);
		//fourth button
		mainparam = new FrameLayout.LayoutParams((int)(width*.2),(int)(height*.1));
		mainparam.setMargins((int)(width*.5), (int)(height*.3),0,0);
		sound4btn.setLayoutParams(mainparam);
		//fifth button
	    mainparam = new FrameLayout.LayoutParams((int)(width*.2),(int)(height*.1));
		mainparam.setMargins((int)(width*.7), (int)(height*.3),0,0);
		sound5btn.setLayoutParams(mainparam);
		
		//text
		mainparam = new FrameLayout.LayoutParams((int)(height*.8),(int)(height*.15));
		mainparam.setMargins((int)(height*.1), (int)(height*.7),0,0);
		text.setLayoutParams(mainparam);
		
	}
	
	private SoundPool.OnLoadCompleteListener poolDone = new SoundPool.OnLoadCompleteListener()
	{
		@Override
		public void onLoadComplete(SoundPool soundPool, int sampleId, int status)
	    {
	        text.setText(text.getText()+" sound:"+sampleId+" complete");
	        
	    }
	};
	  
	private OnCompletionListener mainDone = new OnCompletionListener() {
		
		@Override
		public void onCompletion(MediaPlayer mp) {
			isMainPlaying = false;
			mainSoundbtn.setText("play sound");
			//or set an endless loop
			//mp.seekTo(0);
	    	//mp.start();
		}
	};
	private OnPreparedListener okStart = new OnPreparedListener() {
		
		@Override
		public void onPrepared(MediaPlayer mp) {
			mp.start();
			mainSoundbtn.setText("pause sound");
			isMainPlaying = true;
		}
	};
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.soundss, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent inteent = new Intent(SoundsOrientation.this, TestThreads.class);
		startActivity(inteent);
		return super.onOptionsItemSelected(item);
	}
}
