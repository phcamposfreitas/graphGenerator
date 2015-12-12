package com.generator.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class Sessao implements Serializable {

	private static final long serialVersionUID = 471402455079087866L;
	
	private String id;
	private String usuario;
	private Date dataInicio;
	private Date dataFim;
	private Long duracao;
	
	public Sessao(UnidadeSessaoInicio usInicio, UnidadeSessaoFim usFim) {
		this.id = usInicio.getIdSessao();
		this.usuario = usInicio.getUsuario();
		this.dataInicio = usInicio.getData();
		this.dataFim = usFim.getData();
		this.duracao = usFim.getDuracao();
	}
	
	

}
