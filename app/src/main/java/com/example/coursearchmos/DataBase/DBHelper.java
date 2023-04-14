package com.example.coursearchmos.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class DBHelper extends SQLiteOpenHelper {
	public static final String DATABASE_NAME = "reader.db";
	public static final int DATABASE_VERSION = 1;
	private static final String CREATE_TABLE_NOTES =
			"CREATE TABLE " + NoteDBHelper.NOTE_TABLE
					+ " (" + NoteDBHelper.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ NoteDBHelper.COLUMN_NOTE_TITLE + " TEXT, "
					+ NoteDBHelper.COLUMN_NOTE_TEXT + " TEXT)";
	private static final String CREATE_TABLE_BOOKS =
			"CREATE TABLE " + BookDBHelper.NOTE_TABLE
					+ " (" + BookDBHelper.COLUMN_ID
					+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ BookDBHelper.COLUMN_NOTE_PATH + " TEXT, "
					+ BookDBHelper.COLUMN_NOTE_INFO + " TEXT)";


	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_NOTES);
		db.execSQL(CREATE_TABLE_BOOKS);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Adding any table mods to this guy here
	}
}