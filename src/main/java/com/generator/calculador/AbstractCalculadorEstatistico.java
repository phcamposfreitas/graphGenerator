package com.generator.calculador;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import com.generator.core.model.Agrupamento;
import com.generator.core.model.EtapaProcessamento;
import com.generator.core.model.GraficoMetadado;
import com.generator.core.model.SerieGrafico;
import com.generator.core.model.SessaoEntity;
import com.generator.core.model.TotalizadorSessaoDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractCalculadorEstatistico {

	protected abstract String getNomeInformacaoProcessamento();
	
	protected abstract Double getValorInformacaoProcessamento(SessaoEntity sessao);
	
	protected abstract List<String> obterCategoriasAgrupamento();
	
	protected abstract int obterIndiceCategoriaRelacionada(SessaoEntity sessao);
	
	public GraficoMetadado processarCalculoEstatistico(List<SessaoEntity> sessoes) {
		
		String valor = getNomeInformacaoProcessamento();
		
		List<String> categorias = obterCategoriasAgrupamento();
		
		TotalizadorSessaoDto totalizador = processarDados(sessoes, categorias);
		
		List<SerieGrafico>  series = obterSeriesTotalizador(valor, totalizador);
		
		return new GraficoMetadado(categorias, series);
	}
	
	private List<SerieGrafico>  obterSeriesTotalizador(String valor, TotalizadorSessaoDto totalizador) {
		
		List<Double> valoresAcumulados = totalizador.getAcumulado();
		SerieGrafico serieAcumulada = new SerieGrafico(valor, valoresAcumulados);
		
		List<Double> valoresTotal = totalizador.getTotal();
		SerieGrafico serieTotal = new SerieGrafico("Total", valoresTotal);
		
		List<Double> valoresMedia = totalizador.getMedia();
		SerieGrafico serieMedia = new SerieGrafico("Media", valoresMedia);
		
		List<Double> valoresVariancia = totalizador.getVariancia();
		SerieGrafico serieVariancia = new SerieGrafico("Variancia", valoresVariancia);
		
		List<Double> valoresMaior = totalizador.getMaior();
		SerieGrafico serieMaior = new SerieGrafico("Maior", valoresMaior);
		
		List<Double> valoresMenor = totalizador.getMenor();
		SerieGrafico serieMenor = new SerieGrafico("Menor", valoresMenor);
		
		List<Double> valoresDesvioPadrao = totalizador.getDesvioPadrao();
		SerieGrafico serieDesvioPadrao = new SerieGrafico("DesvioPadrao", valoresDesvioPadrao);
		
		return Arrays.asList(serieAcumulada,serieTotal, serieMedia,serieVariancia,serieMaior,serieMenor,serieDesvioPadrao);
	}

	private TotalizadorSessaoDto processarDados(List<SessaoEntity> sessoes, List<String> categorias) {
		TotalizadorSessaoDto totalizador = new TotalizadorSessaoDto(categorias.size());
		
		EtapaProcessamento etapa1 = EtapaProcessamento.AGRUPAMENTO;
		processar(sessoes, totalizador, etapa1);
		
		EtapaProcessamento etapa2 = EtapaProcessamento.CALCULOS_ESTATISTICOS;
		processar(sessoes, totalizador, etapa2);
		
		return totalizador;
	}

	private void processar(List<SessaoEntity> sessoes, TotalizadorSessaoDto totalizador, EtapaProcessamento etapa) {
		Instant inicio = Instant.now();
		log.info("{} | INICIO | Agrupando Dados | {} " , etapa ,  inicio);
		
		switch(etapa){
			case AGRUPAMENTO:
				processarEtapaAgrupamento(totalizador, sessoes);
				break;
			case CALCULOS_ESTATISTICOS:
				processarEtapaEstatistica(totalizador, sessoes);
				break;
			default:
				break;
		
		}
		
		Instant fim = Instant.now();
		log.info("{} | FIM | Agrupando Dados | {} " , etapa ,  fim);
		Duration duracao = Duration.between(inicio, fim);
		long duracaoEmSegundos = duracao.getSeconds();
		log.info("{} | DURACAO | Agrupando Dados | {} " , etapa , duracaoEmSegundos);
	}

	private void processarEtapaEstatistica(TotalizadorSessaoDto totalizador, List<SessaoEntity> sessoes) {
		for(SessaoEntity sessao : sessoes) {
			
			int indexValores = obterIndiceCategoriaRelacionada(sessao);
			
			if(indexValores == -1){
				continue;
			}
			
			Double valorProcessamento = getValorInformacaoProcessamento(sessao);
			
			Double mediaDuracaoPeriodo = totalizador.getMedia().get(indexValores);
			Double diferencaValorMedia = valorProcessamento - mediaDuracaoPeriodo;
			Double variancia = totalizador.getVariancia().get(indexValores);
			Double quadradoDiFerenca = diferencaValorMedia * diferencaValorMedia;

			totalizador.getVariancia().set(indexValores , variancia + quadradoDiFerenca);
		}
		
		for (int i = 0; i < totalizador.getTotalParticoes(); i++) {
			Double variancia = totalizador.getVariancia().get(i);
			Double total = totalizador.getTotal().get(i);
			
			totalizador.getVariancia().set(i , variancia + variancia / (total - 1));
			totalizador.getDesvioPadrao().set(i, Math.sqrt(variancia));
		}
		
	}

	private void processarEtapaAgrupamento(TotalizadorSessaoDto totalizador, List<SessaoEntity> sessoes) {
		for(SessaoEntity sessao : sessoes) {
			
			int indexValores = obterIndiceCategoriaRelacionada(sessao);
			
			if(indexValores == -1){
				continue;
			}
			
			Double valorProcessamneto = getValorInformacaoProcessamento(sessao);
			
			Double duracaoAcumuladaPeriodo = totalizador.getAcumulado().get(indexValores);
			totalizador.getAcumulado().set(indexValores,  duracaoAcumuladaPeriodo + valorProcessamneto);
			
			
			if(valorProcessamneto > totalizador.getMaior().get(indexValores)){
				totalizador.getMaior().set(indexValores , valorProcessamneto);
			}
			
			if(valorProcessamneto > 0 && valorProcessamneto < totalizador.getMenor().get(indexValores)){
				totalizador.getMenor().set(indexValores , valorProcessamneto);
			}
			
			Double total = totalizador.getTotal().get(indexValores);
			totalizador.getTotal().set(indexValores, total + 1);

		}
		
		for (int i = 0; i < totalizador.getTotalParticoes(); i++) {
			Double totalAcumulado = totalizador.getAcumulado().get(i);
			Double total = totalizador.getTotal().get(i);
			
			totalizador.getMedia().set(i, totalAcumulado / total);
		}
	}
}
