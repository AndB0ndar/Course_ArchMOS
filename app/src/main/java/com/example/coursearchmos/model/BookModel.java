package com.example.coursearchmos.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class BookModel implements Serializable {
	private int id;
	private String title, path, info;
	private int lastCurPage, pageCount, time;

	public BookModel(int id, String path, String info
			, int lastCurPage, int pageCount, int time) {
		this.id = id;
		String[] s = path.split("/");
		this.title = s[s.length - 1].split("\\.")[0];
		this.path = path;
		this.info = info;
		this.lastCurPage = lastCurPage;
		this.pageCount = pageCount;
		this.time = time;
	}

	public BookModel(int id, String title, String path
			, String info, int lastCurPage, int pageCount
			, int time) {
		this.id = id;
		this.title = title;
		this.path = path;
		this.info = info;
		this.lastCurPage = lastCurPage;
		this.pageCount = pageCount;
		this.time = time;
	}

	@NonNull
	@Override
	public String toString() {
		return 	"Название: " + title + '\n' +
				"Путь: '" + path + "' \n" +
				"Текущая страница: " + lastCurPage + '\n' +
				"Количество страниц: " + pageCount + '\n' +
				"Общее время чтения: " + time + " с.";
	}

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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getInfo() {
		return info;
	}

	public int getLastCurPage() {
		return lastCurPage;
	}

	public void setLastCurPage(int lastCurPage) {
		this.lastCurPage = lastCurPage;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public int getPageCount() {
		return pageCount;
	}
}
