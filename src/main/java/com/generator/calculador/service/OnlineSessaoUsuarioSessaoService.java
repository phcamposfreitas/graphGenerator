package com.generator.calculador.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import com.generator.common.ChaveSessaoUtils;
import com.generator.core.model.GraficoMetadado;
import com.generator.core.model.SerieGrafico;
import com.generator.core.model.SessaoEntity;
import com.generator.core.model.TotalizadorSessaoUsuario;

@Service
public class OnlineSessaoUsuarioSessaoService {

	public Map<String, TotalizadorSessaoUsuario> mapIntervalos;
	
	public GraficoMetadado processarCalculoEstatistico(List<SessaoEntity> sessoes) {
		mapIntervalos = new HashMap<String, TotalizadorSessaoUsuario>();
		
		sessoes.stream().forEach(sessao -> {
			String chave = ChaveSessaoUtils.getChaveDia(sessao);
			TotalizadorSessaoUsuario totalizador = mapIntervalos.get(chave);
			if(totalizador == null){
				totalizador = new TotalizadorSessaoUsuario(sessao);
				mapIntervalos.put(chave, totalizador);
			}else{
				totalizador.incrementaSessoes();
				totalizador.getUsuarios().add(sessao.getUsuario());
			}
		});
		
		List<TotalizadorSessaoUsuario> totalizadores = mapIntervalos.values().stream()
															.sorted((s1, s2) -> {
																return ObjectUtils.compare(s1.getData(), s2.getData(), true);
															}).collect(Collectors.toList());
		
		List<Double> qtdSessoes = totalizadores.stream().map(t -> new Double(t.getTotalSessoes())).collect(Collectors.toList());
			
		
		SerieGrafico serieSessoes = new SerieGrafico("Sessões", qtdSessoes);

		List<Double> qtdUsuarios = totalizadores.stream().map(t -> new Double(t.getUsuarios().size())).collect(Collectors.toList());
		SerieGrafico serieUsuarios = new SerieGrafico("Usuários Únicos", qtdUsuarios);
		
		List<String> categorias = totalizadores.stream().map(t -> t.getChave()).collect(Collectors.toList());
		return new GraficoMetadado(categorias, Arrays.asList(serieSessoes, serieUsuarios));
	}


}
