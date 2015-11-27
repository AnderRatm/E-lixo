package com.project.anderson.e_lixo;

public class Item {
	int id;
	String descricao;
	int quantidade;
	double peso;
	int cod_entrega;
	Entrega entrega;

	public Item(int id, String descricao, int quantidade, double peso,int cod_entrega, Entrega entrega) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.quantidade = quantidade;
		this.peso = peso;
		this.cod_entrega = cod_entrega;
		this.entrega = entrega;
	}

	public Item() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

	public Entrega getEntrega() {
		return entrega;
	}

	public void setEntrega(Entrega entrega) {
		this.entrega = entrega;
	}
	public String toString() {
		String s = this.entrega != null ? this.entrega.getId() + " - ": "";
		s += this.id;
		
		return s;
	}

	public int getCod_entrega() {
		return cod_entrega;
	}

	public void setCod_entrega(int cod_entrega) {
		this.cod_entrega = cod_entrega;
	}
	

}
