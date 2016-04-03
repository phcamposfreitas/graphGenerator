package com.generator.coletor.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.generator.coletor.model.UnidadeSessaoInicio;
import com.generator.coletor.repository.GrupoInicioSessao;

@Component
public class GrupoInicioSessaoHandler {

	@Autowired
	private GrupoInicioSessao grupoStart;
	
	@Handler
	public void iniciarSessao(Exchange exchange){
		UnidadeSessaoInicio usInicio = exchange.getIn().getBody(UnidadeSessaoInicio.class);
		String idSessao = usInicio.getIdSessao();
		grupoStart.put(idSessao, usInicio);
	}
}
