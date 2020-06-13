package org.elsmancs.practica.servicio;

import java.util.ArrayList;
import java.util.List;

import org.elsmancs.practica.domain.Orden;
import org.elsmancs.practica.domain.Usuaria;
import org.elsmancs.practica.repositorio.Repositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class Servicio {
	
	@Autowired
	Repositorio repo;
	
	public List<Orden> listarOrdenesUser(String nameUser) {
		// TODO Auto-generated method stub
		return repo.queryUser(nameUser);
	}
	
	public Usuaria peticionUser(String userName) {
		
		return repo.cargaUser(userName);
	}
    
}