package com.example.drawlogo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.widget.ImageView;

public class Logo extends View {
	Context context;
	Canvas canvas;
	private Paint textPaint; // Paint used to draw text
	int textSize = 50;
	int midOfSAX, midOfSAY, lineThickness;

	int x, y;

	public Logo(Context context, int x, int y) {
		super(context);
		lineThickness = 10;
		this.x = x;

		this.y = y;
		midOfSAX = x + (textSize / 2) + 5;
		midOfSAY = y - (textSize / 2) + 5;

		textPaint = new Paint(); // Paint for drawing text
		textPaint.setTextSize(textSize); // text size 1/20 of screen width
		textPaint.setAntiAlias(true); // smoothes the text
		textPaint.setColor(Color.RED); // smoothes the text

	}

	@Override
	protected void onDraw(Canvas canvas) {
		this.canvas = canvas;
		Paint paintRed = new Paint();
		paintRed.setColor(Color.RED);
		
		Paint paintWhite = new Paint();
		paintWhite.setColor(Color.WHITE);
		Paint paintWhite2 = new Paint();
		paintWhite2.setColor(Color.WHITE);
		paintWhite2.setStrokeWidth(lineThickness);
		Paint line = new Paint();
		line.setColor(Color.RED);
		line.setStrokeWidth(lineThickness);


		canvas.drawCircle(midOfSAX, midOfSAY, textSize*2, paintRed);
		canvas.drawCircle(midOfSAX, midOfSAY, textSize*2 - lineThickness, paintWhite);
		canvas.drawRect(midOfSAX-textSize*2, midOfSAY-textSize*2, midOfSAX+textSize*2, midOfSAY+textSize*2, paintRed);
		canvas.drawRect(midOfSAX-textSize*2+lineThickness, midOfSAY-textSize*2+lineThickness, midOfSAX+textSize*2-lineThickness, midOfSAY+textSize*2-lineThickness, paintWhite);
		canvas.drawCircle(midOfSAX, midOfSAY, textSize*2, paintRed);
		canvas.drawCircle(midOfSAX, midOfSAY, textSize*2 - lineThickness, paintWhite);
		
		canvas.drawLine(midOfSAX-textSize*2+5, midOfSAY+textSize*2-5, (midOfSAX+textSize*2+midOfSAX-textSize*2)/2, midOfSAY-textSize*2+5, line);
		canvas.drawLine((midOfSAX+textSize*2+midOfSAX-textSize*2)/2,midOfSAY-textSize*2+5, midOfSAX+textSize*2 -5, midOfSAY+textSize*2-5, line);
	
		canvas.drawText("SA", x, y, textPaint);
		

	}

}
