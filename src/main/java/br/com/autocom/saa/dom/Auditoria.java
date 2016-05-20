package br.com.autocom.saa.dom;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.autocom.saa.dom.enums.Acao;
import br.com.autocom.saa.dom.enums.Tabela;
import br.com.sw3.persistence.converter.LocalDateTimeConverter;
import br.com.sw3.persistence.entity.Entidade;

/**
 * Classe que representa auditoria do Sistema.
 * 
 * @author Denilson Godinho / Samuel Oliveira
 *
 */

@Entity
@Table(name = "auditoria")
@SequenceGenerator(name = "auditoria_seq", sequenceName = "auditoria_seq", initialValue = 1, allocationSize = 1)
public class Auditoria implements Entidade<Long> {

	private static final long serialVersionUID = 1183804671908478150L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "auditoria_seq")
	@Column(name = "auditoria_id")
	private Long id;
	
	@Column(name="entidade_id")
    private Long entidade_id;
	
	@Column(name="tabela")
	@Enumerated(EnumType.STRING)
	private Tabela tabela;
	
	@Enumerated(EnumType.STRING)
	private Acao acao;
	
	@Column(name = "data")
	@Convert(converter = LocalDateTimeConverter.class)
	private LocalDateTime data;
	
	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;

	/**
	 * @return the id
	 */
	public Long getId() {
		return this.id;
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
	 * @return the acao
	 */
	public Acao getAcao() {
		return acao;
	}

	/**
	 * 
	 * @param acao the acao to set
	 */
	public void setAcao(Acao acao) {
		this.acao = acao;
	}

	/**
	 * 
	 * @return the data
	 */
	public LocalDateTime getData() {
		return data;
	}

	/**
	 * 
	 * @param data the data to set
	 */
	public void setData(LocalDateTime data) {
		this.data = data;
	}

	/**
	 * 
	 * @return the usuario
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * 
	 * @param usuario the usuario to set
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the entidade_id
	 */
	public Long getEntidade_id() {
		return entidade_id;
	}

	/**
	 * @param entidade_id the entidade_id to set
	 */
	public void setEntidade_id(Long entidade_id) {
		this.entidade_id = entidade_id;
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

}
