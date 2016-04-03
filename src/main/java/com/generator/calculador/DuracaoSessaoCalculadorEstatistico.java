package com.generator.calculador;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;

import org.apache.commons.lang3.StringUtils;

import com.generator.core.model.GraficoMetadado;
import com.generator.core.model.SerieGrafico;
import com.generator.core.model.SessaoEntity;
import com.generator.core.model.TotalizadorSessaoDto;
import com.generator.core.model.TotalizadorTrafegoDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class DuracaoSessaoCalculadorEstatistico {

	public Map<String, TotalizadorTrafegoDto> mapIntervalos;
	
	protected abstract List<String> obterCategoriasAgrupamento();
	
	protected abstract String getIntervalo(SessaoEntity sessao);
	
	protected abstract boolean isSameIntervalo(String chave, String intervalo);
	
	public GraficoMetadado processarCalculoEstatistico(List<SessaoEntity> sessoes) {
		
		mapIntervalos = new HashMap<String, TotalizadorTrafegoDto>();
				
		List<String> categorias = obterCategoriasAgrupamento();

		TotalizadorSessaoDto totalizador = processar(sessoes, categorias);
		
		List<SerieGrafico>  series = obterSeriesTotalizador(totalizador);
		
		return new GraficoMetadado(categorias, series);
	}

	private List<SerieGrafico>  obterSeriesTotalizador(TotalizadorSessaoDto totalizador) {
		
		List<Double> valoresMedia = totalizador.getMedia();
		SerieGrafico serieMedia = new SerieGrafico("Média", valoresMedia);
		
		List<Double> valoresVariancia = totalizador.getVariancia();
		SerieGrafico serieVariancia = new SerieGrafico("Variancia", valoresVariancia);
		
		List<Double> valoresDesvioPadrao = totalizador.getDesvioPadrao();
		SerieGrafico serieDesvioPadrao = new SerieGrafico("Desvio Padrão", valoresDesvioPadrao);
		
		return Arrays.asList(serieMedia,serieVariancia,serieDesvioPadrao);
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
			
			String intervalo = getIntervalo(sessao);
			if(StringUtils.isEmpty(intervalo)){
				log.info("Erro ao processar {}", sessao);
				continue;
			}
			
			TotalizadorTrafegoDto valorParcialIntervalo = mapIntervalos.get(intervalo);
			double minutosDuracao = sessao.getDuracao();
			
			if(valorParcialIntervalo == null){
				mapIntervalos.put(intervalo, new TotalizadorTrafegoDto(minutosDuracao, 0D));
			}else{
				valorParcialIntervalo.add(minutosDuracao, 0D);
			}
		}
		
		for(String categoria : categorias){
			OptionalDouble media = mapIntervalos.entrySet().stream()
												   .filter(entry -> isSameIntervalo(entry.getKey(), categoria))
												   .mapToDouble(entry -> entry.getValue().getMediaMinutosIn())
												   .average();
			if(media.isPresent())
				totalizador.getMedia().set(categorias.indexOf(categoria), media.getAsDouble());
		}
		
		for(String categoria : categorias){
			Double media = totalizador.getMedia().get(categorias.indexOf(categoria));
			
			Double somatorio = mapIntervalos.entrySet().stream()
							   					       .filter(entry -> isSameIntervalo(entry.getKey(), categoria))
							   					       .mapToDouble(entry -> (entry.getValue().getMediaMinutosIn() - media) * (entry.getValue().getMediaMinutosIn() - media))
							   					       .sum();
			
			long total = mapIntervalos.entrySet().stream()
			 								     .filter(entry -> isSameIntervalo(entry.getKey(), categoria))
												 .count();
			
			Double variancia = somatorio / (total - 1);
			
			totalizador.getVariancia().set(categorias.indexOf(categoria), variancia);
			totalizador.getDesvioPadrao().set(categorias.indexOf(categoria), Math.sqrt(variancia));
		}		
		
	}
}
