package com.example.coursearchmos.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.coursearchmos.model.BookMarkModel;

import java.util.ArrayList;
import java.util.List;

public class BookMarksDBHelper extends DBHelper {
	public static final String BOOKMARKS_TABLE = "BOOKMARKS_TABLE";
	public static final String COLUMN_ID = "ID";
	public static final String COLUMN_ID_BOOK = "BOOKMARKS_ID_BOOK";
	public static final String COLUMN_NUMBER_PAGE = "BOOKMARKS_NUMBER_PAGE";

	private Context context;

	public BookMarksDBHelper(Context context) {
		super(context);
		this.context = context;
	}

	public boolean addOne(int bookId, int nmPage) {
			BookMarkModel bookModel = new BookMarkModel(-1
					, bookId
					, nmPage
			);

		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();

		cv.put(COLUMN_ID_BOOK, bookModel.getBookId());
		cv.put(COLUMN_NUMBER_PAGE, bookModel.getNumberPage());

		long insert = db.insert(BOOKMARKS_TABLE, null, cv);

		db.close();
		return insert != -1;
	}

	public boolean deleteOne(BookMarkModel bookMarkModel) {
		String queryString = "DELETE FROM " + BOOKMARKS_TABLE + " WHERE " + COLUMN_ID + " = " + bookMarkModel.getId();
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(queryString, null);
		return cursor.moveToFirst();
	}

	public boolean updateOne(BookMarkModel bookMarkModel) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();

		cv.put(COLUMN_ID, bookMarkModel.getId());
		cv.put(COLUMN_ID_BOOK, bookMarkModel.getBookId());
		cv.put(COLUMN_NUMBER_PAGE, bookMarkModel.getNumberPage());

		int f = db.update(BOOKMARKS_TABLE, cv, "id = ?", new String[]{String.valueOf(bookMarkModel.getId())});
		db.close();
		return f != -1;
	}

	public List<BookMarkModel> getAllByBookId(int id) {
		List<BookMarkModel> returnList = new ArrayList<>();

		String queryString = "SELECT * FROM " + BOOKMARKS_TABLE + " WHERE " + COLUMN_ID_BOOK + " = " + id;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(queryString, null);
		if (cursor.moveToFirst()){
			do {
				int bmID = cursor.getInt(0);
				int bmBookID = cursor.getInt(1);
				int bmNmPage = cursor.getInt(2);

				returnList.add(new BookMarkModel(bmID, bmBookID, bmNmPage));
			} while (cursor.moveToNext());
		}

		cursor.close();
		db.close();

		return returnList;
	}

	public boolean isEmpty() {
		String queryString = "SELECT EXISTS (SELECT 1 FROM "  + BOOKMARKS_TABLE + ")";
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

