package com.generator.core.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.generator.common.ChaveSessaoUtils;

import lombok.Data;

@Data
public class TotalizadorSessaoUsuario {

	private Integer totalSessoes;
	private Set<String> usuarios;
	private String chave;
	private LocalDateTime data;
	
	public TotalizadorSessaoUsuario(SessaoEntity sessao) {
		totalSessoes = 1;
		usuarios = new HashSet<String>();  
		usuarios.add(sessao.getUsuario());
		data = sessao.getDataInicioAsLocalDate();
		chave = ChaveSessaoUtils.getChaveDia(sessao);
	}

	public void incrementaSessoes() {
		totalSessoes++;
	}
}
