package com.thiagojacinto.osrestapi.api.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.thiagojacinto.osrestapi.domain.models.Cliente;
import com.thiagojacinto.osrestapi.domain.repository.ClienteRepository;
import com.thiagojacinto.osrestapi.domain.service.CadastroClienteService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

@Api()
@RestController
@RequestMapping(path = "/clientes")
public class ClienteController {

	@Autowired
	private ClienteRepository clientRepository;
	
	@Autowired
	private CadastroClienteService cadastroClienteService;

	@GetMapping
	public List<Cliente> listar() {

		return clientRepository.findAll();
	}
	
	@ApiOperation(
			value = "[pt-BR] Buscar um cliente a partir do seu ID. [en] Find a client by his/her ID.", 
			notes = "",
			nickname = "listarClientePorId")
	 @ApiResponses(value = {
	            @ApiResponse(code = 400, message = "[pt-BR] ID inválido. [en] Invalid ID input."),
	            @ApiResponse(code = 404, message = "Nenhum cliente encontrado com o ID inserido. [en] Client not found with input ID.")
	            })
	@GetMapping("/{clienteId}") 
	public ResponseEntity<Cliente> buscar(
			@ApiParam(value = "[pt-BR] ID válido do cliente desejado. [en] Desired client's valid ID.", 
			required = true,
			example = "2")
			@PathVariable Long clienteId) {
		Optional<Cliente> clienteProcurado = clientRepository.findById(clienteId);
		
		if (clienteProcurado.isPresent()) {
			return ResponseEntity.ok(clienteProcurado.get());
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente registrar(@Valid @RequestBody Cliente novoCliente) {
		return cadastroClienteService.salvar(novoCliente);
	}
	
	@PutMapping("/{clienteId}")
	public ResponseEntity<Cliente> atualizar(@PathVariable Long clienteId, @Valid @RequestBody Cliente clienteAtualizado) {
		
		if (!clientRepository.existsById(clienteId)) {
			return ResponseEntity.notFound().build();
		}
		
		clienteAtualizado.setId(clienteId);
		cadastroClienteService.salvar(clienteAtualizado);
		
		return ResponseEntity.ok(clienteAtualizado);
	}
	
	@DeleteMapping("/{clienteId}")
	public ResponseEntity<Void> remover(@PathVariable Long clienteId) {
		
		if (!clientRepository.existsById(clienteId)) {
			return ResponseEntity.notFound().build();
		}
		
		cadastroClienteService.remover(clienteId);
		
		return ResponseEntity.noContent().build();
	}
}
