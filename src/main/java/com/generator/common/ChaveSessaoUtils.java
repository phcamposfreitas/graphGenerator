package com.generator.common;

import com.generator.core.model.SessaoEntity;

public final class ChaveSessaoUtils {
	
	public static String getChaveDia(SessaoEntity sessao) {
		String chave = sessao.getDataInicio();
		if(chave == null) return "ERRO";
		return chave.substring(0, 10);
	}

}
