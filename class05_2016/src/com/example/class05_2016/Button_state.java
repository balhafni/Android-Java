package com.example.class05_2016;



import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class Button_state extends Activity {
ImageView img, img01,img02,img03,img04;
AlertDialog.Builder alertDiag;

int [] matches ={0,0,1,1};//answer 0 =green, 1 = goomba

boolean pick1,pick2;
int match1,match2;
ImageView temp1,temp2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.button_layout);
		
		img01 = (ImageView)findViewById(R.id.imageView1);
		img02 = (ImageView)findViewById(R.id.imageView2);
		img03 = (ImageView)findViewById(R.id.imageView3);
		img04 = (ImageView)findViewById(R.id.imageView4);
		
		img01.setOnClickListener(xx);
		img02.setOnClickListener(xx);
		img03.setOnClickListener(xx);
		img04.setOnClickListener(xx);
		
	}

	OnClickListener xx = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if(v == img01)
			{
				if(pick1 == false)
				{
					pick1 = true;
					match1 = 0;
					Bitmap mBitmap = BitmapFactory.decodeResource(getResources(),
							   R.drawable.m_green);
					img01.setImageBitmap(Bitmap.createBitmap(mBitmap));
					temp1 = img01;
				}
				else
				{
					match2 = 0;
					temp2 = img01;
					if (match1 == match2)
					{
						//make them both alpha
						Bitmap mBitmap = BitmapFactory.decodeResource(getResources(),
								   R.drawable.m_green);
						img01.setImageBitmap(Bitmap.createBitmap(mBitmap));
						img01.setAlpha(.3f);
						img01.setEnabled(false);
						
						disableViews();
					}
					else
					{
						//reset images
						resetImage();
						
						match1 = -1;
						match2 = -1;
						pick1 = pick2 = false;
					}
				}
			}
			else if(v == img02)
			{
				if(pick1 == false)
				{
					pick1 = true;
					match1 = 0;
					Bitmap mBitmap = BitmapFactory.decodeResource(getResources(),
							   R.drawable.m_green);
					img02.setImageBitmap(Bitmap.createBitmap(mBitmap));
					temp1 = img02;
				}
				else
				{
					match2 = 0;
					temp2 = img02;
					if (match1 == match2)
					{
						Bitmap mBitmap = BitmapFactory.decodeResource(getResources(),
								   R.drawable.m_green);
						img02.setImageBitmap(Bitmap.createBitmap(mBitmap));
						//make them both alpha
						img02.setAlpha(.3f);
						img02.setEnabled(false);
						disableViews();
					}
					else
					{
						//reset images
						resetImage();
						match1 = -1;
						match2 = -1;
						pick1 = pick2 = false;
						
					}
				}
			}
			else if(v == img03)
			{
				if(pick1 == false)
				{
					pick1 = true;
					match1 = 1;
					Bitmap mBitmap = BitmapFactory.decodeResource(getResources(),
							   R.drawable.gumba);
					img03.setImageBitmap(Bitmap.createBitmap(mBitmap));
					temp1 = img03;
				}
				else
				{
					match2 = 1;
					temp2 = img03;
					if (match1 == match2)
					{
						//make them both alpha
						Bitmap mBitmap = BitmapFactory.decodeResource(getResources(),
								   R.drawable.gumba);
						img03.setImageBitmap(Bitmap.createBitmap(mBitmap));
						//img03.setAlpha(.3f);
						//img03.setEnabled(false);
						disableViews();
					}
					else
					{
						//reset images
						resetImage();
						match1 = -1;
						match2 = -1;
						pick1 = pick2 = false;
						
					}
				}
			}
			else if(v == img04)
			{
				if(pick1 == false)
				{
					pick1 = true;
					match1 = 1;
					Bitmap mBitmap = BitmapFactory.decodeResource(getResources(),
							   R.drawable.gumba);
					img04.setImageBitmap(Bitmap.createBitmap(mBitmap));
					temp1 = img04;
				}
				else
				{
					match2 = 1;
					temp2 = img04;
					if (match1 == match2)
					{
						//make them both alpha
						Bitmap mBitmap = BitmapFactory.decodeResource(getResources(),
								   R.drawable.gumba);
						img04.setImageBitmap(Bitmap.createBitmap(mBitmap));
						//img04.setAlpha(.3f);
						//img04.setEnabled(false);
						disableViews();
					}
					else
					{
						//reset images
						resetImage();
						match1 = -1;
						match2 = -1;
						pick1 = pick2 = false;
						
					}
				}
			}
		}
	};
	public void disableViews()
	{
		ImageView [] allviews = {img01,img02,img03,img04};
		for	(int i=0; i < 4; i++)
		{
			if (temp1 == allviews[i])
			{
				temp1.setAlpha(.3f);
				temp1.setEnabled(false);
		
			}
			if (temp2 == allviews[i])
			{
				temp2.setAlpha(.3f);
				temp2.setEnabled(false);
			}
		}
		
	}
	public void resetImage()
	{
		if (match1 == 0)
		{
			Bitmap mBitmap = BitmapFactory.decodeResource(getResources(),
					   R.drawable.m_red);
			img01.setImageBitmap(mBitmap);
		}
		if (match1 == 1)
		{
			Bitmap mBitmap = BitmapFactory.decodeResource(getResources(),
					   R.drawable.m_red);
			img02.setImageBitmap(mBitmap);
		}
		else if (match1 == 2)
		{
			Bitmap mBitmap = BitmapFactory.decodeResource(getResources(),
					   R.drawable.m_red);
			img03.setImageBitmap(mBitmap);	
		}
		else if (match1 == 3)
		{
			Bitmap mBitmap = BitmapFactory.decodeResource(getResources(),
					   R.drawable.m_red);
			img04.setImageBitmap(mBitmap);
		}
		
		if (match2 == 0)
		{
			Bitmap mBitmap = BitmapFactory.decodeResource(getResources(),
					   R.drawable.m_red);
			img01.setImageBitmap(mBitmap);
		}
		if (match2 == 1)
		{
			Bitmap mBitmap = BitmapFactory.decodeResource(getResources(),
					   R.drawable.m_red);
			img02.setImageBitmap(mBitmap);
		}
		else if (match2 == 2)
		{
			Bitmap mBitmap = BitmapFactory.decodeResource(getResources(),
					   R.drawable.m_red);
			img03.setImageBitmap(mBitmap);	
		}
		else if (match2 == 3)
		{
			Bitmap mBitmap = BitmapFactory.decodeResource(getResources(),
					   R.drawable.m_red);
			img04.setImageBitmap(mBitmap);
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		getMenuInflater().inflate(R.menu.main1, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		int id = item.getItemId();
		if (id == R.id.toShape) {
			Intent toDiags = new Intent(Button_state.this,Shape_shape.class);
			startActivity(toDiags);
		}
		return super.onOptionsItemSelected(item);
	}
}
