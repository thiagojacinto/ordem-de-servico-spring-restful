package com.thiagojacinto.osrestapi.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thiagojacinto.osrestapi.domain.exception.DomainException;
import com.thiagojacinto.osrestapi.domain.models.Cliente;
import com.thiagojacinto.osrestapi.domain.repository.ClienteRepository;

@Service
public class CadastroClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	public Cliente salvar(Cliente novoCliente) {
		Cliente clienteExistente = clienteRepository.findByEmail(novoCliente.getEmail());
		
		if (clienteExistente != null && !clienteExistente.equals(novoCliente)) {
			throw new DomainException("Já existe cliente registrado com esse e-mail. Tente inserir outro.");
		}
		
		return clienteRepository.save(novoCliente);
	}
	
	public void remover(Long clienteId) {
		clienteRepository.deleteById(clienteId);
	}

}
