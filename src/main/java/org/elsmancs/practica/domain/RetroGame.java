package org.elsmancs.practica.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_items")
public class RetroGame {
	
	@Id
	@Column(name = "item_nom")
	private String nom;
	
	@Column(name = "item_prop")
	private int dificultad;
	
	@Column(name = "item_tipo")
	private String tipo;

	public String getNombre() {
		// TODO Auto-generated method stub
		return nom;
	}

	public int getDificultad() {
		// TODO Auto-generated method stub
		return dificultad;
	}

	public String getTipo() {
		// TODO Auto-generated method stub
		return tipo;
	}
	
	
}
