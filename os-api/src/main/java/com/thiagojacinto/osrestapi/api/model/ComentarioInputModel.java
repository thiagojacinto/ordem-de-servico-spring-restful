package com.thiagojacinto.osrestapi.api.model;

import javax.validation.constraints.NotBlank;

public class ComentarioInputModel {
	
	@NotBlank
	private String descricao;
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
