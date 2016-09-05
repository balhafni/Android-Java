package com.example.finalproject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	Context context = this;

	Animation rotate;
	Button goToProfile, createAProfile;
	public static ProfileDataBase profilesDataBase;
	public static AcademicDataBase academicDataBase;
	TextView title;
	ImageView ourPic;
	private Vibrator vibrate;
	String className, classTime, major;
	int credits, priority;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Logo logo = new Logo(context, 700, 600);
		setContentView(logo);
		profilesDataBase = new ProfileDataBase(this);
		academicDataBase = new AcademicDataBase(this);
		vibrate = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
		rotate = AnimationUtils.loadAnimation(this, R.animator.rotate);
		rotate.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				setContentView(R.layout.activity_main);
				//dropData
				MainActivity.academicDataBase.clearDataBase();
				//adding data to the adcademic database
				addData();
				// initiazling elements
				goToProfile = (Button) findViewById(R.id.pickMajor);
				createAProfile = (Button) findViewById(R.id.button2);
				title = (TextView) findViewById(R.id.title);
				ourPic = (ImageView) findViewById(R.id.ourPic);

				Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.our_pic);
				ourPic.setImageBitmap(bitmap);

				// to go to the create profile activity
				createAProfile.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						vibrate.vibrate(200);
						Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
						startActivityForResult(intent, 0);
					}
				});
				// go to add taken classes
				goToProfile.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						vibrate.vibrate(200);
						checkForProfile();
					}
				});
			}
		});
		logo.startAnimation(rotate);

	}

	public void read() throws IOException {
		InputStream inputStream = null;
		StringBuffer buff = new StringBuffer();
		String str = "";
		String[] temp;
		inputStream = this.getResources().getAssets().open("AcademicInfo.txt");
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		if (inputStream != null) {
			while ((str = reader.readLine()) != null) {
				// str = reader.readLine();
				if (!str.contains(" ")) {
					major = str;
					str = reader.readLine();
				}
				System.out.println("INSERTEEEED");
				temp = str.split(" ");
				className = temp[0];
				classTime = temp[1];
				credits = Integer.parseInt(temp[2]);
				priority = Integer.parseInt(temp[3]);
				MainActivity.academicDataBase.insertData(major, className, classTime, credits, priority);
				System.out.println(str);
				buff.append(str + "\n");
			}
			inputStream.close();
		}
	}

	public void addData() {
		try {
			read();
			Toast.makeText(getApplicationContext(), "Data was inserted", Toast.LENGTH_LONG).show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void checkForProfile() {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle("Hello User");
		alert.setMessage("What is your ID:");

		// Use an EditText view to get user input.
		final EditText input = new EditText(this);
		alert.setView(input);
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int whichButton) {
				String id = input.getText().toString();
				Profile profile = profilesDataBase.getStudentWithID(id);
				if (profile == null) {
					showMessage("Oppps!!", "You're profile wasn't found. Please create a profile");
				} else {
					Intent intent = new Intent(MainActivity.this, AcademicActivity.class);
					intent.putExtra("id", input.getText().toString());
					startActivityForResult(intent, 0);
				}
			}
		});

		alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				return;
			}
		});

		alert.show();
	}

	public void showMessage(String title, String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setCancelable(true);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.show();
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
