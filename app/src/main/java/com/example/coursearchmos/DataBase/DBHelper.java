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
					+ NoteDBHelper.COLUMN_NOTE_TEXT + " TEXT, "
					+ NoteDBHelper.COLUMN_NOTE_ID_BOOK + " INTEGER)";
	private static final String CREATE_TABLE_BOOKS =
			"CREATE TABLE " + BookDBHelper.BOOK_TABLE
					+ " (" + BookDBHelper.COLUMN_ID
					+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ BookDBHelper.COLUMN_BOOK_PATH + " TEXT, "
					+ BookDBHelper.COLUMN_BOOK_INFO + " TEXT, "
					+ BookDBHelper.COLUMN_BOOK_LAST_CUR_PAGE + " INTEGER, "
					+ BookDBHelper.COLUMN_BOOK_PAGE_COUNT + " INTEGER, "
					+ BookDBHelper.COLUMN_BOOK_TIME + " INTEGER)";
	private static final String CREATE_TABLE_BOOKMARK =
			"CREATE TABLE " + BookMarksDBHelper.BOOKMARKS_TABLE
					+ " (" + BookMarksDBHelper.COLUMN_ID
					+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ BookMarksDBHelper.COLUMN_ID_BOOK + " INTEGER, "
					+ BookMarksDBHelper.COLUMN_NUMBER_PAGE + " INTEGER)";


	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_NOTES);
		db.execSQL(CREATE_TABLE_BOOKS);
		db.execSQL(CREATE_TABLE_BOOKMARK);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Adding any table mods to this guy here
	}
}