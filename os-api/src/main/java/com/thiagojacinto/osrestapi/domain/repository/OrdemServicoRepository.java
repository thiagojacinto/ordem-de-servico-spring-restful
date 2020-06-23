package com.thiagojacinto.osrestapi.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thiagojacinto.osrestapi.domain.models.OrdemServico;

public interface OrdemServicoRepository extends JpaRepository<OrdemServico, Long> {

	public List<OrdemServico> findByCliente(Long clientId);

}
