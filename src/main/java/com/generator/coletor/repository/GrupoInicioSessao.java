package com.generator.coletor.repository;

import java.util.LinkedHashMap;

import org.springframework.stereotype.Component;

import com.generator.coletor.model.UnidadeSessaoInicio;

@Component
public class GrupoInicioSessao {

	private LinkedHashMap<String, UnidadeSessaoInicio> grupoStart = new LinkedHashMap<String, UnidadeSessaoInicio>();
	
	public void put(String idSessao, UnidadeSessaoInicio usInicio){
		grupoStart.put(idSessao, usInicio);
	}

	public boolean containsKey(String idSessao) {
		return grupoStart.containsKey(idSessao);
	}

	public void remove(String idSessao, UnidadeSessaoInicio usInicio) {
		grupoStart.remove(idSessao, usInicio);
	}

	public UnidadeSessaoInicio get(String idSessao) {
		return grupoStart.get(idSessao);
	}
}
