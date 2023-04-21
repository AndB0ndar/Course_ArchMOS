package com.example.coursearchmos.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.coursearchmos.model.BookModel;

import java.util.ArrayList;
import java.util.List;

public class BookDBHelper extends DBHelper {
	public static final String NOTE_TABLE = "BOOK_TABLE";
	public static final String COLUMN_BOOK_PATH = "BOOK_PATH";
	public static final String COLUMN_BOOK_INFO = "BOOK_INFO";
	public static final String COLUMN_BOOK_LAST_CUR_PAGE = "BOOK_LAST_CUR_PAGE";
	public static final String COLUMN_BOOK_TIME = "BOOK_TIME";
	public static final String COLUMN_ID = "ID";


	public BookDBHelper(Context context) {
		super(context);
	}

	public boolean addOne(BookModel bookModel) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();

		if (!isExistByPath(bookModel)) {
			cv.put(COLUMN_BOOK_PATH, bookModel.getPath());
			cv.put(COLUMN_BOOK_INFO, bookModel.getInfo());
			cv.put(COLUMN_BOOK_LAST_CUR_PAGE, bookModel.getLastCurPage());
			cv.put(COLUMN_BOOK_TIME, bookModel.getTime());
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
		cv.put(COLUMN_BOOK_PATH, bookModel.getPath());
		cv.put(COLUMN_BOOK_INFO, bookModel.getInfo());
		cv.put(COLUMN_BOOK_LAST_CUR_PAGE, bookModel.getLastCurPage());
		cv.put(COLUMN_BOOK_TIME, bookModel.getTime());

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
				int bookLCP = cursor.getInt(3);
				int bookTime = cursor.getInt(4);

				BookModel bookModel = new BookModel(bookID, bookPATH, bookINFO, bookLCP, bookTime);
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
			int bookLCP = cursor.getInt(3);
			int bookTime = cursor.getInt(4);

			bookModel = new BookModel(bookID, bookPATH, bookINFO, bookLCP, bookTime);
		}

		cursor.close();
		db.close();

		return bookModel;
	}

	public BookModel getLast() {
		BookModel bookModel = null;

		String queryString = "SELECT *, max(" + COLUMN_BOOK_TIME + ") FROM " + NOTE_TABLE;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(queryString, null);
		if (cursor.moveToFirst()){
			int bookID = cursor.getInt(0);
			String bookPATH = cursor.getString(1);
			String bookINFO = cursor.getString(2);
			int bookLCP = cursor.getInt(3);
			int bookTime = cursor.getInt(4);

			bookModel = new BookModel(bookID, bookPATH, bookINFO, bookLCP, bookTime);
		}

		cursor.close();
		db.close();

		return bookModel;
	}

	public boolean isEmpty() {
		String queryString = "SELECT EXISTS (SELECT 1 FROM "  + NOTE_TABLE + ")";
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(queryString, null);
		int ret = 0;
		if (cursor.moveToFirst()){
			ret = cursor.getInt(0);
		}

		cursor.close();
		db.close();

		return ret == 0;
	}
}
