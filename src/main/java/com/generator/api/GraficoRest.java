package com.generator.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.generator.core.model.GraficoMetadado;
import com.generator.core.repository.ChaveValorRepository;
import com.generator.core.service.GraficoMetadadoService;

@RestController
@RequestMapping(value = "/grafico")
public class GraficoRest {

	@Autowired
	private GraficoMetadadoService graficoService;
	
	@Autowired
	private ChaveValorRepository cvrepo;
	
	@RequestMapping(value = "/sessao", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public GraficoMetadado graficoSessao(){
		return graficoService.getGraficoSessao();
	}
	
	@RequestMapping(value = "/teste", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public ChaveValor testeInclusao(@RequestParam String chave, @RequestParam String valor){
		return cvrepo.save(new ChaveValor(chave, valor));
	}
}
