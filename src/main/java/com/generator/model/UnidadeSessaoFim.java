package com.generator.model;

import java.text.ParseException;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class UnidadeSessaoFim extends UnidadeSessao {

	private static final long serialVersionUID = -1599135035061450035L;

	private Long duracao;
	private Long bytesEnviados;
	private Long bytesRecebidos;
	private Long pacotesEnviados;
	private Long pacotesRecebidos;
	
	public UnidadeSessaoFim(String dataRegistro, String codigoSessao, String usuario, String duracao) throws ParseException {
		super(dataRegistro, codigoSessao, usuario);
		this.duracao = Long.parseLong(duracao);
	}
}
