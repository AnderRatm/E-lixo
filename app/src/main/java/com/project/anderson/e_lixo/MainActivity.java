package com.project.anderson.e_lixo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;


import android.app.ActionBar;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import au.com.bytecode.opencsv.CSVWriter;

import com.project.anderson.dao.*;


public class MainActivity extends AppCompatActivity {
	private Item item = new Item();
	private Entrega entrega = new Entrega();
	private ItemDAO itemDAO;
	private EntregaDAO entregaDAO;

	Spinner spinner_item, spinner_qnt;
	// CheckBox cadastrar_peso;
	ImageButton remove_item, mais, menos;
	public String var, var1, var2;
	private ListView listView;
	private List<String> itens = new ArrayList<String>();
	private List<String> qntds = new ArrayList<String>();
	private List<String> local = new ArrayList<String>();
	protected String parametro;
	protected double peso;
	protected int quantidade;
	private TextView textviewLocal;
	private String hora_cadastro;
	private ListView ltwDados;
	private FloatingActionButton btSave,btAddItem;
	private LinearLayout rootView;

	@Override
	protected void onCreate(Bundle savedInstanceState)
			throws SQLiteConstraintException {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		this.itemDAO = new ItemDAO(this);
		this.entregaDAO = new EntregaDAO(this);

		spinner_item = (Spinner) findViewById(R.id.spnItem);
		spinner_qnt = (Spinner) findViewById(R.id.spnQuantidade);
		// cadastrar_peso = (CheckBox) findViewById(R.id.checkBox1);
		btAddItem = (FloatingActionButton) findViewById(R.id.fab);
		listView = (ListView) findViewById(android.R.id.list);
		btSave= (FloatingActionButton) findViewById(R.id.fabEnt);
		textviewLocal = (TextView) findViewById(R.id.txvLocal);
		rootView= (LinearLayout) findViewById(R.id.root_layout);

		entregaDAO.open();
		if (entrega.local != null) {
			CapturarDataAtual();
			entregaDAO.create(entrega);

			android.support.v7.app.ActionBar bar = getSupportActionBar();
			bar.setSubtitle("Entrega " + entrega.id);
			textviewLocal.setText(entrega.getLocal());
		} else {
			Snackbar.make(rootView, R.string.infoma_local,Snackbar.LENGTH_LONG).show();
		}

		// Configurando a Acao de clique nos itens da ListView
		itens.add("Selecione o item");
		itens.add("Aparelho celular");
		itens.add("Aparelho de dvd");
		itens.add("Aparelho de fax");
		itens.add("Baterias de celular");
		itens.add("Baterias de notebook");
		itens.add("Cable modem");
		itens.add("Cabos de força, de rede, de energia e de telefone");
		itens.add("Carregador de celular");
		itens.add("Central Telefônica");
		itens.add("Cobre");
		itens.add("Conversor de canais");
		itens.add("Cooler");
		itens.add("Copiadoras");
		itens.add("Decodificador");
		itens.add("Desktop");
		itens.add("Disjuntor");
		itens.add("Dissipador");
		itens.add("Drive CD/DVD");
		itens.add("Estabilizador");
		itens.add("Fios Elétricos");
		itens.add("Fonte ATX, de Notebook, de energia");
		itens.add("Gabinetes Computadores");
		itens.add("HD");
		itens.add("Impressora");
		itens.add("Leitor de cartões internos");
		itens.add("Leitor de CD, DVD e Disquete");
		itens.add("Memória");
		itens.add("Microondas");
		itens.add("Monitor de tubo e led");
		itens.add("Motor Elétrico");
		itens.add("Nobreak");
		itens.add("Notebook");
		itens.add("Pilhas");
		itens.add("Placa de fax modem, de rede, de som, de vídeo, circuito interno");
		itens.add("Placa Mãe");
		itens.add("Processador");
		itens.add("Projetor Multimídia");
		itens.add("Switchs");
		itens.add("Telefones");
		itens.add("Tonner");
		itens.add("Transformador");
		itens.add("TV ( não desmontada)");
		itens.add("TV led");
		itens.add("Vídeo Cassete");
		itens.add("Outros");

		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_dropdown_item, itens);
		ArrayAdapter<String> spinnerArrayAdapter = arrayAdapter;
		spinnerArrayAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner_item.setAdapter(spinnerArrayAdapter);

		spinner_item
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> parent, View v,
											   int posicao, long id) {

					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub
					}
				});

		// spinner quantidades
		qntds.add("...");
		for (int i = 1; i <= 25; i++) {
			qntds.add(String.valueOf(i));
		}
		qntds.add("Mais");

		ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_dropdown_item, qntds);
		ArrayAdapter<String> spinnerArrayAdapter2 = arrayAdapter2;
		spinnerArrayAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner_qnt.setAdapter(spinnerArrayAdapter2);

		spinner_qnt
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> parent, View v,
											   int posicao, long id) {

						if (posicao == 26) {
							parametro = "quantidade";

							ExibeDialogo();
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub
					}
				});

		// Botão adicionar itens
		btAddItem.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				String validacao = "";
				if (spinner_item.getSelectedItemPosition() == 0)
					validacao += "Selecione um item\n";
				if (spinner_qnt.getSelectedItemPosition() == 0)
					validacao += "Selecione a quantidade\n";
				if (entrega.local == null)
					validacao += "Informe o local de coleta";

				if (!validacao.equals("")) {
					Snackbar.make(v, "Erro!\n " + validacao, Snackbar.LENGTH_LONG).show();
					return;
				}

				itemDAO.open();

				item.setDescricao((String) spinner_item.getSelectedItem());
				if (spinner_qnt.getSelectedItemPosition() != 26) {
					item.setQuantidade(Integer.parseInt((String) spinner_qnt
							.getSelectedItem()));
				}
				item.setPeso(peso);
				item.setCod_entrega(entrega.id);

				// Verifica se o objeto novo ou já existe no banco de dados
				/*
				 * if (item.getId() > 0) { // Solicita a atualição do objeto no
				 * banco de dados itemDAO.update(item);
				 * Toast.makeText(getBaseContext(), "Item atualizado",
				 * Toast.LENGTH_SHORT).show(); } else {
				 */
				// Solicita a criação do objeto no banco de dados
				itemDAO.create(item);
				Snackbar.make(v, "Item cadastrado com sucesso!", Snackbar.LENGTH_LONG).show();

				// item.setId(0);
				item.setDescricao(null);
				item.setQuantidade(0);
				item.setPeso(0);
				item.setEntrega(entrega);
				spinner_item.setSelection(0);
				spinner_qnt.setSelection(0);
				// cadastrar_peso.setChecked(false);
				atualizarLista();

				itemDAO.close();

			}

		});

		btSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View v) {
				// TODO Auto-generated method stub
				entregaDAO.open();

				if (entrega.local != null) {
					CapturarDataAtual();

					// if (item.getId() == 0) {
					if (ltwDados.getCount() == 0) {
						new AlertDialog.Builder(
								MainActivity.this)
								.setTitle("Nova entrada")
								.setMessage("Entrada não contem dados, deseja cadastrar uma nova?")
								.setPositiveButton("Sim",
										new DialogInterface.OnClickListener() {
											@Override
											public void onClick(DialogInterface dialog,
																int which) {
												entregaDAO.open();
												entregaDAO.create(entrega);

												Snackbar.make(v,"Sucesso ao cadastrar entrega",Snackbar.LENGTH_SHORT).show();

												spinner_item.setSelection(0);
												spinner_qnt.setSelection(0);
												peso = 0;

												ActionBar bar = getActionBar();
												bar.setSubtitle("Entrega " + entrega.id);
												atualizarLista();

												dialog.dismiss();
											}
										})

								.setNegativeButton("Não",null)
								.show();

					} else {
						entregaDAO.create(entrega);
						Snackbar.make(v,"Sucesso ao cadastrar entrega",Snackbar.LENGTH_SHORT).show();

						spinner_item.setSelection(0);
						spinner_qnt.setSelection(0);
						peso = 0;


						getSupportActionBar().setSubtitle("Entrega " + entrega.id);
						atualizarLista();
					}

				} else {
					Snackbar.make(v,"Selecione um local de coleta para continuar!",Snackbar.LENGTH_LONG).show();
				}
				entregaDAO.close();
			}
		});
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
		if (id == R.id.action_settings) {

			Intent intent = new Intent(MainActivity.this, SmplExpandabe.class);
			startActivity(intent);
			/*Toast.makeText(getBaseContext(), "Configurações", Toast.LENGTH_LONG)
					.show();*/
			return true;

		} else if (item.getItemId() == R.id.action_exportar_dados) {

			// getallDb();
			new ExportDatabaseCSVTask().execute();
			/*
			 * Toast.makeText(getBaseContext(), "Exportar dados",
			 * Toast.LENGTH_LONG).show();
			 */

		} else if (item.getItemId() == R.id.action_sobre) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);

			builder.setTitle("Sobre");
			builder.setIcon(R.drawable.ic_about);
			builder.setMessage("Developed by Anderson Pereira \nContato: ander.ratm@gmail.com");

			final TextView input = new TextView(this);
			/*input.setText("Contato: ander.ratm@gmail.com");
			builder.setView(input);*/

			builder.setPositiveButton("OK",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {

							dialog.dismiss();
						}
					});
			AlertDialog alert = builder.create();
			alert.show();
		} else if (item.getItemId() == R.id.menuLocal) {

			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Local");
			builder.setMessage("Selecione o local");

			final Spinner input = new Spinner(this);
			// spinner Local de coleta
			if (local.size()<1) {
				local.add("Centro");
				local.add("Coopagro");
				local.add("Jardim Panorama");
				local.add("Shopping Panambi");
				local.add("Porto Alegre");
				local.add("Vila Pioneiro");
			}
			builder.setView(input);

			ArrayAdapter<String> arrayAdapter3 = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_dropdown_item, local);
			ArrayAdapter<String> spinnerArrayAdapter = arrayAdapter3;
			spinnerArrayAdapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			input.setAdapter(spinnerArrayAdapter);

			input.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
				@Override
				public void onItemSelected(AdapterView<?> parent, View v,
										   int posicao, long id) {
					entrega.setLocal((String) input.getSelectedItem());

				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
				}
			});

			builder.setPositiveButton("OK",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							entregaDAO.open();
							CapturarDataAtual();
							entregaDAO.create(entrega);

							getSupportActionBar().setSubtitle(String.format("Entrega %d", entrega.id));
							textviewLocal.setText(entrega.getLocal());

							entregaDAO.close();
							dialog.dismiss();

						}

					});
			AlertDialog alert = builder.create();
			alert.show();

		}
		return super.onOptionsItemSelected(item);

	}

	private void ExibeDialogo() {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		if (parametro.equals("peso")) {
			builder.setTitle("Peso");
			builder.setMessage("Informe o peso do item");
		} else {
			builder.setTitle("Quantidade");
			builder.setMessage("Informe a quantidade");
		}
		final EditText input = new EditText(this);
		input.setSingleLine();
		input.setInputType(InputType.TYPE_CLASS_NUMBER);

		input.setText("");
		builder.setView(input);

		builder.setPositiveButton("SALVAR",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (parametro.equals("peso")) {
							peso = Double.parseDouble(input.getText()
									.toString());

						} else {
							quantidade = Integer.parseInt(input.getText()
									.toString());
							item.setQuantidade(quantidade);
						}

						dialog.dismiss();

					}

				});

		builder.setNegativeButton("CANCELAR",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						dialog.dismiss();
					}

				});
		AlertDialog alert = builder.create();
		alert.show();
	}

	protected void onResume() {
		itemDAO.open();
		super.onResume();

		atualizarLista();
	}

	private void atualizarLista() {
		itemDAO.open();
		// List<Item> lista;

		SQLiteDatabase db = openOrCreateDatabase("Collector.db",
				Context.MODE_PRIVATE, null);

		Cursor cursor = db.rawQuery("SELECT * FROM " + "tb_item"
				+ " WHERE entregaId = " + entrega.getId(), null);
		// pega os nomes das colunas da tabela selecionada
		// var = cursor.getColumnName(0);
		var1 = cursor.getColumnName(1);
		var2 = cursor.getColumnName(2);

		String[] from = {var1, var2};
		int[] to = {R.id.textviewDescricao, R.id.txvQuantidade};

		@SuppressWarnings("deprecation")
		SimpleCursorAdapter ad = new SimpleCursorAdapter(
				getBaseContext(), R.layout.list, cursor, from, to);

		ltwDados = (ListView) findViewById(R.id.list_item);
		ltwDados.setAdapter(ad);

		// Apagar itens ao clicar

		ltwDados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView adapter, View view,
									final int position, final long id) {

				SQLiteCursor c = (SQLiteCursor) adapter.getAdapter().getItem(
						position);

				AlertDialog.Builder builder = new AlertDialog.Builder(
						MainActivity.this);
				builder.setTitle("Apagar");
				builder.setMessage("Apagar item?");

				builder.setPositiveButton("SIM",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
												int which) {
								itemDAO.open();
								item.setId((int) id);

								if (itemDAO.delete(item) == true) {
									Toast.makeText(getBaseContext(),
											"Item apagado", Toast.LENGTH_SHORT)
											.show();
									atualizarLista();
								} else {
									Toast.makeText(getBaseContext(),
											"Erro ao apagar item",
											Toast.LENGTH_SHORT).show();
								}
								dialog.dismiss();
							}
						});

				builder.setNegativeButton("NÃO",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
												int which) {
								dialog.dismiss();
							}

						});
				AlertDialog alert = builder.create();
				alert.show();

			}
		});

		itemDAO.close();
	}

	void getallDb() {

		itemDAO.open();
		entregaDAO.open();

		List<Item> lista;
		ArrayList<Item> items = new ArrayList<Item>();

		SQLiteDatabase db = openOrCreateDatabase("Collector.db",
				Context.MODE_PRIVATE, null);

		String query = "SELECT " + "item._id" + "," + "item.descricao,"
				+ "quantidade," + "entregaId," + "entrega.local, "
				+ "entrega.horario " + " FROM " + " tb_item  item, "
				+ " tb_entrega entrega" + " WHERE item.entregaId"
				+ " = entrega.id";

		Log.d("query", query);
		Cursor cursor = db.rawQuery(query, null);
		while (cursor.moveToNext()) {
			Item item = new Item();
			item.setId(cursor.getInt(0));
			item.setDescricao(cursor.getString(1));
			item.setQuantidade(cursor.getInt(2));

			Entrega entrega = new Entrega();
			entrega.setId(cursor.getInt(3));
			entrega.setLocal(cursor.getString(4));
			entrega.setHorario(cursor.getString(5));

			item.setEntrega(entrega);
			items.add(item);
		}
		// SELECT * FROM dbname.sqlite_master WHERE type='table';
	}

	private void CapturarDataAtual() {

		/***** Capturar data do sistema ****/
		Locale locale = new Locale("pt", "BR");
		GregorianCalendar calendar = new GregorianCalendar();
		SimpleDateFormat form = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss",
				locale);
		hora_cadastro = form.format(calendar.getTime());
		entrega.setHorario(hora_cadastro);

	}

	// Exporta Banco de dados para arquivo CSV
	public class ExportDatabaseCSVTask extends AsyncTask<String, Void, Boolean> {

		private final ProgressDialog dialog = new ProgressDialog(
				MainActivity.this);

		@Override
		protected void onPreExecute()

		{

			this.dialog.setMessage("Exportando dados...");

			this.dialog.show();

		}

		protected Boolean doInBackground(final String... args)

		{
			SQLiteDatabase db = openOrCreateDatabase("Collector.db",
					Context.MODE_PRIVATE, null);

			File dbFile = getDatabasePath("database_name");
			// AABDatabaseManager dbhelper = new
			// AABDatabaseManager(getApplicationContext());
			CustomSQLiteOpenHelper dbhelper = new CustomSQLiteOpenHelper(
					MainActivity.this);
			System.out.println(dbFile); // displays the data base path in your
			// logcat

			File exportDir = new File(
					Environment.getExternalStorageDirectory(), "E-Lixo");

			if (!exportDir.exists())

			{
				exportDir.mkdirs();
			}

			File file = new File(exportDir, "GarbageColectorDB.csv");
			try
			{
				if (file.createNewFile()) {
					System.out.println("Arquivo criado com sucesso!");
					System.out.println("meuarquivo.csv "
							+ file.getAbsolutePath());
				} else {
					System.out.println("Arquivo ja existe.");
				}

				CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
				// SQLiteDatabase db = dbhelper.getWritableDatabase();

				Cursor curCSV = db.rawQuery("select * from "
						+ "tb_item INNER JOIN tb_entrega ON tb_item.entregaId =tb_entrega.id", null);

				csvWrite.writeNext(curCSV.getColumnNames());

				while (curCSV.moveToNext())
				{
					String arrStr[] = {curCSV.getString(0),
							curCSV.getString(1), curCSV.getString(2),
							curCSV.getString(3), curCSV.getString(4),
							curCSV.getString(5), curCSV.getString(6),
							curCSV.getString(7)};

					csvWrite.writeNext(arrStr);

				}

				csvWrite.close();
				curCSV.close();

				Uri u1  =   null;
				u1  =   Uri.fromFile(file);

				Intent sendIntent = new Intent(Intent.ACTION_SEND);
				sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Dados E-lixo");
				sendIntent.putExtra(Intent.EXTRA_STREAM, u1);
				sendIntent.setType("text/html");
				startActivity(sendIntent);
				return true;

			} catch (SQLException sqlEx)

			{

				Log.e("MainActivity", sqlEx.getMessage(), sqlEx);

				return false;

			} catch (IOException e)

			{

				Log.e("MainActivity", e.getMessage(), e);

				return false;

			}

		}

		protected void onPostExecute(final Boolean success)

		{

			if (this.dialog.isShowing())

			{

				this.dialog.dismiss();

			}

			if (success)

			{
				Snackbar.make(rootView, R.string.dados_enviados,Snackbar.LENGTH_LONG).show();

			}
			else

			{
				Snackbar.make(rootView, R.string.erro_dados_enviados,Snackbar.LENGTH_LONG).show();

			}
		}
	}

}
