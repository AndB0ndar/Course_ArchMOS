package com.example.coursearchmos.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.coursearchmos.model.BookModel;

import java.util.ArrayList;
import java.util.List;

public class BookDBHelper extends SQLiteOpenHelper {

	public static final String NOTE_TABLE = "BOOK_TABLE";
	public static final String COLUMN_NOTE_PATH = "BOOK_PATH";
	public static final String COLUMN_NOTE_INFO = "BOOK_INFO";
	public static final String COLUMN_ID = "ID";

	public BookDBHelper(Context context) {
		super(context, "reader.db", null, 1);
	}

	// this is called the first time a database is accessed
	@Override
	public void onCreate(SQLiteDatabase db) {
		String createTableStatement = "CREATE TABLE " + NOTE_TABLE
				+ " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ COLUMN_NOTE_PATH + " TEXT, "
				+ COLUMN_NOTE_INFO + " TEXT)";

		db.execSQL(createTableStatement);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	public boolean addOne(BookModel bookModel) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();

		if (!isExistByPath(bookModel)) {
			cv.put(COLUMN_NOTE_PATH, bookModel.getPath());
			cv.put(COLUMN_NOTE_INFO, bookModel.getInfo());
		}

		long insert = db.insert(NOTE_TABLE, null, cv);
		db.close();
		return insert != -1;
	}

	public boolean deleteOne(BookModel bookModel) {
		String queryString = "DELETE FROM " + NOTE_TABLE + " WHERE " + COLUMN_ID + " = " + bookModel.getId();
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(queryString, null);
		return cursor.moveToFirst();
	}

	public boolean updateOne(BookModel bookModel) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();

		cv.put(COLUMN_ID, bookModel.getId());
		cv.put(COLUMN_NOTE_PATH, bookModel.getPath());
		cv.put(COLUMN_NOTE_INFO, bookModel.getInfo());

		db.update(NOTE_TABLE, cv, "id = ?", new String[]{String.valueOf(bookModel.getId())});
		db.close();
		return true;
	}

	public boolean isExistByPath(BookModel bookModel) {
		// TODO: add code
		return false;
	}

	public List<BookModel> getAll() {
		List<BookModel> returnList = new ArrayList<>();

		String queryString = "SELECT * FROM " + NOTE_TABLE;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(queryString, null);
		if (cursor.moveToFirst()){
			// loop through the cursor and create new Note object
			do {
				int bookID = cursor.getInt(0);
				String bookPATH = cursor.getString(1);
				String bookINFO = cursor.getString(2);

				BookModel bookModel = new BookModel(bookID, bookPATH, bookINFO);
				returnList.add(bookModel);
			} while (cursor.moveToNext());
		}

		cursor.close();
		db.close();

		return returnList;
	}

	public BookModel getById(int id) {
		BookModel bookModel = null;

		String queryString = "SELECT * FROM " + NOTE_TABLE + " WHERE " + COLUMN_ID + " = " + id;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(queryString, null);
		if (cursor.moveToFirst()){
			int bookID = cursor.getInt(0);
			String bookPATH = cursor.getString(1);
			String bookINFO = cursor.getString(2);

			bookModel = new BookModel(bookID, bookPATH, bookINFO);
		}

		cursor.close();
		db.close();

		return bookModel;
	}
}
