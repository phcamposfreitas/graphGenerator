package com.generator.calculador.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.generator.calculador.DistribuicaoQuantidadePorDuracaoSessaoCalculador;

@Service
public class DistribuicaoQuantidadePorDuracaoSessaoService extends DistribuicaoQuantidadePorDuracaoSessaoCalculador {

	protected List<String> obterCategoriasAgrupamento() {
		String categoriasConcatenadas = "0-1h;1-2h;2-3h;3-4h;4-5h;5-6h;6-7h;7-8h;8-9h;9-10h;10-11h;11-12h;12-13h;13-14h;14-15h;15-16h;16-17h;17-18h;18-19h;19-20h;20-21h;21-22h;22-23h;23-24h;24h+";
		return Arrays.asList(categoriasConcatenadas.split(";"));
	}

}
