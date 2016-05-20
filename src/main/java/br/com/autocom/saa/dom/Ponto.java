package br.com.autocom.saa.dom;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.sw3.persistence.converter.LocalDateTimeConverter;
import br.com.sw3.persistence.entity.Entidade;

/**
 * Classe que representa Ponto do Sistema.
 * 
 * @author Samuel Oliveira
 *
 */

@Entity
@Table(name = "ponto")
@SequenceGenerator(name = "ponto_seq", sequenceName = "ponto_seq", initialValue = 1, allocationSize = 1)
public class Ponto implements Entidade<Long> {
	
	private static final long serialVersionUID = -8530657927613784819L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ponto_seq")
	@Column(name = "ponto_id")
	private Long id;
	
	@Column(name = "data")
	@Convert(converter = LocalDateTimeConverter.class)
	private LocalDateTime data;
	
	@Column(name = "entradaDia")
	@Convert(converter = LocalDateTimeConverter.class)
	private LocalDateTime entradaDia;
	
	@Column(name = "saidaDia")
	@Convert(converter = LocalDateTimeConverter.class)
	private LocalDateTime saidaDia;
	
	@Column(name = "entradaAlmoco")
	@Convert(converter = LocalDateTimeConverter.class)
	private LocalDateTime entradaAlmoco;
	
	@Column(name = "saidaAlmoco")
	@Convert(converter = LocalDateTimeConverter.class)
	private LocalDateTime saidaAlmoco;
	
	@Column(name = "minutosTrabalhados")
	private long minutosTrabalhados;
	
	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;
	
	@Transient
	private LocalDateTime dataAtual;
	
	@Transient
	private String tempoTrabalhado;

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
	 * @return the entradaDia
	 */
	public LocalDateTime getEntradaDia() {
		return entradaDia;
	}

	/**
	 * @param entradaDia the entradaDia to set
	 */
	public void setEntradaDia(LocalDateTime entradaDia) {
		this.entradaDia = entradaDia;
	}

	/**
	 * @return the saidaDia
	 */
	public LocalDateTime getSaidaDia() {
		return saidaDia;
	}

	/**
	 * @param saidaDia the saidaDia to set
	 */
	public void setSaidaDia(LocalDateTime saidaDia) {
		this.saidaDia = saidaDia;
	}

	/**
	 * @return the entradaAlmoco
	 */
	public LocalDateTime getEntradaAlmoco() {
		return entradaAlmoco;
	}

	/**
	 * @param entradaAlmoco the entradaAlmoco to set
	 */
	public void setEntradaAlmoco(LocalDateTime entradaAlmoco) {
		this.entradaAlmoco = entradaAlmoco;
	}

	/**
	 * @return the saidaAlmoco
	 */
	public LocalDateTime getSaidaAlmoco() {
		return saidaAlmoco;
	}

	/**
	 * @param saidaAlmoco the saidaAlmoco to set
	 */
	public void setSaidaAlmoco(LocalDateTime saidaAlmoco) {
		this.saidaAlmoco = saidaAlmoco;
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
	 * @return the data
	 */
	public LocalDateTime getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(LocalDateTime data) {
		this.data = data;
	}

	/**
	 * @return the dataAtual
	 */
	public LocalDateTime getDataAtual() {
		return dataAtual;
	}

	/**
	 * @param dataAtual the dataAtual to set
	 */
	public void setDataAtual(LocalDateTime dataAtual) {
		this.dataAtual = dataAtual;
	}

	/**
	 * @return the minutosTrabalhados
	 */
	public long getMinutosTrabalhados() {
		return minutosTrabalhados;
	}

	/**
	 * @param minutosTrabalhados the minutosTrabalhados to set
	 */
	public void setMinutosTrabalhados(long minutosTrabalhados) {
		this.minutosTrabalhados = minutosTrabalhados;
	}

	/**
	 * @return the tempoTrabalhado
	 */
	public String getTempoTrabalhado() {
		return tempoTrabalhado;
	}

	/**
	 * @param tempoTrabalhado the tempoTrabalhado to set
	 */
	public void setTempoTrabalhado(String tempoTrabalhado) {
		this.tempoTrabalhado = tempoTrabalhado;
	}

}
