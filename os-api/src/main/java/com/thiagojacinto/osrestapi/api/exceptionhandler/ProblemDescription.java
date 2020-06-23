package com.thiagojacinto.osrestapi.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ProblemDescription {
	
	private Integer status;
	private OffsetDateTime dataHora;
	private String titulo;
	private List<ProblemType> types;
	
	public static class ProblemType {
		private String campo;
		private String mensagem;
		
		public ProblemType(String campo, String mensagem) {
			super();
			this.campo = campo;
			this.mensagem = mensagem;
		}
		
		public String getCampo() {
			return campo;
		}
		public String getMensagem() {
			return mensagem;
		}
		public void setCampo(String campo) {
			this.campo = campo;
		}
		public void setMensagem(String mensagem) {
			this.mensagem = mensagem;
		}
		
		
	}
	
	public Integer getStatus() {
		return status;
	}
	public OffsetDateTime getDataHora() {
		return dataHora;
	}
	public String getTitulo() {
		return titulo;
	}
	public List<ProblemType> getTypes() {
		return types;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}
	public void setDataHora(OffsetDateTime dataHota) {
		this.dataHora = dataHota;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public void setTypes(List<ProblemType> types) {
		this.types = types;
	}
	
}
