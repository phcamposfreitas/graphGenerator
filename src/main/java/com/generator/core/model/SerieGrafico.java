package com.generator.core.model;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class SerieGrafico implements Serializable {

	private static final long serialVersionUID = 5399910651884018303L;

	public String nome;
	public List<Double> valores;
	
	public SerieGrafico(String nome, List<Double> valores) {
		this.nome = nome;
		this.valores = valores;
	}

}
