/**
 * 
 */
package br.com.autocom.saa.dom.filtro;

import java.time.LocalDateTime;

import br.com.autocom.saa.dom.Usuario;

/**
 * Classe de filtro responsavel por manter os filtros para busca dos Pontos.
 * 
 * @author Samuel Oliveira
 *
 */
public class PontoFiltro {
	
	
	private LocalDateTime dataInicial;
	
	private LocalDateTime dataFinal;
	
	private Usuario usuario;
	

	/**
	 * @return the dataInicial
	 */
	public LocalDateTime getDataInicial() {
		return dataInicial;
	}

	/**
	 * @param dataInicial the dataInicial to set
	 */
	public void setDataInicial(LocalDateTime dataInicial) {
		this.dataInicial = dataInicial;
	}

	/**
	 * @return the dataFinal
	 */
	public LocalDateTime getDataFinal() {
		return dataFinal;
	}

	/**
	 * @param dataFinal the dataFinal to set
	 */
	public void setDataFinal(LocalDateTime dataFinal) {
		this.dataFinal = dataFinal;
	}

	/**
	 * @return the usario
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * @param usario the usario to set
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
