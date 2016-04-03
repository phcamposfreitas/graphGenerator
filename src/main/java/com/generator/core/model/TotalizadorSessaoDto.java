package com.generator.core.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class TotalizadorSessaoDto {

	private ArrayList<Double> acumulado;
	private ArrayList<Double> total;
	private ArrayList<Double> maior;
	private ArrayList<Double> menor;
	private ArrayList<Double> media;
	private ArrayList<Double> mediaOut;
	private ArrayList<Double> variancia;
	private ArrayList<Double> varianciaOut;
	private ArrayList<Double> desvioPadrao;
	private ArrayList<Double> desvioPadraoOut;
	
	private ArrayList<Double> quantidadeIntervalo;
	
	private int totalParticoes;
	
	public TotalizadorSessaoDto(int totalParticoes){
		this.totalParticoes = totalParticoes;
		
		Double maiorSessao = Double.MIN_VALUE;
		Double menorSessao = Double.MAX_VALUE;
		Double duracaoSessao = 0D;
		Double mediaDuracaoSessao = 0D;
		Double totalSessoes = 0D;
		Double variacia = 0D;
		Double desvioPadrao = 0D;
		Double quantidadeIntervalo = 0D;
		
		this.media = new ArrayList<Double>();
		this.mediaOut = new ArrayList<Double>();
		this.acumulado = new ArrayList<Double>();
		this.maior = new ArrayList<Double>();
		this.menor = new ArrayList<Double>();
		this.total = new ArrayList<Double>();
		this.variancia = new ArrayList<Double>();
		this.varianciaOut = new ArrayList<Double>();
		this.desvioPadrao = new ArrayList<Double>();
		this.desvioPadraoOut = new ArrayList<Double>();
		this.quantidadeIntervalo = new ArrayList<Double>();
		
		inicializarListas(totalParticoes, this.media, mediaDuracaoSessao);
		inicializarListas(totalParticoes, this.mediaOut, mediaDuracaoSessao);
		inicializarListas(totalParticoes, this.acumulado, duracaoSessao);
		inicializarListas(totalParticoes, this.maior, maiorSessao);
		inicializarListas(totalParticoes, this.menor, menorSessao);
		inicializarListas(totalParticoes, this.total, totalSessoes);
		inicializarListas(totalParticoes, this.variancia, variacia);
		inicializarListas(totalParticoes, this.varianciaOut, variacia);
		inicializarListas(totalParticoes, this.desvioPadrao, desvioPadrao);
		inicializarListas(totalParticoes, this.desvioPadraoOut, desvioPadrao);
		inicializarListas(totalParticoes, this.quantidadeIntervalo, quantidadeIntervalo);
	}
	
	private void inicializarListas(int totalParticoes, List<Double> valores, Double valorInicial) {
		for (int j = 0; j < totalParticoes; j++) {
			valores.add(j, valorInicial);
		}
	}
}
