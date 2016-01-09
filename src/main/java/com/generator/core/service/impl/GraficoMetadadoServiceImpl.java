package com.generator.core.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.generator.calculador.service.DuracaoSessaoDiarioCalculadorEstatisticoService;
import com.generator.calculador.service.DuracaoSessaoMensalCalculadorEstatisticoService;
import com.generator.calculador.service.DuracaoSessaoSemanalCalculadorEstatisticoService;
import com.generator.calculador.service.DuracaoSessaoTotalCalculadorEstatisticoService;
import com.generator.core.model.Agrupamento;
import com.generator.core.model.GraficoMetadado;
import com.generator.core.model.SessaoEntity;
import com.generator.core.service.GraficoMetadadoService;
import com.generator.core.service.SessaoService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class GraficoMetadadoServiceImpl implements GraficoMetadadoService {

	@Autowired
	private SessaoService sessaoService;
	
	@Autowired
	private DuracaoSessaoDiarioCalculadorEstatisticoService duracaoDiario;
	
	@Autowired
	private DuracaoSessaoSemanalCalculadorEstatisticoService duracaoSemanal;
	
	@Autowired
	private DuracaoSessaoMensalCalculadorEstatisticoService duracaoMensal;
	
	@Autowired
	private DuracaoSessaoTotalCalculadorEstatisticoService duracaoTotal;
	
	private Map<Agrupamento, GraficoMetadado> cache;
	
	@PostConstruct
	public void setup(){
		log.info("Iniciando Cache " , Agrupamento.DIARIO);
		iniciarCache();
	}
	
	@Override
	public GraficoMetadado getGraficoSessao(Agrupamento agrupamento) {

		GraficoMetadado metadado = cache.get(agrupamento);
		return metadado;
	}

	
	private void iniciarCache() {
		cache = new HashMap<Agrupamento, GraficoMetadado>();
		List<SessaoEntity> sessoes = sessaoService.buscarTodos();
		
		log.info("Iniciando Calculo {} " , Agrupamento.DIARIO);
		cache.put(Agrupamento.DIARIO, duracaoDiario.processarCalculoEstatistico(sessoes));
		
		log.info("Iniciando Calculo {} " , Agrupamento.SEMANAL);
		cache.put(Agrupamento.SEMANAL, duracaoSemanal.processarCalculoEstatistico(sessoes));
		
		log.info("Iniciando Calculo {} " , Agrupamento.MENSAL);
		cache.put(Agrupamento.MENSAL, duracaoMensal.processarCalculoEstatistico(sessoes));
		
		log.info("Iniciando Calculo {} " , Agrupamento.TOTAL);
		cache.put(Agrupamento.TOTAL, duracaoTotal.processarCalculoEstatistico(sessoes));
	}

}
