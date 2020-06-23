package com.thiagojacinto.osrestapi.domain.models;

import java.time.OffsetDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Comentario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@ManyToOne
	private OrdemServico ordemServico;
	
	@NotBlank
	@Size(max = 255)
	private String descricao;
	
	private OffsetDateTime dataPublicacao;
	private OffsetDateTime dataEdicao;
	
	public Long getId() {
		return id;
	}
	public OrdemServico getOrdemServico() {
		return ordemServico;
	}
	public String getDescricao() {
		return descricao;
	}
	public OffsetDateTime getDataPublicacao() {
		return dataPublicacao;
	}
	public OffsetDateTime getDataEdicao() {
		return dataEdicao;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setOrdemServico(OrdemServico ordemServico) {
		this.ordemServico = ordemServico;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public void setDataPublicacao(OffsetDateTime dataPublicacao) {
		this.dataPublicacao = dataPublicacao;
	}
	public void setDataEdicao(OffsetDateTime dataEdicao) {
		this.dataEdicao = dataEdicao;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Comentario other = (Comentario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
