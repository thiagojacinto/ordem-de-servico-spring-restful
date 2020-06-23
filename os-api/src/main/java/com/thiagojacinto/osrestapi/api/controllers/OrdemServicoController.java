package com.thiagojacinto.osrestapi.api.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
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

import com.thiagojacinto.osrestapi.api.model.OrdemServicoRepresentationModel;
import com.thiagojacinto.osrestapi.domain.models.OrdemServico;
import com.thiagojacinto.osrestapi.domain.repository.OrdemServicoRepository;
import com.thiagojacinto.osrestapi.domain.service.GestaoOrdemServicoService;

@RestController
@RequestMapping("/os")
public class OrdemServicoController {
	
	@Autowired
	private GestaoOrdemServicoService ordemServicoService;
	
	@Autowired
	private OrdemServicoRepository ordemServicoRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<OrdemServicoRepresentationModel> listar() {
		return converterParaOrdemServicoRepresentationModels(
				ordemServicoRepository.findAll()
				);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public OrdemServicoRepresentationModel registrar(@Valid @RequestBody OrdemServico novaOrdemServico) {
		return converterParaOrdemServicoRepresentationModel(
				ordemServicoService.registrar(novaOrdemServico)
				);
	}
	
	@GetMapping("/{ordemServicoId}")
	public ResponseEntity<OrdemServicoRepresentationModel> buscar(@PathVariable Long ordemServicoId) {
		
		Optional<OrdemServico> ordemServicoProcurada = ordemServicoRepository.findById(ordemServicoId);
		
		if (ordemServicoProcurada.isPresent()) {
			OrdemServicoRepresentationModel ordemServicoModel = 
					converterParaOrdemServicoRepresentationModel(ordemServicoProcurada.get());
					
			return ResponseEntity.ok(ordemServicoModel);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{ordemServicoId}")
	public void remover(@PathVariable Long ordemServicoId) {
		
		ordemServicoService.remover(ordemServicoId);
		
	}
	
	@PutMapping("/{ordemServicoId}/finalizacao")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void finalizar(@PathVariable Long ordemServicoId) {
		ordemServicoService.finalizar(ordemServicoId);
	}
	
	private OrdemServicoRepresentationModel converterParaOrdemServicoRepresentationModel(OrdemServico ordemServico) {
		return modelMapper.map(ordemServico, OrdemServicoRepresentationModel.class);
	}
	
	private List<OrdemServicoRepresentationModel> converterParaOrdemServicoRepresentationModels(List<OrdemServico> ordensDeServico) {
		return ordensDeServico.stream()
				.map(ordem -> converterParaOrdemServicoRepresentationModel(ordem))
				.collect(Collectors.toList());
	}
}
