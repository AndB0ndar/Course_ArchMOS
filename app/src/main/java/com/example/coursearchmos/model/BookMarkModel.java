package com.example.coursearchmos.model;

public class BookMarkModel {
	private int id;
	private int bookId, numberPage;

	public BookMarkModel(int id, int bookId, int numberPage) {
		this.id = id;
		this.bookId = bookId;
		this.numberPage = numberPage;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public int getNumberPage() {
		return numberPage;
	}

	public void setNumberPage(int numberPage) {
		this.numberPage = numberPage;
	}
}
