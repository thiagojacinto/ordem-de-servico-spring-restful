package com.thiagojacinto.osrestapi.api.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class OrdemServicoRepresentationModel {
	
	private Long id;
//	private String nomeCliente;
	private ClienteRepresentationModel cliente;
	private String descricao;
	private BigDecimal preco;
	private OffsetDateTime dataAbertura;
	private OffsetDateTime dataModificacao;
	private OffsetDateTime dataFechamento;
	
	public Long getId() {
		return id;
	}
//	public String getNomeCliente() {
//		return nomeCliente;
//	}
	public ClienteRepresentationModel getCliente() {
		return cliente;
	}
	public String getDescricao() {
		return descricao;
	}
	public BigDecimal getPreco() {
		return preco;
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
	public void setId(Long id) {
		this.id = id;
	}
//	public void setNomeCliente(String nomeCliente) {
//		this.nomeCliente = nomeCliente;
//	}
	public void setCliente(ClienteRepresentationModel Cliente) {
		this.cliente = Cliente;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public void setPreco(BigDecimal preco) {
		this.preco = preco;
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
	
}
