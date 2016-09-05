package com.example.graphicsanimation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class TwoViews extends Activity {

	ViewGroup container;
	Scene scene1;
	Scene scene2;
	Transition customTransition;

	// same ids
	Scene scene1same;
	Scene scene2same;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.two_views_diff_ids);
		container = (ViewGroup) findViewById(R.id.two_container);
		scene1 = Scene.getSceneForLayout(container, R.layout.v1, this);
		scene2 = Scene.getSceneForLayout(container, R.layout.v2, this);

		scene1same = Scene.getSceneForLayout(container, R.layout.v1a, this);// R.layout.layouts1,this);
		scene2same = Scene.getSceneForLayout(container, R.layout.v2, this);// R.layout.layouts2,this

		customTransition = TransitionInflater.from(this).inflateTransition(R.transition.reg_trans);// cust_trans);
		// customTransition =
		// TransitionInflater.from(this).inflateTransition(R.transition.trans_set);

		scene1same.enter();
	}

	public void trans_views_different(View view) {
		Scene tmp = scene2;
		scene2 = scene1;
		scene1 = tmp;

		TransitionManager.go(scene1, customTransition);
	}

	public void trans_views_same(View view) {
		Scene tmp = scene2same;
		scene2same = scene1same;
		scene1same = tmp;

		TransitionManager.go(scene1same, customTransition);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.crossfade3point1, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
		if (id == R.id.action_settings) {
			// return true;
			Intent tt = new Intent(TwoViews.this, CodeCrossfade.class);
			startActivity(tt);
		}
		return super.onOptionsItemSelected(item);
	}
}
