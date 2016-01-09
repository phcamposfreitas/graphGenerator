package com.generator.calculador.service;

import org.springframework.stereotype.Service;

import com.generator.calculador.CalculadorEstatisticoDiario;
import com.generator.core.model.SessaoEntity;

import lombok.Getter;

@Service
public class DuracaoSessaoDiarioCalculadorEstatisticoService extends CalculadorEstatisticoDiario {

	@Getter
	private String nomeInformacaoProcessamento = "Duração Sessão";

	@Override
	protected Double getValorInformacaoProcessamento(SessaoEntity sessao) {
		return sessao.getDuracao();
	}

}
