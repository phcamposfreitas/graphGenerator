package com.generator.calculador.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.generator.calculador.DuracaoSessaoCalculadorEstatistico;
import com.generator.core.model.SessaoEntity;

@Service
public class DuracaoSessaoSemanalCalculadorEstatisticoService extends DuracaoSessaoCalculadorEstatistico {

	@Override
	protected List<String> obterCategoriasAgrupamento() {
		String categoriasConcatenadas = "Seg;Ter;Qua;Quin;Sex;Sab;Dom";
		return Arrays.asList(categoriasConcatenadas.split(";"));
	}

	@Override
	protected String getIntervalo(SessaoEntity sessao) {
		String dataInicio = sessao.getDataInicio();
		
		if(dataInicio == null) return StringUtils.EMPTY;
		
		String intervalo = dataInicio.substring(0, 10);
		
		LocalDateTime dia = sessao.getDataInicioAsLocalDate();
		
		int indexCategoria = dia.getDayOfWeek().getValue() - 1;
		List<String> categoria = obterCategoriasAgrupamento();
		
		return intervalo.concat("?".concat(categoria.get(indexCategoria)));
	}

	@Override
	protected boolean isSameIntervalo(String chave, String intervalo) {
		return chave.substring(chave.indexOf("?") + 1).equals(intervalo);
	}

}
