package com.generator.calculador;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.generator.core.model.GraficoMetadado;
import com.generator.core.model.SerieGrafico;
import com.generator.core.model.SessaoEntity;
import com.generator.core.model.TotalizadorSessaoDto;
import com.generator.core.model.TotalizadorTrafegoDto;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public abstract class TrafegoCalculadorEstatistico {

	@Getter
	private String nomeInformacaoProcessamento = "Tráfego em Bytes";
	
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
		SerieGrafico serieMediaIn = new SerieGrafico("Upload", valoresMedia);
		
		List<Double> valoresMediaOut = totalizador.getMediaOut();
		SerieGrafico serieMediaOut = new SerieGrafico("Download", valoresMediaOut);
		
		List<Double> valoresDesvioPadraoIn = totalizador.getDesvioPadrao();
		SerieGrafico serieDesvioPadraoIn = new SerieGrafico("Desvio Padrão Upload", valoresDesvioPadraoIn);
		
		List<Double> valoresDesvioPadraoOut = totalizador.getDesvioPadraoOut();
		SerieGrafico serieDesvioPadraoOut = new SerieGrafico("Desvio Padrão Download", valoresDesvioPadraoOut);
		
		return Arrays.asList(serieMediaIn,serieMediaOut,serieDesvioPadraoIn, serieDesvioPadraoOut);
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
			
			TotalizadorTrafegoDto trafego = mapIntervalos.get(intervalo);
			
			if(trafego == null){
				TotalizadorTrafegoDto trafegoNovo = new TotalizadorTrafegoDto(sessao.getBytesRecebidos(), sessao.getBytesEnviados());
				mapIntervalos.put(intervalo, trafegoNovo);
			}else{
				trafego.add(sessao.getBytesRecebidos(), sessao.getBytesEnviados());
			}
		}
		
		for(String categoria : categorias){
			OptionalDouble mediaIn = mapIntervalos.entrySet().stream()
												   .filter(entry -> isSameIntervalo(entry.getKey(), categoria))
												   .mapToDouble(entry -> entry.getValue().getMediaMBytesIn())
												   .average();
			
			OptionalDouble mediaOut = mapIntervalos.entrySet().stream()
															   .filter(entry -> isSameIntervalo(entry.getKey(), categoria))
															   .mapToDouble(entry -> entry.getValue().getMediaMBytesOut())
															   .average();
			
			if(mediaIn.isPresent())
				totalizador.getMedia().set(categorias.indexOf(categoria), mediaIn.getAsDouble());
			
			if(mediaOut.isPresent())
				totalizador.getMediaOut().set(categorias.indexOf(categoria), mediaOut.getAsDouble());
		}
		
		for(String categoria : categorias){
			Double media = totalizador.getMedia().get(categorias.indexOf(categoria));
			Double mediaOut = totalizador.getMediaOut().get(categorias.indexOf(categoria));
			
			Double somatorioIn = mapIntervalos.entrySet()
							   .stream()
	   					       .filter(entry -> isSameIntervalo(entry.getKey(), categoria))
	   					       .mapToDouble(entry -> (entry.getValue().getMediaMBytesIn() - media) * (entry.getValue().getMediaMBytesIn() - media))
	   					       .sum();
			
			Double somatorioOut = mapIntervalos.entrySet()
					   .stream()
				       .filter(entry -> isSameIntervalo(entry.getKey(), categoria))
				       .mapToDouble(entry -> (entry.getValue().getMediaMBytesOut() - mediaOut) * (entry.getValue().getMediaMBytesOut() - mediaOut))
				       .sum();
			
			long total = mapIntervalos.entrySet().stream()
			 								     .filter(entry -> isSameIntervalo(entry.getKey(), categoria))
												 .count();
			
			Double varianciaIn = somatorioIn / (total - 1);
			Double varianciaOut = somatorioOut / (total - 1);
			
			totalizador.getVariancia().set(categorias.indexOf(categoria), varianciaIn);
			totalizador.getVarianciaOut().set(categorias.indexOf(categoria), varianciaOut);
			
			totalizador.getDesvioPadrao().set(categorias.indexOf(categoria), Math.sqrt(varianciaIn));
			totalizador.getDesvioPadraoOut().set(categorias.indexOf(categoria), Math.sqrt(varianciaOut));
		}		
		
	}

}
