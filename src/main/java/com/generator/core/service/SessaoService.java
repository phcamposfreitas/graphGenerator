package com.generator.core.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.generator.core.model.FiltroSessao;
import com.generator.core.model.SessaoEntity;

public interface SessaoService {

	List<SessaoEntity> buscarPor(FiltroSessao filtro);

	List<SessaoEntity> buscarTodos();
}
