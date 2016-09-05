package com.example.class05_2016;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AlertDialogsss  extends Activity {
	ImageView img;
	ImageButton moviesBtn;
	AlertDialog.Builder alertDiag;
	
	boolean [] checked; 
	String [] alltypes;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alertdiag_layout);
		
		img = (ImageView)findViewById(R.id.imageView1);
		
		img.setOnClickListener(new View.OnClickListener() {
				
			@Override
			public void onClick(View v) {
			
				//img.setBackgroundResource(R.drawable.m_red);
				
				alertDiag = new AlertDialog.Builder(AlertDialogsss.this);
				alertDiag.setTitle("Select Replacement Image");
				alertDiag.setPositiveButton("Goomba", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						img.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.gumba));
						Toast.makeText(AlertDialogsss.this, "is a goomba", Toast.LENGTH_SHORT).show();
					}
				});
				alertDiag.setNegativeButton("Red Mushroom", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						img.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.m_red));
						Toast.makeText(AlertDialogsss.this, "is a red mushroom", Toast.LENGTH_SHORT).show();
					}
				});
				alertDiag.setMessage("Pick one of the two");
				
				//TextView txview = new TextView(AlertDialogsss.this);
				//txview.setText("Pick one of the two");
				//txview.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL);
				//alertDiag.setView(txview);
				AlertDialog alert = alertDiag.create();
				alert.setCancelable(false);
				alert.setCanceledOnTouchOutside(false);
				alert.show();
			}
		});
		
		moviesBtn = (ImageButton)findViewById(R.id.moviesButton);
		moviesBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				AlertDialog.Builder myMovies= new AlertDialog.Builder(AlertDialogsss.this);
				myMovies.setTitle("Pick a Genre");
				
				alltypes = getResources().getStringArray(R.array.TypesOfMovies);
				checked = new boolean[alltypes.length];
				myMovies.setMultiChoiceItems(alltypes, checked, new DialogInterface.OnMultiChoiceClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which, boolean isChecked) {
						checked[which] = true;
						Toast.makeText(AlertDialogsss.this,"picked "+alltypes[which], Toast.LENGTH_SHORT).show();
					}
				});
				
				myMovies.show();
			}
		});
				
		Log.e("create done","is done");
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		getMenuInflater().inflate(R.menu.main3, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		int id = item.getItemId();
		if (id == R.id.toSimple) {
			Intent toDiags = new Intent(AlertDialogsss.this,SimpleAnimations.class);
			startActivity(toDiags);
		}
		return super.onOptionsItemSelected(item);
	}

}
