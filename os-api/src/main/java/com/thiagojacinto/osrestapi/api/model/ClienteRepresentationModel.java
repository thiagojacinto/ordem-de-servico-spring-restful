package com.thiagojacinto.osrestapi.api.model;

public class ClienteRepresentationModel {
	
	private Long id;
	private String nome;
	
	public Long getId() {
		return id;
	}
	public String getNome() {
		return nome;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setNome(String name) {
		this.nome = name;
	}
	
}
