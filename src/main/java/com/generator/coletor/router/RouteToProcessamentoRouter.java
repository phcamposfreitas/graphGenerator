package com.generator.coletor.router;

import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.generator.coletor.model.UnidadeSessaoFim;
import com.generator.coletor.model.UnidadeSessaoInicio;
import com.generator.coletor.processor.GrupoFimSessaoHandler;
import com.generator.coletor.processor.GrupoInicioSessaoHandler;

@Component
public class RouteToProcessamentoRouter extends SpringRouteBuilder {

	@Value("${fila.processamento.unidade.sessao}")
	String endpoitMetadados;
	
	String enpointMongo = "mongodb:myDb?database=graphGenerator&collection=sessao&operation=save";
	
	@Override
	public void configure() throws Exception {
		
		from(endpoitMetadados)
		.choice()
        	.when(body().isInstanceOf(UnidadeSessaoInicio.class))
            	.bean(GrupoInicioSessaoHandler.class)
        	.when(body().isInstanceOf(UnidadeSessaoFim.class))
            	.bean(GrupoFimSessaoHandler.class)
            	.to(enpointMongo)
        	.otherwise()
            	.to("log:Nada");
	}

}
