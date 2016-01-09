package com.generator.calculador;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import com.generator.core.model.SessaoEntity;

public abstract class CalculadorEstatisticoSemanal extends AbstractCalculadorEstatistico {

	protected abstract String getNomeInformacaoProcessamento();

	protected abstract Double getValorInformacaoProcessamento(SessaoEntity sessao);

	@Override
	protected List<String> obterCategoriasAgrupamento() {
		String categoriasConcatenadas = "Seg;Ter;Qua;Quin;Sex;Sab;Dom";
		return Arrays.asList(categoriasConcatenadas.split(";"));

	}

	@Override
	protected int obterIndiceCategoriaRelacionada(SessaoEntity sessao) {
		LocalDateTime dia = sessao.getDataInicioAsLocalDate();
		
		if(dia == null){
			return -1;
		}
		
		DayOfWeek dayOfWeek = dia.getDayOfWeek();
		int indexValores = dayOfWeek.getValue() - 1;
		return indexValores;
	}

}
