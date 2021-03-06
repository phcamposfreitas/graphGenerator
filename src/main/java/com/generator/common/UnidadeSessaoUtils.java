package com.generator.common;

import java.text.ParseException;

import org.apache.commons.lang3.StringUtils;

import com.generator.coletor.model.UnidadeSessao;
import com.generator.coletor.model.UnidadeSessaoFim;
import com.generator.coletor.model.UnidadeSessaoInicio;

public final class UnidadeSessaoUtils {

	public static UnidadeSessao createUnidadeSessao(String logUnidadeSessao) throws ParseException {
		UnidadeSessao unidade;
		String[] linhas = logUnidadeSessao.split(ParserConstantes.caracterQuebraAtributo);
		
		String dataRegistro = obterValor(linhas, IndiceLogRadius.DATA_REGISTRO);
		String tipoUnidade = obterValor(linhas, IndiceLogRadius.TIPO_REGISTRO);
		
		if(ParserConstantes.INICIO_SESSAO.equalsIgnoreCase(StringUtils.trim(tipoUnidade))){
			String codigoSessao = obterValor(linhas, IndiceLogRadius.COD_SESSAO_START);
			String usuario = obterValor(linhas, IndiceLogRadius.NOME_USUARIO_START);
			
			unidade = new UnidadeSessaoInicio(dataRegistro, parseLogValue(codigoSessao), parseLogValue(usuario));
		}else{
			String codigoSessao = obterValor(linhas, IndiceLogRadius.COD_SESSAO_STOP);
			String usuario = obterValor(linhas, IndiceLogRadius.NOME_USUARIO_STOP);
			String duracao = obterValor(linhas, IndiceLogRadius.DURACAO);
			String bytesIn = obterValor(linhas, IndiceLogRadius.BYTES_IN);
			String bytesOut = obterValor(linhas, IndiceLogRadius.BYTES_OUT);
			String pktIn = obterValor(linhas, IndiceLogRadius.PKT_IN);
			String pktOut = obterValor(linhas, IndiceLogRadius.PKT_OUT);
			
			unidade = new UnidadeSessaoFim(dataRegistro, parseLogValue(codigoSessao), parseLogValue(usuario), parseLogValue(duracao)
					, parseLogValue(bytesOut),  parseLogValue(bytesIn),  parseLogValue(pktOut) , parseLogValue(pktIn));
		}
		
		
		return unidade;
	}

	private static String parseLogValue(String codigoSessao) {
		return StringUtils.trim(codigoSessao.replaceAll("\"", StringUtils.EMPTY));
	}

	private static String obterValor(String[] linhas, Integer tipoRegistro) {
		if(IndiceLogRadius.DATA_REGISTRO.equals(tipoRegistro)){
			return linhas[IndiceLogRadius.DATA_REGISTRO];
		}
		
		String[] tupla = linhas[tipoRegistro].split(ParserConstantes.caracterSeparadorAtributo);
		return tupla[1];
	}

}
