package com.generator.calculador;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.generator.core.model.GraficoMetadado;
import com.generator.core.model.SerieGrafico;
import com.generator.core.model.SessaoEntity;
import com.generator.core.model.TotalizadorSessaoDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class DistribuicaoQuantidadePorDuracaoSessaoCalculador {

	public GraficoMetadado processarCalculoEstatistico(List<SessaoEntity> sessoes) {
		
		List<String> categorias = obterCategoriasAgrupamento();

		TotalizadorSessaoDto totalizador = processar(sessoes, categorias);
		
		List<SerieGrafico>  series = obterSeriesTotalizador(totalizador);
		
		return new GraficoMetadado(categorias, series);
	}

	protected abstract List<String> obterCategoriasAgrupamento(); 
	
	private List<SerieGrafico>  obterSeriesTotalizador(TotalizadorSessaoDto totalizador) {
		
		List<Double> distribuicaoQuantidadePorTempo = totalizador.getQuantidadeIntervalo();
		SerieGrafico serie = new SerieGrafico("Duração das Sessões", distribuicaoQuantidadePorTempo);
		
		List<Double> percentual = totalizador.getAcumulado();
		SerieGrafico seriePercentual = new SerieGrafico("Duração das Sessões", percentual);
		
		return Arrays.asList(serie,seriePercentual);
	}


	private TotalizadorSessaoDto processar(List<SessaoEntity> sessoes, List<String> categorias) {
		TotalizadorSessaoDto totalizador = new TotalizadorSessaoDto(categorias.size());
		
		Instant inicio = Instant.now();
		log.info("| INICIO | Agrupando Dados | {} " ,  inicio);
		
		calcular(totalizador, sessoes, categorias);
		
		Instant fim = Instant.now();
		log.info("| FIM | Agrupando Dados | {} "  ,  fim);
		
		Duration duracao = Duration.between(inicio, fim);
		long duracaoEmSegundos = duracao.getSeconds();
		log.info("| DURACAO | Agrupando Dados | {} " , duracaoEmSegundos);
		
		return totalizador;
	}

	private void calcular(TotalizadorSessaoDto totalizador, List<SessaoEntity> sessoes, List<String> categorias) {
		for(SessaoEntity sessao : sessoes) {
					
			Double duracaoEmHoras = sessao.getDuracao() / 3600;
			BigDecimal duracao = new BigDecimal(duracaoEmHoras.toString()).setScale(0, BigDecimal.ROUND_DOWN);
			
			int indice = duracao.intValue(); 
			if(indice >= 24){
				indice = 24;
			}
			
			Double parcial = totalizador.getQuantidadeIntervalo().get(indice);
			totalizador.getQuantidadeIntervalo().set(indice, parcial + 1);
		}
		
		List<Double> percentual = totalizador.getQuantidadeIntervalo().stream().map(valor -> (valor / sessoes.size()) * 100 ).collect(Collectors.toList());
		totalizador.getAcumulado().clear();
		totalizador.getAcumulado().addAll(percentual);
	}
}
