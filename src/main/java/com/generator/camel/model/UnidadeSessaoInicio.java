package com.generator.camel.model;

import java.text.ParseException;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class UnidadeSessaoInicio extends UnidadeSessao {

	private static final long serialVersionUID = 2419964965489795117L;

	public UnidadeSessaoInicio(String dataRegistro, String codigoSessao, String usuario) throws ParseException {
		super(dataRegistro, codigoSessao, usuario);
	}

}
