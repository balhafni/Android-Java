package com.example.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class AcademicDataBase extends SQLiteOpenHelper {

	public static final String DATABASE_NAME = "AcademicPlans.db";
	public static final String TABLE_NAME = "AcademicMajors";
	public static final String MAJOR = "Major";
	public static final String CLASS_NAME = "Class_Name";
	public static final String CLASS_TIME = "Class_Time";
	public static final String CLASS_CREDIT = "Class_Credit";
	public static final String CLASS_PRIORITY = "Class_Priority";

	public AcademicDataBase(Context context) {
		super(context, DATABASE_NAME, null, 1);

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table " + TABLE_NAME
				+ " (Major TEXT,Class_Name TEXT,Class_Time TEXT,Class_Credit INTEGER,Class_Priority INTEGER);");
		
	}

	public boolean insertData(String major, String className, String classTime, int credit, int classPriority) {

		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(MAJOR, major);
		values.put(CLASS_NAME, className);
		values.put(CLASS_TIME, classTime);
		values.put(CLASS_CREDIT, classTime);
		values.put(CLASS_PRIORITY, classPriority);
		long check = db.insert(TABLE_NAME, null, values);
		if (check == -1) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);

	}

	public void clearDataBase() {
		SQLiteDatabase db = this.getReadableDatabase();
		db.execSQL("delete from" + TABLE_NAME);
	}

	public void dropTable() {
		SQLiteDatabase db = this.getReadableDatabase();
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
	}

}
