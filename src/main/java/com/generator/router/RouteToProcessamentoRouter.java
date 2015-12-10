package com.generator.router;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.generator.model.UnidadeSessao;
import com.generator.model.UnidadeSessaoFim;
import com.generator.model.UnidadeSessaoInicio;

@Component
public class RouteToProcessamentoRouter extends SpringRouteBuilder {

	@Value("${fila.processamento.unidade.sessao}")
	String endpoitMetadados;
	
	
	@Override
	public void configure() throws Exception {
		
		from(endpoitMetadados).
		choice()
        	.when(body().isInstanceOf(UnidadeSessaoInicio.class))
            	.to("log:InicioSessao")
        	.when(body().isInstanceOf(UnidadeSessaoFim.class))
            	.to("log:FimSessao")
        	.otherwise()
            	.to("log:Nada");
	}

}
