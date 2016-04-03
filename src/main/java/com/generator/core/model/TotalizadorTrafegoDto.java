package com.generator.core.model;

import lombok.Data;

@Data
public class TotalizadorTrafegoDto {

	private static final double MIL = 1000;
	private static final double BYTES_IN_MB = 1048576D;
	private static final double SEGUNDOS_IN_MINUTOS = 60D;
	private Double acumuladoIn;
	private Double acumuladoOut;
	private Double totalIn;
	private Double totalOut;
	
	private Double varianciaIn;
	private Double varianciaOut;
	private Double desvioPadraoIn;
	private Double desvioPadraoOut;
	
	private int totalParticoes;
	
	public TotalizadorTrafegoDto(Double in, Double out){
		acumuladoIn = in;
		acumuladoOut = out;
		totalIn = 1D;
		totalOut = 1D;
		varianciaIn = 0D;
		varianciaOut = 0D;
		desvioPadraoIn = 0D;
		desvioPadraoOut = 0D;
	}
	
	public void add(Double in, Double out){
		acumuladoIn += in;
		acumuladoOut += out;
		totalIn++;
		totalOut++;
	}
	
	public Double getMediaMBytesIn(){
		return (acumuladoIn / totalIn) / BYTES_IN_MB;
	}
	
	public Double getMediaMBytesOut(){
		return (acumuladoOut / totalOut) / BYTES_IN_MB ;
	}
	
	public Double getMediaIn(){
		return (acumuladoIn / totalIn) / MIL;
	}
	
	public Double getMediaOut(){
		return (acumuladoOut / totalOut) / MIL ;
	}
	
	public Double getMediaMinutosIn(){
		return (acumuladoIn / totalIn) / SEGUNDOS_IN_MINUTOS;
	}
	
	public Double getMediaMinutosOut(){
		return (acumuladoOut / totalOut) / SEGUNDOS_IN_MINUTOS ;
	}
}
