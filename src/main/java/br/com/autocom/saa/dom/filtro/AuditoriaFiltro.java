/**
 * 
 */
package br.com.autocom.saa.dom.filtro;

import java.time.LocalDateTime;

import br.com.autocom.saa.dom.Usuario;
import br.com.autocom.saa.dom.enums.Acao;
import br.com.autocom.saa.dom.enums.Tabela;

/**
 * Classe de filtro responsavel por manter os filtros para busca da Auditoria.
 * 
 * @author Samuel Oliveira
 *
 */
public class AuditoriaFiltro {
	
	
	private LocalDateTime dataInicial;
	
	private String registros;
	
	private LocalDateTime dataFinal;
	
	private Acao acao;
	
	private Usuario usuario;
	
	private Tabela tabela;

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
	 * @return the acao
	 */
	public Acao getAcao() {
		return acao;
	}

	/**
	 * @param acao the acao to set
	 */
	public void setAcao(Acao acao) {
		this.acao = acao;
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

	/**
	 * @return the tabela
	 */
	public Tabela getTabela() {
		return tabela;
	}

	/**
	 * @param tabela the tabela to set
	 */
	public void setTabela(Tabela tabela) {
		this.tabela = tabela;
	}

	/**
	 * @return the registros
	 */
	public String getRegistros() {
		return registros;
	}

	/**
	 * @param registros the registros to set
	 */
	public void setRegistros(String registros) {
		this.registros = registros;
	}

	
}
