package com.generator.calculador.service;

import org.springframework.stereotype.Service;

import com.generator.calculador.CalculadorEstatisticoSemanal;
import com.generator.core.model.SessaoEntity;

import lombok.Getter;

@Service
public class DuracaoSessaoSemanalCalculadorEstatisticoService extends CalculadorEstatisticoSemanal {

	@Getter
	private String nomeInformacaoProcessamento = "Duração Sessão";

	@Override
	protected Double getValorInformacaoProcessamento(SessaoEntity sessao) {
		return sessao.getDuracao();
	}

}
