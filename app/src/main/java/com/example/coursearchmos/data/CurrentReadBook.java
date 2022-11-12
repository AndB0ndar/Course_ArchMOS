package com.example.coursearchmos.data;

import com.example.coursearchmos.model.Book;

public class CurrentReadBook {
	static Book book = null;

	public static Book getBook() {
		return book;
	}

	public static void setBook(Book book) {
		CurrentReadBook.book = book;
	}
}
