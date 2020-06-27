package com.thiagojacinto.osrestapi.api.controllers;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.standaloneSetup;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thiagojacinto.osrestapi.domain.models.Cliente;
import com.thiagojacinto.osrestapi.domain.repository.ClienteRepository;
import com.thiagojacinto.osrestapi.domain.service.CadastroClienteService;

import io.restassured.http.ContentType;

@WebMvcTest(controllers = ClienteController.class)
public class ClienteControllerTest {

	@Autowired
	private ClienteController clienteController;
	
	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private CadastroClienteService cadastroClienteService;

	@MockBean
	private ClienteRepository clienteRepository;

	@BeforeEach
	public void setup() {
		// Static imported from RestAssuredMockMvc
		standaloneSetup(this.clienteController);
	}

	@Test
	public void deveRetornarSucesso_QuandoBuscarPorCliente() {

		Mockito.when(this.clienteRepository.findById(1L))
			.thenReturn(Optional.of(new Cliente()));

		given()
				.accept(ContentType.JSON)
			.when()
				.get("/clientes/{clienteId}", 1L)
			.then()
				.statusCode(HttpStatus.OK.value());

	}
	
	@Test
	public void deveRetornarFalha_QuandoBuscarPorClienteInexistente() {

		Mockito.when(this.clienteRepository.findById(10000000L))
			.thenReturn(Optional.ofNullable(null));

		given()
			.accept(ContentType.JSON)
		.when()
			.get("/clientes/{clienteId}", 10000000L)
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	@Test
	public void deveRetornarArrayVazio_QuandoListarCursosSemNenhumCadastrado() {
		
		Mockito.when(this.clienteRepository.findAll())
			.thenReturn(Collections.emptyList());
		
		given()
			.accept(ContentType.JSON)
		.when()
			.get("/clientes")
		.then()
			.statusCode(HttpStatus.OK.value())
			.contentType(ContentType.JSON)
			.body(is(equalTo("[]")));
		
	}
	
	@Test
	public void deveRetornarSucesso_QuandoCadastrarNovoCliente() throws JsonProcessingException {
		
		Cliente novoCliente = new Cliente("Thiago", "Address", "mail@mail.mail", "+55 83 99999 9999"); 
		String clientToString = objectMapper.writeValueAsString(novoCliente);
		
		Mockito.when(this.cadastroClienteService.salvar(novoCliente))
			.thenReturn(novoCliente);
		
		given()
			.accept("application/json;charset=utf-8")
			.contentType("application/json;charset=utf-8")
			.body(clientToString)
		.when()
			.post("/clientes")
		.then()
			.contentType(ContentType.JSON)
			.log().ifValidationFails()
			.statusCode(HttpStatus.CREATED.value())
			.assertThat()
				.body("nome", equalTo(novoCliente.getNome()))
				.body("endereco", equalTo(novoCliente.getEndereco()))
				.body("email", equalTo(novoCliente.getEmail()))
				.body("telefone", equalTo(novoCliente.getTelefone()));
	}
	
	@Test
	public void deveFalhar_QuandoInserirUmClienteComNomeEmBranco() throws JsonProcessingException {
		
		Cliente clienteNomeEmBranco = new Cliente();
		clienteNomeEmBranco.setNome("");
		clienteNomeEmBranco.setEmail("email@email.email");
		clienteNomeEmBranco.setEndereco("Address");
		clienteNomeEmBranco.setTelefone("+55 81 99999-9999");
		
		String clienteEmString = objectMapper.writeValueAsString(clienteNomeEmBranco);
		
		Mockito.when(this.clienteRepository.existsById(1L))
			.thenReturn(true);
		
		Mockito.when(this.cadastroClienteService.salvar(clienteNomeEmBranco))
			.thenReturn(clienteNomeEmBranco);
		
		given()
			.accept("application/json;charset=utf-8")
			.contentType("application/json;charset=utf-8")
			.body(clienteEmString)
		.when()
			.post("/clientes")
		.then()
			.expect(result -> 
				assertTrue(result.getResolvedException() instanceof org.springframework.web.bind.MethodArgumentNotValidException))
			.log().ifValidationFails()
			.expect(result -> result.getResolvedException().getMessage().contains("[must not be blank]"));

		
	}
	
	@Test
	public void deveFalhar_QuandoInserirUmClienteComEmailEmBranco() throws JsonProcessingException {
		
		Cliente clienteEmailEmBranco = new Cliente();
		clienteEmailEmBranco.setNome("Nome");
		clienteEmailEmBranco.setEmail("");
		clienteEmailEmBranco.setEndereco("Address");
		clienteEmailEmBranco.setTelefone("+55 81 99999-9999");
		
		String clienteEmString = objectMapper.writeValueAsString(clienteEmailEmBranco);
		
		Mockito.when(this.clienteRepository.existsById(1L))
			.thenReturn(true);
		
		Mockito.when(this.cadastroClienteService.salvar(clienteEmailEmBranco))
			.thenReturn(clienteEmailEmBranco);
		
		given()
			.accept("application/json;charset=utf-8")
			.contentType("application/json;charset=utf-8")
			.body(clienteEmString)
		.when()
			.post("/clientes")
		.then()
			.expect(result -> 
				assertTrue(result.getResolvedException() instanceof org.springframework.web.bind.MethodArgumentNotValidException))
			.log().ifValidationFails()
			.expect(result -> result.getResolvedException().getMessage().contains("[must not be blank]"));

		
	}
	
	@Test
	public void deveFalhar_QuandoInserirUmClienteComEnderecoEmBranco() throws JsonProcessingException {
		
		Cliente clienteEnderecoEmBranco = new Cliente();
		clienteEnderecoEmBranco.setNome("Nome");
		clienteEnderecoEmBranco.setEmail("email@email.email");
		clienteEnderecoEmBranco.setEndereco("");
		clienteEnderecoEmBranco.setTelefone("+55 81 99999-9999");
		
		String clienteEmString = objectMapper.writeValueAsString(clienteEnderecoEmBranco);
		
		Mockito.when(this.clienteRepository.existsById(1L))
			.thenReturn(true);
		
		Mockito.when(this.cadastroClienteService.salvar(clienteEnderecoEmBranco))
			.thenReturn(clienteEnderecoEmBranco);
		
		given()
			.accept("application/json;charset=utf-8")
			.contentType("application/json;charset=utf-8")
			.body(clienteEmString)
		.when()
			.post("/clientes")
		.then()
			.expect(result -> 
				assertTrue(result.getResolvedException() instanceof org.springframework.web.bind.MethodArgumentNotValidException))
			.expect(result -> result.getResolvedException().getMessage().contains("[must not be blank]"));
	}
	
	@Test
	public void deveRetornarSucesso_QuandoAtualizarUmClienteExistente() throws JsonProcessingException {
		
		Cliente clienteAtualizado = new Cliente();
		clienteAtualizado.setId(1L);
		clienteAtualizado.setNome("Nome atualizado");
		clienteAtualizado.setEmail("atualizado@mail.mail");
		clienteAtualizado.setEndereco("Address");
		clienteAtualizado.setTelefone("+55 81 99999-9999");
		
		String clienteAtualizadoEmString = objectMapper.writeValueAsString(clienteAtualizado);
		
		Mockito.when(this.clienteRepository.existsById(1L))
			.thenReturn(true);
		
		Mockito.when(this.cadastroClienteService.salvar(clienteAtualizado))
			.thenReturn(clienteAtualizado);
		
		given()
			.accept("application/json;charset=utf-8")
			.contentType("application/json;charset=utf-8")
			.body(clienteAtualizadoEmString)
		.when()
			.put("/clientes/{clienteId}", 1L)
		.then()
			.contentType(ContentType.JSON)
			.statusCode(HttpStatus.OK.value())
			.assertThat()
				.body("id", equalTo(Integer.valueOf(clienteAtualizado.getId().toString())))
				.body("nome", equalTo(clienteAtualizado.getNome()))
				.body("endereco", equalTo(clienteAtualizado.getEndereco()))
				.body("email", equalTo(clienteAtualizado.getEmail()))
				.body("telefone", equalTo(clienteAtualizado.getTelefone()));
	}
	
	@Test
	public void deveFalhar_QuandoAtualizarUmClienteQueNãoExiste() throws JsonProcessingException {
		
		Cliente clienteAtualizado = new Cliente();
		clienteAtualizado.setId(1L);
		clienteAtualizado.setNome("Nome atualizado");
		clienteAtualizado.setEmail("atualizado@mail.mail");
		clienteAtualizado.setEndereco("Address");
		clienteAtualizado.setTelefone("+55 81 99999-9999");
		
		String clienteAtualizadoEmString = objectMapper.writeValueAsString(clienteAtualizado);
		
		Mockito.when(this.clienteRepository.existsById(1L))
			.thenReturn(false);
		
		given()
			.accept("application/json;charset=utf-8")
			.contentType("application/json;charset=utf-8")
			.body(clienteAtualizadoEmString)
		.when()
			.put("/clientes/{clienteId}", 1L)
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
			
	}
}
