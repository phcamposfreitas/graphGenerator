package com.generator.calculador.service;

import org.springframework.stereotype.Service;

import com.generator.calculador.CalculadorEstatisticoTotal;
import com.generator.core.model.SessaoEntity;

import lombok.Getter;

@Service
public class DuracaoSessaoTotalCalculadorEstatisticoService extends CalculadorEstatisticoTotal {

	@Getter
	private String nomeInformacaoProcessamento = "Duração Sessão";

	@Override
	protected Double getValorInformacaoProcessamento(SessaoEntity sessao) {
		return sessao.getDuracao();
	}

}
