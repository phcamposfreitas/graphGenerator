package com.generator.coletor.repository;

import java.util.LinkedHashMap;

import org.springframework.stereotype.Component;

import com.generator.coletor.model.UnidadeSessaoFim;

@Component
public class GrupoFimSessao {

	private LinkedHashMap<String, UnidadeSessaoFim> grupoStop = new LinkedHashMap<String, UnidadeSessaoFim>();
	
	public void put(String idSessao, UnidadeSessaoFim usFim){
		grupoStop.put(idSessao, usFim);
	}
	
	
}
