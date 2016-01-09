package com.generator.core.model;

import java.io.Serializable;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
@Document(collection="sessao")
public class SessaoEntity implements Serializable {

	private static final long serialVersionUID = 8540971251512455634L;
	private static final String dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"; 

	@Id
	private String id;
	
	private String idSessao;
	private String usuario;
	
	@JsonFormat(pattern=dateFormat)
	private String dataInicio;
	
	@JsonFormat(pattern=dateFormat)
	private String dataFim;
	
	private Double duracao;
	
	private Double bytesEnviados;
	private Double bytesRecebidos;
	private Double pacotesEnviados;
	private Double pacotesRecebidos;
	
	public LocalDateTime getDataInicioAsLocalDate() {
		// TODO: Mapear entidade com date/Calendar/LocalDate, etc
		LocalDateTime ld = null;
		
		if(getDataInicio() == null)	return null;
		
		try{
			Date parsedDate = DateUtils.parseDate(getDataInicio(), dateFormat);
			ld = parsedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		}catch(ParseException pe){
			pe.printStackTrace();
		}
		
		return ld;
	}
}
