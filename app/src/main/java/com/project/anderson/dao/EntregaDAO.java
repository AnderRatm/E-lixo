package com.project.anderson.dao;

import java.util.ArrayList;
import java.util.List;

import com.project.anderson.e_lixo.Entrega;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;


public class EntregaDAO extends GenericDAO<Entrega> {

	public EntregaDAO(Context context) {
		super(context);
		this.columns = new String[]{"id", "local", "horario"};
	}

	@Override
	public boolean create(Entrega obj) {
		ContentValues values = new ContentValues();
		values.put(columns[1], obj.getLocal());
		values.put(columns[2], obj.getHorario());
		
		long insertId = database.insert(CustomSQLiteOpenHelper.TABLE_ENTREGA, null, values);
		obj.setId( (int)insertId );
		
		return true;
	}

	@Override
	public boolean delete(Entrega obj) {
		int res = database.delete(CustomSQLiteOpenHelper.TABLE_ENTREGA, "id="+obj.getId(), null);
		
		return res == 0 ? false : true;
	}

	@Override
	public boolean update(Entrega obj) {
		ContentValues values =  new ContentValues();
		values.put(columns[1], obj.getLocal());
		values.put(columns[2], obj.getHorario());
		
		database.update(CustomSQLiteOpenHelper.TABLE_ENTREGA, values, "id = "+obj.getId(), null);
		return true;
	}
	
	public Entrega get(int id) {
		return get(CustomSQLiteOpenHelper.TABLE_ENTREGA, this.columns, id);
	}

	@Override
	public List<Entrega> getAll() {
		Cursor cursor = database.query(CustomSQLiteOpenHelper.TABLE_ENTREGA, columns, null, null, null, null, columns[1]);
		
		return processarResultadoQuery(cursor);
	}
		
	public List<Entrega> findByEntrega(String nome) {
		//Filtro da consulta, cl�usula WHERE
		String expressao = columns[1] + " LIKE '%"+nome+"%'";

		Cursor cursor = database.query(CustomSQLiteOpenHelper.TABLE_ENTREGA, columns, expressao, null, null, null, columns[1]);
		
		return processarResultadoQuery(cursor);
	}
	
	protected List<Entrega> processarResultadoQuery(Cursor cursor) {
		List<Entrega> entregas = new ArrayList<Entrega>();
		
		cursor.moveToFirst();
		
		while (!cursor.isAfterLast()) {
			entregas.add( cursorToObject(cursor) );
			cursor.moveToNext();
		}
		
		cursor.close();
		
		return entregas;
	}
	
	protected Entrega cursorToObject(Cursor cursor) {
		Entrega entrega = new Entrega();
		entrega.setId(cursor.getInt(0));
		entrega.setLocal(cursor.getString(1));
		entrega.setHorario(cursor.getString(2));
				
		return entrega;
	}
}
