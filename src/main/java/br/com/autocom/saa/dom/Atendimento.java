package br.com.autocom.saa.dom;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.autocom.saa.dom.enums.Status;
import br.com.sw3.persistence.converter.LocalDateTimeConverter;
import br.com.sw3.persistence.entity.Entidade;

/**
 * Entity implementation class for Entity: Atendimento
 *
 */
@Entity
@Table(name = "atendimento")
@SequenceGenerator(name = "atendimento_seq", sequenceName = "atendimento_seq", initialValue = 1, allocationSize = 1)
public class Atendimento implements Entidade<Long> {

	private static final long serialVersionUID = 8244896298370533850L;
	
	@Id
	@Column(name = "atendimento_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "atendimento_seq")
	private Long id;
	
	@ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name="atendimento_usuario", joinColumns={@JoinColumn(name="atendiento_id")}, 
    inverseJoinColumns={@JoinColumn(name="usuario_id")})
	private List<Usuario> usuarios = new ArrayList<Usuario>();

	@ManyToOne
	private Cliente cliente;
	
	@ManyToOne
	private Usuario usuarioSolucao;
	
	@ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name="atendimento_assunto", joinColumns={@JoinColumn(name="atendiento_id")}, 
    inverseJoinColumns={@JoinColumn(name="assunto_id")})
	private List<Assunto> assuntos;
	
	@Column(name = "problema" , length = 500)
	private String problema;
	
	@Column(name = "contato" , length = 100)
	private String contato;
	
	@Column(name = "solucao" , length = 500)
	private String solucao;
	
	@Enumerated(EnumType.STRING)
	private Status status;
	
	@Column( name = "data_inicio" , nullable=false)
	@Convert(converter = LocalDateTimeConverter.class)
	private LocalDateTime dataInicio;

	@Column( name = "data_fim" )
	@Convert(converter = LocalDateTimeConverter.class)
	private LocalDateTime dataFim;
	
	@Column(name = "favorito" )
	private boolean favorito;

	/**
	 * @see arq.persistence.entity.Entidade#getId()
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 
	 * @return the usuarios
	 */
	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	/**
	 * 
	 * @param usuarios the usuarios to set
	 */
	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	/**
	 * 
	 * @return the Cliente
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
	 * @return the Assunto
	 */
	public List<Assunto> getAssuntos() {
		return assuntos;
	}

	/**
	 * 
	 * @param assunto the assunto to set
	 */
	public void setAssuntos(List<Assunto> assuntos) {
		this.assuntos = assuntos;
	}

	/**
	 * 
	 * @return the Solução
	 */
	public String getSolucao() {
		return solucao;
	}

	/**
	 * 
	 * @param solução the solução to set
	 */
	public void setSolucao(String solucao) {
		this.solucao = solucao;
	}

	/**
	 * 
	 * @return the Contato
	 */
	public String getContato() {
		return contato;
	}

	/**
	 * 
	 * @param contato the contato to set
	 */
	public void setContato(String contato) {
		this.contato = contato;
	}

	/**
	 * 
	 * @return the Status
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
	 * 
	 * @return the Favorito
	 */
	public boolean isFavorito() {
		return favorito;
	}
	
	/**
	 * 
	 * @param favorito the favorito to set
	 */
	public void setFavorito(boolean favorito) {
		this.favorito = favorito;
	}

	/**
	 * 
	 * @param problema the problema to set
	 */
	public String getProblema() {
		return problema;
	}

	/**
	 * 
	 * @param problema the problema to set
	 */
	public void setProblema(String problema) {
		this.problema = problema;
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

	
}
