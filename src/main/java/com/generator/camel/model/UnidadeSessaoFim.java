package com.generator.camel.model;

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

	public UnidadeSessaoFim(String dataRegistro, String codigoSessao, String usuario, String duracao, String bytesEnviados,
			String bytesRecebidos, String pacotesEnviados, String pacotesRecebidos) throws ParseException {
		super(dataRegistro, codigoSessao, usuario);
		this.duracao = Long.parseLong(duracao);
		this.bytesEnviados = Long.parseLong(bytesEnviados);
		this.bytesRecebidos = Long.parseLong(bytesRecebidos);
		this.pacotesEnviados = Long.parseLong(pacotesEnviados);
		this.pacotesRecebidos = Long.parseLong(pacotesRecebidos);
	}
}
