package com.thiagojacinto.osrestapi.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thiagojacinto.osrestapi.domain.models.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	// Customized methods with Spring JPA
	List<Cliente> findByTelefone(String telefone);

	List<Cliente> findByNomeContaining(String subString);
	
	Cliente findByEmail(String email);
	
}
