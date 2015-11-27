package com.project.anderson.dao;

import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.project.anderson.dao.CustomSQLiteOpenHelper;

public abstract class GenericDAO <T> {
	protected SQLiteDatabase database;
	protected CustomSQLiteOpenHelper sqliteOpenHelper;
	protected String[] columns = {};
 
	public GenericDAO(Context context) {
		sqliteOpenHelper = new CustomSQLiteOpenHelper(context);
	}
	
	public void open() throws SQLException {
		database = sqliteOpenHelper.getWritableDatabase();
	}

	public void close() {
		sqliteOpenHelper.close();
	}
	
	public T get(String tabela, String colunas[], int id) {
		String expressao = "id = " + id;

		Cursor cursor = database.query(tabela, colunas, expressao, null, null, null, null);
		
		if(cursor.getCount() == 0)
			return null;
		
		cursor.moveToFirst();
		return cursorToObject(cursor);
	}
	
	public abstract T get(int id);
	public abstract boolean create( T obj );
	public abstract boolean delete( T obj );
	public abstract boolean update( T obj );
	public abstract List<T> getAll();
	
	protected abstract List<T> processarResultadoQuery(Cursor cursor);
	protected abstract T cursorToObject(Cursor cursor);
}
