package com.example.lecture08;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	Button oncevib,longvib,pulsevib,cancelvib;
	Vibrator brrr;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vibrate_layout);
		
		/*MAKE SURE THE PERMISSION IS ADDED TO THE MANIFEST*/
		
		oncevib = (Button)findViewById(R.id.onceBtn);
		longvib = (Button)findViewById(R.id.patternBtn);
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
		
		case R.id.patternBtn:
			//pattern vibration
			//first value(0) is the wait time(delay) before it starts the pattern
			//second (100) is how long it will vibrate
			//3rd value (1000) is how long it will be off, 4th how long on,5th how long off, etc. etc. 
			long[] patterns = {0, 100, 1000,200,500,100,300,50,800,25,1000,2000};
			/* first value is the pattern, second is the repeat count
			*  if second value is -1, that means NO repetition of the pattern
			*  if the second value is NOT -1, say 0 then it WILL repeat forever starting at the location 0 of the pattern 
			*  if the second value is NOT -1, say 1 then it WILL repeat forever starting at the location 1 of the pattern 
			*  if the second value is NOT -1, say 5 then it WILL repeat forever starting at the location 5 of the pattern 
			*/ 
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
			//cancel all vibrations , infine or not
			brrr.cancel();
			break;
		}
			
		}
	};
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings)
		{
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
