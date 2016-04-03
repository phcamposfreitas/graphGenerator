package com.generator.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.generator.core.model.Agrupamento;
import com.generator.core.model.GraficoMetadado;
import com.generator.core.service.GraficoMetadadoService;

@RestController
@RequestMapping(value = "/grafico")
public class GraficoRest {

	@Autowired
	private GraficoMetadadoService graficoService;
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/sessao/{agrupamento}", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public GraficoMetadado graficoSessao(@PathVariable String agrupamento){
		Agrupamento agrupamentoGrafico = Agrupamento.valueOf(agrupamento);
		return graficoService.getGraficoSessao(agrupamentoGrafico);
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/qtdSessao/{agrupamento}", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public GraficoMetadado graficoQuantidadeSessao(@PathVariable String agrupamento){
		Agrupamento agrupamentoGrafico = Agrupamento.valueOf(agrupamento);
		return graficoService.getGraficoQuantidadeSessao(agrupamentoGrafico);
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/trafego/bytes/{agrupamento}", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public GraficoMetadado graficoQuantidadeTrafego(@PathVariable String agrupamento){
		Agrupamento agrupamentoGrafico = Agrupamento.valueOf(agrupamento);
		return graficoService.getGraficoTrafego(agrupamentoGrafico);
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/trafego/pacotes/{agrupamento}", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public GraficoMetadado graficoQuantidadePacotes(@PathVariable String agrupamento){
		Agrupamento agrupamentoGrafico = Agrupamento.valueOf(agrupamento);
		return graficoService.getGraficoTrafegoPacotes(agrupamentoGrafico);
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/distribuicao/qtd-duracao/{agrupamento}", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public GraficoMetadado graficoDistribuicaoQuantidadePortempo(@PathVariable String agrupamento){
		Agrupamento agrupamentoGrafico = Agrupamento.valueOf(agrupamento);
		return graficoService.getGraficoDistribuicaoQuantidadePorDuracao(agrupamentoGrafico);
	}

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/online/sessao-usuario", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public GraficoMetadado graficoOnlineSessaoUsuario(){
		return graficoService.getGraficoOnlineSessaoUsuario();
	}

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/distribuicao/sessao-usuario", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public GraficoMetadado graficoDistribuicaoSessaoUsuario(){
		return graficoService.getGraficoDistribuicaoSessaoUsuario();
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/cache/reset", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public void reset(){
		graficoService.resetCache();
	}

}
