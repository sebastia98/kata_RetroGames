package org.elsmancs.practica.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GeneratorType;

@Entity
@Table(name = "t_ordenes")
public class Orden {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ord_id")
	private long id;
	
	@JoinColumn(name = "ord_user")
	@OneToOne
	private Usuaria user;
	
	@JoinColumn(name = "ord_item")
	@OneToOne
	private RetroGame game;

	public Usuaria getUser() {
		// TODO Auto-generated method stub
		return this.user;
	}

	public RetroGame getItem() {
		// TODO Auto-generated method stub
		return this.game;
	}
	
	public void setUser(Usuaria user) {
		this.user = user;
	}
	
	public void setGame(RetroGame game) {
		this.game = game;
	}

	public Long getId() {
		// TODO Auto-generated method stub
		return this.id;
	}
}
