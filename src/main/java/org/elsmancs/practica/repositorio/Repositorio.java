package org.elsmancs.practica.repositorio;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.elsmancs.practica.domain.Orden;
import org.elsmancs.practica.domain.RetroGame;
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
	public RetroGame cargaGame(String gameName) {
		return em.find(RetroGame.class, gameName);
	}

	public Orden ordenar(String userName, String gameName) {
		Usuaria user = cargaUser(userName);
		RetroGame game = cargaGame(gameName);
		Orden orden = new Orden();
		
		if(user != null && game != null) {
			orden.setUser(user);
			orden.setGame(game);
			return orden;
		}
		return null;
	}

}
