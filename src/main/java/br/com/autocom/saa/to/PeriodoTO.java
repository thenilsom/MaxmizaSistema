package br.com.autocom.saa.to;

import java.time.LocalDateTime;

/**
 * Classe de Representação do intervalo de tempo
 * para o relatório geral.
 * 
 * @author Danillo Santana
 */
public class PeriodoTO {

	private LocalDateTime dataInicio;
	private LocalDateTime dataFim;
	
	public LocalDateTime getDataInicio() {
		return dataInicio;
	}
	public void setDataInicio(LocalDateTime dataInicio) {
		this.dataInicio = dataInicio;
	}
	public LocalDateTime getDataFim() {
		return dataFim;
	}
	public void setDataFim(LocalDateTime dataFim) {
		this.dataFim = dataFim;
	}
	
	
}
