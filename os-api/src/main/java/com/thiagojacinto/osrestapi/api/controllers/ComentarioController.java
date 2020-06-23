package com.thiagojacinto.osrestapi.api.controllers;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.thiagojacinto.osrestapi.api.model.ComentarioInputModel;
import com.thiagojacinto.osrestapi.api.model.ComentarioModel;
import com.thiagojacinto.osrestapi.domain.exception.EntityNotFoundException;
import com.thiagojacinto.osrestapi.domain.models.Comentario;
import com.thiagojacinto.osrestapi.domain.models.OrdemServico;
import com.thiagojacinto.osrestapi.domain.repository.OrdemServicoRepository;
import com.thiagojacinto.osrestapi.domain.service.GestaoComentarioService;

@RestController
@RequestMapping("/os/{ordemServicoId}/comentarios")
public class ComentarioController {
	
	@Autowired
	private GestaoComentarioService comentarioService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private OrdemServicoRepository ordemServicoRepository;
	
	@GetMapping
	public List<ComentarioModel> listar(@PathVariable Long ordemServicoId) {
		
		OrdemServico ordemServicoProcurada = ordemServicoRepository
				.findById(ordemServicoId)
				.orElseThrow(() -> new EntityNotFoundException("Ordem de serviço não encontrada."));
		
		return convertToComentariosList(ordemServicoProcurada.getComentarios());
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ComentarioModel adicionar(@PathVariable Long ordemServicoId,
			@Valid @RequestBody ComentarioInputModel comentarioInput) {
		
		Comentario novoComentario = comentarioService.cadastrar(ordemServicoId, comentarioInput.getDescricao());
		
		return convertToComentarioModel(novoComentario);
		
	}
	
	private ComentarioModel convertToComentarioModel(Comentario comentario) {
		return modelMapper.map(comentario, ComentarioModel.class);
	}
	
	private List<ComentarioModel> convertToComentariosList(List<Comentario> comentarios) {
		return comentarios.stream()
				.map(comentario -> convertToComentarioModel(comentario))
				.collect(Collectors.toList());
	}
}
