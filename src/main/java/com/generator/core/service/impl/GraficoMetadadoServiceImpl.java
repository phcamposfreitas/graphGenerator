package com.generator.core.service.impl;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.generator.core.model.GraficoMetadado;
import com.generator.core.model.SerieGrafico;
import com.generator.core.model.SessaoEntity;
import com.generator.core.service.GraficoMetadadoService;
import com.generator.core.service.SessaoService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class GraficoMetadadoServiceImpl implements GraficoMetadadoService {

	@Autowired
	private SessaoService sessaoService;
	
	@Override
	public GraficoMetadado getGraficoSessao() {

		List<SessaoEntity> sessoes = sessaoService.buscarTodos();
		
		String categoriasSemana = "Seg;Ter;Qua;Quin;Sex;Sab;Dom";
		List<String> categorias = Arrays.asList(categoriasSemana.split(";"));
		List<Long> valores = countDuracaoSessaoSemana(sessoes, categorias);
		
		SerieGrafico serie = new SerieGrafico("duracaoSessao", valores);
		
		return new GraficoMetadado(categorias, serie);
	}

	private List<Long> countDuracaoSessaoSemana(List<SessaoEntity> sessoes, List<String> categorias) {
		List<Long> valores = new ArrayList<Long>();
		getValoresInicializados(categorias, valores);
		
		
		sessoes.stream().forEach(sessao -> {
			LocalDate dia = sessao.getDataInicioAsLocalDate();
			
			if(dia != null){
			
				DayOfWeek dayOfWeek = dia.getDayOfWeek();
				int indexValores = dayOfWeek.getValue() - 1;
				
				Long duracao = valores.get(indexValores);
				duracao += sessao.getDuracao();
				
//				log.info("Dia {} , dOw {} , idx {} , duracao {} ", dia, dayOfWeek, indexValores, duracao);
				valores.set(indexValores , duracao);
				
			}else{
				log.info("Erro: {}", sessao);
			}
		});
		
		return valores;
	}

	private void getValoresInicializados(List<String> categorias, List<Long> valores) {
		for (int j = 0; j < categorias.size(); j++) {
			valores.add(j, 0L);
		}
	}

}
