package com.generator.calculador.service;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.generator.calculador.QuantidadeSessaoCalculadorEstatistico;
import com.generator.core.model.SessaoEntity;

@Service
public class QuantidadeSessaoDiarioCalculadorEstatisticoService extends QuantidadeSessaoCalculadorEstatistico{

	@Override
	protected List<String> obterCategoriasAgrupamento() {
		String categoriasConcatenadas = "00;01;02;03;04;05;06;07;08;09;10;11;12;13;14;15;16;17;18;19;20;21;22;23";
		return Arrays.asList(categoriasConcatenadas.split(";"));
	}

	@Override
	protected String getIntervalo(SessaoEntity sessao) {
		String dataInicio = sessao.getDataInicio();
		
		if(dataInicio == null) return StringUtils.EMPTY;
		
		String intervalo = dataInicio.substring(0, sessao.getDataInicio().indexOf(":"));
		return intervalo;
	}

	@Override
	protected boolean isSameIntervalo(String chave, String intervalo) {
		return chave.endsWith(intervalo);
	}

}
