package org.elsmancs.practica.repositorio;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.elsmancs.practica.domain.Usuaria;
import org.springframework.stereotype.Repository;

@Repository
public class Repositorio {
	
	@PersistenceContext
	EntityManager em;

	public Usuaria cargaUser(String userName) {
		// TODO Auto-generated method stub
		return em.find(Usuaria.class, userName);
	}

}
