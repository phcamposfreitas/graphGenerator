package com.generator.calculador.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.generator.calculador.TrafegoPacotesCalculadorEstatistico;
import com.generator.core.model.SessaoEntity;

@Service
public class TrafegoPacotesMensalCalculadorEstatisticoService extends TrafegoPacotesCalculadorEstatistico {

	@Override
	protected List<String> obterCategoriasAgrupamento() {
		String categoriasConcatenadas = "Jan;Fev;Mar;Abr;Mai;Jun;Jul;Ago;Set;Out;Nov;Dez";
		return Arrays.asList(categoriasConcatenadas.split(";"));
	}

	@Override
	protected String getIntervalo(SessaoEntity sessao) {
		String dataInicio = sessao.getDataInicio();
		
		if(dataInicio == null) return StringUtils.EMPTY;
		
		String intervalo = dataInicio.substring(0, 7);
		LocalDateTime dia = sessao.getDataInicioAsLocalDate();
		
		int indexCategoria = dia.getMonthValue() - 1;
		List<String> categoria = obterCategoriasAgrupamento();
		
		return intervalo.concat("?".concat(categoria.get(indexCategoria)));
	}
	
	@Override
	protected boolean isSameIntervalo(String chave, String intervalo) {
		boolean equals = chave.substring(chave.indexOf("?") + 1).equals(intervalo);
		return equals;
	}
}
