package com.thiagojacinto.osrestapi.api.controllers;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thiagojacinto.osrestapi.domain.models.Cliente;

@RestController
public class ControllerWithEntityManager {
	
	@PersistenceContext
	private EntityManager manager;
	
	@GetMapping("/clientes/EM")
	public List<Cliente> listar() {
		return manager.createQuery("from Cliente", Cliente.class)
				.getResultList();
	}

}
