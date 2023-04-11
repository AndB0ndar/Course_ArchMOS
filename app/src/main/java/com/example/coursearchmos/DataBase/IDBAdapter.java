package com.example.coursearchmos.DataBase;

import java.io.Serializable;
import java.util.List;

public interface IDBAdapter {
	public boolean addOne(Serializable model);
	public boolean deleteOne(Serializable model);
	public List<Serializable> getAll();
	public Serializable getById(int id);
	public boolean updateOne(Serializable model);
}
