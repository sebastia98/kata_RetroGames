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
import org.elsmancs.practica.domain.Orden;
import org.elsmancs.practica.domain.RetroGame;
import org.elsmancs.practica.domain.Usuaria;
import org.elsmancs.practica.repositorio.NotEnoughProException;
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
	// Completa la definicion y el mapping
	// de la clase Usuaria a la tabla t_users
	@Test
	public void test_mapping_user() {
		Usuaria guy = em.find(Usuaria.class, "Guybrush");
		assertNotNull(guy);
		assertEquals("Guybrush", guy.getNombre()); //user_nom
		assertEquals(15, guy.getDestreza(), 0);  //user_prop
	}
	// Completa la definicion y el mapping
	// de la clase Orden a la tabla t_ordenes
	// El id de esta clase ha de seguir una estrategia
	// Identity
	@Test 
	public void test_mapping_orden() {
		Orden pedido = em.find(Orden.class, 1L);
		assertNotNull(pedido);
		assertEquals("Guybrush", pedido.getUser().getNombre()); //ord_user
		assertEquals("El dia del tentaculo", pedido.getItem().getNombre()); //ord_item
	}
	/**
	 * Crea una clase llamada Repositorio e indica
	 * que es un repositorio o componente de Spring 
	 */
	@Test
	public void test_repositorio_es_componente() {
		assertNotNull(repo);
	}
	/**
	 * Implementa el metodo cargaUser del repositorio
	 * que devuelve el usuario/a con el nombre indicado
	 */
	@Test
	public void test_carga_user() {
		assertNotNull(repo);
		Usuaria guy = repo.cargaUser("Guybrush");
		assertNotNull(guy);
		assertEquals("Guybrush", guy.getNombre());
	}
	/**
     * Implementa el metodo ordenar del repositorio
	 * que permite a un usuario/a pedir un item (RetroGame).
     * El usuario/a y el item ya existen en la bbdd (NO has de crearlos).
	 * 
     * El metodo devuelve la orden de tipo Orden creada.
     * 
     * Guarda la orden en la base de datos.
	 * @throws NotEnoughProException 
	 */
	@Test
	@Transactional
	public void test_ordenar_ok() throws NotEnoughProException {
		assertNotNull(repo);
		Orden orden = repo.ordenar("Bernard Bernoulli", "Ghosts n Goblins");
		assertNotNull(orden);
		assertNotNull(orden.getId());
		assertEquals("Bernard Bernoulli", orden.getUser().getNombre());
		assertEquals("Ghosts n Goblins", orden.getItem().getNombre());
	}
	/**
     * Implementa el metodo ordenar del repositorio
	 * para que no permita generar ordenes de RetroGames
	 * si no existe el usuario/a en la base de datos.
	 * @throws NotEnoughProException 
	 */
	@Test
	@Transactional
	public void test_ordenar_no_user() throws NotEnoughProException {
		assertNotNull(repo);
		Orden orden = repo.ordenar("Wilson", "Ghosts n Goblins");
		assertNull(orden);
	}
	/**
     * Implementa el metodo ordenar del repositorio
	 * para que no permita generar ordenes de RetroGames
	 * si no existe el RetroGame en la base de datos.
	 * @throws NotEnoughProException 
	 */
	@Test
	@Transactional
	public void test_ordenar_no_game() throws NotEnoughProException {
		assertNotNull(repo);
		Orden orden = repo.ordenar("Bernard Bernoulli", "Grim Fandango");
		assertNull(orden);
	}
	/**
	 * Modifica el metodo ordenar para que lance una excepcion
	 * del tipo NotEnoughProException
	 * cuando la destreza del usuario/a sea menor
	 * a la dificultad del RetroGame.
	 */
	@org.junit.Test(expected = NotEnoughProException.class)
	@Transactional
	public void test_ordenar_game_sin_pro() throws NotEnoughProException {
		assertNotNull(repo);
		repo.ordenar("Guybrush", "Ghosts n Goblins");
		Assert.fail();
	}
	/**
	 * Implementa el metodo ordenarMultiple para que un usuario/a
	 * pueda ordenar más de un RetroGame a la vez.
	 * Guarda las ordenes en la base de datos.
     * 
     * No se permiten ordenes si el usuario no existe en la base de datos.
	 * 
	 * No se ordenan items que no existen en la base de datos.
	 * @throws NotEnoughProException 
	 */
	@Test
	@Transactional
	public void test_ordenar_multiples_items() throws NotEnoughProException {
		assertNotNull(repo);
		List<Orden> ordenes = repo.ordenarMultiple("Bernard Bernoulli", Arrays.asList("Ghosts n Goblins", "El dia del tentaculo"));
		assertNotNull(ordenes);

		assertEquals(2, ordenes.size());
		assertFalse(ordenes.contains(null));

		// no se permiten ordenes si el usuario no existe en la base de datos
		ordenes = repo.ordenarMultiple("Wilson", Arrays.asList("Ghosts n Goblins", "El dia del tentaculo"));
		assertTrue(ordenes.isEmpty());
		assertEquals(0, ordenes.size());

		// no se ordenan items que no existen en la base de datos
		ordenes = repo.ordenarMultiple("Bernard Bernoulli", Arrays.asList("Ghosts n Goblins", "Grim Fandango"));
		assertEquals(1, ordenes.size());
	}

}