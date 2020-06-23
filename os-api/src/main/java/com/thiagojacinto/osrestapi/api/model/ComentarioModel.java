package com.thiagojacinto.osrestapi.api.model;

import java.time.OffsetDateTime;

public class ComentarioModel {
	
	private Long id;
	private String descricao;
	private OffsetDateTime dataPublicacao;
	
	public Long getId() {
		return id;
	}
	public String getDescricao() {
		return descricao;
	}
	public OffsetDateTime getDataPublicacao() {
		return dataPublicacao;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public void setDataPublicacao(OffsetDateTime dataPublicacao) {
		this.dataPublicacao = dataPublicacao;
	}
	
}
