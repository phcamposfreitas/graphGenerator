package com.generator.processor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.camel.Handler;
import org.apache.commons.lang3.StringUtils;

import com.generator.common.ParserConstantes;
import com.generator.common.UnidadeSessaoUtils;
import com.generator.model.UnidadeSessao;

public class TransformLogToUnidadeSessao {

	@Handler
	public List<UnidadeSessao> transform(File log){
		List<UnidadeSessao> unidades = new ArrayList<UnidadeSessao>();
		StringBuffer bufferLog = new StringBuffer();
				
		try (BufferedReader br = new BufferedReader(new FileReader(log)))
		{
			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null) {
				try{
					if(sCurrentLine.equals(StringUtils.EMPTY)){
						UnidadeSessao usGerada = UnidadeSessaoUtils.createUnidadeSessao(bufferLog.toString());
						unidades.add(usGerada);
						bufferLog.setLength(0);
					}else{
						bufferLog.append(sCurrentLine).append(ParserConstantes.caracterQuebraAtributo);
					}
				}catch (ParseException e) {
					e.printStackTrace();
				} 
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		return unidades;
	}
}
