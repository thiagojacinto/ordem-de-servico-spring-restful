package com.thiagojacinto.osrestapi.domain.service;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thiagojacinto.osrestapi.domain.exception.EntityNotFoundException;
import com.thiagojacinto.osrestapi.domain.models.Comentario;
import com.thiagojacinto.osrestapi.domain.models.OrdemServico;
import com.thiagojacinto.osrestapi.domain.repository.ComentarioRepository;
import com.thiagojacinto.osrestapi.domain.repository.OrdemServicoRepository;

@Service
public class GestaoComentarioService {
	
	@Autowired
	private ComentarioRepository comentarioRepository;
	
	@Autowired
	private OrdemServicoRepository ordemServicoRepository;
	
	public Comentario cadastrar(Long ordemServicoId, String descricao) {
		
		OrdemServico ordemServico = ordemServicoRepository.findById(ordemServicoId)
				.orElseThrow(() -> new EntityNotFoundException("Ordem de serviço não foi encontrada."));
		
		Comentario novoComentario = new Comentario();
		novoComentario.setOrdemServico(ordemServico);
		novoComentario.setDescricao(descricao);
		novoComentario.setDataPublicacao(OffsetDateTime.now());
		
		return comentarioRepository.save(novoComentario);
	}
	
	public void remover(Long comentarioId) {
		
		comentarioRepository.deleteById(comentarioId);
		
	}
	
}
