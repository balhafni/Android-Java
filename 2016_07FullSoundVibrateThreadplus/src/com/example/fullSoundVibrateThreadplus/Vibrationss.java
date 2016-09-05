package com.example.fullSoundVibrateThreadplus;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Vibrationss extends Activity{
	Button oncevib,longvib,pulsevib,cancelvib;
	Vibrator brrr;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vibrate_layout);
		oncevib = (Button)findViewById(R.id.onceBtn);
		longvib = (Button)findViewById(R.id.longBtn);
		pulsevib = (Button)findViewById(R.id.pulseBtn);
		cancelvib = (Button)findViewById(R.id.cancelBtn);
		
		oncevib.setOnClickListener(vibs);
		longvib.setOnClickListener(vibs);
		pulsevib.setOnClickListener(vibs);
		cancelvib.setOnClickListener(vibs);
		
		
		    
		Vibrator brrr = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
		 
		 if (brrr.hasVibrator()) 
		 {
		    Log.v("Can Vibrate", "YES");
		 }
		 else
			{
		    Log.v("Can Vibrate", "NO");
		    }
	}
	OnClickListener vibs = new OnClickListener() {
		
		
		 
		@Override
		public void onClick(View v) {
		int id = v.getId();
		switch(id)
		{
		case R.id.onceBtn:
			//vibrate once
			
			 // Vibrate for 500 milliseconds
			 brrr.vibrate(500);
			break;
		
		case R.id.longBtn:
			//long vibrate
			long[] patterns = {0, 100, 1000,200,1000,100,1000,50,1000,25,1000,12};
			brrr.vibrate(patterns, -1);
			break;
			
		case R.id.pulseBtn:
			//pulse vibration
			// Start without a delay
			// Vibrate for 100 milliseconds
			// Sleep for 1000 milliseconds
			long[] pattern = {0, 100, 1000};
			
			// The '0' here means to repeat indefinitely
			// '0' is actually the index at which the pattern keeps repeating from (the start)
			// To repeat the pattern from any other point, you could increase the index, e.g. '1'
			//-1 means no repeat
			brrr.vibrate(pattern, 0);
			break;
		
		case R.id.cancelBtn:
			brrr.cancel();
			break;
		}
			
		}
	};
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.vibs, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		//int id = item.getItemId();
		//if (id == R.id.action_settings) {
		//	return true;
		//}
		Intent toVibrations = new Intent(Vibrationss.this,SoundsOrientation.class);
		startActivity(toVibrations);
		return super.onOptionsItemSelected(item);
	}

}
