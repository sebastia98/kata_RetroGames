package org.elsmancs.practica.controlador;

import org.elsmancs.practica.domain.Usuaria;
import org.elsmancs.practica.servicio.Servicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Controlador {
    
	@Autowired
	Servicio service;
	
	@RequestMapping(path = "/usuaria/{userName}")
	@ResponseBody
	public Usuaria peticionUser(@PathVariable String userName) {
		return service.peticionUser(userName);
	}
	
	
	
}