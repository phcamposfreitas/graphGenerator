package com.generator.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.generator.core.model.FiltroSessao;
import com.generator.core.model.SessaoEntity;
import com.generator.core.repository.SessaoRepository;
import com.generator.core.service.SessaoService;

@Service
public class SessaoServiceImpl implements SessaoService {

	@Autowired
	private SessaoRepository repository;
	
	@Override
	public List<SessaoEntity> buscarPor(FiltroSessao filtro) {
		if(filtro == null)
			return buscarTodos();
		return new ArrayList<SessaoEntity>();
	}

	@Override
	public List<SessaoEntity> buscarTodos() {
		return repository.findAll();
	}

}
