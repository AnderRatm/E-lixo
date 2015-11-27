package com.project.anderson.e_lixo;

public class Entrega {
	int id;
	String local;
	String horario;

	
	public Entrega(int id, String local, String horario) {
		super();
		this.id = id;
		this.local = local;
		this.horario = horario;
		
	}
	
	public Entrega() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLocal() {
		return local;
	}
	public void setLocal(String local) {
		this.local = local;
	}

	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}
}
