package com.generator.calculador.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import com.generator.common.ChaveSessaoUtils;
import com.generator.core.model.GraficoMetadado;
import com.generator.core.model.SerieGrafico;
import com.generator.core.model.SessaoEntity;
import com.generator.core.model.TotalizadorSessaoUsuario;

@Service
public class DistribuicaoUsuarioSessaoService {

	public Map<String, Double> mapIntervalos;
	
	public GraficoMetadado processarCalculoEstatistico(List<SessaoEntity> sessoes) {
		mapIntervalos = new HashMap<String, Double>();
		
		sessoes.stream().forEach(sessao -> {
			String chave = sessao.getUsuario();
			Double totalizador = mapIntervalos.get(chave);
			if(totalizador == null){
				mapIntervalos.put(chave, 1D);
			}else{
				mapIntervalos.put(chave, totalizador + 1D);
			}
		});
		
		List<Double> qtdSessoes = mapIntervalos.values().stream()
				.sorted(Collections.reverseOrder())
				.collect(Collectors.toList());
		SerieGrafico serieUsuarios = new SerieGrafico("Usuários", qtdSessoes);

		List<String> categorias = mapIntervalos.keySet().stream().collect(Collectors.toList());
		return new GraficoMetadado(categorias, Arrays.asList(serieUsuarios));
	}
	
	public GraficoMetadado processarCalculoEstatistico2(List<SessaoEntity> sessoes) {
		mapIntervalos = new HashMap<String, Double>();
		
		sessoes.stream().forEach(sessao -> {
			String chave = sessao.getUsuario();
			Double totalizador = mapIntervalos.get(chave);
			if(totalizador == null){
				mapIntervalos.put(chave, 1D);
			}else{
				mapIntervalos.put(chave, totalizador + 1D);
			}
		});
		
		Map<Double, Double> mapSessaoUsuario = new HashMap<Double, Double>();
		mapIntervalos.values().stream()
							  .sorted()
							  .forEach(quantidadeSessao -> {
			Double totalizador = mapSessaoUsuario.get(quantidadeSessao);
			if(totalizador == null){
				mapSessaoUsuario.put(quantidadeSessao, 1D);
			}else{
				mapSessaoUsuario.put(quantidadeSessao, totalizador + 1D);
			}
		});
		
		List<Double> qtdUsuarios = mapSessaoUsuario.values().stream().collect(Collectors.toList());
		SerieGrafico serieUsuarios = new SerieGrafico("Usuários", qtdUsuarios);

		List<String> categorias = mapSessaoUsuario.keySet().stream().map(k -> k.toString()).collect(Collectors.toList());
		return new GraficoMetadado(categorias, Arrays.asList(serieUsuarios));
	}


}
