package com.example.fullSoundVibrateThreadplus;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TestThreads extends Activity {

	Button btn, counterBtn;
	TextView tView, counterTextView;
	int threadAmount = 0;
	int counterAmount = 0;
	double million = 100000000000000l;

	View vv;
	Timer t;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.threads_layout);

		btn = (Button) findViewById(R.id.button1);
		counterBtn = (Button) findViewById(R.id.button2);

		tView = (TextView) findViewById(R.id.textView1);
		counterTextView = (TextView) findViewById(R.id.textView2);

		counterBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				
					//while(million >0)
					//	million--;
				
				counterAmount++;
				counterTextView.setText(counterAmount + "");

			}
		});
		// this is the way blocking the UI
		
		/*  btn.setOnClickListener(new View.OnClickListener() {
		  
		  @Override public void onClick(View v) 
		  { 
			  try 
			  { 
				  Thread.sleep(5000); 
			  }
		 catch (InterruptedException e) 
		{ // TODO Auto-generated catch block
		  e.printStackTrace(); 
		  } 
			  threadAmount++; 
			  btn.setText(threadAmount+"");
		  
		 } });
		 */
		// this is the way to get try to change a UI view NOT using the ui
		// thread ( the only one that can change the UI views)

		
		/*btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				v.setEnabled(false);
				vv = v;
				Thread kk = new Thread(new Runnable() {

					@Override
					public void run() {
						try {
							Thread.sleep(5000);
						} catch (InterruptedException e) { // TODO
															// Auto-generated
															// catch block
							e.printStackTrace();
						}
						threadAmount++;
						tView.setText(threadAmount + "");
						vv.setEnabled(true);
					}
				});
				kk.start();
			}
		});
		 */
		// this is the way with threads, non-blocking the UI
		
	/*	btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				v.setEnabled(false);
				vv = v;
				Thread kk = new Thread(new Runnable() {

					@Override
					public void run() {
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) { // TODO
															// Auto-generated
															// catch block
							e.printStackTrace();
						}
						threadAmount++;
						TestThreads.this.runOnUiThread(new Runnable() {

							@Override
							public void run() {
								try {
									Thread.sleep(3000);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								tView.setText(threadAmount + "");
								vv.setEnabled(true);
							}
						});

					}
				});
				kk.start();

			}
		});
		 */
		// handler way not blocking the UI
		
	/*	btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				v.setEnabled(false);
				vv = v;
				Handler hand = new Handler();
				hand.postDelayed(new Runnable() {

					@Override
					public void run() {
						threadAmount++;
						tView.setText(threadAmount + "");
						vv.setEnabled(true);

					}
				}, 2000);

			}
		});
		*/ 
		// want something to happen over and over use Timer
		// NOTE: handler is the better way to do since it is native to android
		// timertask is known to have memory leak problems and it is not
		// recomended to be used
		
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				v.setEnabled(false);
				vv = v;
				t = new Timer();
				// Timer one time call
				t.schedule(new TimerTask() {

					@Override
					public void run() {
						threadAmount++;
						// timertask is not the UI thread, using the post on the
						// view ( slightly different way of calling post)
						tView.post(new Runnable() {

							@Override
							public void run() {

								tView.setText(threadAmount + "");
							}
						});

					}
				}, 2000);

			}
		});
			//timer repeat
		/*		t.scheduleAtFixedRate(new TimerTask() {
					
					@Override
					public void run() {
						tView.post(new Runnable() {

							@Override
							public void run() {
								threadAmount++;
								tView.setText(threadAmount + "");
								if (threadAmount == 5)//if is 5 then stop the timer
								{
									t.cancel();
									vv.setEnabled(true);
								}
							}
						});
						
					}
				}, 0, 1000);//no delay and do it again every 1 sec ( 1000 milliseconds)
			}
		});*/
	}// end oncreate

}
