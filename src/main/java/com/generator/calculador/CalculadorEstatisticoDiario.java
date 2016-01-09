package com.generator.calculador;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import com.generator.core.model.SessaoEntity;

public abstract class CalculadorEstatisticoDiario extends AbstractCalculadorEstatistico {

	protected abstract String getNomeInformacaoProcessamento();

	protected abstract Double getValorInformacaoProcessamento(SessaoEntity sessao);

	@Override
	protected List<String> obterCategoriasAgrupamento() {
		String categoriasConcatenadas = "0;1;2;3;4;5;6;7;8;9;10;11;12;13;14;15;16;17;18;19;20;21;22;23";
		return Arrays.asList(categoriasConcatenadas.split(";"));

	}

	@Override
	protected int obterIndiceCategoriaRelacionada(SessaoEntity sessao) {
		LocalDateTime dia = sessao.getDataInicioAsLocalDate();
		
		if(dia == null){
			return -1;
		}
		
		int indexValores = dia.getHour();
		return indexValores;
	}

}
