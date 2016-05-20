package br.com.autocom.saa.dom.filtro;

import java.time.LocalDateTime;

import br.com.autocom.saa.dom.Assunto;
import br.com.autocom.saa.dom.Cliente;
import br.com.autocom.saa.dom.Usuario;
import br.com.autocom.saa.dom.enums.Categoria;
import br.com.autocom.saa.dom.enums.Status;

/**
 * Classe de filtro responsavel por manter os filtros para busca de Atendimento.
 * 
 * @author Denilson Godinho
 *
 */
public class AtendimentoFiltro {

	private LocalDateTime dataInicial;
	
	private LocalDateTime dataFinal;
	
	private Usuario usuario;
	
	private Assunto assunto;
	
	private Cliente cliente;
	
	private Categoria categoria;
	
	private Status status;
	
	private String favorito;
	
	private String login;

	
	/**
	 * 
	 * @return the dataInicial
	 */
	public LocalDateTime getDataInicial() {
		return dataInicial;
	}

	/**
	 * 
	 * @param dataInicial the dataInicial to set
	 */
	public void setDataInicial(LocalDateTime dataInicial) {
		this.dataInicial = dataInicial;
	}

	/**
	 * 
	 * @return the dataFinal
	 */
	public LocalDateTime getDataFinal() {
		return dataFinal;
	}

	/**
	 * 
	 * @param dataFinal the dataFinal to set
	 */
	public void setDataFinal(LocalDateTime dataFinal) {
		this.dataFinal = dataFinal;
	}

	

	/**
	 * @return the usuario
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the assunto
	 */
	public Assunto getAssunto() {
		return assunto;
	}

	/**
	 * @param assunto the assunto to set
	 */
	public void setAssunto(Assunto assunto) {
		this.assunto = assunto;
	}

	/**
	 * 
	 * @return the cliente
	 */
	public Cliente getCliente() {
		return cliente;
	}

	/**
	 * 
	 * @param cliente the cliente to set
	 */
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	/**
	 * 
	 * @return the categoria
	 */
	public Categoria getCategoria() {
		return categoria;
	}

	/**
	 * 
	 * @param categoria the categoria to set
	 */
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	/**
	 * 
	 * @return the status
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * 
	 * @param status the status to set
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	/**
	 * @return the favorito
	 */
	public String getFavorito() {
		return favorito;
	}

	/**
	 * @param favorito the favorito to set
	 */
	public void setFavorito(String favorito) {
		this.favorito = favorito;
	}

	/**
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * @param login the login to set
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	
	
}
