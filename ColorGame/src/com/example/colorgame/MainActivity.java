package com.example.colorgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MainActivity extends Activity {

	Scene oneScene, anotherScene;
	ViewGroup rootScene;
	Transition custom, mFadeTransition;
	Button btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		rootScene = (ViewGroup) findViewById(R.id.two_container);
		btn = (Button) findViewById(R.id.button2);

		oneScene = Scene.getSceneForLayout(rootScene, R.layout.another_scene, this);
		anotherScene = Scene.getSceneForLayout(rootScene, R.layout.another_another_scene, this);
		mFadeTransition = new Fade();
		oneScene.enter();
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,Game.class);
				startActivityForResult(intent, 0);
				
			}
		});

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
