package com.thiagojacinto.osrestapi.domain.service;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thiagojacinto.osrestapi.domain.exception.DomainException;
import com.thiagojacinto.osrestapi.domain.models.Cliente;
import com.thiagojacinto.osrestapi.domain.models.OrdemServico;
import com.thiagojacinto.osrestapi.domain.models.StatusOrdemServico;
import com.thiagojacinto.osrestapi.domain.repository.ClienteRepository;
import com.thiagojacinto.osrestapi.domain.repository.OrdemServicoRepository;

@Service
public class GestaoOrdemServicoService {

	@Autowired
	private OrdemServicoRepository ordemServicoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;

	public OrdemServico registrar(OrdemServico ordemServico) {
		
		Cliente clienteExistente = clienteRepository.findById(ordemServico.getCliente().getId())
				.orElseThrow(() -> new DomainException("Cliente não encontrado."));
		
		ordemServico.setCliente(clienteExistente);
		ordemServico.setStatusOrdemServico(StatusOrdemServico.ABERTA);
		ordemServico.setDataAbertura(OffsetDateTime.now());

		return ordemServicoRepository.save(ordemServico);
	}
	
	public void remover(Long ordemServicoId) {
		
		OrdemServico ordemServicoProcurada = encontrarOrdemServico(ordemServicoId);
		
		ordemServicoRepository.delete(ordemServicoProcurada);
		
	}

	
	public void finalizar(Long ordemServicoId) {
		
		OrdemServico ordemServicoProcurada = encontrarOrdemServico(ordemServicoId);
		
		ordemServicoProcurada.finalizarOrdemServico();
		
		ordemServicoRepository.save(ordemServicoProcurada);
		
	}
	
	private OrdemServico encontrarOrdemServico(Long ordemServicoId) {
		OrdemServico ordemServicoProcurada = ordemServicoRepository.findById(ordemServicoId)
				.orElseThrow(() -> new DomainException("Ordem de serviço não encontrado"));
		return ordemServicoProcurada;
	}
}
