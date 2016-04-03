package com.generator.core.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.generator.calculador.service.DistribuicaoQuantidadePorDuracaoSessaoService;
import com.generator.calculador.service.DistribuicaoUsuarioSessaoService;
import com.generator.calculador.service.DuracaoSessaoDiarioCalculadorEstatisticoService;
import com.generator.calculador.service.DuracaoSessaoMensalCalculadorEstatisticoService;
import com.generator.calculador.service.DuracaoSessaoSemanalCalculadorEstatisticoService;
import com.generator.calculador.service.OnlineSessaoUsuarioSessaoService;
import com.generator.calculador.service.QuantidadeSessaoDiarioCalculadorEstatisticoService;
import com.generator.calculador.service.QuantidadeSessaoMensalCalculadorEstatisticoService;
import com.generator.calculador.service.QuantidadeSessaoSemanalCalculadorEstatisticoService;
import com.generator.calculador.service.TrafegoBytesDiarioCalculadorEstatisticoService;
import com.generator.calculador.service.TrafegoBytesMensalCalculadorEstatisticoService;
import com.generator.calculador.service.TrafegoBytesSemanalCalculadorEstatisticoService;
import com.generator.calculador.service.TrafegoPacotesDiarioCalculadorEstatisticoService;
import com.generator.calculador.service.TrafegoPacotesMensalCalculadorEstatisticoService;
import com.generator.calculador.service.TrafegoPacotesSemanalCalculadorEstatisticoService;
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
	private QuantidadeSessaoDiarioCalculadorEstatisticoService qtdSessaoDiario;
	
	@Autowired
	private QuantidadeSessaoSemanalCalculadorEstatisticoService qtdSessaoSemanal;
	
	@Autowired
	private QuantidadeSessaoMensalCalculadorEstatisticoService qtdSessaoMensal;
	
	@Autowired
	private TrafegoBytesDiarioCalculadorEstatisticoService trafegoDiario;
	
	@Autowired
	private TrafegoBytesSemanalCalculadorEstatisticoService trafegoSemanal;
	
	@Autowired
	private TrafegoBytesMensalCalculadorEstatisticoService trafegoMensal;

	@Autowired
	private TrafegoPacotesDiarioCalculadorEstatisticoService trafegoPacotesDiario;
	
	@Autowired
	private TrafegoPacotesSemanalCalculadorEstatisticoService trafegoPacotesSemanal;
	
	@Autowired
	private TrafegoPacotesMensalCalculadorEstatisticoService trafegoPacotesMensal;

	@Autowired
	private DistribuicaoQuantidadePorDuracaoSessaoService distribuicaoQtdPorTempo;
	
	@Autowired
	private OnlineSessaoUsuarioSessaoService onlineSessaoUsuario;

	@Autowired
	private DistribuicaoUsuarioSessaoService distribuicaoUsuarioSessao;
	
	//TODO: Incluir cache do Spring (EhCache)
	private Map<Agrupamento, GraficoMetadado> cache;
	private Map<Agrupamento, GraficoMetadado> cacheQuantidadeSessao;
	private Map<Agrupamento, GraficoMetadado> cacheTrafego;
	private Map<Agrupamento, GraficoMetadado> cacheTrafegoPacotes;
	private Map<Agrupamento, GraficoMetadado> cacheDistribuicaoQuantidadePorDuracao;
	private Map<Agrupamento, GraficoMetadado> cacheOnlineSessaoUsuario;
	private Map<Agrupamento, GraficoMetadado> cacheDistribuicaoUsuarioSessao;
	
	@PostConstruct
	public void setup(){
		log.info("Iniciando Cache " , Agrupamento.DIARIO);
		iniciarCache();
	}
	
	@Override
	public GraficoMetadado getGraficoSessao(Agrupamento agrupamento) {
		return cache.get(agrupamento);
	}
	
	@Override
	public GraficoMetadado getGraficoQuantidadeSessao(Agrupamento agrupamento) {
		return cacheQuantidadeSessao.get(agrupamento);
	}
	
	@Override
	public GraficoMetadado getGraficoTrafego(Agrupamento agrupamento) {
		return cacheTrafego.get(agrupamento);
	}
	
	@Override
	public GraficoMetadado getGraficoTrafegoPacotes(Agrupamento agrupamento) {
		return cacheTrafegoPacotes.get(agrupamento);
	}
	
	@Override
	public GraficoMetadado getGraficoDistribuicaoQuantidadePorDuracao(Agrupamento agrupamento) {
		return cacheDistribuicaoQuantidadePorDuracao.get(agrupamento);
	}
	
	@Override
	public void resetCache(){
		log.debug("Resetando Cache ");
		iniciarCache();
		log.debug("Finalizado Reset Cache ");
	}

	
	private void iniciarCache() {
		List<SessaoEntity> sessoes = sessaoService.buscarTodos();

		/*iniciarCacheDuracaoSessao(sessoes);
		iniciarCacheQuantidadeSessao(sessoes);
		iniciarCacheTrafego(sessoes);
		iniciarCacheTrafegoPacotes(sessoes);
		iniciarCacheDistribuicaoQuantidadePorDuracao(sessoes);*/
		iniciarCacheOnlineSessaoUsuario(sessoes);
		iniciarCacheDistribuicaoUsuarioSessao(sessoes);
	}


	private void iniciarCacheDuracaoSessao(List<SessaoEntity> sessoes) {
		log.info("Inicio Cache Duração Sessão");
		cache = new HashMap<Agrupamento, GraficoMetadado>();
		
		log.info("Iniciando Calculo {} " , Agrupamento.DIARIO);
		cache.put(Agrupamento.DIARIO, duracaoDiario.processarCalculoEstatistico(sessoes));
		
		log.info("Iniciando Calculo {} " , Agrupamento.SEMANAL);
		cache.put(Agrupamento.SEMANAL, duracaoSemanal.processarCalculoEstatistico(sessoes));
		
		log.info("Iniciando Calculo {} " , Agrupamento.MENSAL);
		cache.put(Agrupamento.MENSAL, duracaoMensal.processarCalculoEstatistico(sessoes));
		
		log.info("Fim Cache Duração Sessão");
	}
	
	private void iniciarCacheQuantidadeSessao(List<SessaoEntity> sessoes) {
		log.info("Inicio Cache Quantidade Sessão");
		
		cacheQuantidadeSessao = new HashMap<Agrupamento, GraficoMetadado>();
		
		log.info("Iniciando Calculo {} " , Agrupamento.DIARIO);
		cacheQuantidadeSessao.put(Agrupamento.DIARIO, qtdSessaoDiario.processarCalculoEstatistico(sessoes));
		
		log.info("Iniciando Calculo {} " , Agrupamento.SEMANAL);
		cacheQuantidadeSessao.put(Agrupamento.SEMANAL, qtdSessaoSemanal.processarCalculoEstatistico(sessoes));
		
		log.info("Iniciando Calculo {} " , Agrupamento.MENSAL);
		cacheQuantidadeSessao.put(Agrupamento.MENSAL, qtdSessaoMensal.processarCalculoEstatistico(sessoes));
		
		log.info("Fim Cache Quantidade Sessão");
	}
	
	private void iniciarCacheTrafego(List<SessaoEntity> sessoes) {
		log.info("Inicio Cache Trafego Bytes");
		
		cacheTrafego = new HashMap<Agrupamento, GraficoMetadado>();
		
		log.info("Iniciando Calculo {} " , Agrupamento.DIARIO);
		cacheTrafego.put(Agrupamento.DIARIO, trafegoDiario.processarCalculoEstatistico(sessoes));
		
		log.info("Iniciando Calculo {} " , Agrupamento.SEMANAL);
		cacheTrafego.put(Agrupamento.SEMANAL, trafegoSemanal.processarCalculoEstatistico(sessoes));
		
		log.info("Iniciando Calculo {} " , Agrupamento.MENSAL);
		cacheTrafego.put(Agrupamento.MENSAL, trafegoMensal.processarCalculoEstatistico(sessoes));
		
		log.info("Fim Cache Trafego Bytes");
	}

	private void iniciarCacheTrafegoPacotes(List<SessaoEntity> sessoes) {
		log.info("Inicio Cache Trafego Pacotes");
		
		cacheTrafegoPacotes = new HashMap<Agrupamento, GraficoMetadado>();
		
		log.info("Iniciando Calculo {} " , Agrupamento.DIARIO);
		cacheTrafegoPacotes.put(Agrupamento.DIARIO, trafegoPacotesDiario.processarCalculoEstatistico(sessoes));
		
		log.info("Iniciando Calculo {} " , Agrupamento.SEMANAL);
		cacheTrafegoPacotes.put(Agrupamento.SEMANAL, trafegoPacotesSemanal.processarCalculoEstatistico(sessoes));
		
		log.info("Iniciando Calculo {} " , Agrupamento.MENSAL);
		cacheTrafegoPacotes.put(Agrupamento.MENSAL, trafegoPacotesMensal.processarCalculoEstatistico(sessoes));
		
		log.info("Fim Cache Trafego Pacotes");
		
	}
	
	private void iniciarCacheDistribuicaoQuantidadePorDuracao(List<SessaoEntity> sessoes) {
		log.info("Inicio Cache DistribuicaoQuantidadePorDuracao");
		
		cacheDistribuicaoQuantidadePorDuracao = new HashMap<Agrupamento, GraficoMetadado>();
		cacheDistribuicaoQuantidadePorDuracao.put(Agrupamento.QTD_TEMPO, distribuicaoQtdPorTempo.processarCalculoEstatistico(sessoes));
		
	}
	
	private void iniciarCacheOnlineSessaoUsuario(List<SessaoEntity> sessoes) {
		log.info("Inicio Cache OnlineSessaoUsuario");
		
		cacheOnlineSessaoUsuario = new HashMap<Agrupamento, GraficoMetadado>();
		cacheOnlineSessaoUsuario.put(Agrupamento.DEFAULT, onlineSessaoUsuario.processarCalculoEstatistico(sessoes));
		
	}

	private void iniciarCacheDistribuicaoUsuarioSessao(List<SessaoEntity> sessoes) {
		cacheDistribuicaoUsuarioSessao = new HashMap<Agrupamento, GraficoMetadado>();		
		cacheDistribuicaoUsuarioSessao.put(Agrupamento.DEFAULT, distribuicaoUsuarioSessao.processarCalculoEstatistico(sessoes));
	}
	
	@Override
	public GraficoMetadado getGraficoOnlineSessaoUsuario() {
		return cacheOnlineSessaoUsuario.get(Agrupamento.DEFAULT);
	}

	@Override
	public GraficoMetadado getGraficoDistribuicaoSessaoUsuario() {
		return cacheDistribuicaoUsuarioSessao.get(Agrupamento.DEFAULT);
	}
}
