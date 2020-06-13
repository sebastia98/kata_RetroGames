package org.elsmancs.practica.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_users")
public class Usuaria {
	
	@Id
	@Column(name = "user_nom")
	private String userName;
	
	@Column(name = "user_prop")
	private int userSkill;

	public String getNombre() {
		// TODO Auto-generated method stub
		return this.userName;
	}

	public int getDestreza() {
		// TODO Auto-generated method stub
		return this.userSkill;
	}
}
