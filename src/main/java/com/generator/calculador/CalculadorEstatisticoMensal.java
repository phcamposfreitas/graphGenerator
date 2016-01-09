package com.generator.calculador;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import com.generator.core.model.SessaoEntity;

public abstract class CalculadorEstatisticoMensal extends AbstractCalculadorEstatistico {

	protected abstract String getNomeInformacaoProcessamento();

	protected abstract Double getValorInformacaoProcessamento(SessaoEntity sessao);

	@Override
	protected List<String> obterCategoriasAgrupamento() {
		String categoriasConcatenadas = "Jan;Fev;Mar;Abr;Mai;Jun;Jul;Ago;Set;Out;Nov;Dez";
		return Arrays.asList(categoriasConcatenadas.split(";"));

	}

	@Override
	protected int obterIndiceCategoriaRelacionada(SessaoEntity sessao) {
		LocalDateTime dia = sessao.getDataInicioAsLocalDate();
		
		if(dia == null){
			return -1;
		}
		
		int indexValores = dia.getMonthValue() - 1;
		return indexValores;
	}

}
