package com.generator.coletor.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.apache.camel.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.generator.coletor.model.Sessao;
import com.generator.coletor.model.UnidadeSessaoFim;
import com.generator.coletor.model.UnidadeSessaoInicio;
import com.generator.coletor.repository.GrupoFimSessao;
import com.generator.coletor.repository.GrupoInicioSessao;

@Component
public class GrupoFimSessaoHandler {

	@Autowired
	private GrupoInicioSessao grupoStart;
	
	@Autowired
	private GrupoFimSessao grupoStop;
	
	@Handler
	public void finalizarSessao(Exchange exchange){
		Message in = exchange.getIn();
		UnidadeSessaoFim usFim = in.getBody(UnidadeSessaoFim.class);
		String idSessao = usFim.getIdSessao();
		
		if(grupoStart.containsKey(idSessao)){
			UnidadeSessaoInicio usInicio = grupoStart.get(idSessao);
			grupoStart.remove(idSessao, usInicio);
			Sessao sessao = new Sessao(usInicio, usFim);
			Message out = exchange.getOut();
			out.setBody(sessao);
		}else{
			grupoStop.put(idSessao, usFim);
		}
	}
}
