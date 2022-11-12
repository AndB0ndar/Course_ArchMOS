package com.example.coursearchmos.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Book implements Parcelable {
	int id;
	String title;
	String author;
	String year;
	String path;

	public Book(int id, String title, String author) {
		this.id = id;
		this.title = title;
		this.author = author;
	}

	protected Book(Parcel in) {
		id = in.readInt();
		title = in.readString();
		author = in.readString();
		year = in.readString();
		path = in.readString();
	}

	public static final Creator<Book> CREATOR = new Creator<Book>() {
		@Override
		public Book createFromParcel(Parcel in) {
			return new Book(in);
		}

		@Override
		public Book[] newArray(int size) {
			return new Book[size];
		}
	};

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(@NonNull Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeString(title);
		dest.writeString(author);
		dest.writeString(year);
		dest.writeString(path);
	}
}
