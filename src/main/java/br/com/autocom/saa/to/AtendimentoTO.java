package br.com.autocom.saa.to;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.com.autocom.saa.dom.Assunto;
import br.com.autocom.saa.dom.Atendimento;
import br.com.autocom.saa.dom.Cliente;
import br.com.autocom.saa.dom.Usuario;
import br.com.autocom.saa.dom.enums.Status;

/**
 * Entity implementation class for Entity: AtendimentoTO
 *
 */

public class AtendimentoTO {
	
	/**
	 * 
	 */
	public AtendimentoTO(Atendimento atendimento) {

		this.setAssuntos(atendimento.getAssuntos());
		this.setCliente(atendimento.getCliente());
		this.setContato(atendimento.getContato());
		this.setDataFim(atendimento.getDataFim());
		this.setDataInicio(atendimento.getDataInicio());
		this.setId(atendimento.getId());
		this.setFavorito(atendimento.isFavorito());
		this.setProblema(atendimento.getProblema());
		this.setSolucao(atendimento.getSolucao());
		this.setStatus(atendimento.getStatus());
		this.setUsuarios(atendimento.getUsuarios());
		this.setUsuarioSolucao(atendimento.getUsuarioSolucao());
	}
	
	private Long id;
	
	private List<Usuario> usuarios = new ArrayList<Usuario>();

	private Cliente cliente;

	private Usuario usuarioSolucao;
	
	private List<Assunto> assuntos;
	
	private String problema;

	private String contato;
	
	private String solucao;
	
	private Status status;
	
	private LocalDateTime dataInicio;

	private LocalDateTime dataFim;
	
	private String tempo; 
	
	private boolean favorito;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the usuarios
	 */
	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	/**
	 * @param usuarios the usuarios to set
	 */
	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	/**
	 * @return the cliente
	 */
	public Cliente getCliente() {
		return cliente;
	}

	/**
	 * @param cliente the cliente to set
	 */
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	/**
	 * @return the usuarioSolucao
	 */
	public Usuario getUsuarioSolucao() {
		return usuarioSolucao;
	}

	/**
	 * @param usuarioSolucao the usuarioSolucao to set
	 */
	public void setUsuarioSolucao(Usuario usuarioSolucao) {
		this.usuarioSolucao = usuarioSolucao;
	}

	/**
	 * @return the assuntos
	 */
	public List<Assunto> getAssuntos() {
		return assuntos;
	}

	/**
	 * @param assuntos the assuntos to set
	 */
	public void setAssuntos(List<Assunto> assuntos) {
		this.assuntos = assuntos;
	}

	/**
	 * @return the problema
	 */
	public String getProblema() {
		return problema;
	}

	/**
	 * @param problema the problema to set
	 */
	public void setProblema(String problema) {
		this.problema = problema;
	}

	/**
	 * @return the contato
	 */
	public String getContato() {
		return contato;
	}

	/**
	 * @param contato the contato to set
	 */
	public void setContato(String contato) {
		this.contato = contato;
	}

	/**
	 * @return the solucao
	 */
	public String getSolucao() {
		return solucao;
	}

	/**
	 * @param solucao the solucao to set
	 */
	public void setSolucao(String solucao) {
		this.solucao = solucao;
	}

	/**
	 * @return the status
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	/**
	 * @return the dataInicio
	 */
	public LocalDateTime getDataInicio() {
		return dataInicio;
	}

	/**
	 * @param dataInicio the dataInicio to set
	 */
	public void setDataInicio(LocalDateTime dataInicio) {
		this.dataInicio = dataInicio;
	}

	/**
	 * @return the dataFim
	 */
	public LocalDateTime getDataFim() {
		return dataFim;
	}

	/**
	 * @param dataFim the dataFim to set
	 */
	public void setDataFim(LocalDateTime dataFim) {
		this.dataFim = dataFim;
	}

	/**
	 * @return the tempo
	 */
	public String getTempo() {
		return tempo;
	}

	/**
	 * @param tempo the tempo to set
	 */
	public void setTempo(String tempo) {
		this.tempo = tempo;
	}

	/**
	 * @return the favorito
	 */
	public boolean isFavorito() {
		return favorito;
	}

	/**
	 * @param favorito the favorito to set
	 */
	public void setFavorito(boolean favorito) {
		this.favorito = favorito;
	}

}
