package com.generator.calculador;

import java.util.Arrays;
import java.util.List;

import com.generator.core.model.SessaoEntity;

public abstract class CalculadorEstatisticoTotal extends AbstractCalculadorEstatistico {

	protected abstract String getNomeInformacaoProcessamento();

	protected abstract Double getValorInformacaoProcessamento(SessaoEntity sessao);

	@Override
	protected List<String> obterCategoriasAgrupamento() {
		String categoriasConcatenadas = "Total;";
		return Arrays.asList(categoriasConcatenadas.split(";"));

	}

	@Override
	protected int obterIndiceCategoriaRelacionada(SessaoEntity sessao) {
		return 0;
	}

}
