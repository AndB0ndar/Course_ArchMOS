package com.example.coursearchmos.model;

public class BookMarkModel {
	private int id;
	private String title;
	private int bookId, bookPage;

	public BookMarkModel(int id, int bookId, String title, int numberPage) {
		this.id = id;
		this.bookId = bookId;
		this.title = title;
		this.bookPage = numberPage;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getBookPage() {
		return bookPage;
	}

	public void setBookPage(int bookPage) {
		this.bookPage = bookPage;
	}
}
