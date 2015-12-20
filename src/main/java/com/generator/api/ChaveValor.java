package com.generator.api;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection="chaveValor")
public class ChaveValor implements Serializable {

	private static final long serialVersionUID = 477072976413825749L;

	@Id
	private String id;
	
	private String chave;
	
	private String valor;
	
	public ChaveValor(String chave, String valor){
		this.chave = chave;
		this.valor = valor;
	}
}
