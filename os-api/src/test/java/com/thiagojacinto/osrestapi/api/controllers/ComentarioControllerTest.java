package com.thiagojacinto.osrestapi.api.controllers;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.standaloneSetup;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.util.NestedServletException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thiagojacinto.osrestapi.api.model.ComentarioInputModel;
import com.thiagojacinto.osrestapi.api.model.ComentarioModel;
import com.thiagojacinto.osrestapi.core.ModelMapperConfig;
import com.thiagojacinto.osrestapi.domain.models.Comentario;
import com.thiagojacinto.osrestapi.domain.models.OrdemServico;
import com.thiagojacinto.osrestapi.domain.repository.OrdemServicoRepository;
import com.thiagojacinto.osrestapi.domain.service.GestaoComentarioService;

import io.restassured.http.ContentType;

@WebMvcTest(controllers = ComentarioController.class)
@Import(ModelMapperConfig.class)
public class ComentarioControllerTest {
	
	@Autowired
	private ComentarioController comentarioController;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private OrdemServicoRepository ordemServicoRepository;
	
	@MockBean
	private GestaoComentarioService comentarioService;
	
	@MockBean
	private ComentarioModel comentarioModel;
	
	@MockBean
	private ComentarioInputModel comentarioInputModel;
	
	@BeforeEach
	public void setup() {
		standaloneSetup(this.comentarioController);
	}
	
	@Test
	public void deveRetornarComentarios_QuandoProcurarPorOrdemServicoRegistrada() {
		
		@SuppressWarnings("unchecked")
		List<Comentario> comentariosMock = Mockito.mock(ArrayList.class);
		
		OrdemServico OSMock = Mockito.mock(OrdemServico.class);
		OSMock.setComentarios(comentariosMock);
		
		Mockito.when(ordemServicoRepository.findById(1L))
			.thenReturn(Optional.of(OSMock));
		
		given()
			.accept(ContentType.JSON)
		.when()
			.get("/os/{ordemServicoId}/comentarios", 1L)
		.then()
			.contentType(ContentType.JSON)
			.statusCode(HttpStatus.OK.value())
			.assertThat()
				.body(equalTo("[]"));
		
	}
	
	@Test
	public void deveRetornarException_QuandoNãoEncontrarRecurso() {
		
		Mockito.when(ordemServicoRepository.findById(1L))
			.thenReturn(Optional.empty());
		
		assertThrows(NestedServletException.class, 
		() -> given()
				.accept(ContentType.JSON)
			.when()
				.get("/os/{ordemServicoId}/comentarios", 1L)
			.then()
				.log().ifError()
		);
	}
	
	@Test
	public void deveRetornarHttpcodeCreated_QuandoAdicionarComentarioValido() throws JsonProcessingException {
		
		String descricao = "novo comentario";
		
		ComentarioInputModel comentarioInputModel = new ComentarioInputModel();
		comentarioInputModel.setDescricao(descricao);
		String comentarioInputString = objectMapper.writeValueAsString(comentarioInputModel);
		
		Comentario comentario = new Comentario();
		comentario.setDescricao(comentarioInputModel.getDescricao());
		
		Mockito.when(comentarioService.cadastrar(1L, comentarioInputModel.getDescricao()))
			.thenReturn(comentario);
		
		given()
			.accept("application/json;charset=utf-8")
			.contentType("application/json;charset=utf-8")
			.body(comentarioInputString)
		.when()
			.post("/os/{ordemServicoId}/comentarios", 1L)
		.then()
			.contentType(ContentType.JSON)
			.log().ifValidationFails()
			.statusCode(HttpStatus.CREATED.value())
			.assertThat()
				.body("descricao", equalTo(descricao));
	}
	
	@Test
	public void deveRetornarTipoComentarioModel_QuandoChamarMetodoConvertToComentarioModel() {
		
		OrdemServico os = Mockito.mock(OrdemServico.class);
		
		Comentario comentario = new Comentario();
		comentario.setDataPublicacao(OffsetDateTime.MIN);
		comentario.setDescricao("novo comentario");
		comentario.setOrdemServico(os);
		
		ComentarioModel comentarioModel = ReflectionTestUtils
				.invokeMethod(comentarioController, "convertToComentarioModel", comentario);
		
		assertEquals(comentario.getDescricao(), comentarioModel.getDescricao());
		assertEquals(comentario.getDataPublicacao(), comentarioModel.getDataPublicacao());
	}
	
//	@Test
//	public void deveRetornarListaTipoComentarioModel_QuandoChamarMetodoConvertToComentariosList() {
//		
//	}
	
}
