package com.generator.core.service;

import com.generator.core.model.Agrupamento;
import com.generator.core.model.GraficoMetadado;

public interface GraficoMetadadoService {

	GraficoMetadado getGraficoSessao(Agrupamento agrupamento);
	
	GraficoMetadado getGraficoQuantidadeSessao(Agrupamento agrupamento);
	
	GraficoMetadado getGraficoTrafego(Agrupamento agrupamento);
	
	GraficoMetadado getGraficoTrafegoPacotes(Agrupamento agrupamento);
	
	GraficoMetadado getGraficoDistribuicaoQuantidadePorDuracao(Agrupamento agrupamento);

	void resetCache();

	GraficoMetadado getGraficoOnlineSessaoUsuario();
	
	GraficoMetadado getGraficoDistribuicaoSessaoUsuario();
}
