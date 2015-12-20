package com.generator.core.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class GraficoMetadado implements Serializable {

	private static final long serialVersionUID = 7744628563759967802L;
	
	public List<String> categorias;
	public List<SerieGrafico> series;
	
	public GraficoMetadado(List<String> categorias, SerieGrafico serie) {
		this(categorias, Arrays.asList(serie));
	}

	public GraficoMetadado(List<String> categorias, List<SerieGrafico> series) {
		this.categorias = categorias;
		this.series = series;
	}

}
