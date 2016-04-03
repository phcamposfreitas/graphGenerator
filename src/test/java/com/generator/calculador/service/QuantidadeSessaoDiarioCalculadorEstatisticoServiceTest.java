package com.generator.calculador.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.generator.core.model.GraficoMetadado;
import com.generator.core.model.SerieGrafico;
import com.generator.core.model.SessaoEntity;

@RunWith(MockitoJUnitRunner.class)
public class QuantidadeSessaoDiarioCalculadorEstatisticoServiceTest {

	@InjectMocks
	private QuantidadeSessaoDiarioCalculadorEstatisticoService service;
	
	@Test
	public void sucessoAoCalcularMediaVarianciaDesvioPadrao() throws Exception {
		List<SessaoEntity> sessoes = new ArrayList<>();
		
		sessoes.add(new SessaoEntity("2016-03-26T00:15:12.000+0000"));
		sessoes.add(new SessaoEntity("2016-03-26T00:30:12.000+0000"));
		sessoes.add(new SessaoEntity("2016-03-26T00:60:12.000+0000"));
		sessoes.add(new SessaoEntity("2016-03-27T00:15:12.000+0000"));
		sessoes.add(new SessaoEntity("2016-03-27T00:30:12.000+0000"));
		sessoes.add(new SessaoEntity("2016-03-28T00:30:12.000+0000"));
		
		sessoes.add(new SessaoEntity("2016-03-26T01:15:12.000+0000"));
		sessoes.add(new SessaoEntity("2016-03-26T01:30:12.000+0000"));
		sessoes.add(new SessaoEntity("2016-03-26T01:60:12.000+0000"));
		sessoes.add(new SessaoEntity("2016-03-26T01:30:12.000+0000"));
		sessoes.add(new SessaoEntity("2016-03-26T01:60:12.000+0000"));
		sessoes.add(new SessaoEntity("2016-03-26T01:15:12.000+0000"));
		sessoes.add(new SessaoEntity("2016-03-27T01:30:12.000+0000"));
		sessoes.add(new SessaoEntity("2016-03-27T01:30:12.000+0000"));
		
		GraficoMetadado grafico = service.processarCalculoEstatistico(sessoes);
		
		SerieGrafico serie1 = new SerieGrafico("Média", Arrays.asList(2.0 ,4.0 ,0.0 ,0.0 ,0.0 ,0.0 ,0.0 ,0.0 ,0.0 ,0.0 ,0.0 ,0.0 ,0.0 ,0.0 ,0.0 ,0.0 ,0.0 ,0.0 ,0.0 ,0.0 ,0.0 ,0.0 ,0.0 ,0.0 ));
		SerieGrafico serie2 = new SerieGrafico("Variancia", Arrays.asList(1.0 ,8.0 ,0.0 ,0.0 ,0.0 ,0.0 ,0.0 ,0.0 ,0.0 ,0.0 ,0.0 ,0.0 ,0.0 ,0.0 ,0.0 ,0.0 ,0.0 ,0.0 ,0.0 ,0.0 ,0.0 ,0.0 ,0.0 ,0.0));
		SerieGrafico serie3 = new SerieGrafico("Desvio Padrão", Arrays.asList(Math.sqrt(1.0) ,4.0 ,0.0 ,0.0 ,0.0 ,0.0 ,0.0 ,0.0 ,0.0 ,0.0 ,0.0 ,0.0 ,0.0 ,0.0 ,0.0 ,0.0 ,0.0 ,0.0 ,0.0 ,0.0 ,0.0 ,0.0 ,0.0 ,0.0));
		
		Assert.assertThat(grafico.series.get(0), Matchers.equalTo(serie1));
		Assert.assertThat(grafico.series.get(1), Matchers.equalTo(serie2));
		Assert.assertThat(grafico.series.get(2), Matchers.equalTo(serie3));
	}
}
