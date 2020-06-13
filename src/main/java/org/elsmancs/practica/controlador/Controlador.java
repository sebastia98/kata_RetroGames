package org.elsmancs.practica.controlador;


import org.elsmancs.practica.domain.RetroGame;
import org.elsmancs.practica.domain.Usuaria;
import org.elsmancs.practica.repositorio.NotEnoughProException;
import org.elsmancs.practica.servicio.Servicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Controlador {
    
	@Autowired
	Servicio service;
	
	@RequestMapping(path = "/usuaria/{userName}", method = RequestMethod.GET)
	@ResponseBody
	public Usuaria peticionUser(@PathVariable String userName) {
		return service.peticionUser(userName);
	}
	
	@RequestMapping(path = "/ordena", method = RequestMethod.POST)
	@ResponseBody
	//los parametros deben tener el mismo nombre que se ha implementado en el caso test
	public String añadirOrden(@RequestParam String usuaria, String item) throws NotEnoughProException {
		return service.peticionOrdenar(usuaria, item);
	}
	
	
}