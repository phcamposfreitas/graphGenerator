package com.generator.coletor.model;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

import lombok.Data;

@Data
public class UnidadeSessao implements Serializable {

	private static final long serialVersionUID = -6684301400175881236L;

	private Date data;
	private String idSessao;
	private String usuario;
	
	public UnidadeSessao(String dataRegistro, String codigoSessao, String usuario) throws ParseException {
		this.data = DateUtils.parseDate(dataRegistro, "EEE MMM d HH:mm:ss yyyy");
		this.idSessao = codigoSessao;
		this.usuario = usuario;
	}
	
}
