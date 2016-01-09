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
	private ArrayList<Double> variancia;
	private ArrayList<Double> desvioPadrao;
	
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
		
		this.media = new ArrayList<Double>();
		this.acumulado = new ArrayList<Double>();
		this.maior = new ArrayList<Double>();
		this.menor = new ArrayList<Double>();
		this.total = new ArrayList<Double>();
		this.variancia = new ArrayList<Double>();
		this.desvioPadrao = new ArrayList<Double>();
		
		inicializarListas(totalParticoes, this.media, mediaDuracaoSessao);
		inicializarListas(totalParticoes, this.acumulado, duracaoSessao);
		inicializarListas(totalParticoes, this.maior, maiorSessao);
		inicializarListas(totalParticoes, this.menor, menorSessao);
		inicializarListas(totalParticoes, this.total, totalSessoes);
		inicializarListas(totalParticoes, this.variancia, variacia);
		inicializarListas(totalParticoes, this.desvioPadrao, desvioPadrao);
	}
	
	private void inicializarListas(int totalParticoes, List<Double> valores, Double valorInicial) {
		for (int j = 0; j < totalParticoes; j++) {
			valores.add(j, valorInicial);
		}
	}
}
