package com.generator.router;

import java.util.LinkedHashMap;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.generator.model.Sessao;
import com.generator.model.UnidadeSessao;
import com.generator.model.UnidadeSessaoFim;
import com.generator.model.UnidadeSessaoInicio;

@Component
public class RouteToProcessamentoRouter extends SpringRouteBuilder {

	@Value("${fila.processamento.unidade.sessao}")
	String endpoitMetadados;
	
	private LinkedHashMap<String, UnidadeSessaoInicio> grupoStart = new LinkedHashMap<String, UnidadeSessaoInicio>();
	private LinkedHashMap<String, UnidadeSessaoFim> grupoStop = new LinkedHashMap<String, UnidadeSessaoFim>();
	
	
	
	@Override
	public void configure() throws Exception {
		
		from(endpoitMetadados)
		.choice()
        	.when(body().isInstanceOf(UnidadeSessaoInicio.class))
            	.to("log:InicioSessao")
            	.process(new Processor() {
					
					@Override
					public void process(Exchange exchange) throws Exception {
						UnidadeSessaoInicio usInicio = exchange.getIn().getBody(UnidadeSessaoInicio.class);
						String idSessao = usInicio.getIdSessao();
						grupoStart.put(idSessao, usInicio);
					}
				})
        	.when(body().isInstanceOf(UnidadeSessaoFim.class))
            	.to("log:InicioFimSessao")
            	.process(new Processor() {

					@Override
					public void process(Exchange exchange) throws Exception {
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
				})
            	.to("log:FimFimSessao")
        	.otherwise()
            	.to("log:Nada");
	}

}
