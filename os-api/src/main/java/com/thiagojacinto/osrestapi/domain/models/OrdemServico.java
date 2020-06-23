package com.thiagojacinto.osrestapi.domain.models;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.thiagojacinto.osrestapi.domain.ValidationGroups;
import com.thiagojacinto.osrestapi.domain.exception.DomainException;

@Entity
public class OrdemServico {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Valid
	@ConvertGroup(from = Default.class, to = ValidationGroups.ValidClienteId.class)
	@ManyToOne
	@NotNull
	private Cliente cliente;
	
	@NotBlank
	private String descricao;
	
	@NotNull
	private BigDecimal preco;

	@JsonProperty(access = Access.READ_ONLY)
	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private StatusOrdemServico statusOrdemServico;

	@JsonProperty(access = Access.READ_ONLY)
	private OffsetDateTime dataAbertura;
	
	@JsonProperty(access = Access.READ_ONLY)
	private OffsetDateTime dataModificacao;
	
	@JsonProperty(access = Access.READ_ONLY)
	@Column(name = "data_finalizacao")
	private OffsetDateTime dataFechamento;
	
	@OneToMany(mappedBy = "ordemServico")
	private List<Comentario> comentarios = new ArrayList<Comentario>();

	public Long getId() {
		return id;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public String getDescricao() {
		return descricao;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public StatusOrdemServico getStatusOrdemServico() {
		return statusOrdemServico;
	}

	public OffsetDateTime getDataAbertura() {
		return dataAbertura;
	}

	public OffsetDateTime getDataModificacao() {
		return dataModificacao;
	}

	public OffsetDateTime getDataFechamento() {
		return dataFechamento;
	}
	
	public List<Comentario> getComentarios() {
		return comentarios;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public void setStatusOrdemServico(StatusOrdemServico statusOrdemServico) {
		this.statusOrdemServico = statusOrdemServico;
	}

	public void setDataAbertura(OffsetDateTime dataAbertura) {
		this.dataAbertura = dataAbertura;
	}

	public void setDataModificacao(OffsetDateTime dataModificacao) {
		this.dataModificacao = dataModificacao;
	}

	public void setDataFechamento(OffsetDateTime dataFechamento) {
		this.dataFechamento = dataFechamento;
	}
	
	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
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
		OrdemServico other = (OrdemServico) obj;
		if (cliente == null) {
			if (other.cliente != null)
				return false;
		} else if (!cliente.equals(other.cliente))
			return false;
		return true;
	}
	
	private boolean podeSerFinalizada() {
		return getStatusOrdemServico().equals(StatusOrdemServico.ABERTA);
	}
	
	private boolean naoPodeSerFinalizada() {
		return !podeSerFinalizada();
	}
	
	public void finalizarOrdemServico() {
		if (naoPodeSerFinalizada()) {
			throw new DomainException("Ordem de serviço não pode ser finalizada.");
		}
		
		setStatusOrdemServico(StatusOrdemServico.FINALIZADA);
		setDataFechamento(OffsetDateTime.now());
	}
}
