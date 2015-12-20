package com.generator.camel.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.Data;

@Data
public class Sessao implements Serializable {

	private static final long serialVersionUID = 471402455079087866L;
	
	private String idSessao;
	private String usuario;
	
	@JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSSZ")
	private Date dataInicio;
	
	@JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSSZ")
	private Date dataFim;
	
	private Long duracao;
	
	public Sessao(UnidadeSessaoInicio usInicio, UnidadeSessaoFim usFim) {
		this.idSessao = usInicio.getIdSessao();
		this.usuario = usInicio.getUsuario();
		this.dataInicio = usInicio.getData();
		this.dataFim = usFim.getData();
		this.duracao = usFim.getDuracao();
	}
	
	

}
