package com.generator.router;

import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.generator.processor.TransformLogToUnidadeSessao;

@Component
public class LogCollectorRouter extends SpringRouteBuilder {

	private String diretorioLinux = "/media/paulo/Tania";

	private String diretorioArquivos = "/aadsiMonografia/arquivos";
	private String diretorioLogs = "/logs";
	private String diretorioMetadados = "/metadados";
	
	String endpoitLog = "file:" + diretorioLinux + diretorioArquivos + diretorioLogs;
	
	@Value("$(fila.processamento.unidade.sessao)")
	String endpoitMetadados;
	
	@Override
	public void configure() throws Exception {
		String configuracaoLeitorLog = "?move=.done";//&delay=60000";
		
		from(endpoitLog + configuracaoLeitorLog).
		bean(TransformLogToUnidadeSessao.class).
		to(endpoitMetadados)
		;
		
	}
	
}
