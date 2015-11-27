package com.project.anderson.dao;

import java.util.ArrayList;
import java.util.List;





import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.widget.ListView;

import com.project.anderson.e_lixo.Item;


public class ItemDAO extends GenericDAO<Item> {
	private EntregaDAO entregaDAO;

	

	public ItemDAO(Context context) {
		super(context);
		this.columns = new String[]{"_id", "descricao", "quantidade", "peso", "entregaId"};
		
		this.entregaDAO = new EntregaDAO(context);
	
	}

	@Override
	public boolean create(Item obj) {
		ContentValues values = new ContentValues();
		values.put(columns[1], obj.getDescricao());
		values.put(columns[2], obj.getQuantidade());
		values.put(columns[3], obj.getPeso());
		values.put(columns[4], obj.getCod_entrega());
	

		long insertId = database.insert(CustomSQLiteOpenHelper.TABLE_ITEM, null, values);
		obj.setId( (int)insertId );
		
		return true;
	}

	@Override
	public boolean delete(Item obj) {
		int res = database.delete(CustomSQLiteOpenHelper.TABLE_ITEM, "_id="+obj.getId(), null);
		
		return res == 0 ? false : true;
	}

	@Override
	public boolean update(Item obj) {
		
		ContentValues values =  new ContentValues();	
		values.put(columns[1], obj.getDescricao());
		values.put(columns[2], obj.getQuantidade());
		values.put(columns[3], obj.getPeso());
		values.put(columns[4], obj.getCod_entrega());
		
		database.update(CustomSQLiteOpenHelper.TABLE_ITEM, values, "_id = "+obj.getId(), null);
		return true;
	}

	@Override
	public List<Item> getAll() {
		Cursor cursor = database.query(CustomSQLiteOpenHelper.TABLE_ITEM, columns, null, null, null, null, columns[1]);
	
		return processarResultadoQuery(cursor);
	}
	public List<Item> findbyEntrega(int entrega) {
		String expressao = columns[4] + " LIKE '%" + entrega + "%'";
		Cursor cursor = database.query(CustomSQLiteOpenHelper.TABLE_ITEM, columns, expressao, null, null, null, columns[0]);
	
		return processarResultadoQuery(cursor);
	}
	
	public List<Item> findByNome(String nome) {
		String expressao = columns[1] + " LIKE '%" + nome + "%'";
		Cursor cursor = database.query(CustomSQLiteOpenHelper.TABLE_ITEM, columns, expressao, null, null, null, columns[1]);
		
		return processarResultadoQuery(cursor);
	}

	@Override
	protected ArrayList<Item> processarResultadoQuery(Cursor cursor) {
		entregaDAO.open();
		
		// pega os nomes das colunas da tabela selecionada
					
				
		ArrayList<Item> lista = new ArrayList<Item>();

		cursor.moveToFirst();
		
		while (!cursor.isAfterLast()) {
			lista.add( cursorToObject(cursor) );
			cursor.moveToNext();
		}
		cursor.close();
		
		entregaDAO.close();
	
		
		return lista;
	}

	@Override
	public Item get(int id) {
		entregaDAO.open();
	
		Item p = this.get(CustomSQLiteOpenHelper.TABLE_ITEM, this.columns, id);
		
		entregaDAO.close();
	
		
		return p;
	}

	@Override
	protected Item cursorToObject(Cursor cursor) {
		int idEntrega= cursor.getInt( cursor.getColumnIndex("entregaId"));	
		
		Item patrimonio = new Item();
			
		patrimonio.setId(cursor.getInt(0));	
		patrimonio.setDescricao( cursor.getString(1) );
		patrimonio.setQuantidade( cursor.getInt(2) );
		patrimonio.setPeso( cursor.getInt(3));
		patrimonio.setCod_entrega(cursor.getInt(4));
	
		
		return patrimonio;
	}
}
