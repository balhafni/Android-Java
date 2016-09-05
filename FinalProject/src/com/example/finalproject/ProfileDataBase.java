package com.example.finalproject;

import java.io.ByteArrayOutputStream;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;

public class ProfileDataBase extends SQLiteOpenHelper {
	public static final String DATABASE_NAME = "StudentProfiles.db";
	public static final String TABLE_NAME = "Profiles";
	public static final String ID = "ID";
	public static final String FIRST_NAME = "First_Name";
	public static final String LAST_NAME = "Last_Name";
	public static final String DATE_OF_BIRTH = "Date_of_Birth";
	public static final String MAJOR = "Major";
	public static final String CLASSESS_TAKEN = "Classes_Taken";
	public static final String IMAGE = "Image";

	public ProfileDataBase(Context context) {
		super(context, DATABASE_NAME, null, 1);

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table " + TABLE_NAME
				+ " (ID TEXT,First_Name TEXT,Last_Name TEXT,Date_of_Birth TEXT,Major TEXT,Classes_Taken TEXT,Image BLOB);");

	}

	public boolean insertData(String id, String firstName, String lastName, String dateOfBirth, String major,
			String classesTaken, Bitmap bitmap) {

		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(ID, id);
		values.put(FIRST_NAME, firstName);
		values.put(LAST_NAME, lastName);
		values.put(DATE_OF_BIRTH, dateOfBirth);
		values.put(MAJOR, major);
		values.put(CLASSESS_TAKEN, classesTaken);
		values.put(IMAGE, getBytes(bitmap));
		long check = db.insert(TABLE_NAME, null, values);
		if (check == -1) {
			return false;
		} else {
			return true;
		}
	}

	// converting from bitmap to a byte array
	public static byte[] getBytes(Bitmap bitmap) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.PNG, 100, stream);
		return stream.toByteArray();
	}

	// converting the byte array to a bitmap
	public static Bitmap getPhoto(byte[] image) {
		return BitmapFactory.decodeByteArray(image, 0, image.length);
	}

	public boolean updateData(String id, String firstName, String lastName, String dateOfBirth, String major,
			String classesTaken, Bitmap bitmap) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(ID, id);
		values.put(FIRST_NAME, firstName);
		values.put(LAST_NAME, lastName);
		values.put(DATE_OF_BIRTH, dateOfBirth);
		values.put(MAJOR, major);
		values.put(CLASSESS_TAKEN, classesTaken);
		values.put(IMAGE, getBytes(bitmap));
		db.update(TABLE_NAME, values, "ID = ?", new String[] { id });
		return true;
	}

	// clear the database
	public void deleteDataBase() {
		SQLiteDatabase db = this.getReadableDatabase();
		db.execSQL("delete from " + TABLE_NAME);
	}

	public Profile getStudentWithID(String id) {
		SQLiteDatabase db = this.getWritableDatabase();
		Profile profile = null;
		System.out.println("THE ID IN THE QUERY IS " + id);
		System.out.println("It is working");
		Cursor res = db.rawQuery("select * from " + TABLE_NAME + " where ID ='" + id + "'", null);
		if (res.getCount() > 0) {
			while (res.moveToNext()) {
				profile = new Profile(res.getString(0), res.getString(1), res.getString(2), res.getString(3),
						res.getString(4), res.getString(5),res.getBlob(6));
			}

		}
		return profile;
	}

	public Cursor getAll() {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
		return res;
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);

	}

}
