package com.project.anderson.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Classe auxiliar para gerenciar a cria��o de banco de dados e gerenciamento de vers�o.
 * Esta subclasse de SQLiteOpenHelper subscreve os m�todos onCreate(SQLiteDatabase), 
 * onUpgrade(SQLiteDatabase, int, int) e, opcionalmente, onOpen(SQLiteDatabase).
 * Esta classe se encarrega de abrir o banco de dados se ele existir, caso contr�rio ela
 * cria o mesmo, al�m de atualiz�-lo, quando necess�rio.
 * 
 * Nota: esta classe assume monotonicamente crescente n�meros de vers�o para upgrades.
 *
 */
public class CustomSQLiteOpenHelper extends SQLiteOpenHelper {
	/**
	 * Indica o nome do banco de dados
	 */
	private static final String DATABASE_NAME = "Collector.db";
	
	/**
	 * Indica a vers�o do banco de dados
	 */
	private static final int DATABASE_VERSION = 1;

	/**
	 * Strings que determinam o nome de cada uma das tabelas;
	 */
	public static final String TABLE_ENTREGA = "tb_entrega";
	public static final String TABLE_ITEM = "tb_item";	
	
	
	/**
	 * SQL para criação da tabela de entregas
	 */
	private static final String SQL_TABLE_ENTREGA_CREATE = "CREATE TABLE " + TABLE_ENTREGA
			+ " ( id INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ " local TEXT NOT NULL, "
			+ " horario TEXT NOT NULL "
			+ " );";	
	
	/**
	 * SQL para criação da tabela de itens
	 */
	private static final String SQL_TABLE_ITEM_CREATE = "CREATE TABLE " + TABLE_ITEM
			+ " ( _id INTEGER PRIMARY KEY AUTOINCREMENT, "			
			+ " descricao TEXT, "		
			+ " quantidade INTEGER NOT NULL DEFAULT (1), "
			+ " peso NUMERIC(15,2) DEFAULT (0), "
			+ " entregaId INTEGER NOT NULL, "		
			+ " FOREIGN KEY(entregaId) REFERENCES " + TABLE_ENTREGA+ "(id) "			
			+ " );";
	
	
	public CustomSQLiteOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	/**
	 * Este método � executado na fase de cria��o do Bando de Dados.
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_TABLE_ENTREGA_CREATE);
		db.execSQL(SQL_TABLE_ITEM_CREATE);
		
		
		System.out.println(SQL_TABLE_ENTREGA_CREATE);
		System.out.println(SQL_TABLE_ITEM_CREATE);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ENTREGA);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEM);	
		onCreate(db);
	}
}
