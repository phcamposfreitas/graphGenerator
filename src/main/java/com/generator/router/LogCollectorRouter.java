package com.generator.router;

import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class LogCollectorRouter extends SpringRouteBuilder {

	private String diretorioLinux = "/media/paulo/Tania";

	private String diretorioArquivos = "/aadsiMonografia/arquivos";
	private String diretorioLogs = "/logs";
	private String diretorioMetadados = "/metadados";
	
	String endpoitLog = "file:" + diretorioLinux + diretorioArquivos + diretorioLogs;
	String endpoitMetadados = "file:" + diretorioLinux + diretorioArquivos + diretorioMetadados;
	
	@Override
	public void configure() throws Exception {
		String configuracaoLeitorLog = "?move=.done&delay=60000";
		
		from(endpoitLog + configuracaoLeitorLog).
		
		to(endpoitMetadados);
		
	}
	
}
