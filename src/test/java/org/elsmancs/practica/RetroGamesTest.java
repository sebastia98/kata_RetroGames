/*
 *  - Crea un repo privado compartido sólo con dfleta en GitHub.
 *  - Realiza un commit al pasar cada caso test.
 *  - Sin este commit tras cada caso, no corrijo el examen.
 */



package org.elsmancs.practica;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.elsmancs.practica.controlador.Controlador;
import org.elsmancs.practica.domain.RetroGame;
import org.elsmancs.practica.repositorio.Repositorio;
import org.elsmancs.practica.servicio.Servicio;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Construye una aplicacion que maneja una base de datos
 * de un servicio para jugar a videojuegos retro,
 * con las personas usuarias (users) del servicio
 * y los retrogames disponibles (items).
 * Las usuarias realizan peticiones (ordenes) al servicio
 * para jugar a juegos. 
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Sql(statements = {
		"delete from t_ordenes",
		"delete from t_items",
		"delete from t_users",
		"insert into t_users (user_nom, user_prop) values ('Guybrush', 15)",
		"insert into t_users (user_nom, user_prop) values ('Bernard Bernoulli', 100)",
		"insert into t_items (item_nom, item_prop, item_tipo) values ('Ghosts n Goblins', 20, 'RetroGame')",
		"insert into t_items (item_nom, item_prop, item_tipo) values ('El dia del tentaculo', 7, 'RetroGame')",
		"insert into t_ordenes (ord_id, ord_user, ord_item) values (1,'Guybrush','El dia del tentaculo')",
})
@AutoConfigureMockMvc
public class RetroGamesTest {

    @PersistenceContext
	private EntityManager em;

	@Autowired(required = false)
	Repositorio repo;
	
	@Autowired(required = false)
	Servicio servicio;

	@Autowired(required = false)
	private MockMvc mockMvc;

	@Autowired(required = false)
	Controlador controlador;
	
	/**
	 * Tests sobre los mappings
	 * 
	 * Observa el esquema de la base de datos que espera 
	 * la aplicacion en el fichero:
	 * src/main/resources/schema.sql
	 */
	
	// Completa la definicion y el mapping
	// de la clase RetroGame a la tabla t_items
	@Test
	public void test_mapping_game() {
		RetroGame game = em.find(RetroGame.class, "El dia del tentaculo");
		assertNotNull(game);
		assertEquals("El dia del tentaculo", game.getNombre()); //item_nom
		assertEquals(7, game.getDificultad(), 0); //item_prop
		assertEquals("RetroGame", game.getTipo()); //item_tipo
	}	
}